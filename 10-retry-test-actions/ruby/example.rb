require 'selenium-webdriver'
require 'rspec-expectations'

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


# Before

run {
  @driver.get 'http://the-internet.herokuapp.com/notification_message'
  notification_message = @driver.find_element(id: 'flash').text
  notification_message.should =~ /Action successful/
}


# After

def get_notification_message
  @notification_message = @driver.find_element(id: 'flash').text
end

def retry_if_notification_message_contains(fail_message)
  count = 0
  yield
  until !@notification_message.include? fail_message || count == 3
    yield
    count =+ 1
  end
end

run {
  retry_if_notification_message_contains 'please try again' do
    @driver.get 'http://the-internet.herokuapp.com/notification_message'
    get_notification_message
  end
  @notification_message.should =~ /Action successful/
}
