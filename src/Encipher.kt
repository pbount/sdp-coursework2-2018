
fun encipher(s: String, n: Int): String {
    if(n < 0 || n > 25) {
        println("n should be between 0 and 25")
        System.exit(-1)
    }
    return when {
       s.isEmpty() -> ""
       else -> p(s.substring(0,1),n) + encipher(s.drop(1),n)

    }

}

fun p(input: String, rotate:Int):String{


    if(input == " ") return " "
    val myArrayOfLetters = arrayOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n",
            "o","p","q","r","s","t","u","v","w,","x","y","z")
    val indexLimit = 25

    val idx = myArrayOfLetters.indexOf(input.toLowerCase())

    val result: String = if (idx + rotate > indexLimit){

         myArrayOfLetters[idx + rotate - myArrayOfLetters.size]

    } else {

        myArrayOfLetters[idx + rotate]
    }

    return when(input[0].isUpperCase()){
        true -> result.toUpperCase()
        false -> result
    }

}


fun main(args: Array<String>){
    val oldStr = " a "
    //println(myArrayOfLetters.indexOf("A"))

    val newString = encipher(oldStr,25)
    println(newString)
    //println(newString.length)


}