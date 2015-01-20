# Encoding: utf-8

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

run do
  @driver.get "http://the-internet.herokuapp.com/drag_and_drop"
  dnd_javascript = File.read(Dir.pwd + '/dnd.js')
  @driver.execute_script(dnd_javascript+"$('#column-a').simulateDragDrop({ dropTarget: '#column-b'});")
  @driver.find_element(id: 'column-a').text.should == 'B'
  @driver.find_element(id: 'column-b').text.should == 'A'
#  left = @driver.find_element(id: "column-a")
#  right = @driver.find_element(id: "column-b")
#  @driver.action.drag_and_drop(left, right).perform
end
