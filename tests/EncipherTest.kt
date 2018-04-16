import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import kotlin.test.assertEquals


class EncipherTest {


    @Rule
    @JvmField
    var expectedException = ExpectedException.none()!!

    @Test
    fun characterEncipherTest() {
        // Single step rotation
        assertEquals("b", encipher("a",1))
        assertEquals("a", encipher("z",1))
        assertEquals("g", encipher("f",1))
        assertEquals("z", encipher("y",1))

    }

    @Test
    fun shouldThrowIllegalArgumentException(){
        expectedException.expect(IllegalArgumentException :: class.java)
        expectedException.expectMessage("Shift must be between 0 and 25")
        encipher("b", -1)
    }

    @Test
    fun textEncipherTest(){
        // Simple text encipher
        assertEquals("bcde", encipher("abcd", 1))
        assertEquals("pqrs", encipher("opqr", 1))
        assertEquals("fghi", encipher("abcd", 5))
        assertEquals("tuvw", encipher("opqr", 5))

        // Text including edge cases
        assertEquals("yzabcd", encipher("xyzabc", 1))
        assertEquals("bcdefghijklmnopqrstuvwxyza", encipher("abcdefghijklmnopqrstuvwxyz", 1))
        assertEquals("yzabcd", encipher("tuvwxy", 5))
        assertEquals("klmnopqrstuvwxyzabcdefghij", encipher("abcdefghijklmnopqrstuvwxyz", 10))

        // Text including spaces and edge cases
        assertEquals("yzabcd yzabcd yzabcd", encipher("xyzabc xyzabc xyzabc", 1))
    }

    @Test
    fun tailRecursionTest(){
        assertEquals("b".repeat(10000), encipher("a".repeat(10000), 1))
    }

}