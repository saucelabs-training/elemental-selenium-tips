# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/23-dynamic-pages
"""

import unittest
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


class Upload(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_dynamic_page_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/dynamic_loading/1')
        assert driver.find_element_by_css_selector('#start button').is_displayed()
        assert not driver.find_element_by_id("finish").is_displayed()
        driver.find_element_by_css_selector('#start button').click()
        try:
            element = WebDriverWait(driver, 6).until(
                EC.visibility_of_element_located((By.ID, "finish"))
            )
        finally:
            assert driver.find_element_by_id("finish").is_displayed()

    def test_dynamic_page_2(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/dynamic_loading/2')
        assert driver.find_element_by_css_selector('#start button').is_displayed()
        driver.find_element_by_css_selector('#start button').click()
        try:
            element = WebDriverWait(driver, 6).until(
                EC.visibility_of_element_located((By.ID, "finish"))
            )
        finally:
            assert driver.find_element_by_id("finish").is_displayed()


if __name__ == "__main__":
    unittest.main()