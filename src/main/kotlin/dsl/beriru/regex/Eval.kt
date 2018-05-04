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

    /**
     * flatten AST, specially made for `Sequential` and `Alternative` to reduce ast depth
     */
    abstract fun reduce(): Regexp
}

object Fail : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = false
    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

object Pass : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean) = true
    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


data class Sequential(val left: Regexp, val right: Regexp) : Regexp() {
    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented")
    }
}

data class Alternative(val left: Regexp, val right: Regexp) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


data class Exactly(val char: Char) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce() = this
}


data class Lazy(val regexp: Regexp, val min: Int = 0, val max: Int = Int.MAX_VALUE) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


data class Greedy(val regexp: Regexp, val min: Int = 0, val max: Int = Int.MAX_VALUE) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


object Word : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce() = this
}


// match any digital
// shorthand -> \d
object Digital : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce() = this
}

object Blank : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

// match anything except line-break
object Any : Regexp() {
    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


