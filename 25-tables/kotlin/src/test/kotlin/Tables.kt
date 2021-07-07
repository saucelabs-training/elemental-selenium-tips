import org.junit.jupiter.api.TestInstance
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import java.util.*
import kotlin.test.*

/* a single instance of of the test class is used for every method */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Tables {
    
    private val driver by lazy {
        FirefoxDriver()
    }

    @AfterTest
    fun tearDown() {
        // Quit session (closes browser)
        driver.quit()
    }

    @Test
    fun `Without Helpful Markup Dues Ascending`() {
        driver.get("http://the-internet.herokuapp.com/tables")

        // sort dues column in ascending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click()

        // get values from dues column (w/o $)
        val dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"))

        val copy = mutableListOf<Double>()
        for (element in dues) {
            copy.add(element.text.replace("$", "").toDouble())
        }

        val copyAscending = copy.toMutableList()
        copyAscending.sort()

        assertEquals(copy, copyAscending)
    }

    @Test
    fun `Without Helpful Markup Dues Descending`() {
        driver.get("http://the-internet.herokuapp.com/tables")

        // sort dues column in descending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click()
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(4)")).click()

        // get values from dues column (w/o $) again
        val dues = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(4)"))

        val copy = LinkedList<Double>()
        //val copy = mutableListOf<Double>()
        for (element in dues) {
            copy.add(element.text.replace("$", "").toDouble())
        }

        val copyDescending = copy.toMutableList()
        copyDescending.sortDescending()

        assertEquals(copy, copyDescending)
    }

    @Test
    fun `Without Helpful Markup Email Ascending`() {
        driver.get("http://the-internet.herokuapp.com/tables")

        // sort email column in ascending order
        driver.findElement(By.cssSelector("#table1 thead tr th:nth-of-type(3)")).click()

        // get values from email column
        val emails = driver.findElements(By.cssSelector("#table1 tbody tr td:nth-of-type(3)"))

        val copy = mutableListOf<String>()
        for (element in emails) {
            copy.add(element.text)
        }

        val copyAscending = copy.toMutableList()
        copyAscending.sort()

        assertEquals(copy, copyAscending)
    }

    @Test
    fun `With Helpful Markup`() {
        driver.get("http://the-internet.herokuapp.com/tables")

        // sort dues column in ascending order
        driver.findElement(By.cssSelector("#table2 thead .dues")).click()

        // get values from dues column
        val dues = driver.findElements(By.cssSelector("#table2 tbody .dues"))

        val copy = mutableListOf<Double>()
        for (element in dues) {
            copy.add(element.text.replace("$", "").toDouble())
        }

        val copyAscending = copy.toMutableList()
        copyAscending.sort()

        assertEquals(copy, copyAscending)
    }
}