# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/10-retry-test-actions
"""

import unittest

from selenium import webdriver
from selenium.webdriver.support.wait import WebDriverWait

class RetryAction(unittest.TestCase):

	def setUp(self):
		self.base_url = 'http://the-internet.herokuapp.com/notification_message'
		self.err_message = 'please try again'

		self.driver = webdriver.Firefox()

		self.driver.get(self.base_url)

	def tearDown(self):
		self.driver.quit()

	def notification_message(self):
		elem = WebDriverWait(self.driver, 10).until(
			lambda x: x.find_element_by_id('flash'))

		return not(self.err_message in elem.text)

	def test_example_1(self):
		assert (self.notification_message()), "Error message"

	def test_example_2(self):
		count = 0
		while not self.notification_message() and count < 3:
			self.driver.get(self.base_url)
			count += 1

		assert (self.notification_message()), "Error message after %s retries" % str(count)


if __name__ == '__main__':
	unittest.main()