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

run do
  @driver.get 'http://the-internet.herokuapp.com/key_presses'
  @driver.find_element(class: 'example').send_keys :space
  expect(@driver.find_element(id: 'result').text).to eql('You entered: SPACE')
  @driver.action.send_keys(:tab).perform
  expect(@driver.find_element(id: 'result').text).to eql('You entered: TAB')
end
