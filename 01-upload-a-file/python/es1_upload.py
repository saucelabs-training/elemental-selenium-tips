# http://elementalselenium.com/tips/1-upload-a-file
import os
import unittest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys # for send_keys

class ES1_Upload(unittest.TestCase):
    
    def setUp(self):
        self.driver = webdriver.Firefox()

    def test_example_1(self):
        filename = 'some-file.txt'
        file = os.path.join(os.getcwd(), filename)
        self.driver.get('http://the-internet.herokuapp.com/upload')
        self.driver.find_element_by_id('file-upload').send_keys(file)
        self.driver.find_element_by_id('file-submit').click()
        
        uploaded_file = self.driver.find_element_by_id('uploaded-files').text
        assert(uploaded_file == filename)

    def tearDown(self):
        self.driver.quit()
