require_relative 'spec_helper'

describe 'Selectors' do

  def wait_for(seconds = 5)
    wait = Selenium::WebDriver::Wait.new(:timeout => seconds)
    wait.until { yield }
  end

  context 'by ID and Class,' do

    it "with CSS on #{ENV['browser']} #{ENV['browser_version']}" do
      @driver.get 'http://the-internet.herokuapp.com/tables'
      @driver.find_element(css: '#table2 thead .dues').click
      dues = @driver.find_elements(css: '#table2 tbody .dues')
      due_values = []
      dues.each { |due| due_values << due.text.gsub(/\$/,'').to_f }
      (due_values == due_values.sort).should == true
    end

    it "with XPath on #{ENV['browser']} #{ENV['browser_version']}" do
      @driver.get 'http://the-internet.herokuapp.com/tables'
      @driver.find_element(xpath: "//*[contains(@class, 'dues')]").click
      dues = @driver.find_elements(xpath: "//table[@id='table2']//tr/div[contains(@class,'dues')]")
      due_values = []
      dues.each { |due| due_values << due.text.gsub(/\$/,'').to_f }
      (due_values == due_values.sort).should == true
    end

  end

end
