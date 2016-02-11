# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/4-work-with-multiple-windows
"""

import unittest
from selenium import webdriver


class ES4_Windows(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/windows')
        driver.find_element_by_css_selector('.example a').click()
        driver.switch_to_window(driver.window_handles[0])
        assert driver.title != "New Window", "title should not be New Window"
        driver.switch_to_window(driver.window_handles[-1])
        assert driver.title == "New Window", "title should be New Window"

    def test_example_2(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/windows')

        first_window = driver.window_handles[0]
        driver.find_element_by_css_selector('.example a').click()
        all_windows = driver.window_handles
        for window in all_windows:
            if window != first_window:
                new_window = window
        driver.switch_to_window(first_window)
        assert driver.title != "New Window", "title should not be New Window"
        driver.switch_to_window(new_window)
        assert driver.title == "New Window", "title should be New Window"

if __name__ == "__main__":
    unittest.main()
