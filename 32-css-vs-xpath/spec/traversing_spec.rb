require_relative 'spec_helper'

describe 'Selectors' do

  def wait_for(seconds = 5)
    wait = Selenium::WebDriver::Wait.new(:timeout => seconds)
    wait.until { yield }
  end

  context 'by Traversing,' do

    it "with CSS on #{ENV['browser']} #{ENV['browser_version']}" do
      @driver.get 'http://the-internet.herokuapp.com/tables'

      # sort DUES ascending
      @driver.find_element(css: '#table1 thead tr th:nth-of-type(4)').click

      # get DUES
      dues = @driver.find_elements(css: '#table1 tbody tr td:nth-of-type(4)')
      due_values = []
      dues.each { |due| due_values << due.text.gsub(/\$/,'').to_f }

      # assert DUES are ascending
      (due_values == due_values.sort).should == true

      # sort DUES descending
      @driver.find_element(css: '#table1 thead tr th:nth-of-type(4)').click

      # get DUES
      dues = @driver.find_elements(css: '#table1 tbody tr td:nth-of-type(4)')
      due_values = []
      dues.each { |due| due_values << due.text.gsub(/\$/,'').to_i }

      # assert DUES are desending
      (due_values == due_values.sort).should == false

      # sort EMAIL ascending
      @driver.find_element(css: '#table1 thead tr th:nth-of-type(3)').click

      # get EMAIL values
      emails = @driver.find_elements(css: '#table1 tbody tr td:nth-of-type(3)')
      email_values = []
      emails.each { |email| email_values << email.text }

      # assert EMAIL is ascending
      (email_values == email_values.sort).should == true
    end

    it "with XPath on #{ENV['browser']} #{ENV['browser_version']}" do
      @driver.get 'http://the-internet.herokuapp.com/tables'

      # sort DUES ascending
      @driver.find_element(xpath: "//table[@id='table1']//tr/th[4]").click

      # get DUES
      dues = @driver.find_elements(xpath: "//table[@id='table1']//tr/td[4]")
      due_values = []
      dues.each { |due| due_values << due.text.gsub(/\$/,'').to_f }

      # assert DUES are ascending
      (due_values == due_values.sort).should == true

      # sort DUES descending
      @driver.find_element(xpath: "//table[@id='table1']//tr/th[4]").click

      # get DUES
      dues = @driver.find_elements(xpath: "//table[@id='table1']//tr/td[4]")
      due_values = []
      dues.each { |due| due_values << due.text.gsub(/\$/,'').to_i }

      # assert DUES are desending
      (due_values == due_values.sort).should == false

      # sort EMAIL ascending
      @driver.find_element(xpath: "//table[@id='table1']//tr/th[3]").click

      # get EMAIL values
      emails = @driver.find_elements(xpath: "//table[@id='table1']//tr/td[3]")
      email_values = []
      emails.each { |email| email_values << email.text }

      # assert EMAIL is ascending
      (email_values == email_values.sort).should == true
    end

  end

end
