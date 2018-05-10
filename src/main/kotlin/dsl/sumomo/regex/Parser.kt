package dsl.sumomo.regex

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.*
import com.github.h0tk3y.betterParse.lexer.TokenMatch
import com.github.h0tk3y.betterParse.parser.*
import com.github.h0tk3y.betterParse.parser.Parser
import dsl.sumomo.regex.Parser.Quantifier.*


object Parser : Grammar<Regexp>() {

    // quantifier flags
    enum class Quantifier {
        Greedy,
        Laziness,
        Possessive
    }

    // characters
    private val dollar by token("""\$""")
    private val period by token("""\.""")
    private val pipe by token("""\|""")
    private val lpar by token("""\(""")
    private val rpar by token("""\)""")
    // character classes
    private val lbkt by token("""\[""")
    private val rbkt by token("]")
    // not-support-yet
    private val caret by token("""\^""")

    private val hyphen by token("-")
    // shorthand
    private val word by token("""\\w""")
    private val digit by token("""\\d""")
    private val space by token("""\\s""")
    // quantifier (lazy greedy possessive[not-support-yet])
    private val lcur by token("""\{""")
    private val rcur by token("""}""")
    private val comma by token(",")
    private val questionMark by token("""\?""")
    private val asterisk by token("""\*""")
    private val plus by token("""\+""")
    // escape chars
    private val escape by token("""\\.""")
    // meta token
    private val number by token("""\d""")
    private val literal by token("""[^(\[\]){}*+?|^$\d\-]""")
    private val eof by token("""\Z""")


    private val numbers by oneOrMore(number).map {
        it.joinToString("") {
            it.text
        }.toInt()
    }

    // `{9}` `{9,}` `{,9}` `{1,9}` `*`  `+`  `?`
    // range for quantifier parts
    private val range by (-lcur * numbers * -comma * numbers * -rcur).map { (min, max) ->
        min..max
    } or (-lcur * numbers * -rcur).map {
        (it - 1) until it
    } or (-lcur * -comma * numbers * -rcur).map {
        0..it
    } or (-lcur * numbers * -comma * -rcur).map {
        it..Int.MAX_VALUE
    } or asterisk.map {
        0..Int.MAX_VALUE
    } or plus.map {
        1..Int.MAX_VALUE
    } or questionMark.map {
        0..1
    }

    // i.e `{9,10}` `{9,10}?` `{1,2}+`
    // default -> greedy, ? -> lazy, + -> possessive
    private val factor by (range * (optional(questionMark.map {
        Laziness
    } or plus.map {
        Possessive
    })).map {
        it?.let { it } ?: Greedy
    }).map { (quantity, flag) ->
        quantity to flag
    }

    // chars outside square bracket
    private val character by period.map {
        Any
    } or hyphen.map {
        Exactly(it.text[0])
    } or word.map {
        Word
    } or digit.map {
        Digital
    } or space.map {
        Blank
    } or number.map {
        Exactly(it.text[0])
    } or escape.map {
        Exactly(it.text[1])
    } or literal.map {
        Exactly(it.text[0])
    }

    // anything except `(` `)` `[` `]` `{` `}` `-` `^`
    // used in character classes as literal value
    private
    val isolation by (number.map {
        Exactly(it.text[0])
    } or word.map {
        Word
    } or digit.map {
        Digital
    } or space.map {
        Blank
    } or hyphen.map {
        Exactly(it.text[0])
    } or escape.map {
        Exactly(it.text[1])
    } or period.map {
        Exactly(it.text[0])
    } or literal.map {
        Exactly(it.text[0])
    })

    // `a-b`
    // range grammar in choice
    private
    val dashRange by ((number or literal).map { it.text[0] } * -hyphen * (number or literal).map { it.text[0] }) map { (start, end) ->
        Range(start, end)
    }

    // `[ab\dc-z]`
    // character class
    private
    val choice by (-lbkt * oneOrMore(dashRange or isolation) * -rbkt) map {
        when {
            it.size == 1 -> it[0]
            else -> it.reversed().fold(Fail) { acc: Regexp, ele ->
                Alternative(ele, acc)
            }
        }
    }

    // `(abc[a-b])` `(a|b)`
    // grouping value
    private
    val parenthesis: Parser<Regexp> by -lpar * parser(this::term) * -rpar

    // `abc\w{1,3}.[ab\wc-z](abc|123){3,4}?`
    // sequential term
    private
    val sequential: Parser<Regexp> by (-optional(caret) * oneOrMore((character or
            choice or parenthesis) * optional(factor))
            ) map {

        when (it.size) {
            1 -> it[0].let { (term, quantifier) ->
                quantifier?.let { (range, quantifier) ->
                    when (quantifier) {
                        Greedy, Possessive -> Greedy(term, range.first, range.last)
                        Laziness -> Lazy(term, range.first, range.last)
                    }
                } ?: term
            }
            else -> it.reversed().fold(Pass) { acc: Regexp, (term, quantifier) ->
                Sequential(
                        quantifier?.let { (range, quantifier) ->
                            when (quantifier) {
                                Greedy, Possessive -> Greedy(term, range.first, range.last)
                                Laziness -> Lazy(term, range.first, range.last)
                            }
                        } ?: term
                        ,
                        acc
                )
            }
        }
    }

    // `abc|123|seq`
    // alternative terms
    private
    val alternative: Parser<Regexp> by separated(sequential, pipe).map {
        when (it.terms.size) {
            1 -> it.terms[0]
            else -> it.terms.reversed().fold(Fail) { acc: Regexp, ele ->
                Alternative(ele, acc)
            }
        }
    }

    private
    val term by -optional(caret) * (alternative or sequential) * -optional(dollar)

    override
    val rootParser by term

    fun parse(input: String) = (rootParser * -eof).parseToEnd(tokenizer.tokenize(input) + TokenMatch(eof, "", 0, 0, 0))
}
