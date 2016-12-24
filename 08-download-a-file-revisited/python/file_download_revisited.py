# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/8-download-a-file-revisited
"""

import unittest
from selenium import webdriver
import httplib # Use http.client if using Python 3.x.x


class FileDownloadRevisited(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/download')
        download_link = driver.find_element_by_css_selector('.example a').get_attribute('href')

        connection = httplib.HTTPConnection('the-internet.herokuapp.com')
        connection.request('HEAD', download_link)
        response = connection.getresponse()
        content_type = response.getheader('Content-type')
        content_length = response.getheader('Content-length')

        assert content_type == 'application/octet-stream'
        assert content_length > 0

if __name__ == "__main__":
    unittest.main()
