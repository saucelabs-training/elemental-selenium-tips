# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/50-hovers
"""

import unittest
from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains


class Hovers(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/hovers')
        avatar = driver.find_element_by_class_name('figure')
        ActionChains(driver).move_to_element(avatar).perform()
        avatar_caption = driver.find_element_by_class_name('figcaption')
        assert avatar_caption.is_displayed()

if __name__ == "__main__":
    unittest.main()
