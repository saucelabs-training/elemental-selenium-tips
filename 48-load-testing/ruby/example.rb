# Encoding: utf-8

require 'selenium-webdriver'
require 'browsermob/proxy'
require 'typhoeus'

def configure_proxy
  proxy_binary = BrowserMob::Proxy::Server.new('./browsermob-proxy/bin/browsermob-proxy')
  proxy_binary.start
  proxy_binary.create_proxy
end

def browser_profile
  browser_profile = Selenium::WebDriver::Firefox::Profile.new
  browser_profile.proxy = @proxy.selenium_proxy
  browser_profile
end

def setup
  @proxy = configure_proxy
  @driver = Selenium::WebDriver.for :firefox, profile: browser_profile
end

def teardown
  @driver.quit
  @proxy.close
end

def capture_traffic
  @proxy.new_har
  yield
  @proxy.har
end

def error_handling(request)
  request.on_complete do |response|
    if response.success?
      puts "success"
    elsif response.timed_out?
      puts "got a time out"
    elsif response.code == 0
      # Could not get an http response, something's wrong.
      puts response.return_message
    else
      # Received a non-successful http response.
      puts "HTTP request failed: " + response.code.to_s
    end
  end
end

def replay(http_requests, number_of_replays, debug = false)
  requests = []
  http_requests.entries.each do |entry|
    requests << Typhoeus::Request.new(
      entry.request.url,
      method: entry.request.method.downcase.to_sym)
  end

  start_time = Time.now
  puts "Start time: #{start_time}"
  threads = []
  number_of_replays.times do
    threads << Thread.new do
      requests.each do |request|
        error_handling request if debug
        request.run
      end
    end
  end
  threads.each {|thread| thread.join }
  finish_time = Time.now
  puts "Finish time: #{finish_time}"
  puts "#{number_of_replays} runs completed in #{finish_time - start_time} seconds"
end

def run
  setup
  http_requests = capture_traffic { yield }
  teardown
  replay(http_requests, 100)
  #replay(http_requests, 100, true)
end

run do
  @driver.get 'http://the-internet.herokuapp.com/dynamic_loading/2'
  @driver.find_element(css: '#start button').click
  Selenium::WebDriver::Wait.new(timeout: 8).until do
    @driver.find_element(css: '#finish')
  end
end
