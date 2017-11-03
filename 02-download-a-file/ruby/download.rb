require 'selenium-webdriver'
require 'rspec/expectations'
require 'uuid'
require 'fileutils'

include RSpec::Matchers

def create_firefox_driver
  profile = Selenium::WebDriver::Firefox::Profile.new
  Selenium::WebDriver::Firefox::Options.profile
  profile['browser.download.dir'] = @download_dir
  profile['browser.download.folderList'] = 2 # the last folder specified for download
  profile['browser.helperApps.neverAsk.saveToDisk'] = 'image/jpeg, application/pdf, application/octet-stream'
  profile['pdfjs.disabled'] = true # need to set with PDFs or else it will view them in the browser
  @driver = Selenium::WebDriver.for(:firefox, profile: profile)
end

def create_chrome_driver
  profile = {
    download: {
      prompt_for_download: false,
      default_directory: @download_dir
    }
  }
  @driver = Selenium::WebDriver.for(:chrome, prefs: profile)
end

def setup
  @download_dir = File.join(Dir.pwd, UUID.new.generate)
  FileUtils.mkdir_p(@download_dir)

  create_chrome_driver # Set this to `create_firefox_driver` to use the Firefox driver
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
  sleep 0.5 # Sometimes chrome can be super fast, and you get the temporary downloaded file when you scrape the directory
  files = Dir.glob("#{@download_dir}/**")

  expect(files.size).to eq 1
  file = files.first

  expect(File.size(file)).to be > 0
end
