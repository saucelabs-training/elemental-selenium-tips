# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'

#def setup
#  caps = Selenium::WebDriver::Remote::Capabilities.firefox
#  caps.version = "23"
#  caps.platform = :XP
#  caps[:name] = "Testing Selenium 2 with Ruby on Sauce"
#
#  @driver = Selenium::WebDriver.for(
#    :remote,
#    :url => "http://the-internet:26bd4eac-9ef2-4cf0-a6e0-3b7736bd5359@ondemand.saucelabs.com:80/wd/hub",
#    :desired_capabilities => caps)
#end

def setup
  caps = Selenium::WebDriver::Remote::Capabilities.internet_explorer
  caps.version = "8"
  caps.platform = "Windows XP"
  caps[:name] = "Testing Selenium 2 with Ruby on Sauce"

  @driver = Selenium::WebDriver.for(
    :remote,
    :url => "http://the-internet:26bd4eac-9ef2-4cf0-a6e0-3b7736bd5359@ondemand.saucelabs.com:80/wd/hub",
    :desired_capabilities => caps)
end

def teardown
  @driver.quit
end

def run
  setup
  yield
  teardown
end

def wait_for
  begin
    Selenium::WebDriver::Wait.new(:timeout => 10).until { yield }
  rescue NoSuchElementException
    false
  end
end

run do
  @driver.get 'http://the-internet.herokuapp.com'
  wait_for { @driver.find_element(css: '#content').displayed? }
  @driver.title.include?('The Internet').should == true
end
