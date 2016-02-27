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
  begin
    yield
  rescue RSpec::Expectations::ExpectationNotMetError => error
    puts error.message
    @driver.save_screenshot "./#{Time.now.strftime("failshot__%d_%m_%Y__%H_%M_%S")}.png"
  end
  teardown
end

run do
  @driver.get 'http://the-internet.herokuapp.com'
  expect(@driver.find_element(css: 'h1').text).to eql 'blah blah blah'
end
