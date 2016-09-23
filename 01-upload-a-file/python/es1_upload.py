# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/1-upload-a-file
"""

import os
import unittest
from selenium import webdriver


class ES1_Upload(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        filename = 'some-file.txt'
        file = os.path.join(os.getcwd(), filename)
        driver.get('http://the-internet.herokuapp.com/upload')
        driver.find_element_by_id('file-upload').send_keys(file)
        driver.find_element_by_id('file-submit').click()

        uploaded_file = driver.find_element_by_id('uploaded-files').text
        assert uploaded_file == filename, "uploaded file should be %s" % filename

if __name__ == "__main__":
    unittest.main()
