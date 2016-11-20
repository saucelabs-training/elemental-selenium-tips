# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/12-opt-out-of-ab-tests
"""

import unittest
from selenium import webdriver


class ABTestOptOut(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Firefox()

    def tearDown(self):
        self.driver.quit()

    def test_forge_cookie_on_target_page(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/abtest')
        heading_text = driver.find_element_by_tag_name('h3').text
        assert heading_text in ['A/B Test Variation 1', 'A/B Test Control']
        driver.add_cookie({'name' : 'optimizelyOptOut', 'value' : 'true'})
        driver.refresh()
        heading_text = driver.find_element_by_tag_name('h3').text
        assert heading_text == 'No A/B Test'

    def test_forge_cookie_on_homepage_then_navigate_to_target_page(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com')
        driver.add_cookie({'name' : 'optimizelyOptOut', 'value' : 'true'})
        driver.get('http://the-internet.herokuapp.com/abtest')
        heading_text = driver.find_element_by_tag_name('h3').text
        assert heading_text == 'No A/B Test'

    def test_url_parameter(self):
        driver = self.driver
        driver.get('http://the-internet.herokuapp.com/abtest?optimizely_opt_out=true')
        driver.switch_to.alert.dismiss()
        heading_text = driver.find_element_by_tag_name('h3').text
        assert heading_text == 'No A/B Test'

if __name__ == "__main__":
    unittest.main()
