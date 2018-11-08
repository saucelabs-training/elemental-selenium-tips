# -*- coding: utf-8 -*-
"""
Implementation of http://elementalselenium.com/tips/39-drag-and-drop
	DragAndDrop is currently not available on selenium anymore without JS injection.
"""

import os
import time
import unittest

from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains

class DragAndDrop(unittest.TestCase):

	def setUp(self):
		self.driver = webdriver.Firefox()

	def tearDown(self):
		self.driver.quit()

	def test_example_1(self):
		self.driver.get('http://the-internet.herokuapp.com/drag_and_drop')

		source = self.driver.find_element_by_id('column-a')
		target = self.driver.find_element_by_id('column-b')

		ActionChains(self.driver).drag_and_drop(source, target).perform()

		time.sleep(3)

		assert (self.driver.find_element_by_id('column-a').text == 'B' and self.driver.find_element_by_id('column-b').text == 'A'), "Issue with classic drag-and-drop"

	def test_example_2(self):
		self.driver.get('http://the-internet.herokuapp.com/drag_and_drop')

		with open(os.path.dirname(os.path.abspath(__file__)) + '\dnd.js') as f:
			dndJS = f.read()

		self.driver.execute_script(dndJS + "$('#column-a').simulateDragDrop({dropTarget:'#column-b'});")

		time.sleep(3)

		assert (self.driver.find_element_by_id('column-a').text == 'B' and self.driver.find_element_by_id('column-b').text == 'A'), "Issue with JQuery drag-and-drop"

if __name__ == '__main__':
	unittest.main()