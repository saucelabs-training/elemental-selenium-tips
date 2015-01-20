require 'selenium-webdriver'
require 'rspec/expectations'
require 'rest-client'
include RSpec::Matchers

def setup
  @driver = Selenium::WebDriver.for :firefox
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
  @driver.get 'http://the-internet.herokuapp.com/download'
  link = @driver.find_element(css: '.example a').attribute('href')
  response = RestClient.head link
  expect(response.headers[:content_type]).to eql('application/pdf')
  expect(response.headers[:content_length].to_i).to be > 0
end
