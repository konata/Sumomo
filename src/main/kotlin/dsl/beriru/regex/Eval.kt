package dsl.beriru.regex


sealed class Regexp {
    companion object {
        val digit = '0'..'9'
        val alphabetic = ('a'..'z') + ('A'..'Z')
    }

    /**
     *  CPS style matcher, match the target with current regex component,
     *  pass changed position and dest to the remaining
     */
    abstract fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean
}

// object always fail
object Fail : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = false
}

// object always success
object Pass : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = true
}

// basic sequential builder like `abc`
data class Sequential(val left: Regexp, val right: Regexp) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented")
    }
}

// compose range like `[a-z]`
data class Range(val start: Char, val end: Char) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// alternative sign [abc] or a|b
data class Alternative(val left: Regexp, val right: Regexp) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// exactly some char literal
data class Exactly(val char: Char) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// quantity followed by `?`
data class Lazy(val regexp: Regexp, val min: Int = 0, val max: Int = Int.MAX_VALUE) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// default quantity greedy,
// currently possessive is also considered as greedy
data class Greedy(val regexp: Regexp, val min: Int = 0, val max: Int = Int.MAX_VALUE) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// match any word
// shorthand -> \w
object Word : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// match any digital
// shorthand -> \d
object Digital : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// match any blank
// shorthand -> \s
object Blank : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// match anything except line-break
// shorthand -> .
object Any : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

