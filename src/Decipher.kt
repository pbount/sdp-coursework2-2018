fun main(args: Array<String>){
    val stringForSecondSetOfRules = encipher("Good morning Alabama world",1)
    val t:String = "A wiki is run using wiki software, otherwise known as a wiki engine. A wiki engine is a type of content management system, but it differs from most other such systems"
    val testingNewDecipherMethod = "No. If the pairing is braking down you should inform the instructor as soon as possible. If this is too near the submission date, and you completed the remainder of the assignment on your own, then you should turn in your assignment with just your name on it. You should state who you worked with and the reason why you did not finish the assignment together."
    val e:String = encipher(testingNewDecipherMethod ,16)
    val com:CharArray = charArrayOf('e', 't', 'a', 'o', 'i', 'n', 's', 'r', 'h')
    val result = decipher(stringForSecondSetOfRules)

    println(result)

}

fun secondStep(set: Set<Int>, string: String): Set<Int> {
    return set.filter {

        setOfRules(shiftString(string,it))
    }.toSet()
}

/**
 *    Receives a string and returns true if it passes a set of defined rules.
 */
fun setOfRules(string: String): Boolean {

    return doesntEndWith(string) && singleLetterWorlds(string) &&
            if(string.contains("q",true)) { afterQisU(string) } else {
                true
            } && !string.contains("sx") && doesntContainOnlyConsonants(string)

}

fun decipher(string: String): String {
    // function for this
    val charCount: Map<Char, Int> = string.toLowerCase().replace(" ", "").groupingBy { it }.eachCount()
    val mostChars: Map<Char, Int> = getLargest(charCount, 5)
    val largestChar: Char = getLargest(charCount, 1).keys.first()
    val mostFrequentCharactersArray = charArrayOf('e', 't', 'a', 'o', 'i', 'n', 's', 'r', 'h')
    val setOfKeys = mutableSetOf<Int>()

    for (i in mostFrequentCharactersArray) {
        // get step from highest occurring character from first expected character
        val step = largestChar - i

        // get array based on that offset (need a charArray)
        val exp: CharArray = offset(mostChars.keys.toCharArray(), step)

        // find if the char Array exists in the most frequent characters array
        val comp: Boolean = mostFrequentCharactersArray.compare(exp)

        if (comp) {
            // array keys
            // check array.size > 1
            // array [1,6,9] -> result
            // dictionary
            setOfKeys.add(step)
            println("Adding step to list:  $step")
            //return shiftString(string,step)
        }
    }

    when (setOfKeys.size) {
        1 -> return shiftString(string, setOfKeys.first())
        0 -> {
            //no keys where returned from step 1
            println("second step with zero keys from step 1")
            val keys = secondStep((1..25).toSet(), string)
            return when (keys.size) {
                1 -> shiftString(string, keys.first())
                else -> {
                    println(keys.size)
                    "dictionary"
                }
            //dictionary
            }
        }
        else -> {
            //keys returned from step 1 are more than 1
            val fewerKeys = secondStep(setOfKeys, string)
            println("second step with some keys from step 1")
            return when (fewerKeys.size) {
                1 -> shiftString(string, fewerKeys.first())
                else -> "dictionary"
            }
        }
    }
}

/**
 *    Rule: A word should not end with I, U, V, J
 *    Receives a multi-word sentence as a single string and returns true if each word
 *    in the sentence does not end with I,U,V,J.
 *    Example: [This is a test] -> true
 */
fun doesntEndWith(string: String): Boolean {

    val splitString = splitString(string.toLowerCase())

    return splitString.all {
        if(it.length == 1) {true}
        else {
            !it.endsWith("i",true) && !it.endsWith("u", true) &&
                    !it.endsWith("v",true) && !it.endsWith("j",true)
        }

    }
}

/**
 *    Rule: Single letter words can only be I or A.
 *    Receives a a multi-word sentence as a single string and returns true if each word
 *    in the sentence is either I or A.
 *    Example: [This is a test] -> true
 */
fun singleLetterWorlds(string: String): Boolean {
    val splitString = splitString(string.toLowerCase())

    return splitString.all {
        if(it.length!=1) true
        else it == "i" || it == "a"
    }
}

/**
 *    Rule: Every appearance of the character Q should be followed by U unless Q is the
 *    last character of the word.
 *    Receives a a multi-word sentence as a single string and returns true if each word
 *    in the sentence which contains the character Q has U immidtatelly after it unless
 *    Q is the final character of the word.
 *    Example: [This is a question] -> true
 */
fun afterQisU(string: String): Boolean {

    val splitString = splitString(string.toLowerCase())

    return splitString.all { !it.dropLast(1).replace("qu","").contains("q") }
}

/**
 *    Rule: A word can not be composed of consonants only.
 *    Receives a a multi-word sentence as a single string and returns true if the word
 *    contains at least one vowel.
 *    Example: [This is a question] -> true
 */
fun doesntContainOnlyConsonants(string: String): Boolean {

    val myCons = listOf('b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','x','z','w')

    val splitString = splitString(string.toLowerCase())

    return splitString.all { !myCons.containsAll(it.toList()) }
}

/**
 *    Receives a String and an Int and shifts each alphabetical character regardless of letter case
 *    backwards by n characters. All remaining numeric and punctuation characters remain unaffected.
 *    The string received should be a product of the encipher function and the number of steps
 *    should be known.
 *    Example:  [bcde!!] -> [abcd!!]
 */
fun shiftString(str: String, n: Int):String{
    val s: Int = 26 - modulus(n,26)
    return encipher(str,s)
}

/**
 *    Receives a Map<Char, Int> and an Int n and returns the n entries of the Map which
 *    have the highest Int.
 */
fun getLargest(i: Map<Char, Int>, n:Int):Map<Char, Int>{
    return i.toList().sortedBy { (_, value) -> value.inv()}.take(n).toMap()
}

/**
 *    Receives a number i and a number j and returns the result of
 *    i mod j.
 */
fun modulus(i: Int, j: Int):Int{
    return (i+j)%j
}

fun offset(input:CharArray, value:Int):CharArray{
    return shiftString(input.joinToString().replace(", ",""), value).toCharArray()
}


fun CharArray.compare(charToCompareWith: CharArray): Boolean {
    for(c in charToCompareWith) {
        if(!this.contains(c))
            return false
    }
    return true
}

fun splitString(string: String): List<String> = string.split(" ")



