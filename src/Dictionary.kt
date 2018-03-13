import java.io.File

fun main(args:Array<String>){
    val dictionary: List<String> = buildDictionary()
    println(dictionary.contains(("AAAA")))
    println(dictionary.size)
}

fun buildDictionary():List<String>{
    val file = File("c:"+File.separator+"words.txt")
    val bufferedReader = file.bufferedReader()
    val text:List<String> = bufferedReader.readLines()
    return text
}