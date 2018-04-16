//--------------- "Helper" Functions ---------------//

fun shiftString(str: String, n: Int): String {

    val s: Int = 26 - modulus(n,26)
    return encoder(str,s)
}

fun getLargest(i: Map<Char, Int>, n:Int): Map<Char, Int> {
    return i.toList().sortedBy { (_, value) -> value.inv()}.take(n).toMap()
}

fun modulus(i: Int, j: Int):Int {

    return (i+j)%j
}

fun offset(input:CharArray, value:Int): CharArray {
    return shiftString(input.joinToString().replace(", ",""), value).toCharArray()
}

fun splitString(string: String): List<String> = string.split(" ")

fun wasItSuccessful(set: Set<Int>) = set.size == 1

//---------- Rules for evaluating strings ----------//

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

fun singleLetterWorlds(string: String): Boolean {
    val splitString = splitString(string.toLowerCase())

    return splitString.all {
        if(it.length!=1) true
        else it == "i" || it == "a"
    }
}

fun afterQisU(string: String): Boolean {

    val splitString = splitString(string.toLowerCase())

    return splitString.all { !it.dropLast(1).replace("qu","").contains("q") }


}

fun doesntContainOnlyConsonants(string: String): Boolean {

    val myCons = CustomLibrary.listOfConsonants()

    val splitString = splitString(string.trim().toLowerCase())

    return splitString.all { !myCons.containsAll(it.toList()) }

}

//------------ Extension Functions ------------//

fun String.charCount() = this.toLowerCase().replace(" ", "").groupBy {it}.mapValues { it.value.size }


fun CharArray.containsAll(charArrayToCompare: CharArray): Boolean {
    for(c in charArrayToCompare) {
        if(!this.contains(c))
            return false
    }
    return true
}

//------------------ Steps ------------------//

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

val heuristicsOfRules = { string: String -> doesntEndWith(string) && singleLetterWorlds(string) &&
        if(string.contains("q",true)) { afterQisU(string) } else {
            true
        } && !string.contains("sx") && doesntContainOnlyConsonants(string)
}

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

