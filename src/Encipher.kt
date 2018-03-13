/**
 *    Receives a String and an Int and shifts each alphabetical character regardless of letter case
 *    by n characters. All remaining numeric and punctuation characters remain unaffected.
 *    Example:  [abcd!!] -> [bcde!!]
 */
fun encipher(str: String, n: Int): String{

    tailrec fun helper(remStr: String, n:Int, resultSoFar: String): String {
        return when {
            remStr.isEmpty() -> resultSoFar
            remStr[0].isUpperCase() -> helper(remStr.drop(1),n,
                    resultSoFar + ((remStr[0].toInt() + n - 65) % 26 + 65).toChar().toString())

            remStr[0].isLowerCase() -> helper(remStr.drop(1),n,resultSoFar +
                    ((remStr[0].toInt() + n - 97) % 26 + 97).toChar().toString())

            else -> helper(remStr.drop(1),n, resultSoFar +remStr[0].toString())
        }
    }
    return helper(str,n,"")
}