import org.springframework.beans.factory.BeanFactory
import org.springframework.context.support.ClassPathXmlApplicationContext

object CustomLibrary {

    //System.getProperty will get to the directory of the project. Replace after the "+" symbol with the name
    //of the module that has the xml file.
    private val ROOT = System.getProperty("user.dir") // + ("your module name)

    private val beanFactory: BeanFactory
        @Throws (Exception::class)
        get() = ClassPathXmlApplicationContext("file:$ROOT/beans.xml")

    fun listOfMostCommonWords():List<String> = beanFactory.getBean("listWitMostCommonWords") as List<String>


    fun listOfConsonants(): List<Char> = beanFactory.getBean("consonantsList") as List<Char>

    fun listOfMostFrequentDoubleLetters(): List<String> =
            beanFactory.getBean("mostFrequentDoubleLettersList") as List<String>

    fun arrayOfMostFrequentCharacters(): CharArray {
        val lst = beanFactory.getBean("mostFrequentCharacters") as List<Char>
        return lst.toCharArray()
    }
}