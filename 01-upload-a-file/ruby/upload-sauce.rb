require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

def setup
  caps = Selenium::WebDriver::Remote::Capabilities.internet_explorer
  caps.version    = '8'
  caps.platform   = 'Windows XP'
  @driver         = Selenium::WebDriver.for(
                    :remote,
                    url: "http://sauce-username:sauce-access-key@ondemand.saucelabs.com:80/wd/hub",
                    desired_capabilities: caps)

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
  @driver.file_detector = lambda do |args|
     # args => ["/path/to/file"]
     str = args.first.to_s
     str if File.exist?(str)
  end

  filename = 'some-file.txt'
  file = File.join(Dir.pwd, filename)

  @driver.get 'http://the-internet.herokuapp.com/upload'
  @driver.find_element(id: 'file-upload').send_keys file
  @driver.find_element(id: 'file-submit').click

  uploaded_file = @driver.find_element(id: 'uploaded-files').text
  expect(uploaded_file).to eql filename
end
