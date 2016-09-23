# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/3-work-with-frames
"""

import unittest
from selenium import webdriver


class Frames(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_example_1(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/nested_frames')
        driver.switch_to.frame('frame-top')
        driver.switch_to.frame('frame-middle')
        assert driver.find_element_by_id('content').text == "MIDDLE", "content should be MIDDLE"

    def test_example_2(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/tinymce')
        driver.switch_to.frame('mce_0_ifr')
        editor = driver.find_element_by_id('tinymce')
        before_text = editor.text
        editor.clear()
        editor.send_keys('Hello World!')
        after_text = editor.text
        assert after_text != before_text, "%s equals %s" % (before_text, after_text)

        driver.switch_to.default_content()
        assert driver.find_element_by_css_selector('h3').text != "", "h3 elemet should not be empty"

if __name__ == "__main__":
    unittest.main()
