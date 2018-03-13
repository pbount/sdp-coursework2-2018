import org.junit.Test
import org.junit.Assert.*

class DecipherTest {

    @Test
    fun shiftStringTest(){
        val shouldPass:List<String> = listOf(
                "a",
                "A",
                "abcd",
                "ThIs Is A SmALL String",
                "JustAWord",
                "These should all pass",
                "Test1nG s0m3 Num83rs",
                "With   Multiple    Spaces",
                "With   Sp3$1aL   Ch@r@ct3rs!!!!"
        )
        shouldPass.forEach { println("Testing: $it"); assertEquals(it,shiftString(encipher(it, 5),5)) }
    }

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

        shouldBeTrue.forEach { println("Testing: $it"); assertEquals(true,doesntEndWith(it)) }
        shouldBeFalse.forEach { println("Testing: $it"); assertEquals(false,doesntEndWith(it)) }
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

        shouldPass.forEach { println("Testing: $it"); assertEquals(true, singleLetterWorlds(it)) }
        shouldFail.forEach { println("Testing: $it"); assertEquals(false, singleLetterWorlds(it)) }
    }

    @Test
    fun afterQisUTest() {
        val shouldPass:List<String> = listOf(
                "Quest query whatever",
                "Questing in wow Q"
        )

        val shouldFail:List<String> = listOf(
                "qwerty should fail because it's not common english",
                "qr quest Q test"
        )

        shouldPass.forEach { println("Testing: $it"); assertEquals(true, singleLetterWorlds(it)) }
        shouldFail.forEach { println("Testing: $it"); assertEquals(false, singleLetterWorlds(it)) }
    }


    @Test
    fun doesntContainOnlyConsonantsTest() {
        val shouldPass:List<String> = listOf(
                "Awesome beautiful weather Birmingham not",
                "QWrtxfe mjklbnedfg"
        )

        val shouldFail:List<String> = listOf(
                "Thjs js nqt q wqrd",
                "Thls shqvld nGvGr pqss"
        )

        shouldPass.forEach { println("Testing: $it"); assertEquals(true, singleLetterWorlds(it)) }
        shouldFail.forEach { println("Testing: $it"); assertEquals(false, singleLetterWorlds(it)) }
    }


    @Test
    fun setOfRulesTest() {
        val shouldPass:List<String> = listOf(
                "Awesome beautiful weather Birmingham not Quest query whatever I am a man who walks alone",
                "QUrtxfe mjklbnedfg Questing in wow A train"
        )

        val shouldFail:List<String> = listOf(
                "awesome day sx"
        )

        shouldPass.forEach { println("Testing: $it"); assertEquals(true, singleLetterWorlds(it)) }
        shouldFail.forEach { println("Testing: $it"); assertEquals(false, singleLetterWorlds(it)) }
    }
}