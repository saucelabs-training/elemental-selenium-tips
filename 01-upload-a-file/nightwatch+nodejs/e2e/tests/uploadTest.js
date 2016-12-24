// Matching Tip #1: Use really basic code to start.
// No page objects or special formatting yet.

module.exports = {
  before : function(browser) {
    console.log('Setting up...');
  },

  after : function(browser) {
    console.log('Closing down...');
  },

  'Upload test' : function (browser) {

    const filename = 'some-file.txt';
    const path = __dirname + '\\' + filename;  // Node.js command to find current directory
    console.log('Location for the text file: \n ' + path);

    browser
      .url('http://the-internet.herokuapp.com/upload')
      .waitForElementVisible('body', 5000)
      .setValue('input[type=file]', path)
      .waitForElementVisible('input[type=submit]', 5000)
      .click('input[type=submit]')
      .pause(5000)
      .assert.containsText('body', 'File Uploaded!')
      .end();
  }
};