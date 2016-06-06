# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

def setup
  opera_path = File.join(Dir.pwd, '..', '..',"vendor","operadriver")
  Selenium::WebDriver::Opera.driver_path = opera_path
  @driver = Selenium::WebDriver.for :opera
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
  expect(@driver.title).to eql 'The Internet'
end
