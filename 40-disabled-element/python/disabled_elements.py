# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/40-disabled_element
"""

import unittest
from selenium import webdriver


class DisabledElements(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_dropdown(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/dropdown')
        dropdown_list = driver.find_elements_by_tag_name('option')
        assert dropdown_list[0].is_enabled() is False
        assert dropdown_list[1].is_enabled() is True

if __name__ == "__main__":
    unittest.main()
