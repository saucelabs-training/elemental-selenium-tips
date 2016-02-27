# Encoding: utf-8

require 'browsermob/proxy'
require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

def configure_proxy
  server = BrowserMob::Proxy::Server.new('../../vendor/browsermob-proxy/bin/browsermob-proxy')
  server.start
  @proxy = server.create_proxy
  profile = Selenium::WebDriver::Firefox::Profile.new
  profile.proxy = @proxy.selenium_proxy
  profile
end

def setup
  @driver = Selenium::WebDriver.for :firefox, profile: configure_proxy
  @driver.manage.timeouts.page_load = 300 # seconds
  @proxy.limit(upstream_kbps: 20, downstream_kbps: 30)
end

def teardown
  @driver.quit
  @proxy.close
end

def run
  setup
  yield
  teardown
end

run do
  @driver.get 'http://the-internet.herokuapp.com'
  expect(@driver.find_element(class: 'heading').text).to eql('Welcome to the Internet')
end
