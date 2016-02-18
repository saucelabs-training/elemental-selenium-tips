require 'rspec'
require 'selenium-webdriver'
require 'allure-rspec'
require 'nokogiri'
require 'uuid'

RSpec.configure do |c|
  c.include AllureRSpec::Adaptor

  c.before(:each) do
    @driver = Selenium::WebDriver.for :firefox
  end

  c.after(:each) do
    begin
      unless example.exception.nil?
        attach_file("screenshot",
          @driver.save_screenshot(File.join(Dir.pwd, "results/#{UUID.new.generate}.png")))
      end
    ensure
      @driver.quit
    end
  end

end

AllureRSpec.configure do |c|
  c.output_dir = "results/allure"
end

describe "Reporting" do

  it 'passes' do
    @driver.get 'http://www.google.com'
    expect(true).to eql(true)
  end

  it 'fails' do
    @driver.get 'http://www.google.com'
    expect(true).to eql(false)
  end

end
