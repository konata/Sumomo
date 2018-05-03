package dsl.beriru.regex

import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.parser.Parser


object Parser : Grammar<Regexp>() {


    // character classes
    private val lbkt by token("""\[""")
    private val rbkt by token("]")
    private val caret by token("""\^""")
    private val hyphen by token("-")
    private val slash by token("""\\""")

    // characters
    private val dollar by token("""\$""")
    private val period by token("""\.""")
    private val pipe by token("""\|""")
    private val questionMark by token("""\?""")
    private val asterisk by token("""\*""")
    private val plus by token("""\+""")
    private val lpar by token("""\(""")
    private val rpar by token("""\)""")

    // shorthand
    private val word by token("""\\w""")
    private val digit by token("""\\d""")
    private val space by token("""\\s""")


    override val rootParser: Parser<Regexp> = TODO()
}
