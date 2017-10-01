# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/19-data-driven-testing
"""

import csv
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

    def test_login(self):
        driver = self.driver
        with open('user_data.csv') as csvfile:
            reader = csv.DictReader(csvfile)
            for row in reader:
                driver.get('http://the-internet.herokuapp.com/login')
                driver.find_element_by_id('username').send_keys(row['username'])
                driver.find_element_by_id('password').send_keys(row['password'])
                driver.find_element_by_id('login').submit()
                element = WebDriverWait(driver, 6).until(
                    EC.visibility_of_element_located((By.CLASS_NAME, "flash"))
                )
                print row['username'], row['password']
                assert row['notification_message'] in driver.find_element_by_class_name('flash').text


if __name__ == "__main__":
    unittest.main()
