# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/16-take-screenshot-on-failure
"""

import sys
import unittest
from selenium import webdriver


class ScreenShotOnFailure(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        if sys.exc_info()[0]:
            self.driver.save_screenshot("failshot_%s.png" % self._testMethodName)
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com')
        assert driver.title == 'blah blah blah'

if __name__ == "__main__":
    unittest.main()
