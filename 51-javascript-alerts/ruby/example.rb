# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'
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
  @driver.get 'http://the-internet.herokuapp.com/javascript_alerts'
  @driver.find_elements(css: 'button')[1].click

  popup = @driver.switch_to.alert
  popup.accept

  result = @driver.find_element(id: 'result').text
  expect(result).to eq('You clicked: Ok')
end
