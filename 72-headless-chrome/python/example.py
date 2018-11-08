# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/72-headless-chrome
"""

import unittest

from selenium import webdriver
from selenium.webdriver.chrome.options import Options

class Headless(unittest.TestCase):

	def setUp(self):
		options = Options()
		options.headless = True
		self.driver = webdriver.Chrome(options=options)

	def tearDown(self):
		self.driver.quit()

	def test_example_1(self):
		self.driver.get('http://the-internet.herokuapp.com')

		self.driver.save_screenshot('headless.png')
if __name__ == '__main__':
	unittest.main()