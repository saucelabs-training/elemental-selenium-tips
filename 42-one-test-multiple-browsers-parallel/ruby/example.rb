# Encoding: utf-8

ENV['SAUCE_USERNAME'] = ''
ENV['SAUCE_API_KEY']  = ''

require 'selenium-webdriver'
require 'rspec-expectations'

def setup(browser_name, browser_version)
  caps = Selenium::WebDriver::Remote::Capabilities.send(browser_name.to_sym)
  caps.platform = 'Windows XP'
  caps.version = browser_version.to_s

  Thread.current[:driver] = Selenium::WebDriver.for(
    :remote,
    url: "http://#{ENV['SAUCE_USERNAME']}:#{ENV['SAUCE_API_KEY']}@ondemand.saucelabs.com:80/wd/hub",
    desired_capabilities: caps)
end

def teardown
  Thread.current[:driver].quit
end

BROWSERS = { firefox: '27',
             chrome: '32',
             internet_explorer: '8' }

def run
  threads = []
  BROWSERS.each_pair do |browser, browser_version|
    threads << Thread.new do
      puts "Thread Start: #{Time.now}"
      setup(browser, browser_version)
      yield
      teardown
      puts "Thread Finish: #{Time.now}"
    end
  end
  threads.each { |thread| thread.join }
end

run do
  puts "Test Start: #{Time.now}"
  Thread.current[:driver].get 'http://the-internet.herokuapp.com'
  Thread.current[:driver].title.should == 'The Internet'
  puts "Test Finish: #{Time.now}"
end
