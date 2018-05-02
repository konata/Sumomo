import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*

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
})

