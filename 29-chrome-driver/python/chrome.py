# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/29-chrome
"""

import os
import unittest
from selenium import webdriver


class Chrome(unittest.TestCase):

    def setUp(self):
        #chromedriver_path = os.getcwd() + '/vendor/chromedriver'
        #self.driver = webdriver.Chrome(chromedriver_path)
        self.driver = webdriver.Remote('http://localhost:9515', {'browserName': 'chrome'})

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/upload')
        assert driver.title == 'The Internet'

if __name__ == "__main__":
    unittest.main()
