# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/63-right-click
"""

import unittest
from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.keys import Keys


class RightClick(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/context_menu')
        menu_area = driver.find_element_by_id('hot-spot')
        ActionChains(driver).context_click(
            menu_area).send_keys(
            Keys.ARROW_DOWN).send_keys(
            Keys.ARROW_DOWN).send_keys(
            Keys.ARROW_DOWN).send_keys(
            Keys.ENTER).perform()
        alert = driver.switch_to.alert
        assert alert.text == 'You selected a context menu'

if __name__ == "__main__":
    unittest.main()
