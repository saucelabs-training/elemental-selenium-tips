# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/13-work-with-basic-auth
"""

import unittest
from selenium import webdriver


class BasicAuth1(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()
        self.driver.get('http://admin:admin@the-internet.herokuapp.com/basic_auth')

    def tearDown(self):
        self.driver.quit()

    def test_visit_basic_auth_secured_page(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/basic_auth')
        page_message = driver.find_element_by_css_selector('.example p').text
        assert page_message == 'Congratulations! You must have the proper credentials.'

if __name__ == "__main__":
    unittest.main()
