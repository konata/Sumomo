import dsl.beriru.regex.r
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import org.junit.Assert.assertTrue

object ParserSpec : Spek({
    describe("sequential parser") {
        it("should parse") {
            // in-brk meta-chars
            // `-`
            // outside meta-chars
            // `. | ( ) { } * + ?`
            // both
            // `\w \d \s \S [ ] etc`
        }
    }


    describe("alternative parser") {
        it("should parser") {

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

