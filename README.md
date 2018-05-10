Sumomo
======
**Sumomo** is a [regular-expression](https://en.wikipedia.org/wiki/Regular_expression) parsing and evaluating library implemented in pure [kotlin](http://kotlinlang.org/)

Parser
=============
Sumomo takes advantage of [better-parse](https://github.com/h0tk3y/better-parse/issues) to parse the regular expression syntax into ASTs

some extension properties are added to `String` class to create Sumomo instance,

```kotlin

// generate Sumomo instance with non-global & case-sensitive flags
val mobile = """((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}""".r

// generate Sumomo instance with global & case-sensitive flags
val phone = """([0-9]|[-])+""".rg

// generate Sumomo instance with global & case-insensitive flags
val animals = """b(at|ear|ull|ee)""".rgi

// generate Sumomo instance with non-global & case-insensitive flags
val directions = """north|south|east|west""".ri
```

use `Sumomo.ast` property to dump ASTs

currently Sumomo ASTs include

- **Sequential**  : regex like `ab`, holds two children `left` and `right`, children will be composed into `right` recursively if more than two, also known as `Concat`

- **Alternative** : regex like `a|b`, holds two children `left` and `right`, children will be composed into `right` recursively if more than two, also known as `Choice`

- **Range** : represent a range of chars to match, holds `start` and `end` (inclusive)

- **Exactly** : represent a specific char

- **Lazy** : match its child regex at least `min` times and at most `max` times, prefer the least one

- **Greedy** : match its child regex at least `min` times and at most `max` times, prefer the most one

and some shorthand

- **Word** : `\w`, match `[a-zA-Z0-9_]`

- **Digital** :`\d`, match `[0-9]`

- **Blank** :`\s`, match all kinds of blanks

- **Any** : `.`, match any char

- **Pass** : pass the match w/o consume anything

- **Fail** : fail the match w/o consume anything

Runtime
===========

use `Sumomo#match(subject : String)` to match target string

`anchor`, `capturing-group`, `boundary`, `look-around` is not supported yet but planned to,
feel free to make any contributes
