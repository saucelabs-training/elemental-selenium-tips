# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'
include RSpec::Matchers

ENV['SAUCE_USERNAME']    = 'the-internet'
ENV['SAUCE_ACCESS_KEY']  = '26bd4eac-9ef2-4cf0-a6e0-3b7736bd5359'

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
  avatar = @driver.find_element(class: 'figure')
  @driver.action.move_to(avatar).perform
  Selenium::WebDriver::Wait.new(timeout: 2).until do
    @driver.find_element(class: 'figcaption').displayed?
  end
  user_url = @driver.find_element(css: '.figcaption > a').attribute('href')
  expect(user_url).to eq('http://the-internet.herokuapp.com/users/1')
end
