//--------------- "Helper" Functions ---------------//
/**
 * Receives a String and moves each character forward by n alphabetical steps.
 */
fun shiftString(str: String, n: Int): String {

    val s: Int = 26 - modulus(n,26)
    return encoder(str,s)
}

/**
 * Receives a Map<Char, Int> representing alphabetical characters and the number of
 * their occurrences and returns the n ones which have the highest number of
 * occurrences.
 */
fun getLargest(i: Map<Char, Int>, n:Int): Map<Char, Int> {
    return i.toList().sortedBy { (_, value) -> value.inv()}.take(n).toMap()
}

fun modulus(i: Int, j: Int):Int {
    return (i+j)%j
}

/**
 * Receives an array of characters and returns a new array of characters where each
 * character has been shifted by n alphabetical steps.
 *
 * (This is an adapter function modifieing the CharArray to String and utilizing
 * the shiftString function)
 */
fun offset(input:CharArray, value:Int): CharArray {
    return shiftString(input.joinToString().replace(", ",""), value).toCharArray()
}

/**
 * Receives a String representing a sentence and returns a list of string
 * representing each individual word of that sentense.
 */
fun splitString(string: String): List<String> = string.split(" ")

/**
 * Receives a Set of possible deciphering numbers and assumes that deciphering has
 * been achieved if the set contains only one element. (The final result
 * is used to decipher the enciphered text).
 */
fun wasItSuccessful(set: Set<Int>) = set.size == 1

//---------- Rules for evaluating strings ----------//
/**
 * Receives a String representing a single word and returns true if it doesn't end with
 * a set of predefined characters (i or v or j or u)
 */
fun doesntEndWith(string: String): Boolean {

    val splitString = splitString(string.toLowerCase())

    return splitString.all {
        if(it.length == 1) {
            true
        }
        else {
            !it.endsWith("i",true) && !it.endsWith("u", true) &&
                    !it.endsWith("v",true) && !it.endsWith("j",true)
        }

    }
}

/**
 * Receives a String representing a single letter word and returns true if it is either
 * "a" or "i" which are the only accepted single letter words in the english alphabet.
 */
fun singleLetterWorlds(string: String): Boolean {
    val splitString = splitString(string.toLowerCase())

    return splitString.all {
        if(it.length!=1) true
        else it == "i" || it == "a"
    }
}

/**
 * Receives a String representing a single word and returns true if
 * there are no characters "q" followed by "u" unless "q" is the last character
 * of the word.
 */
fun afterQisU(string: String): Boolean {

    val splitString = splitString(string.toLowerCase())

    return splitString.all { !it.dropLast(1).replace("qu","").contains("q") }
}

/**
 * Receives a String representing a single word and returns true if
 * it has at least one vowel. If it consists of consonants only
 * it is not considered a valid word.
 */
fun doesntContainOnlyConsonants(string: String): Boolean {

    val myCons = CustomLibrary.listOfConsonants()

    val splitString = splitString(string.trim().toLowerCase())

    return splitString.all { !myCons.containsAll(it.toList()) }

}

//------------ Extension Functions ------------//
/**
 * Counts all characters of a String
 */
fun String.charCount() = this.toLowerCase().replace(" ", "").groupBy {it}.mapValues { it.value.size }

/**
 * Requests a second character array and returns true if all contents of the initial
 * array appear in the second one.
 */
fun CharArray.containsAll(charArrayToCompare: CharArray): Boolean {
    for(c in charArrayToCompare) {
        if(!this.contains(c))
            return false
    }
    return true
}

//------------------ Steps ------------------//
/**
 * The first step evaluates the enciphered string statistically based on
 * character occurrence. If the enciphered text is sufficiently large,
 * no further step may be needed. Otherwise, the string is evaluated
 * based on linguistic rules.
 */
