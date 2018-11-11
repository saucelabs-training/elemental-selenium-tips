# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/46-headless-ghostdrivers
"""
import unittest

from selenium import webdriver

class HeadlessGhostdriver(unittest.TestCase):

	def setUp(self):
		self.driver = webdriver.Remote(command_executor='http://localhost:8001')

	def tearDown(self):
		self.driver.quit()

	def test_example_1(self):
		self.driver.get('http://the-internet.herokuapp.com')

		return self.driver.title == 'The Internet'

if __name__ == '__main__':
	unittest.main()