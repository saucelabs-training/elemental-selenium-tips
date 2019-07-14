require 'selenium-webdriver'
require 'rspec/expectations'
require 'uuid'
require 'fileutils'

include RSpec::Matchers

def setup
  @download_dir = File.join(Dir.pwd, UUID.new.generate)
  FileUtils.mkdir_p(@download_dir)

  profile = Selenium::WebDriver::Firefox::Profile.new
  profile['browser.download.dir'] = @download_dir
  profile['browser.download.folderList'] = 2 # the last folder specified for download
  profile['browser.helperApps.neverAsk.saveToDisk'] = 'image/jpeg, application/pdf, application/octet-stream'
  profile['pdfjs.disabled'] = true # need to set with PDFs or else it will view them in the browser
  options = Selenium::WebDriver::Firefox::Options.new
  options.profile = profile
  @driver = Selenium::WebDriver.for(:firefox, options: options)
end

def teardown
  @driver.quit
  FileUtils.rm_rf(@download_dir)
end

def run
  setup
  yield
  teardown
end

run do
  @driver.get('http://the-internet.herokuapp.com/download')
  download_link = @driver.find_element(css: '.example a')
  download_link.click
  sleep 0.5 # If execution is too fast you can get the temporary downloaded file when you scrape the directory
  files = Dir.glob("#{@download_dir}/**")

  expect(files.size).to eq 1
  file = files.first

  expect(File.size(file)).to be > 0
end
