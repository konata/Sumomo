package dsl.beriru.regex

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.parser.Parser
import dsl.beriru.regex.Parser.Quantifier.*


object Parser : Grammar<Regexp>() {

    enum class Quantifier {
        Greedy,
        Laziness,
        Possessive
    }

    // intersect to characters and character class
//    private val literal = """[^(\[\]){}*+?|^$\d\-]""".toRegex()

    // characters
    private val dollar by token("""\$""")
    private val period by token("""\.""")

    // TODO pipe should not be in chars
    private val pipe by token("""\|""")

    private val lpar by token("""\(""")
    private val rpar by token("""\)""")
    private val `-chars` by token("""[^(\[){}*+?\d|^$]""")

    // character classes
    private val lbkt by token("""\[""")
    private val rbkt by token("]")

    // not-support-yet
    private val caret by token("""\^""")

    private val hyphen by token("-")
    private val `-classes` by token("""[^\[\]\d^\-]""")

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
    private val escape by token("""\.""")

    // meta token
    private val number by token("""\d""")

    private val literal by token("""[^(\[\]){}*+?|^$\d\-]""")

    private val numbers by oneOrMore(number).map {
        it.joinToString("") {
            it.text
        }.toInt()
    }


    // `{9}` `{9,}` `{,9}` `{1,9}` `*`  `+`  `?`
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
    private val character by (dollar or period or hyphen or
            word or digit or space or
            number or escape
            )

    // anything except () [] {} - ^
    private val `exclude-hyphen` by (number.map { Exactly(it.text[0]) } or
            word.map {
                Word
            } or
            digit.map {
                Digital
            } or
            space.map {
                Blank
            } or
            escape.map {
                Exactly(it.text[1])
            } or
            literal.map {
                Exactly(it.text[0])
            })

    // chars inside square bracket
    // p.s caret not-support-yet
    private val klass by `exclude-hyphen` or hyphen.map { '-' }

    private val dashRange by ((number or literal).map { it.text[0] } * -hyphen * (number or literal).map { it.text[0] }) map { (start, end) ->
        (start..end).reversed().fold(fail) { acc: Regexp, ele ->
            Alternative(Exactly(ele), acc)
        }
    }

    // character class [ab\dc-z]
    private val choice by (-lbkt * oneOrMore(dashRange or `exclude-hyphen`) * -rbkt) map {
        it.reversed().fold(fail) { acc: Regexp, ele ->
            Alternative(ele, acc)
        }
    }

    override val rootParser: Parser<Regexp> = TODO()
}
