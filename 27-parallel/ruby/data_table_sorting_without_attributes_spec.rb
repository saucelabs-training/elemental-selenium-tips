# Encoding: utf-8

require 'spec_helper'

describe 'Sort Data Table' do

  context 'Without Attributes' do

    it 'in Ascending Order' do
      @driver.get 'http://the-internet.herokuapp.com/tables'
      @driver.find_element(css: '#table1 thead tr th:nth-of-type(4)').click
      dues = @driver.find_elements(css: '#table1 tbody tr td:nth-of-type(4)')
      due_values = []
      dues.each { |due| due_values << due.text.gsub(/\$/,'').to_i }
      (due_values == due_values.sort).should == true
    end

    it 'in Descending Order' do
      @driver.get 'http://the-internet.herokuapp.com/tables'
      @driver.find_element(css: '#table1 thead tr th:nth-of-type(4)').click
      @driver.find_element(css: '#table1 thead tr th:nth-of-type(4)').click
      dues = @driver.find_elements(css: '#table1 tbody tr td:nth-of-type(4)')
      due_values = []
      dues.each { |due| due_values << due.text.gsub(/\$/,'').to_i }
      (due_values == due_values.sort).should == false
    end

  end

end
