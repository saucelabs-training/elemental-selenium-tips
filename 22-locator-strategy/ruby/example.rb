require 'selenium-webdriver'

def setup
  @driver = Selenium::WebDriver.for :firefox
end

def teardown
  @driver.quit
end

def run
  setup
  yield
  teardown
end

run do
  @driver.get 'http://the-internet.herokuapp.com/download'
  link = @driver.find_element(css: 'a').attribute('href')
  puts link
end

run do
  @driver.get 'http://the-internet.herokuapp.com/download'
  link = @driver.find_element(css: '#content a').attribute('href')
  puts link
end

run do
  @driver.get 'http://the-internet.herokuapp.com/download'
  link = @driver.find_element(css: '#content .example a').attribute('href')
  puts link
end
