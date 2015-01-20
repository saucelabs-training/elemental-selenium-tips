# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

def setup
  #@driver = Selenium::WebDriver.for :remote, desired_capabilities: :safari
  @driver = Selenium::WebDriver.for :safari
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
