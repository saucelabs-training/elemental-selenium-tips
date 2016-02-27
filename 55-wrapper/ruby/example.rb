# filename: growl.rb

require 'selenium-webdriver'
require_relative 'growl_wrapper'

def setup
  @driver = Selenium::WebDriver.for :remote, listener: GrowlWrapper.new
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
  @driver.get 'http://the-internet.herokuapp.com'
  @driver.find_elements(css: 'a').last.click
end
