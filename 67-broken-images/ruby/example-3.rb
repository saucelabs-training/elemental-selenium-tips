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
  @driver.get 'http://localhost:4567/broken_images'

  # get all images on the page
  all_images = @driver.find_elements tag_name: 'img'

  # iterate through each, reject the ones that are valid
  # return a collection of broken images
  broken_images = all_images.reject do |image|
    @driver.execute_script(
      "return arguments[0].complete && \
        typeof arguments[0].naturalWidth != \"undefined\" && \
        arguments[0].naturalWidth > 0",
      image)
  end

  # if there are no broken images, then the collection should be empty
  expect(broken_images).to be_empty
end
