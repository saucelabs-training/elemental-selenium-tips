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
  frame_top = @driver.find_element(name: 'frame-top')
  @driver.switch_to.frame(frame_top)
  frame_middle = @driver.find_element(name: 'frame-middle')
  @driver.switch_to.frame(frame_middle)
  expect(@driver.find_element(id: 'content').text).to eql 'MIDDLE'
end

run do
  @driver.get 'http://the-internet.herokuapp.com/tinymce'
  @driver.switch_to.frame(@driver.find_element(id: 'mce_0_ifr'))
  editor = @driver.find_element(id: 'tinymce')
  before_text = editor.text
  editor.clear
  editor.send_keys 'Hello World!'
  after_text = editor.text
  expect(after_text).not_to eql before_text

  @driver.switch_to.default_content
  expect(@driver.find_element(css: 'h3').text).not_to be_empty
end
