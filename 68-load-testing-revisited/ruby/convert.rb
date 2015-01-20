# Adapted from https://github.com/flood-io/ruby-jmeter/blob/master/examples/basic_har.rb
# Which is what powers https://flood.io/har2jmx

require 'ruby-jmeter'
require 'recursive-open-struct'
require 'json'
require 'pry-debugger'

module HARtoJMX
  def self.convert(file)
    har = RecursiveOpenStruct.new(JSON.parse(File.open(file).read), recurse_over_arrays: true)

    test do
      cache

      cookies

      header [ 
        { name: 'Accept-Encoding', value: 'gzip,deflate,sdch' },
        { name: 'Accept', value: 'text/javascript, text/html, application/xml, text/xml, */*' }
      ]

      threads count: 1 do

        har.log.entries.collect {|entry| entry.pageref }.uniq.each do |page|

          transaction name: page do
            har.log.entries.select {|request| request.pageref == page }.each do |entry|
              next unless entry.request.url =~ /http/
              params = entry.request.postData && entry.request.postData.params.collect {|param| [param.name, param.value] }.flatten
              self.send entry.request.to_h.values.first.downcase, entry.request.url, fill_in: Hash[*params] do
                with_xhr if entry.request.headers.to_s =~ /XMLHttpRequest/
              end
            end
          end
        end
      end
    end.jmx
  end
end
