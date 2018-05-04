package dsl.beriru.regex

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.lexer.DefaultTokenizer

class Rex(private val pattern: String) {
    fun token() = DefaultTokenizer(Parser.tokens).tokenize(pattern).joinToString()
    fun ast() = Parser.parseToEnd(pattern)
    fun match(subject: String) = ast().match(subject, 0) { _, _ ->
        true
    }
}

val String.r
    get() = Rex(this)
