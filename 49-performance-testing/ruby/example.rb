# Encoding: utf-8

require 'selenium-webdriver'
require 'browsermob/proxy'
require 'rspec/expectations'
include RSpec::Matchers
require 'json'

def configure_proxy
  proxy_binary = BrowserMob::Proxy::Server.new('../../vendor/browsermob-proxy/bin/browsermob-proxy', log: true)
  proxy_binary.start
  proxy_binary.create_proxy
end

def browser_profile
  browser_profile = Selenium::WebDriver::Firefox::Profile.new
  browser_profile.proxy = @proxy.selenium_proxy
  browser_profile
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
  har = capture_traffic { yield }
  @har_file = "./selenium_#{Time.now.strftime("%m%d%y_%H%M%S")}.har"
  har.save_to @har_file
  teardown
end

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
  @driver.find_element(css: '#start button').click
  Selenium::WebDriver::Wait.new(timeout: 8).until do
    @driver.find_element(css: '#finish')
  end
end

performance_results = JSON.parse `yslow --info basic --format json #{@har_file}`
performance_grade = performance_results["o"]
expect(performance_grade).to be > 90
