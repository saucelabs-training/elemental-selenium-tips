# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec/expectations'
require 'csv'
include ::RSpec::Matchers

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

def user_data
  user_data = CSV.read Dir.pwd + '/user_data.csv'
  descriptor = user_data.shift
  descriptor = descriptor.map { |key| key.to_sym }
  user_data.map { |user| Hash[ descriptor.zip(user) ] }
end

def wait_until
  wait = Selenium::WebDriver::Wait.new(:timeout => 5)
  wait.until { yield }
end

def notification_text
  wait_until { @driver.find_element(class: 'flash').displayed? }
  @driver.find_element(class: 'flash').text.delete('^a-zA-z !.')
end

run do
  user_data.each do |user|
    @driver.get 'http://the-internet.herokuapp.com/login'
    @driver.find_element(id: 'username').send_keys user[:username]
    @driver.find_element(id: 'password').send_keys user[:password]
    @driver.find_element(id: 'login').submit
    begin
      expect(notification_text).to eql user[:notification_message]
    rescue Exception => e
      puts e.message
    end
  end
end
