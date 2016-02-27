require 'selenium-webdriver'

RSpec.configure do |config|

  config.before(:each) do
    #@driver = Selenium::WebDriver.for :firefox
    caps = Selenium::WebDriver::Remote::Capabilities.firefox
    caps.version = "23"
    caps.platform = "Windows XP"
    caps[:name] = self.example.metadata[:full_description]

    @driver = Selenium::WebDriver.for(
      :remote,
      :url => "http://#{ENV[SAUCE_USERNAME]}:#{ENV[SAUCE_ACCESS_KEY]}@ondemand.saucelabs.com:80/wd/hub",
      :desired_capabilities => caps)
  end

  config.after(:each) do
    @driver.quit
  end

end
