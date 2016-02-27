ENV['browser']          ||= 'firefox'

case ENV['browser']
when 'internet_explorer'
  ENV['browser_version']  ||= '8'
  ENV['operating_system'] = 'Windows XP'
when 'firefox'
  ENV['browser_version']  ||= '26'
  ENV['operating_system'] = 'Windows XP'
when 'safari'
  ENV['browser_version']  ||= '5'
  ENV['operating_system'] = 'OSX 10.6'
when 'chrome'
  ENV['browser_version']  ||= '31'
  ENV['operating_system'] = 'Windows XP'
when 'opera'
  ENV['browser_version']  ||= '12'
  ENV['operating_system'] = 'Windows XP'
end
