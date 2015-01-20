# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'
include RSpec::Matchers
require_relative 'logger'

def setup
  @driver = Selenium::WebDriver.for :remote, url: 'http://127.0.0.1:4444/wd/hub'
  @logger = Logger.new(@driver.session_id)
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
  @driver.get 'http://the-internet.herokuapp.com'
  puts @logger.latest
  @driver.find_elements(css: 'a').last.click
  puts @logger.latest
end

#def add_growl_notifications
#  @driver.execute_script("if (!window.jQuery) {
#      var jquery = document.createElement('script'); jquery.type = 'text/javascript';
#      jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';
#      document.getElementsByTagName('head')[0].appendChild(jquery);
#    }")
#
#  @driver.execute_script("$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')")
#
#  @driver.execute_script("$('head').append('<link rel=\"stylesheet\" href=\"http://the-internet.herokuapp.com/css/jquery.growl.css\" type=\"text/css\" />');")
#end
#
#def display_growl_message(message)
#  @driver.execute_script("$.growl({ title: 'Selenium', message: '#{message}' });")
#  sleep 2
#end
#
#run do
#  @driver.get 'http://the-internet.herokuapp.com'
#  add_growl_notifications
#  display_growl_message(@logger.latest)
#end
