# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'
include RSpec::Matchers

def setup
#  @driver = Selenium::WebDriver.for(
#    :remote,
#    url: 'http://localhost:4444/wd/hub',
#    #desired_capabilities: :safari) # :firefox, :chrome

  caps = Selenium::WebDriver::Remote::Capabilities.chrome
  caps[:platform] = :mac # :any, :win, :mac, :x

  @driver = Selenium::WebDriver.for(
    :remote,
    url: 'http://localhost:4444/wd/hub',
    desired_capabilities: caps)
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
  expect(@driver.title).to eq('The Internet')
end
