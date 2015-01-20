# Encoding: utf-8

require 'selenium-webdriver'
require 'rspec-expectations'

def setup
  caps = {
    'platform'            => 'Linux',
    'version'             => '4.2',
    'device'              => 'Android',
    'app'                 => 'http://appium.s3.amazonaws.com/NotesList.apk',
    #'app'                 => 'chrome', # not available in Sauce (yet)
    'app-package'         => 'com.example.android.notepad',
    'app-activity'        => '.NotesList',
    'name'                => 'Ruby/Android Example for Appium'
  }

  @driver = Selenium::WebDriver.for(
    :remote,
    :url => 'http://the-internet:26bd4eac-9ef2-4cf0-a6e0-3b7736bd5359@ondemand.saucelabs.com:80/wd/hub',
    :desired_capabilities => caps)
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
  @driver.find_element(:name, 'New note').click
  @driver.find_element(:tag_name, 'textfield').send_keys 'This is a new note, from Ruby'
  @driver.find_element(:name, 'Save').click

  notes = @driver.find_elements(:tag_name, 'text')
  notes[2].text.should == 'This is a new note, from Ruby'
end
