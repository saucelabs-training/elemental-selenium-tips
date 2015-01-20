require 'selenium-webdriver'
require 'rspec-expectations'

def setup
  @driver = Selenium::WebDriver.for :firefox
  @base_url = 'http://www.google.com'
end

def teardown
  @driver.quit
end

def run
  setup
  yield
  teardown
end

def wait_for(seconds=5)
  Selenium::WebDriver::Wait.new(:timeout => seconds).until { yield }
end

def displayed?(how, what)
  @driver.find_element(how, what).displayed?
  true
  rescue Selenium::WebDriver::Error::NoSuchElementError
    false
end

run {
    @driver.get(@base_url + "/")
    @driver.find_element(:id, "gbqfq").clear
    @driver.find_element(:id, "gbqfq").send_keys "elemental selenium tips"
    @driver.find_element(:id, "gbqfb").click
    wait_for { displayed?(:css, '#rso .g') }
    @driver.find_element(:css, '#rso .g').text.should =~ /Receive a Free, Weekly Tip/
}
