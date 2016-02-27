require 'selenium-webdriver'
require 'sauce_whisk'

RSpec.configure do |config|

  config.before(:each) do
    if ENV['host'] == 'localhost'
      @driver = Selenium::WebDriver.for :firefox
    else
      caps = Selenium::WebDriver::Remote::Capabilities.send(ENV['browser'])
      caps.version = ENV['browser_version']
      caps.platform = ENV['operating_system']
      caps[:name] = self.example.metadata[:full_description]

      @driver = Selenium::WebDriver.for(
        :remote,
        :url => "http://the-internet:26bd4eac-9ef2-4cf0-a6e0-3b7736bd5359@ondemand.saucelabs.com:80/wd/hub",
        :desired_capabilities => caps)
    end
  end

  config.after(:each) do
    if ENV['host'] != 'localhost'
      if example.exception.nil?
        SauceWhisk::Jobs.pass_job @driver.session_id
      else
        SauceWhisk::Jobs.fail_job @driver.session_id
      end
    end
    @driver.quit
  end

end
