require 'selenium-webdriver'
require 'rspec-expectations'

def setup
  @driver = Selenium::WebDriver.for :firefox
  @driver.get 'http://admin:admin@the-internet.herokuapp.com/basic_auth'
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
  @driver.get 'http://the-internet.herokuapp.com/basic_auth'
  page_message = @driver.find_element(css: 'p').text
  page_message.should =~ /Congratulations!/
end
