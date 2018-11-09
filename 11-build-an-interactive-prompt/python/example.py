# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/11-build-an-interactive-prompt
"""
import unittest

from selenium import webdriver

class InteractivePrompt(unittest.TestCase):

	def setUp(self):
		self.driver = webdriver.Firefox()

	def tearDown(self):
		self.driver.quit()

	def test_example_1(self):
		while True:
			action = input('>> ')
			if action == 'q':
				print('Quitting..')
				return 0 #sys.exit(0)
			try:
				exec(action)
			except Exception as e:
				print(e)

if __name__ == '__main__':
	unittest.main()