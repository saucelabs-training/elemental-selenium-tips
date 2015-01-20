# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'
require 'browsermob/proxy'

def configure_proxy
  server = BrowserMob::Proxy::Server.new('./browsermob-proxy/bin/browsermob-proxy')
  server.start
  @proxy = server.create_proxy
  @profile = Selenium::WebDriver::Firefox::Profile.new
  @profile.proxy = @proxy.selenium_proxy
end

def setup
  configure_proxy
  @driver = Selenium::WebDriver.for :firefox, :profile => @profile
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
  yield
end

def visit(url)
  @proxy.new_har
  @driver.get url
  @proxy.har.entries.first.response.status
end

run do
  status_code = visit 'http://the-internet.herokuapp.com/status_codes/404'
  status_code.should == 404
end
