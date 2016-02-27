# Encoding: utf-8

require 'selenium-webdriver'

describe 'Example' do

  before(:each) do
    @driver = Selenium::WebDriver.for :firefox
  end

  after(:each) do
    @driver.quit
  end

  it 'works' do
    @driver.get 'http://the-internet.herokuapp.com'
    expect(@driver.title).to eql('The Internet')
  end

end
