require 'terminal-table'

logs = Dir.glob('./benchmarks/*.log')
@harvests = []

logs.each do |log|
  log = File.read(log)
  log.gsub(/ using \w+/,'')
  @titles  = log.scan(/^.*_\w+/).uniq
  datas    = log.scan(/\(.*\)/).each do |datum|
    datum.gsub!(/\)/,'')
    datum.gsub!(/\(/,'')
    datum.strip!
  end
  count = 0
  harvest = []
  @titles.count.times do |i|
    if i == 0
      harvest << [datas[i], datas[i+1]]
    else
      harvest << [datas[count], datas[count+1]]
    end
    count += 2
  end
  @harvests << harvest
end

row = []
@titles.each {|title| row << [title] }
@harvests.each do |harvest|
  harvest.count.times do |i|
    row[i] << harvest[i][0]
    row[i] << harvest[i][1]
  end
end

headings = ['', 'Cr 32', 'Cr 32', 'FF 26', 'FF 26', 'IE 8', 'IE 8', 'Op 12', 'Op 12']
rows = []
rows << ['', 'CSS', 'XPath', 'CSS', 'XPath', 'CSS', 'XPath', 'CSS', 'XPath']

row.each do |ro|
  rows << ro
end
table = Terminal::Table.new headings: headings, rows: rows
puts table
