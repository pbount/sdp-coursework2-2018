import org.springframework.beans.factory.BeanFactory
import org.springframework.context.support.ClassPathXmlApplicationContext

//--------------- "Helper" Functions ---------------//

fun shiftString(str: String, n: Int):String {

    val s: Int = 26 - modulus(n,26)
    return encipher(str,s)
}

fun getLargest(i: Map<Char, Int>, n:Int):Map<Char, Int>{
    return i.toList().sortedBy { (_, value) -> value.inv()}.take(n).toMap()
}

fun modulus(i: Int, j: Int):Int {

    return (i+j)%j
}

fun offset(input:CharArray, value:Int):CharArray {
    return shiftString(input.joinToString().replace(", ",""), value).toCharArray()
}

fun splitString(string: String): List<String> = string.split(" ")

fun wasItSuccessful(set: Set<Int>) = set.size == 1

//---------- Rules for evaluating strings ----------//

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

    val myCons = listOf('b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','x','z','w')

    val splitString = splitString(string.trim().toLowerCase())

    return splitString.all { !myCons.containsAll(it.toList()) }

}

//------------ CharArray Extension Functions ------------//

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
    val mostFrequentCharacters = charArrayOf('e','t','a','o','i','n','s','r','h')
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
 *    Receives a String of a single word and returns true if
 *    it contains any of the most common double letter occurances.
 */
val mostFrequentDoubleLetters = { string: String ->

    tailrec fun loop(string:String): Boolean {
        return when {
            string.length <= 1 -> false
            string[0] == string[1] -> listOf("ss","ee","tt","ff","ll","mm","oo").contains(string.substring(0,2))
            else -> loop(string.drop(1))
        }
    }
    loop(string)
}

/**
 *
 */
val heuristicsOfRules = { string: String -> doesntEndWith(string) && singleLetterWorlds(string) &&
        if(string.contains("q",true)) { afterQisU(string) } else {
            true
        } && !string.contains("sx") && doesntContainOnlyConsonants(string) }




val mostFrequentWords = {string: String ->

    //val ROOT = System.getProperty("user.dir") + "/CW2TestingDav"
    // val beanFactory = ClassPathXmlApplicationContext("file:$ROOT/beans.xml")
    //Loading a list of string from an xml file with spring
    val listWithMostFrequentWords = ListWithMostFrequentWords.myList()
    if(splitString(string).all{it.length > 4})  {
        true
    } else {
        val filteredList = splitString(string).filter {listWithMostFrequentWords.contains(it.toLowerCase())}
        !filteredList.isEmpty()
    }

}

object ListWithMostFrequentWords {
    private val ROOT = System.getProperty("user.dir")

    private val beanFactory: BeanFactory
        @Throws (Exception::class)
        get() = ClassPathXmlApplicationContext("file:$ROOT/beans.xml")

    fun myList():List<String> = beanFactory.getBean("myList") as List<String>

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
        println("Solve after forth attempt")
        return shiftString(string,setOfKeysAfterForthStep.first())
    }


    return "Dictionary under construction"

}


fun main(args: Array<String>){

    val stringForSecondSetOfRules = encipher("It's a beautiful day.!!",1)
    val t:String = "Good Morning"
    val failingString = encipher(t,1)
    val testingNewDecipherMethod = "No. If the pairing is braking down you should inform the instructor as soon as possible. If this is too near the submission date, and you completed the remainder of the assignment on your own, then you should turn in your assignment with just your name on it. You should state who you worked with and the reason why you did not finish the assignment together."
    val e:String = encipher(testingNewDecipherMethod ,16)
    val com:CharArray = charArrayOf('e', 't', 'a', 'o', 'i', 'n', 's', 'r', 'h')
    val result = decipher(failingString)

    println(result)
    println(System.getProperty("user.dir"))

    println("--------".repeat(4))

    println("Testing Function")

}