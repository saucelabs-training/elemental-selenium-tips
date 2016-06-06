# -*- coding: utf-8 -*-
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import NoAlertPresentException
import unittest, time, re
import selenium.webdriver.opera.service as service


# execute pip install -r requirements.txt from terminal before execution
class Test(unittest.TestCase):
    def setUp(self):
    	self.webdriver_service = service.Service('../../vendor/operadriver')
        self.webdriver_service.start()
        self.driver = webdriver.Remote(webdriver_service.service_url, webdriver.DesiredCapabilities.OPERA)
	    self.driver.implicitly_wait(30)
        self.base_url = "http://the-internet.herokuapp.com/"
        self.verificationErrors = []
        self.accept_next_alert = True
    
    def test_(self):
        driver = self.driver
        driver.get(self.base_url);
        title = driver.get_title();
        self.assertEqual(title,"The Internet")
    
    def tearDown(self):
        self.driver.quit()
        self.assertEqual([], self.verificationErrors)

if __name__ == "__main__":
    unittest.main()
