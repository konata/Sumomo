import dsl.sumomo.regex.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*
import org.junit.Assert.assertEquals

object SumomoSpec : Spek({
    describe("should generate reduced ast") {
        it("sequantial") {
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
    }


    describe("quantifier and parenthesis") {
        it("should parse lazy with quantifuer") {
            val ast = """\w\dth{1,3}?""".r.ast()
            assertEquals(
                    seq(Word, Digital, e('t'), lzy(e('h'), 1, 3)),
                    ast
            )
        }

        it("should parser") {
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
    }


    describe("common use case") {
        it("should parse email ") {
            val ast = """^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$""".r.ast()
            println(ast)
        }
    }


})

