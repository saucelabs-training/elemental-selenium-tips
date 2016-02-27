require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers
require 'browsermob/proxy'

def configure_proxy
  server = BrowserMob::Proxy::Server.new(
    File.join(Dir.pwd, '..', '..', 'vendor', 'browsermob-proxy/bin/browsermob-proxy'), log: true)
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

def run
  setup
  yield
  teardown
end

def retrieve_status_code
  @proxy.new_har
  yield
  @proxy.har.entries.first.response.status
end

run do
  status_code = retrieve_status_code do
    @driver.get 'http://the-internet.herokuapp.com/status_codes/404'
  end

  expect(status_code).to eql 404
end
