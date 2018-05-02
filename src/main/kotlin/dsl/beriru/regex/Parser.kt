package dsl.beriru.regex

import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.parser.Parser


object Parser : Grammar<Regexp>() {
    private val number by token("""\d""")
    private val dash by token("""-""")

    private val lcur by token("""\{""")
    private val rcur by token("""}""")

    private val lbkt by token("""\[""")
    private val rbkt by token("""]""")

    private val lpar by token("""\(""")
    private val rpar by token("""\)""")

    private val alt by token("""\|""")


    override val rootParser: Parser<Regexp> = TODO()
}
