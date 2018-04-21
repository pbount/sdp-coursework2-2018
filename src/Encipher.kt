/**
 *    Receives a String and an Int (n) and shifts each alphabetical character regardless of letter case
 *    by n characters. All remaining numeric and punctuation characters remain unaffected.
 *    Example: encoder("abcd??", 1)  -> "bcde??"
 *
 *    Encoder uses parts of the Ascii Table, namely ranges 65 - 90 for Uppercase
 *    and 97 - 122 for Lowercase alphabetical characters.
 *
 *    The formula used is:
 *      x: numeric representation of character
 *      n: encoding number
 *      b: base of the range of characters (65 for uppercase or 97 for lowercase)
 *
 *         ((x + n - b) rem 26) + 65
 */
fun encoder(str: String, n: Int): String {

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

/**
 * Encipher is an adapter function with the purpose of restricting the user
 * from entering values outside the designated range.
 * Only values between 0 and 25 (inclusive) are allowed. Anything outside that range
 * halts the execution.
 */
@Throws(IllegalArgumentException:: class)
fun encipher(str:String, n: Int): String {
    if(n<0 || n>25){
        throw IllegalArgumentException("Shift must be between 0 and 25")

    }
    return encoder(str,n)
}




