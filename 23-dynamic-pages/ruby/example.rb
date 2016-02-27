# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

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

# RETURNS: Unable to locate element: {"method":"css selector","selector":"#finish"} (Selenium::WebDriver::Error::NoSuchElementError)
#run do
#  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
#  @driver.find_element(css: '#start button').click
#  expect(@driver.find_element(id: 'finish').displayed?).to eql true
#end

# WORKS
run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
  @driver.find_element(css: '#start button').click
  Selenium::WebDriver::Wait.new(timeout: 6).until { @driver.find_element(id: 'finish').displayed? }
  expect(@driver.find_element(id: 'finish').displayed?).to eql true
end

# RETURNS: `until': timed out after 2 seconds (Selenium::WebDriver::Error::TimeOutError)
#run do
#  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
#  @driver.find_element(css: '#start button').click
#  Selenium::WebDriver::Wait.new(timeout: 2).until { @driver.find_element(id: 'finish') }
#  expect(@driver.find_element(id: 'finish').displayed?).to eql true
#end

## WORKS ON BOTH
## + cleanup

def wait_for(seconds = 6)
  Selenium::WebDriver::Wait.new(:timeout => seconds).until { yield }
end

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/1'
  @driver.find_element(css: '#start button').click
  wait_for { @driver.find_element(id: 'finish').displayed? }
  expect(@driver.find_element(id: 'finish').displayed?).to eql true
end
