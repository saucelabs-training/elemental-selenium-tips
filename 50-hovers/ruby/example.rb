# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

def setup(browser_name, browser_version)
  caps = Selenium::WebDriver::Remote::Capabilities.send(browser_name.to_sym)
  caps.platform = 'Windows XP'
  caps.version = browser_version.to_s
  @driver = Selenium::WebDriver.for(:remote,
  url: "http://#{ENV['SAUCE_USERNAME']}:#{ENV['SAUCE_ACCESS_KEY']}@\
ondemand.saucelabs.com:80/wd/hub",
  desired_capabilities: caps)
end

def teardown
  @driver.quit
end

BROWSERS = { firefox: '27',
             chrome: '32',
             internet_explorer: '8' }
def run
  BROWSERS.each_pair do |browser, browser_version|
    setup(browser, browser_version)
    yield
    teardown
  end
end


run do
  @driver.get 'http://the-internet.herokuapp.com/hovers'
  an_avatar = @driver.find_element(class: 'figure')
  @driver.action.move_to(an_avatar).perform
  expect(@driver.find_element(class: 'figcaption').displayed?).to eql true
end
