# http://elementalselenium.com/tips/5-select-from-a-dropdown
import unittest
from selenium import webdriver
from selenium.webdriver.support.select import Select as WebDriverSelect # for test_example_2

class ES5_DropDown(unittest.TestCase):
    
    def setUp(self):
        self.driver = webdriver.Firefox()

    def test_example_1(self):
        self.driver.get('http://the-internet.herokuapp.com/dropdown')
        dropdown_list = self.driver.find_element_by_id('dropdown')
        options = dropdown_list.find_elements_by_tag_name('option')
        for opt in options:
            if opt.text == 'Option 1':
                opt.click()
        for opt in options:
            if opt.is_selected():
                selected_option = opt.text
        assert(selected_option == 'Option 1')

    def test_example_2(self):
        self.driver.get('http://the-internet.herokuapp.com/dropdown')
        dropdown = self.driver.find_element_by_id('dropdown')
        select_list = WebDriverSelect(dropdown)
        select_list.select_by_visible_text('Option 1')
        selected_option = select_list.first_selected_option.text
        assert(selected_option == 'Option 1')

    def tearDown(self):
        self.driver.quit()
