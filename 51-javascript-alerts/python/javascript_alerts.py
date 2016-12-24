# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/51-javascript-alerts
"""

import unittest
from selenium import webdriver


class JavaScriptAlerts(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/javascript_alerts')
        driver.find_elements_by_tag_name('button')[1].click()
        popup = driver.switch_to.alert
        popup.accept()
        result = driver.find_element_by_id('result').text
        assert result == 'You clicked: Ok'

if __name__ == "__main__":
    unittest.main()
