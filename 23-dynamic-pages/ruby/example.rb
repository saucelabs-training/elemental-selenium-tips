# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'

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
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
  @driver.find_element(css: '#start button').click
  @driver.find_element(css: '#finish').text.should =~ /Hello World!/
end

# Unable to locate element: {"method":"css selector","selector":"#finish"} (Selenium::WebDriver::Error::NoSuchElementError)

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
  @driver.find_element(css: '#start button').click
  Selenium::WebDriver::Wait.new(:timeout => 8).until { @driver.find_element(css: '#finish') }
  @driver.find_element(css: '#finish').text.should =~ /Hello World!/
end

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
  @driver.find_element(css: '#start button').click
  Selenium::WebDriver::Wait.new(:timeout => 2).until { @driver.find_element(css: '#finish') }
  @driver.find_element(css: '#finish').text.should =~ /Hello World!/
end

# `until': timed out after 2 seconds (Selenium::WebDriver::Error::TimeOutError)

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/1'
  @driver.find_element(css: '#start button').click
  Selenium::WebDriver::Wait.new(:timeout => 8).until { @driver.find_element(css: '#finish') }
  @driver.find_element(css: '#finish').text.should =~ /Hello World!/
end

# expected: /Hello World!/ (RSpec::Expectations::ExpectationNotMetError) got: "" (using =~)

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
  @driver.find_element(css: '#start button').click
  Selenium::WebDriver::Wait.new(:timeout => 8).until { @driver.find_element(css: '#finish').displayed? }
  @driver.find_element(css: '#finish').text.should =~ /Hello World!/
end

# Works on both

def wait_for(seconds=8)
  Selenium::WebDriver::Wait.new(:timeout => seconds).until { yield }
end

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/1'
  @driver.find_element(css: '#start button').click
  wait_for { @driver.find_element(css: '#finish').displayed? }
  @driver.find_element(css: '#finish').text.should =~ /Hello World!/
end
