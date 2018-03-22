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
    fun getLargestTest(){
        val input: Map<Char, Int> = mapOf('a' to 5, 'b' to 4, 'c' to 3, 'd' to 2, 'e' to 6)
        val expected: Map<Char, Int> = mapOf('e' to 6, 'a' to 5, 'b' to 4)
        assertEquals(expected, getLargest(input,3))

        val input2: Map<Char, Int> = mapOf('a' to 5)
        val expected2: Map<Char, Int> = mapOf('a' to 5)
        assertEquals(expected2, getLargest(input2,3))


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

        shouldPass.forEach { println("Testing: $it"); assertEquals(true, afterQisU(it)) }
        shouldFail.forEach { println("Testing: $it"); assertEquals(false, afterQisU(it)) }
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

        shouldPass.forEach { println("Testing: $it"); assertEquals(true, doesntContainOnlyConsonants(it)) }
        shouldFail.forEach { println("Testing: $it"); assertEquals(false, doesntContainOnlyConsonants(it)) }
    }

    @Test
    fun mostFrequentDoubleLettersTest(){
        val shouldBeTrue:List<String> = listOf(
                "Hello world!",
                "Seeing is believing",
                "A loop of wires",
                "See the blizzard",
                "tt rr gg rr tt ww dd ee"
        )

        val shouldBeFalse:List<String> = listOf(
                "bb mm nn bb xx zz",
                "Just a blizzard",
                "pizza is love, pizza is life!",
                "reign of terror",
                "No doubles in this sentence"
        )

        shouldBeTrue.forEach { println("Testing: $it"); assertEquals(true, mostFrequentDoubleLetters(it)) }
        shouldBeFalse.forEach { println("Testing: $it"); assertEquals(false, mostFrequentDoubleLetters(it)) }

    }

    @Test
    fun mostFrequentWordsTest(){
        val shouldBeTrue:List<String> = listOf(
                "This is true because it contains this",
                "The is also a valid ward",
                "kl ih gg sd tr to",
                "!ThIs ?? TO ''iT",
                "omits larger dictionary entries"
        )

        val shouldBeFalse:List<String> = listOf(
                "Non existant dictionary words",
                "",
                "N0t 4 W0rd"
        )

        shouldBeTrue.forEach { println("Testing: $it"); assertEquals(true, mostFrequentWords(it)) }
        shouldBeFalse.forEach { println("Testing: $it"); assertEquals(false, mostFrequentWords(it)) }

    }

    @Test
    fun filterTest(){
        val steps: Set<Int> = (0..25).toSet()

        val accepted:List<String> = listOf(
                "Look", "book","?see?","gLee!!", "glee", "gull", "riff", "really"
        )

        val notAccepted:List<String> = listOf(
                "Todd", "Pizza", "blizzard", "xXx", "B00k is not a word"
        )

        accepted.forEach {
            println("Testing: $it")
            assertEquals(
                    7,
                    filter(steps, accepted[accepted.indexOf(it)], mostFrequentDoubleLetters).size)
        }

        println(filter((0..25).toSet(), "Todd", mostFrequentDoubleLetters))


        notAccepted.forEach {
            println("Testing: $it");
            assertEquals(0, filter(steps, notAccepted[notAccepted.indexOf(it)], mostFrequentDoubleLetters).size)
        }

    }


    @Test
    fun setOfRulesTest() {
        val shouldPass: List<String> = listOf(
                "Awesome beautiful weather Birmingham not Quest query whatever I am a man who walks alone",
                "QUrtxfe mjklbnedfg Questing in wow A train",
                "Even though no single word is in this sentense it still should pass"
        )

        val shouldFail: List<String> = listOf(
                "Awesome day t oday",
                "Stars can t shine without darkness",
                "C R T",
                "7 is just a number"
        )

        shouldPass.forEach { println("Testing: $it"); assertEquals(true, singleLetterWorlds(it)) }
        shouldFail.forEach { println("Testing: $it"); assertEquals(false, singleLetterWorlds(it)) }
    }


    @Test
    fun wasItSuccessfulTest() {
        val shouldPass:List<Set<Int>> = listOf(
                setOf(1), setOf(5), setOf(20)
        )

        val shouldFail:List<Set<Int>> = listOf(
                setOf<Int>(), setOf(1,2,3), setOf(2,3)
        )

        shouldPass.forEach { assertEquals(true, wasItSuccessful(it)) }
        shouldFail.forEach { assertEquals(false, wasItSuccessful(it)) }
    }

    @Test
    fun containsMostCommonDoubleLettersTest(){

        val listOfStrings = listOf(
                "Good morning",
                "Should pass",
                "I weep everyday for my lost dog",
                "Player 3 counterAttacks player 2",
                "The entire staff has done a great job this year.",
                "He called the police because he was really worried.",
                "She commanded that work on the bridge cease immediately.",
                "Fish is a healthy food"
        )


    }

    @Test
    fun decipherTest() {
        val shouldPass = "It's a beautiful day.!!"
        val shouldPass1 = "A wiki is run using wiki software, otherwise known as a wiki engine." +
                " A wiki engine is a type of content management system, but it differs from most other such "
        val shouldPass2 = "No. If the pairing is braking down you should inform the instructor as soon as possible."
        val shouldFail = "Good morning."

        for(i in 1..25) {
            kotlin.test.assertEquals(shouldPass, decipher(encipher(shouldPass, i)))
            kotlin.test.assertEquals(shouldPass1, decipher(encipher(shouldPass1, i)))
            kotlin.test.assertEquals(shouldPass2, decipher(encipher(shouldPass2, i)))
            kotlin.test.assertEquals(shouldFail, decipher(encipher(shouldFail, i)))
        }
    }
}