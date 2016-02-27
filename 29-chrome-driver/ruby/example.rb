require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

def setup
  #Selenium::WebDriver::Chrome::Service.executable_path = File.join(Dir.pwd, '..', '..', 'vendor', 'chromedriver')
  #@driver = Selenium::WebDriver.for :chrome

  @driver = Selenium::WebDriver.for :remote, url: 'http://localhost:9515', desired_capabilities: :chrome
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
  expect(@driver.title).to eql 'The Internet'
end
