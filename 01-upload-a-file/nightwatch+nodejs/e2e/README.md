README for upload_file_using_nightwatchjs_plus_nodejs
----------------------------------------------------
Author: T.J. Maher <http://www.tjmaher.com/>, Software Engineer in Test, QA Engineer since 1996.

The code in this subfolder tracks along with Dave Haefner's Elemental Selenium blog, Tip #1: How to Upload a File in Selenium <http://elementalselenium.com/tips/1-upload-a-file>, runs using Google Chrome, and is written in Nightwatch.js, Node.js, and JavaScript. 

Don't have Nightwatch.Js or Node.js? It is a free install for Mac, Unix, or Windows. 

Note: This code in this subfolder was developed on a Windows 10 machine and may need to be tweaked, such as removing ".exe" from the expected Chromedriver and Geckodriver software packages at elemental-selenium-tips/01-upload-a-file/nightwatch+nodejs/e2e/nightwatch.json. 

Why a JavaScript automation solution?
-----------------------------------

With the popularity of using Node.js for developing web applications, such as VueJS <https://vuejs.org/> for designing JavaScript based front-ends, automated testing solutions needed to be created that could pair well with them. 

Protractor <http://www.protractortest.org/>, specifically created to test AngularJS <https://angularjs.org/> web applications, is one example of a JavaScript testing solution. SeleniumJs is another, covered in Dave Haeffner's Selenium Guidebook: JavaScript Edition <https://seleniumguidebook.com/>. This folder covers another automation solution, Nightwatch.Js <http://nightwatchjs.org/> adopted by both the software developers, including it amongst their unit and integration tests, and the automation developers designing end-to-end tests, at my current workplace.


With this first project, the Nightwatch is written very simply, without Page Objects, reports, or anything.

How to Install:
---------------
1) Get the source code, if you haven't already: Go to Dave Haeffner's GitHub site <https://github.com/tourdedave/elemental-selenium-tips> and select the big green "Clone or Download" button, and download it to your local machine. Since I use Windows at home, I usually place my code in a new sub-directory, "code", at C:\Users\tmaher\code\, since I am T. Maher. 

2) Download and install Node.js <https://nodejs.org/>. This will give you access to the very helpful Node Package Manager (npm).

3) Open the command line on your local machine, either the Terminal or the MS-DOS Command Prompt, and go to the location of the Nightwatch end-to-end tests at elemental-selenium-tips/01-upload-a-file/nightwatch+nodejs/e2e/ 

4) Update the Node Package Manager by using the Node Package Manager, setting it to be executed globally, i.e. anywhere on your system, not just in this folder or directory: Type: *npm install -g npm*      

5) Download Nightwatch.js: Go into the directory: elemental-selenium-tips/01-upload-a-file/nightwatch+nodejs/e2e/ and type: *npm install -g nightwatch*

6) Download all dependencies for Nighwatch, listed in the package.json file, onto your computer. Files such as Chromedriver (either chromedriver.exe or chromedriver): *npm install*

7) Not using a Windows machine? Search package.json for the Windows Executable extension ".exe" in the cli_arguments, and delete that text from the file. They should be listed next to webdriver.chrome.driver.

8) Run all the tests from the command line in the default browser (Chrome): *npm run tests*





