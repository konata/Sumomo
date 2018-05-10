package dsl.sumomo.regex


sealed class Regexp {
    companion object {
        private val alphabetic = ('a'..'z') + ('A'..'Z')
        private const val underscore = '_'
        val digit = '0'..'9'
        val word = digit + alphabetic + digit + underscore
    }

    open fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = target.length > i
}

// object always fail
object Fail : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = false
}

// object always success
object Pass : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = cont(target, i)
}

// basic sequential builder like `abc`
data class Sequential(private val left: Regexp, private val right: Regexp) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = super.match(target, i, cont) &&
            left.match(target, i) { rest, pos ->
                right.match(rest, pos, cont)
            }
}

// compose range like `[a-z]`
data class Range(private val start: Char, private val end: Char) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = super.match(target, i, cont) &&
            target[i] in start..end && cont(target, i + 1)
}

// alternative sign `[abc]` or `a|b`
data class Alternative(private val left: Regexp, private val right: Regexp) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = super.match(target, i, cont) &&
            (left.match(target, i, cont) || right.match(target, i, cont))
}

// exactly some char literal
data class Exactly(private val char: Char) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = super.match(target, i, cont) &&
            char == target[i] && cont(target, i + 1)
}

// quantity followed by `?`
data class Lazy(private val regexp: Regexp, private val min: Int = 0, private val max: Int = Int.MAX_VALUE) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean = when {
        max < 0 || min > max -> false
        min > 0 -> regexp.match(target, i) { rest, pos ->
            Lazy(regexp, min - 1, max - 1).match(rest, pos, cont)
        }
        min <= 0 -> cont(target, i) || regexp.match(target, i) { rest, pos ->
            Lazy(regexp, min - 1, max - 1).match(rest, pos, cont)
        }
        else -> false
    }
}

// default quantity greedy,
// currently possessive is also considered as greedy
data class Greedy(private val regexp: Regexp, private val min: Int = 0, private val max: Int = Int.MAX_VALUE) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean = when {
        min > max || max < 0 -> false
        min > 0 -> regexp.match(target, i) { rest, pos ->
            Greedy(regexp, min - 1, max - 1).match(rest, pos, cont)
        }
        min <= 0 -> regexp.match(target, i) { rest, pos ->
            Greedy(regexp, min - 1, max - 1).match(rest, pos, cont)
        } || cont(target, i)
        else -> false
    }
}

// match any word
// shorthand -> `\w`
object Word : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = super.match(target, i, cont) &&
            target[i] in word && cont(target, i + 1)
}

// match any digital
// shorthand -> `\d`
object Digital : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = super.match(target, i, cont) &&
            target[i] in digit && cont(target, i + 1)
}

// match any blank
// shorthand -> `\s`
object Blank : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = super.match(target, i, cont) &&
            target[i].toString().isBlank() && cont(target, i + 1)
}

// match anything except line-break
// shorthand -> `.`
object Any : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = super.match(target, i, cont) &&
            cont(target, i + 1)
}

