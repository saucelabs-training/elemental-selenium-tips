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
  @driver.get 'http://the-internet.herokuapp.com/checkboxes'
  checkboxes = @driver.find_elements(css: 'input[type="checkbox"]')

  puts "With .attribute('checked')"
  checkboxes.each { |checkbox| puts checkbox.attribute('checked').inspect }

  puts "\nWith .selected?"
  checkboxes.each { |checkbox| puts checkbox.selected?.inspect }
end

run do
  @driver.get 'http://the-internet.herokuapp.com/checkboxes'
  checkboxes = @driver.find_elements(css: 'input[type="checkbox"]')
  expect(checkboxes.last.attribute('checked')).not_to be_nil
  # alternatively
  expect(checkboxes.last.attribute('checked')).to eql("true")
end

run do
  @driver.get 'http://the-internet.herokuapp.com/checkboxes'
  checkboxes = @driver.find_elements(css: 'input[type="checkbox"]')
  expect(checkboxes.last.selected?).to eql true
end
