# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec/expectations'
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
  require 'rest-client'

  @driver.get 'http://the-internet.herokuapp.com/broken_images'

  # Get all images on the page
  all_images = @driver.find_elements(tag_name: 'img')

  # Iterate through each, grab the src, and perform an HTTP GET
  # with an HTTP library, checking the status code to make sure it's correct
  all_images.each do |img|
    RestClient.get img.attribute('src') do |response, request, result|
      expect(response.code).to eq 200
    end
  end

end
