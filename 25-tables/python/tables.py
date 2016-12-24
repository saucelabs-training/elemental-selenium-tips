# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/25-tables
"""

import unittest
from selenium import webdriver


class Tables(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_sort_number_column_in_ascending_order_with_limited_locators(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/tables')
        driver.find_element_by_css_selector('#table1 thead tr th:nth-of-type(4)').click()
        due_column = driver.find_elements_by_css_selector('#table1 tbody tr td:nth-of-type(4)')
        dues = [float(due.text.replace('$','')) for due in due_column]
        assert dues == sorted(dues)

    def test_sort_number_column_in_descending_order_with_limited_locators(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/tables')
        driver.find_element_by_css_selector('#table1 thead tr th:nth-of-type(4)').click()
        driver.find_element_by_css_selector('#table1 thead tr th:nth-of-type(4)').click()
        due_column = driver.find_elements_by_css_selector('#table1 tbody tr td:nth-of-type(4)')
        dues = [float(due.text.replace('$','')) for due in due_column]
        assert dues == sorted(dues, reverse=True)

    def test_sort_text_column_in_ascending_order_with_limited_locators(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/tables')
        driver.find_element_by_css_selector('#table1 thead tr th:nth-of-type(3)').click()
        email_column = driver.find_elements_by_css_selector('#table1 tbody tr td:nth-of-type(3)')
        emails = [email.text for email in email_column]
        assert emails == sorted(emails)

    def test_sort_number_column_in_ascending_order_with_helpful_locators(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/tables')
        driver.find_element_by_css_selector('#table2 thead .dues').click()
        due_column = driver.find_elements_by_css_selector('#table2 tbody .dues')
        dues = [float(due.text.replace('$','')) for due in due_column]
        assert dues == sorted(dues)

if __name__ == "__main__":
    unittest.main()
