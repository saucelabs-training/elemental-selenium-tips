# Encoding: utf-8

require 'selenium-webdriver'

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

def highlight(element, duration = 3)

  # store original style so it can be reset later
  original_style = element.attribute("style")

  # style element with yellow border
  @driver.execute_script(
    "arguments[0].setAttribute(arguments[1], arguments[2])",
    element,
    "style",
    "border: 2px solid red; border-style: dashed;")

  # keep element highlighted for a spell and then revert
  if duration > 0
    sleep duration
    @driver.execute_script(
      "arguments[0].setAttribute(arguments[1], arguments[2])",
      element,
      "style",
      original_style)
  end

end

run do
  @driver.get 'http://the-internet.herokuapp.com/large'
  highlight @driver.find_element(id: 'sibling-2.3')
end
