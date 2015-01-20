# Encoding: utf-8

require 'selenium-webdriver'
require_relative 'wrapper'
require 'rspec-expectations'
include RSpec::Matchers

def setup
  @driver = Selenium::WebDriver.for :firefox, listener: Wrapper.new
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
