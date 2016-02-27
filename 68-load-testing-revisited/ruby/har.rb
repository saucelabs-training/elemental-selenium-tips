# filename: har.rb

require 'selenium-webdriver'
require 'browsermob/proxy'
require_relative 'convert'

def configure_proxy
  server = BrowserMob::Proxy::Server.new(
    File.join(Dir.pwd, '../../vendor/browsermob-proxy/bin/browsermob-proxy'), log: true)
  @proxy = server.start.create_proxy
  profile = Selenium::WebDriver::Firefox::Profile.new
  profile.proxy = @proxy.selenium_proxy
  profile
end

def setup
  @driver = Selenium::WebDriver.for :firefox, profile: configure_proxy
end

def teardown
  @driver.quit
  @proxy.close
end

# ...

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
    @driver.find_element(css: '#finish').displayed?
  end
end

@har.save_to './selenium.har'
HARtoJMX.convert 'selenium.har' # to jmeter.jmx
