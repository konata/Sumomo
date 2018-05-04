import dsl.beriru.regex.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

object ParserSpec : Spek({
    describe("sequential parser") {
        it("should parse") {
            val pattern = """\w\dth{1,3}?"""
            assertEquals(
                    seq(Word, Digital, e('t'), lzy(e('h'), 1, 3)),
                    pattern.r.ast()
            )
        }
    }


    describe("alternative parser") {
        it("should parser") {
//            val pattern = """\s[abc]?|\w?|\d?|a{,9}(abc|\d?)?+ """
//            val pattern = """\w|\d|def"""
            val pattern = """a|b""".r
            println(pattern.token())
            assertEquals(
                    alt(Word,Digital, seq("def")),
                    pattern.ast()
            )

        }
    }


    describe("meta test") {
        it("should pass") {
            assertTrue(true)

        }
    }



    describe("parsing random grammar") {
        it("should parser random grammar") {
            val rex = """^[\w\da-b12]+@[\w0-2_-]+(\\.[ae\s_-]+)+$""".r
//            val rex = """[1-3\dab-]+@""".r
//            val rex = """(\w)+(\.\w+)*@(\w)+((\.\w+)+)""".r
            println(rex.token())
            println(rex.ast())
        }
    }


})

