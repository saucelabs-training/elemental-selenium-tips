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

run do
  loop do
    print '>> '
    input = gets.chomp
    if input == 'q'
      puts 'Quitting...'
      @driver.quit
      exit 0
    end
    begin
      eval input
    rescue Exception => e
      puts e.message
    end
  end
end
