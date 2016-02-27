# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec/expectations'
require 'csv'
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

def user_data
  user_data = CSV.read Dir.pwd + '/user_data.csv'
  descriptor = user_data.shift                          # returns the first row of the data
  descriptor = descriptor.map { |key| key.to_sym }      # converts the first row of data into keys for easy data reference
  user_data.map { |user| Hash[ descriptor.zip(user) ] } # converts the data into a Hash with the descriptors as keys
end

def notification_text
  wait = Selenium::WebDriver::Wait.new(timeout: 5)
  wait.until { @driver.find_element(class: 'flash').displayed? }
  @driver.find_element(class: 'flash').text.delete('^a-zA-z !.') # grabs the text and removes excess characters
end

user_data.each do |user|
  run do
    @driver.get 'http://the-internet.herokuapp.com/login'
    @driver.find_element(id: 'username').send_keys user[:username]
    @driver.find_element(id: 'password').send_keys user[:password]
    @driver.find_element(id: 'login').submit
    begin
      expect(notification_text).to eql user[:notification_message]
    rescue Exception => error
      puts error.message
    end
  end
end
