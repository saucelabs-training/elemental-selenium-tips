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
  @driver.get 'http://the-internet.herokuapp.com/broken_images'

  # Get all images on the page.
  all_images = @driver.find_elements tag_name: 'img'

  # Iterate through each, rejecting the ones that are valid.
  # Return a collection of found broken images.
  broken_images = all_images.reject do |image|
    @driver.execute_script(
      "return arguments[0].complete && \
        typeof arguments[0].naturalWidth != \"undefined\" && \
        arguments[0].naturalWidth > 0",
      image)
  end

  # Assert there are no broken images by checking if the collection is empty.
  expect(broken_images).to be_empty
end
