require 'rspec'

module TagLister
  def self.list_tags
    RSpec.configure do |c|
      c.add_setting :tags
      c.before(:suite) { RSpec.configuration.tags = {} }
      c.around(:each) do |example|
        tag_recorder = RSpec.configuration.tags
        standard_keys = [:example_group,
                         :block,
                         :description_args,
                         :caller,
                         :execution_result,
                         :full_description,
                         :line_number,
                         :file_path,
                         :description,
                         :described_class,
                         :location]
        example.metadata.each do |key, value|
          this_tag = "#{key}:#{value}" unless standard_keys.include?(key)
          unless this_tag.nil?
            tag_recorder[this_tag] = [] unless tag_recorder[this_tag]
            tag_recorder[this_tag] << [
              example.metadata[:full_description],
              example.metadata[:file_path],
              example.metadata[:line_number]
            ]
          end
        end
      end

      c.after(:suite) do
        tags = RSpec.configuration.tags

        case ENV['TAG_SORT_ORDER']
        when 'alphabetical'
          tags = tags.sort_by { |tag| tag[0] }
        when 'usage'
          tags = tags.sort_by { |tag| tag[1].count }.reverse
        end

        puts "\n\n"
        puts "***************"
        puts "* TAGS IN USE *"
        puts "***************"
        puts "\n"

        tags.each do |tag|
          puts "#{tag[0]} (used #{tag[1].count} times)"
          tag[1].each do |test_data|
            puts "  #{test_data[0]} (#{test_data[1]}:#{test_data[2]})\n"
          end
        end

        puts "\n#{tags.count} tags in use: sorted in #{ENV['TAG_SORT_ORDER']} order"

      end
    end

    tests = Dir.glob(File.join(Dir.getwd, 'spec/**/*'))
    RSpec::Core::Runner.run(tests)

  end
end

ENV['TAG_SORT_ORDER'] = ARGV[0] || 'usage'
TagLister.list_tags
