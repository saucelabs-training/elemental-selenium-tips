# filename: download.rb

require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers
require 'uuid'
require 'fileutils'

def setup
  @download_dir = File.join(Dir.pwd, UUID.new.generate)
  FileUtils.mkdir_p @download_dir

  # Firefox
  profile = Selenium::WebDriver::Firefox::Profile.new
  profile['browser.download.dir'] = @download_dir
  profile['browser.download.folderList'] = 2 # the last folder specified for download
  profile['browser.helperApps.neverAsk.saveToDisk'] = 'images/jpeg, application/pdf'
  profile['pdfjs.disabled'] = true #need to set with PDFs or else it will view them in the browser
  @driver = Selenium::WebDriver.for :firefox, profile: profile

  # Chrome
  #Selenium::WebDriver::Chrome::Service.executable_path = File.join(Dir.pwd, './chromedriver')
  #profile = {
  #  download: {
  #    prompt_for_download: false,
  #    default_directory: @download_dir
  #  }
  #}
  #@driver = Selenium::WebDriver.for :chrome, prefs: profile
end

def teardown
  @driver.quit
  FileUtils.rm_rf @download_dir
end

def run
  setup
  yield
  teardown
end

run do
  @driver.get 'http://the-internet.herokuapp.com/download'
  download_link = @driver.find_element(css: '.example a')
  download_link.click

  files = Dir.glob("#{@download_dir}/**")
  expect(files.empty?).to eql false

  sorted_files = files.sort_by { |file| File.mtime(file) }
  expect(File.size(sorted_files.last)).to be > 0
end
