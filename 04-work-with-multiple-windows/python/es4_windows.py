# http://elementalselenium.com/tips/4-work-with-multiple-windows
import os
import unittest
from selenium import webdriver
from selenium.webdriver.common.keys import Keys # for send_keys

class ES4_Windows(unittest.TestCase):
    
    def setUp(self):
        self.driver = webdriver.Firefox()

    def test_example_1(self):
        self.driver.get('http://the-internet.herokuapp.com/windows')
        self.driver.find_element_by_css_selector('.example a').click()
        self.driver.switch_to_window(self.driver.window_handles[0])
        assert(self.driver.title != "New Window")
        self.driver.switch_to_window(self.driver.window_handles[-1])
        assert(self.driver.title == "New Window")
        
    def test_example_2(self):
        self.driver.get('http://the-internet.herokuapp.com/windows')
       
        first_window = self.driver.window_handles[0]
        self.driver.find_element_by_css_selector('.example a').click()
        all_windows = self.driver.window_handles
        for window in all_windows:
            if window != first_window:
                new_window = window
        self.driver.switch_to_window(first_window)
        assert(self.driver.title != "New Window")
        self.driver.switch_to_window(new_window)
        assert(self.driver.title == "New Window")
        
    def tearDown(self):
        self.driver.quit()
