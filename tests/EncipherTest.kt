import org.junit.Test

import org.junit.Assert.*

class EncipherTest {

    @Test
    fun characterEncipherTest() {
        // Single step rotation
        assertEquals("b", p("a",1))
        assertEquals("a", p("z",1))
        assertEquals("g", p("f",1))
        assertEquals("z", p("y",1))

        // Full rotation
        assertEquals("a", p("a",26))
        assertEquals("z", p("z",26))
        assertEquals("f", p("f",26))
        assertEquals("y", p("y",26))
    }

    @Test
    fun textEncipherTest(){
        // Simple text encipher
        assertEquals("bcde", encipher("abcd", 1))
        assertEquals("pqrs", encipher("opqr", 1))
        assertEquals("fghi", encipher("abcd", 5))
        assertEquals("tuvw", encipher("opqr", 5))

        // Text including full rotation
        assertEquals("yzabcd", encipher("xyzabc", 1))
        assertEquals("bcdefghijklmnopqrstuvwxyza", encipher("abcdefghijklmnopqrstuvwxyz", 1))
        assertEquals("yzabcd", encipher("tuvwxy", 5))
        assertEquals("klmnopqrstuvwxyzabcdefghij", encipher("abcdefghijklmnopqrstuvwxyz", 10))

        // Text including spaces and full rotation
        assertEquals("yzabcd yzabcd yzabcd", encipher("xyzabc xyzabc xyzabc", 1))
    }
}