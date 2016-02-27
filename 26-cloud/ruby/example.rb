require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

#def setup
#  caps = Selenium::WebDriver::Remote::Capabilities.firefox
#  caps.version = '23'
#  caps.platform = :XP
#  caps[:name] = 'Testing Selenium 2 with Ruby on Sauce'
#
#  @driver = Selenium::WebDriver.for(
#    :remote,
#    :url => 'http://the-internet:26bd4eac-9ef2-4cf0-a6e0-3b7736bd5359@ondemand.saucelabs.com:80/wd/hub',
#    :desired_capabilities => caps)
#end

def setup
  caps = Selenium::WebDriver::Remote::Capabilities.internet_explorer
  caps.version = '8'
  caps.platform = 'Windows XP'
  caps[:name] = 'Hello World!'

  @driver = Selenium::WebDriver.for(
    :remote,
    url: "http://#{ENV['SAUCE_USERNAME']}:#{ENV['SAUCE_ACCESS_KEY']}@ondemand.saucelabs.com:80/wd/hub",
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
  expect(@driver.title.include?('The Internet')).to eql true
end
