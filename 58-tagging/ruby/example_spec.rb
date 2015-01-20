require 'selenium-webdriver'

describe 'Login' do

  before(:each) do
    @driver = Selenium::WebDriver.for :firefox
  end

  after(:each) do
    @driver.quit
  end

  # it 'succeeded', :smoke do
  it 'succeeded', :smoke do
    # test code goes here
  end

  # it 'failed', :smoke do
  it 'failed', :wip do
    # test code goes here
  end

end

# If RSpec 2 or earlier, the following config is required

# require 'rspec'
# RSpec.configure { |c| c.treat_symbols_as_metadata_keys_with_true_values = true }

# otherwise, standard key/value syntax is required
# e.g., :smoke would be smoke: true
