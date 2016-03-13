import os

from selenium.webdriver.common.by import By
from selenium.webdriver.firefox import webdriver


class Browser(webdriver.WebDriver):
    def __init__(self):
        super().__init__(webdriver.FirefoxProfile())

    def __del__(self):
        self.close()


def test_upload():
    browser = Browser()
    filename = "some-file.txt"
    open(filename, 'a')

    browser.get("http://the-internet.herokuapp.com/upload")
    browser.find_element(By.ID, "file-upload").send_keys(os.path.abspath(filename))
    browser.find_element(By.ID, "file-submit").click()

    os.remove(filename)
    text = browser.find_element(By.ID, "uploaded-files").text
    assert text == filename
