# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'
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
  @driver.get 'http://the-internet.herokuapp.com/context_menu'
  menu_area = @driver.find_element id: 'hot-spot'
  @driver.action.context_click(menu_area).send_keys(:arrow_down).send_keys(:enter).perform
  alert = @driver.switch_to.alert
  expect(alert.text).to eq('You selected a context menu')
end
