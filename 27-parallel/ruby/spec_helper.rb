require 'selenium-webdriver'

RSpec.configure do |config|

  config.before(:each) do
    #@driver = Selenium::WebDriver.for :firefox
    caps = Selenium::WebDriver::Remote::Capabilities.firefox # make caps option driven
    caps.version = "23"
    caps.platform = "Windows XP"
    caps[:name] = self.example.metadata[:full_description]

    @driver = Selenium::WebDriver.for(
      :remote,
      :url => "http://the-internet:26bd4eac-9ef2-4cf0-a6e0-3b7736bd5359@ondemand.saucelabs.com:80/wd/hub",
      :desired_capabilities => caps)
  end

  config.after(:each) do
    @driver.quit
  end

end
