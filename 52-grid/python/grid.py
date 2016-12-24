# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/52-grid
"""

import unittest
from selenium import webdriver


class Grid(unittest.TestCase):

    def setUp(self):
        url = 'http://localhost:4444/wd/hub'
        desired_caps = {}
        desired_caps['browserName'] = 'firefox'
        #desired_caps['browserName'] = 'chrome'
        self.driver = webdriver.Remote(url, desired_caps)

    def tearDown(self):
        self.driver.quit()

    def test_page_loaded(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com')
        assert driver.title == 'The Internet'

if __name__ == "__main__":
    unittest.main()
