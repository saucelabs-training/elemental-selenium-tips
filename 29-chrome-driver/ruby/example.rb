# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'

def setup
  # Option 1
  Selenium::WebDriver::Chrome::Service.executable_path = File.join(Dir.pwd, './chromedriver')
  @driver = Selenium::WebDriver.for :chrome

  # Option 2
  # Start the chromedriver in your terminal
  # Connect to it via Selenium Remote, like so:
  # @driver = Selenium::WebDriver.for :remote, url: 'http://localhost:9515', desired_capabilities: :chrome
end

def teardown
  @driver.quit
end

def run
  setup
  yield
  teardown
end

run do
  @driver.get 'http://the-internet.herokuapp.com/'
  @driver.title.should == 'The Internet'
end
