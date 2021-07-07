import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import java.util.*

//11-build-an-interactive-prompt
fun main(args: Array<String>) {
    InteractivePrompt().main()
}

class InteractivePrompt {

    fun main() {
        val driver = getDriver()
        val userInput = readUserInput()
        while (true) {
            if (userInput.toLowerCase() == "q") {
                println("Quitting...")
                driver.quit()
                System.exit(0)
            }
        }
    }

    private fun getDriver(): WebDriver {
        return FirefoxDriver()
    }

    private fun readUserInput(): String {

        /* Alternative via Scanner:
         * val scan = Scanner(System.`in`)
         * return scan.nextLine()
         */

        println("Enter a command: ")
        return readLine()!!
    }
}