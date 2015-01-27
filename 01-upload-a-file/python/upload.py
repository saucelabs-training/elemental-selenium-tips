import os
import unittest
from selenium import webdriver


class UploadTest(unittest.TestCase):
    def setUp(self):
        self.driver = webdriver.Firefox()

        self.filename = 'some-file.txt'
        self.full_filename = os.path.dirname(os.path.realpath(__file__)) + '/' + self.filename

    def tearDown(self):
        self.driver.quit()


    def test_file_upload(self):
        self.driver.get('http://the-internet.herokuapp.com/upload')

        self.driver.find_element_by_id('file-upload').send_keys(self.full_filename)
        self.driver.find_element_by_id('file-submit').click()

        uploaded_file = self.driver.find_element_by_id('uploaded-files').text
        assert uploaded_file == self.filename


if __name__ == "__main__":
    suite = unittest.TestLoader().loadTestsFromTestCase(UploadTest)
    unittest.TextTestRunner(verbosity=2).run(suite)
