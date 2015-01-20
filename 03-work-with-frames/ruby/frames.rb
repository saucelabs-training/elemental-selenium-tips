require 'selenium-webdriver'
require 'rspec-expectations'

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

# Nested Frames
run {
  @driver.get 'http://the-internet.herokuapp.com/nested_frames'
  @driver.switch_to.frame('frame-top')
    @driver.switch_to.frame('frame-middle')
      @driver.find_element(id: 'content').text.should =~ /MIDDLE/
}

# iFrames
run {
  @driver.get 'http://the-internet.herokuapp.com/tinymce'
  @driver.switch_to.frame('mce_0_ifr')
    editor = @driver.find_element(id: 'tinymce')
    before_text = editor.text
    editor.clear
    editor.send_keys 'Hello World!'
    after_text = editor.text
  after_text.should_not == before_text

  @driver.switch_to.default_content
  @driver.find_element(css: 'h3').text.should_not == ""
}
