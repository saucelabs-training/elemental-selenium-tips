# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/17-retrieve-http-status-codes
"""
import time
import unittest

from selenium import webdriver
from browsermobproxy import Server

class RetrieveHTTPStatusCodes(unittest.TestCase):

	def setUp(self):
		self.configure_proxy()

		profile = webdriver.FirefoxProfile()
		profile.set_proxy(self.proxy.selenium_proxy())
		profile.update_preferences()

		self.driver = webdriver.Firefox(firefox_profile=profile)

	def tearDown(self):
		self.server.stop()
		self.driver.quit()

	def configure_proxy(self):
		self.server = Server('vendor/browsermob-proxy/bin/browsermob-proxy.bat')
		self.server.start()
		self.proxy = self.server.create_proxy()

	def test_example_1(self):
		self.proxy.new_har('herokuapp')
		self.driver.get('http://the-internet.herokuapp.com/status_codes/404')

		status_code = self.proxy.har['log']['entries'][0]['response']['status']
		assert status_code == 404, "Status code is %s" % status_code

if __name__ == '__main__':
	unittest.main()

