# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/45-checkboxes
"""

import unittest
from selenium import webdriver


class Checkboxes(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_list_values_for_different_approaches(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/checkboxes')
        checkboxes = driver.find_elements_by_css_selector('input[type="checkbox"]')

        print("With .get_attribute('checked')")
        for checkbox in checkboxes:
            print(checkbox.get_attribute('checked'))

        print("\nWith .is_selected")
        for checkbox in checkboxes:
            print(checkbox.is_selected())

        assert checkboxes[-1].get_attribute('checked')
        assert checkboxes[-1].is_selected()
        assert checkboxes[0].get_attribute('checked')
        assert checkboxes[0].is_selected()

if __name__ == "__main__":
    unittest.main()