fun firstStep(string: String) : Set<Int> {


    val charCount = string.charCount()
    val mostChars = getLargest(charCount, 5)
    val largestChar = getLargest(charCount, 1).keys.first()
    val mostFrequentCharacters = CustomLibrary.arrayOfMostFrequentCharacters()
    val resultKeys = mutableSetOf<Int>()

    for(i in mostFrequentCharacters) {
        val step = largestChar - i

        val exp = offset(mostChars.keys.toCharArray(), step)

        val comp = mostFrequentCharacters.containsAll(exp)
        if(comp) {
            resultKeys.add(step)
        }
    }

    return resultKeys
}

/**
 * Counts all double letter occurrences and compares those to a list of most common
 * double letters, discarded those that are not applicable.
 */
val mostFrequentDoubleLetters = { string: String ->

    val lstOfMostFrequentDoubleLetters = CustomLibrary.listOfMostFrequentDoubleLetters()
    tailrec fun loop(string:String): Boolean {
        return when {
            string.length <= 1 -> false
            string[0] == string[1] -> lstOfMostFrequentDoubleLetters.contains(string.substring(0,2))
            else -> loop(string.drop(1))
        }
    }

    loop(string)
}

/**
 * Finds all instances of letter q and evaluates in which of those it is followed by u, as well as if
 * s followed by x exists.
 */
val heuristicsOfRules = { string: String -> doesntEndWith(string) && singleLetterWorlds(string) &&
        if(string.contains("q",true)) { afterQisU(string) } else {
            true
        } && !string.contains("sx") && doesntContainOnlyConsonants(string)
}

/**
 * Compares all small sized words (length 1-4) and compares those to an
 * internal dictionary defined in beans.xml. Words like "I", "and", "is"...
 * should exist in almost any sentence.
 */
val mostFrequentWords = {string: String ->


    //Loading a list of string from an xml file with spring
    val listWithMostFrequentWords = CustomLibrary.listOfMostCommonWords()
    if(splitString(string).all{it.length > 4})  {
        true
    } else {
        val filteredList = splitString(string).filter {listWithMostFrequentWords.contains(it.toLowerCase())}
        !filteredList.isEmpty()
    }

}

fun filter(set: Set<Int>, string: String, f: (String) -> Boolean): Set<Int> {

    return  when(set.isEmpty()) {
        true -> {
            (1..25).toSet()
        }
        else -> set
    }.filter {
        f(shiftString(string,it))
    }.toSet()

}

/**
 * Receives the enciphered string and performs all available tests on it.
 * Each test reduces the set of possible deciphering keys until only one remains.
 * If the program sucessfully deciphered the string it returns the deciphered
 * text.
 */
fun decipher(string: String): String {

    //First Attempt to decipher
    val setOfKeysAfterFirstStep = firstStep(string)
    if(wasItSuccessful(setOfKeysAfterFirstStep)) {
        println("Solved in first attempt")
        return shiftString(string, setOfKeysAfterFirstStep.first())
    }


    //Second Attempt
    val setOfKeysAfterSecondStep = filter(setOfKeysAfterFirstStep, string, mostFrequentWords)
    if(wasItSuccessful(setOfKeysAfterSecondStep)) {
        println("Solved in second attempt")
        return shiftString(string, setOfKeysAfterSecondStep.first())
    }


    //Third Attempt
    val setOfKeysAfterThirdStep = filter(setOfKeysAfterSecondStep,string,mostFrequentDoubleLetters)
    if(wasItSuccessful(setOfKeysAfterThirdStep)) {
        println("Solved in third attempt")
        return shiftString(string, setOfKeysAfterThirdStep.first())
    }

    //4th Attempt
    val setOfKeysAfterForthStep = filter(setOfKeysAfterThirdStep,string, heuristicsOfRules)
    if(wasItSuccessful(setOfKeysAfterForthStep)) {
        println("Solve in forth attempt")
        return shiftString(string,setOfKeysAfterForthStep.first())
    }

    //Failed to decipher
    return "Caesar we can't decipher your message. Dictionary is under construction!"

}

