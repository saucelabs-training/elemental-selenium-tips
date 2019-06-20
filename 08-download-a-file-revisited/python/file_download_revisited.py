# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/8-download-a-file-revisited
"""

import unittest
from selenium import webdriver
import http.client # Use httplib if using Python 2.x.x


class FileDownloadRevisited(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/download')
        download_link = driver.find_element_by_css_selector('.example a').get_attribute('href')

        connection = http.client.HTTPConnection('the-internet.herokuapp.com')
        connection.request('HEAD', download_link)
        response = connection.getresponse()
        connection.close()
        content_type = response.getheader('Content-type')
        content_length = int(response.getheader('Content-length'))

        assert content_type == 'application/octet-stream'
        assert content_length > 0

if __name__ == "__main__":
    unittest.main()
