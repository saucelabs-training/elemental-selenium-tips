# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/15-download-secure-files
"""
import unittest
import requests

from selenium import webdriver

class DownloadSecureFiles(unittest.TestCase):

	def setUp(self):
		self.driver = webdriver.Firefox()

	def tearDown(self):
		self.driver.quit()

	def test_example_1(self):
		self.driver.get('http://admin:admin@the-internet.herokuapp.com/download_secure')

		cookie = self.driver.get_cookies()[0]
		link = self.driver.find_element_by_css_selector('.example a').get_attribute('href')

		response = requests.get(link, cookies={cookie['name']:cookie['value']})
		content_type = response.headers.get('content-type')
		content_length = response.headers.get('content-length')

		assert content_type == 'application/pdf', "Content type is %s, excepted application/pdf." % content_type
		assert content_length > 0, "Content length is %s" % content_length

	def content_type(self, file):
		if '.jpg' in file:
			return 'image/jpeg'
		elif '.pdf' in file:
			return 'application/pdf'
		return 'Unknown file type'

	def test_example_2(self):
		self.driver.get('http://admin:admin@the-internet.herokuapp.com/download_secure')

		cookie = self.driver.get_cookies()[0]
		links = self.driver.find_elements_by_css_selector('.example a')
		for k in links:
			# try:
			link = k.get_attribute('href')

			response = requests.get(link, cookies={cookie['name']:cookie['value']})
			content_type = response.headers.get('content-type')
			content_length = response.headers.get('content-length')

			assert content_type == self.content_type(link), "Content type is %s" % self.content_type(link)
			assert content_length > 0, "Content length is %s" % content_length 

			# except AssertionError as e:
			# 	print(e)
			# 	continue;

if __name__ == '__main__':
	unittest.main()