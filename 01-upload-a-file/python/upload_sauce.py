import os
import sys
import unittest
from sauceclient import SauceClient
from selenium import webdriver


SAUCE_USERNAME = os.environ.get('SAUCE_USERNAME')
SAUCE_ACCESS_KEY = os.environ.get('SAUCE_ACCESS_KEY')
sauce = SauceClient(SAUCE_USERNAME, SAUCE_ACCESS_KEY)


class UploadSauceTest(unittest.TestCase):
    def setUp(self):
        caps = webdriver.DesiredCapabilities.INTERNETEXPLORER

        # massage to the correct state
        caps['version'] = '8'
        caps['platform'] = 'Windows XP'
        caps['name'] = 'File Upload Test'

        # http://saucelabs.com/docs/additional-config
        caps['tags'] = ['elemental selenium', '01']

        self.driver = webdriver.Remote(
            desired_capabilities=caps,
            command_executor="http://%s:%s@ondemand.saucelabs.com:80/wd/hub" % (SAUCE_USERNAME, SAUCE_ACCESS_KEY))

        self.filename = 'some-file.txt'
        self.full_filename = os.path.dirname(os.path.realpath(__file__)) + '/' + self.filename

    def tearDown(self):
        print("Link to your job: https://saucelabs.com/jobs/%s" % self.driver.session_id)
        try:
            if sys.exc_info() == (None, None, None):
                sauce.jobs.update_job(self.driver.session_id, passed=True)
            else:
                sauce.jobs.update_job(self.driver.session_id, passed=False)
        finally:
            self.driver.quit()


    def test_file_upload(self):
        self.driver.get('http://the-internet.herokuapp.com/upload')

        self.driver.find_element_by_id('file-upload').send_keys(self.full_filename)
        self.driver.find_element_by_id('file-submit').click()

        uploaded_file = self.driver.find_element_by_id('uploaded-files').text
        assert uploaded_file == self.filename


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(UploadSauceTest)
    unittest.TextTestRunner(verbosity=2).run(suite)
