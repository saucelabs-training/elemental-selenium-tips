require 'selenium-webdriver'
require 'rspec/expectations'
include RSpec::Matchers

def setup
  @driver = Selenium::WebDriver.for :firefox
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
  @driver.get 'http://the-internet.herokuapp.com/nested_frames'
  @driver.switch_to.frame('frame-top')
  @driver.switch_to.frame('frame-middle')
  expect(@driver.find_element(id: 'content').text).to eql 'MIDDLE'
end

run do
  @driver.get 'http://the-internet.herokuapp.com/tinymce'
  @driver.switch_to.frame('mce_0_ifr')
  editor = @driver.find_element(id: 'tinymce')
  before_text = editor.text
  editor.clear
  editor.send_keys 'Hello World!'
  after_text = editor.text
  expect(after_text).not_to eql before_text

  @driver.switch_to.default_content
  expect(@driver.find_element(css: 'h3').text).not_to be_empty
end