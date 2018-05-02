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


class Sequential(val left: Regexp, val right: Regexp) : Regexp() {
    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented")
    }
}

class Alternative(val left: Regexp, val right: Regexp) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce(): Regexp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}


class Exactly(char: Char) : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce() = this
}

class Parenthesis : Regexp() {
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


object Digital : Regexp() {
    override fun match(target: String, i: Int, cont: (String, Int) -> Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduce() = this
}





