# Encoding: utf-8

require 'selenium-webdriver'
require 'browsermob/proxy'
require_relative 'convert'

def configure_proxy
  proxy_binary = BrowserMob::Proxy::Server.new('../66-blacklist/browsermob-proxy-2.0-beta-9/bin/browsermob-proxy')
  proxy_binary.start
  proxy_binary.create_proxy
end

def browser_profile
  profile = Selenium::WebDriver::Firefox::Profile.new
  profile.proxy = @proxy.selenium_proxy
  profile
end

def setup
  @proxy = configure_proxy
  @driver = Selenium::WebDriver.for :firefox, profile: browser_profile
end

def teardown
  @driver.quit
  @proxy.close
end

def capture_traffic
  @proxy.new_har
  yield
  @proxy.har
end

def run
  setup
  @har = capture_traffic { yield }
  teardown
end

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
  @driver.find_element(css: '#start button').click
  Selenium::WebDriver::Wait.new(timeout: 8).until do
    @driver.find_element(css: '#finish')
  end
end

@har.save_to './selenium.har'
HARtoJMX.convert 'selenium.har' # to jmeter.jmx
# Run JMeter using the new jmx file
