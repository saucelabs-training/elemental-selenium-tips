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

#run do
#  @driver.get 'http://the-internet.herokuapp.com/windows'
#  @driver.find_element(css: '.example a').click
#  @driver.switch_to.window(@driver.window_handles.first)
#  expect(@driver.title).not_to eql 'New Window'
#  @driver.switch_to.window(@driver.window_handles.last)
#  expect(@driver.title).to eql 'New Window'
#end

run do
  @driver.get 'http://the-internet.herokuapp.com/windows'

  first_window = @driver.window_handle
  @driver.find_element(css: '.example a').click
  all_windows = @driver.window_handles
  new_window = all_windows.select { |window| window != first_window }

  @driver.switch_to.window(first_window)
  expect(@driver.title).not_to eql 'New Window'

  @driver.switch_to.window(new_window)
  expect(@driver.title).to eql 'New Window'
end