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
  @driver.get 'http://the-internet.herokuapp.com/dropdown'
  dropdowns = @driver.find_elements(tag_name: 'option')
  item_of_interest = dropdowns.find { |dropdown| dropdown.text == 'Please select an option' }
  #item_of_interest = dropdowns.find { |dropdown| dropdown.text == 'Option 1' }
  item_of_interest.enabled?.should be_false
end
