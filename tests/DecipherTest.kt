import org.junit.Test

import org.junit.Assert.*
import kotlin.test.assertTrue

class DecipherTest {

    @Test
    fun doesntEndWith() {
        val shouldBeTrue: List<String> = listOf(
                "I am a man who walks alone",
                "U am a man who walks alone",
                "A bear is scary",
                "Must Question Everything",
                "Q is not a word"
        )

        val shouldBeFalse:List<String> = listOf(
                "I mu",
                "This Questiou has a typo",
                "V for VendetV",
                "The word AII contains two capital i's"
        )

        shouldBeTrue.forEach { assertEquals(true,doesntEndWith(it)) }
        shouldBeFalse.forEach { assertEquals(false,doesntEndWith(it)) }
    }


    @Test
    fun singleLetterWordsTest() {
        val shouldPass:List<String> = listOf(
                "I am a man who walks alone",
                "A train"
        )

        val shouldFail:List<String> = listOf(
                "U is not the same as you",
                "O A I E K J N M L M O P",
                "There is no single letter word G",
                "The space in the word G oogle ruins this test"

        )

        shouldPass.forEach { assertEquals(true, singleLetterWorlds(it)) }
        shouldFail.forEach { assertEquals(false, singleLetterWorlds(it)) }
    }

    @Test
    fun afterQisUTest() {
        val shouldPass = "Quest query whatever"
        val shouldPass1 = "Questing in wow Q"
        val shouldFail = "qwerty should fail because it's not common english"
        val shouldFail1 = "qr quest Q test"
        assertTrue(afterQisU(shouldPass))
        assertTrue(afterQisU(shouldPass1))
        assertFalse(afterQisU(shouldFail))
        assertFalse(afterQisU(shouldFail1))

    }


    @Test
    fun doesntContainOnlyConsonantsTest() {
        val shouldPass = "Awesome beautiful weather Birmingham not"
        val shouldPass1 = "QWrtxfe mjklbnedfg"
        val shouldFail = "QWrtxf mjklbndfg"

        assertTrue(doesntContainOnlyConsonants(shouldPass))
        assertTrue(doesntContainOnlyConsonants(shouldPass1))
        assertFalse(doesntContainOnlyConsonants(shouldFail))
    }


    @Test
    fun setOfRulesTest() {
        val shouldPass = "Awesome beautiful weather Birmingham not Quest query whatever I am a man who walks alone"
        val shouldPass1 = "QUrtxfe mjklbnedfg Questing in wow A train"
        val shouldFailSx = "awesome day sx"

        assertTrue(setOfRules(shouldPass))
        assertTrue(setOfRules(shouldPass1))
        assertFalse(setOfRules(shouldFailSx))
    }
}