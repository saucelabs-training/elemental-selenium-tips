# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers
require 'gmail'

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

def try(number_of_times)
  count = 0 ; item_of_interest = nil
  until item_of_interest != nil || count == number_of_times
    item_of_interest = yield
    sleep 10
    count += 1
  end
end

# ...

run do
  # Part 1: Initiate forgot password email
  @driver.get 'http://the-internet.herokuapp.com/forgot_password'
  @driver.find_element(id: 'email').send_keys(ENV['EMAIL_USERNAME'])
  @driver.find_element(id: 'form_submit').click

  # Part 2: Find the forgot password email from a test Gmail account
  gmail = Gmail.new(ENV['EMAIL_USERNAME'], ENV['EMAIL_PASSWORD'])
  try(6) { @email = gmail.inbox.emails(:unread, from: 'no-reply@the-internet.herokuapp.com').last }
  message_body = @email.message.body.raw_source

  # Part 3: Grab the URL, username, and password values from the e-mail's message body
  url =  message_body.scan(/https?:\/\/[\S]+/).last
  username = message_body.scan(/username: (.*)$/)[0][0].strip
  password = message_body.scan(/password: (.*)$/)[0][0].strip

  # Part 4: Visit the URL from the email the browser, log in, and assert that it worked
  @driver.get url
  @driver.find_element(id: 'username').send_keys username
  @driver.find_element(id: 'password').send_keys password
  @driver.find_element(id: 'login').submit
  expect(@driver.current_url.include?('/secure')).to eql true
end
