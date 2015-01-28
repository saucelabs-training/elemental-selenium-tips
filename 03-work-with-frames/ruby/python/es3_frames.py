# http://elementalselenium.com/tips/3-work-with-frames
import os
import unittest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys # for send_keys

class ES3_Frames(unittest.TestCase):
    
    def setUp(self):
        self.driver = webdriver.Firefox()

    def test_example_1(self):
        self.driver.get('http://the-internet.herokuapp.com/nested_frames')
        self.driver.switch_to.frame('frame-top')
        self.driver.switch_to.frame('frame-middle')
        assert(self.driver.find_element_by_id('content').text == "MIDDLE")
        
    def test_example_2(self):
        self.driver.get('http://the-internet.herokuapp.com/tinymce')
        self.driver.switch_to.frame('mce_0_ifr')
        editor = self.driver.find_element_by_id('tinymce')
        before_text = editor.text
        editor.clear()
        editor.send_keys('Hello World!')
        after_text = editor.text
        assert(after_text != before_text)
        
        self.driver.switch_to.default_content()
        assert(self.driver.find_element_by_css_selector('h3').text != "")

    def tearDown(self):
        self.driver.quit()
