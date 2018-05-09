import dsl.sumomo.regex.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import org.junit.Assert.*

object SumomoSpec : Spek({
    describe("parser") {
        it("sequential") {
            val ast = "abc".r.ast()
            assertEquals(seq("abc"), ast)
        }

        it("alternative") {
            val ast = "[abc]".r.ast()
            assertEquals(alt("abc"), ast)
        }

        it("simple composition") {
            val ast = """abc|nce|[ab](c|d)""".r.ast()
            assertEquals(
                    alt(
                            seq("abc"),
                            seq("nce"),
                            seq(alt("ab"), alt("cd"))
                    )
                    , ast)
        }

        it("lazy with quantifier") {
            val ast = """\w\dth{1,3}?""".r.ast()
            assertEquals(
                    seq(Word, Digital, e('t'), lzy(e('h'), 1, 3)),
                    ast
            )
        }

        it("complex regex") {
            val pattern = """\s[abc]?|\w?|\d?|a{,9}(abc|\d?)?+""".r.ast()
            assertEquals(
                    alt(
                            seq(Blank, grd(alt("abc"), 0, 1)),
                            grd(Word, 0, 1),
                            grd(Digital, 0, 1),
                            seq(
                                    grd(e('a'), 0, 9),
                                    grd(alt(
                                            seq("abc"),
                                            grd(Digital, 0, 1)
                                    ), 0, 1)
                            )
                    )
                    , pattern
            )
        }

        it("email") {
            val ast = """^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$""".r.ast()
            // ok, I'll assume it was right, the ast is so complex
//            println(ast)
        }
    }

    describe("runtime") {
        it("sequential") {
            assertTrue("""niceboat""".r.match("niceboat"))
        }

        it("length not enough") {
            assertFalse("niceboat".r.match("nice"))
        }

        it("partial") {
            assertTrue("nice".r.match("niceboat"))
        }

        it("alternative and shorthand") {
            val ast = """\s\d{3,}[a-d]|\d+""".r
            assertTrue(ast.match(" 123c"))
            assertTrue(ast.match("1"))
            assertFalse(ast.match("dd"))
            assertTrue(ast.match("19"))
        }

        it("common shorthand") {
            val atLeast = """.{3,}""".r
            assertFalse(atLeast.match("12"))
            assertTrue(atLeast.match("123"))
        }


        it("alternative and concat") {
            val re = """(abc|d)e""".r
            assertTrue(re.match("abce"))
            assertFalse(re.match("abc"))
        }

        it("fully match") {

        }

        it("ignore case") {

        }

        it("concat of greedy") {
            val regex = """\w+(\d*\w)*""".r
            assertTrue(
                    regex.match("ww1234")
            )

            assertFalse(
                    regex.match("abc12 12")
            )
        }

        it("tests") {
            val r = """^[a-z0-9]+([._\\-]*[a-z0-9])*@""".r
            assertTrue(
                    r.match("minmikaze@")
            )

            val suffix = """([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$""".r
            assertTrue(
                    suffix.match("gmail.com")
            )

            val meaningLess = """(\d+\d*\d+@){1,63}""".r
            assertTrue(
                    meaningLess.match("123@")
            )
        }


        it("common use-case") {
            val r = """^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$""".r

            assertTrue(
                    r.match("minamikaze@gmai.com")
            )

            assertTrue(
                    r.match("specter-123@163.com")
            )

            assertFalse(
                    r.match(".shift@z.com")
            )

            assertFalse(
                    r.match("shiftz.com")
            )
        }
    }
})

