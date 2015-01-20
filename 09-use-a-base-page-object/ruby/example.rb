require 'selenium-webdriver'
require 'rspec-expectations'

def setup
  @driver = Selenium::WebDriver.for :firefox
  ENV['base_url'] = 'http://www.google.com'
end

def teardown
  @driver.quit
end

def run
  setup
  yield
  teardown
end


class Base

  attr_reader :driver

  def initialize(driver)
    @driver = driver
  end

  def visit(url='/')
    driver.get(ENV['base_url'] + url)
  end

  def find(locator)
    driver.find_element locator
  end

  def clear(locator)
    find(locator).clear
  end

  def type(locator, input)
    find(locator).send_keys input
  end

  def click_on(locator)
    find(locator).click
  end

  def displayed?(locator)
    driver.find_element(locator).displayed?
    true
    rescue Selenium::WebDriver::Error::NoSuchElementError
      false
  end

  def text_of(locator)
    find(locator).text
  end

  def title
    driver.title
  end

  def wait_for(seconds=5)
    Selenium::WebDriver::Wait.new(:timeout => seconds).until { yield }
  end

end


class GoogleSearch < Base

  SEARCH_BOX        = { id: 'gbqfq'     }
  SEARCH_BOX_SUBMIT = { id: 'gbqfb'     }
  TOP_SEARCH_RESULT = { css: '#rso .g'  }

  def initialize(driver)
    super
    visit
    verify_page
  end

  def search_for(search_term)
    type SEARCH_BOX, search_term
    click_on SEARCH_BOX_SUBMIT
  end

  def search_result_present?(search_result)
    wait_for { displayed?(TOP_SEARCH_RESULT) }
    text_of(TOP_SEARCH_RESULT).include? search_result
  end

  private

    def verify_page
      title.include?('Google').should == true
    end

end


run {
  google = GoogleSearch.new(@driver)
  google.search_for 'elemental selenium tips'
  result = google.search_result_present? 'Receive a Free, Weekly Tip'
  result.should == true
}
