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
  @driver.get 'http://the-internet.herokuapp.com/hovers'
  avatar = @driver.find_element(class: 'figure')
  @driver.action.move_to(avatar).perform
  Selenium::WebDriver::Wait.new(timeout: 2).until do
    @driver.find_element(class: 'figcaption').displayed?
  end
  user_url = @driver.find_element(css: '.figcaption > a').attribute('href')
  expect(user_url).to eq('http://the-internet.herokuapp.com/users/1')
end
