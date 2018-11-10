# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/22-locator-strategy
"""
import unittest

from selenium import webdriver

class LocatorStrategy(unittest.TestCase):

	def setUp(self):
		self.driver = webdriver.Firefox()

	def tearDown(self):
		self.driver.quit()

	def test_example_1(self):
		self.driver.get('http://the-internet.herokuapp.com/download')
		link = self.driver.find_element_by_css_selector('a').get_attribute('href')
		print(link)

	def test_example_2(self):
		self.driver.get('http://the-internet.herokuapp.com/download')
		link = self.driver.find_element_by_css_selector('#content a').get_attribute('href')
		print(link)

	def test_example_3(self):
		self.driver.get('http://the-internet.herokuapp.com/download')
		link = self.driver.find_element_by_css_selector('#content .example a').get_attribute('href')
		print(link)

if __name__ == '__main__':
	unittest.main()
