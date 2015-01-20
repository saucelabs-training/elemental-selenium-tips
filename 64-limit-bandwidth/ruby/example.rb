# Encoding: utf-8

require 'browsermob/proxy'
require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

def configure_proxy
  server = BrowserMob::Proxy::Server.new('./browsermob-proxy-2.0-beta-9/bin/browsermob-proxy')
  server.start
  @proxy = server.create_proxy
  profile = Selenium::WebDriver::Firefox::Profile.new
  profile.proxy = @proxy.selenium_proxy
  return Selenium::WebDriver::Remote::Capabilities.firefox(firefox_profile: profile)
end

def setup
  client = Selenium::WebDriver::Remote::Http::Default.new
  client.timeout = 300 # seconds
  @driver = Selenium::WebDriver.for :remote, desired_capabilities: configure_proxy, http_client: client
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
