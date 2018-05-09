Sumomo
=========
**Sumomo** is a [regular-expression](https://en.wikipedia.org/wiki/Regular_expression) parsing and evaluation library implemented in pure [kotlin](http://kotlinlang.org/)

Parser
===========
Sumomo take advantage of [better-parse](https://github.com/h0tk3y/better-parse/issues) to parse the regular expression syntax into ASTs
some nice extension methods are added to `String` class to generate Sumomo instance,
```kotlin
val mobile = """((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}""".r
val phone = """([0-9]|[-])+""".rg
val animals = """b(at|ear|ull|ee)""".rgi
val directions = """north|south|east|west""".ri
```

use `Sumomo#ast()` method to dump ASTs
currently Sumomo ASTs include
1. Sequential
2. Alternative
3. Range
4. Exactly
5. Lazy
6. Greedy

and some shorthand
1. Word
2. Digital
3. Blank
4. Any
5. Pass
6. Fail




Runtime
===========

use `Sumomo#match(subject : String)` to match target string

Options
===========






