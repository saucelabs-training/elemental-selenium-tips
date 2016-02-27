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
  @driver.get 'http://the-internet.herokuapp.com/hovers'
  an_avatar = @driver.find_element(class: 'figure')
  @driver.action.move_to(an_avatar).perform
  expect(@driver.find_element(class: 'figcaption').displayed?).to eql true
end
