# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/44-exception-handling
"""
import unittest

from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import StaleElementReferenceException

class ExceptionHandling(unittest.TestCase):

	def setUp(self):
		self.driver = webdriver.Firefox()

	def tearDown(self):
		self.driver.quit()

	def test_example_1(self):
		self.driver.get('http://the-internet.herokuapp.com/login')
		self.driver.find_element_by_id('username').send_keys('tomsmith')
		self.driver.find_element_by_id('password').send_keys('SuperSecretPassword!')
		self.driver.find_element_by_id('login').submit()

		return self.driver.find_element_by_id('login').is_displayed() == False

	def test_example_2(self):
		self.driver.get('http://the-internet.herokuapp.com/login')
		self.driver.find_element_by_id('username').send_keys('tomsmith')
		self.driver.find_element_by_id('password').send_keys('SuperSecretPassword!')
		self.driver.find_element_by_id('login').submit()

		try:
			return self.driver.find_element_by_id('login').is_displayed() == False
		except NoSuchElementError:
			return False
		except StaleElementReferenceException:
			return False

	def is_displayed(self, locator):
		try:
			return self.driver.find_element_by_id(locator).is_displayed()
		except NoSuchElementError:
			return False
		except StaleElementReferenceException:
			return False

	def test_example_3(self):
		self.driver.get('http://the-internet.herokuapp.com/login')
		self.driver.find_element_by_id('username').send_keys('tomsmith')
		self.driver.find_element_by_id('password').send_keys('SuperSecretPassword!')
		self.driver.find_element_by_id('login').submit()

		return self.is_displayed('login') == False


if __name__ == '__main__':
	unittest.main()