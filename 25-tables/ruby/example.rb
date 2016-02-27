# Encoding: utf-8

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
  @driver.get 'http://the-internet.herokuapp.com/tables'

  # sort DUE ascending
  @driver.find_element(css: '#table1 thead tr th:nth-of-type(4)').click

  # get DUE values
  dues = @driver.find_elements(css: '#table1 tbody tr td:nth-of-type(4)')
  due_values = dues.map { |due| due.text.delete('$').to_f }

  # assert DUE values are ascending
  expect(due_values).to eql due_values.sort
end

run do
  @driver.get 'http://the-internet.herokuapp.com/tables'

  # sort DUE descending
  @driver.find_element(css: '#table1 thead tr th:nth-of-type(4)').click
  @driver.find_element(css: '#table1 thead tr th:nth-of-type(4)').click

  # get DUE values
  dues = @driver.find_elements(css: '#table1 tbody tr td:nth-of-type(4)')
  due_values = dues.map { |due| due.text.delete('$').to_f }

  # assert DUE values are descending
  expect(due_values).to eql due_values.sort.reverse
end

run do
  @driver.get 'http://the-internet.herokuapp.com/tables'

  # sort EMAIL ascending
  @driver.find_element(css: '#table1 thead tr th:nth-of-type(3)').click

  # get EMAIL values
  emails = @driver.find_elements(css: '#table1 tbody tr td:nth-of-type(3)')
  email_values = emails.map { |email| email.text }

  # assert EMAIL is ascending
  expect(email_values).to eql email_values.sort
end

run do
  @driver.get 'http://the-internet.herokuapp.com/tables'

  # sort DUE ascending
  @driver.find_element(css: '#table2 thead .dues').click

  # get DUE values
  dues = @driver.find_elements(css: '#table2 tbody .dues')
  due_values = dues.map { |due| due.text.delete('$').to_f }

  # assert DUE values are ascending
  expect(due_values).to eql due_values.sort
end
