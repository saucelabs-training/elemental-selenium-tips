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

# Part 1: Discovery
run do
  @driver.get 'http://the-internet.herokuapp.com/checkboxes'
  checkboxes = @driver.find_elements(css: 'input[type="checkbox"]')

  puts "With .attribute('checked')"
  checkboxes.each { |checkbox| puts checkbox.attribute('checked').inspect }

  puts "\nWith .selected?"
  checkboxes.each { |checkbox| puts checkbox.selected?.inspect }
end

# Part 2: Option 1
run do
  @driver.get 'http://the-internet.herokuapp.com/checkboxes'
  checkboxes = @driver.find_elements(css: 'input[type="checkbox"]')
  checkboxes.last.attribute('checked').should_not be_nil
end

# Part 3: Option 2
run do
  @driver.get 'http://the-internet.herokuapp.com/checkboxes'
  checkboxes = @driver.find_elements(css: 'input[type="checkbox"]')
  checkboxes.last.selected?.should be_true
end
