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
  return profile
end

def setup
  @driver = Selenium::WebDriver.for :firefox, profile: configure_proxy
  @proxy.blacklist('http:\/\/the-internet.herokuapp.com\/slow_external', 404)
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
  @proxy.new_har
  @driver.get 'http://the-internet.herokuapp.com/slow'
  entry = @proxy.har.entries.find { |e| e.request.url.include? 'slow_external' }
  expect(entry).not_to be_nil
  expect(entry.response.status).to eq(404)

  # @proxy.har.entries.each { |entry| puts "#{entry.request.url} -> #{entry.response.status}" }
end
