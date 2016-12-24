# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/65-highlight-elements
"""

import unittest
from selenium import webdriver
import time


class HighlightElements(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def highlight(self, element, duration=3):
        driver = self.driver

        # Store original style so it can be reset later
        original_style = element.get_attribute("style")

        # Style element with dashed red border
        driver.execute_script(
            "arguments[0].setAttribute(arguments[1], arguments[2])",
            element,
            "style",
            "border: 2px solid red; border-style: dashed;")

        # Keep element highlighted for a spell and then revert
        if (duration > 0):
            time.sleep(duration)
            driver.execute_script(
                "arguments[0].setAttribute(arguments[1], arguments[2])",
                element,
                "style",
                original_style)

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/large')
        self.highlight(driver.find_element_by_id('sibling-2.3'))

if __name__ == "__main__":
    unittest.main()
