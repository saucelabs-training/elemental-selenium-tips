class Wrapper < Selenium::WebDriver::Support::AbstractEventListener

  def after_navigate_to(url, driver)
    add_growl(driver)
    display_growl(driver, "Navigated to #{url}")
  end

  def before_find(by, what, driver)
    display_growl(driver, "Finding element #{what}")
  end

  def after_find(by, what, driver)
    display_growl(driver, "Found element #{what}")
  end

  def before_click(element, driver)
    display_growl(driver, "Clicking on #{element.text}")
    @pre_click_url = driver.current_url
  end

  def after_click(element, driver)
    unless @pre_click_url == driver.current_url
      add_growl(driver)
      display_growl(driver, "URL changed to #{driver.current_url}")
    end
  end

  private

  def add_growl(driver)
     driver.execute_script("if (!window.jQuery) {
        var jquery = document.createElement('script'); jquery.type = 'text/javascript';
        jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';
        document.getElementsByTagName('head')[0].appendChild(jquery);
      }")

    driver.execute_script("$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')")

    driver.execute_script("$('head').append('<link rel=\"stylesheet\" href=\"http://the-internet.herokuapp.com/css/jquery.growl.css\" type=\"text/css\" />');") 

    sleep 1
  end

  def display_growl(driver, message)
    driver.execute_script("$.growl({ title: 'Selenium', message: '#{message}' });")
    sleep 0.5
  end

end
