# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'

def setup
  caps = {
    'platform'            => 'Mac 10.8',
    'version'             => '6.1',
    'device'              => 'iPhone Simulator',
    'app'                 => 'http://appium.s3.amazonaws.com/TestApp6.0.app.zip',
    #'app'                 => 'safari',
    'name'                => 'Ruby/iPhone Example for Appium'
  }

  @driver = Selenium::WebDriver.for(
    :remote,
    :url => "http://the-internet:26bd4eac-9ef2-4cf0-a6e0-3b7736bd5359@ondemand.saucelabs.com:80/wd/hub",
    :desired_capabilities => caps)
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
    values = [rand(10), rand(10)]
    expected_sum = values.reduce(&:+)

    elements = @driver.find_elements(:tag_name, 'textField')
    elements.each_with_index do |element, index|
      element.send_keys values[index]
    end

    @driver.find_element(:tag_name, 'button').click
    @driver.find_element(:tag_name, 'staticText').text.should == expected_sum.to_s
end
