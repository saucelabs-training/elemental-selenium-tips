# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/53-growl
"""

import unittest
from selenium import webdriver
import time


class Growl(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com')

        # Check for jQuery on the page, add it if needbe
        driver.execute_script(
            "if (!window.jQuery) {"
            "var jquery = document.createElement('script');"
            "jquery.type = 'text/javascript';"
            "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js';"
            "document.getElementsByTagName('head')[0].appendChild(jquery);}")

        # Use jQuery to add jquery-growl to the page
        driver.execute_script("$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')")

        # Use jQuery to add jquery-growl styles to the page
        driver.execute_script(
            "$('head').append('"
            "<link rel=stylesheet "
            "href=http://the-internet.herokuapp.com/css/jquery.growl.css "
            "type=text/css />');")

        # add delay for resource loading
        time.sleep(1)

        # jquery-growl w/ no frills
        driver.execute_script("$.growl({ title: 'GET', message: '/' });")

        # jquery-growl w/ colorized output
        driver.execute_script("$.growl.error({ title: 'ERROR', message: 'your error message goes here' });")
        driver.execute_script("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });")
        driver.execute_script("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });")

        time.sleep(5)

if __name__ == "__main__":
    unittest.main()
