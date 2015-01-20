require 'rest-client'
require 'json'

class Logger

  def initialize(session_id)
    @session_id = session_id
  end

  def latest
    parse(fetch).last
  end

  private

  def fetch
    RestClient.post(
      "http://127.0.0.1:4444/wd/hub/session/#{@session_id}/log",
      { "type" => "client" }.to_json,
      content_type: :json,
      accept: :json
    )
  end

  def parse(input)
    logs = JSON.parse(input)
    messages = []
    logs["value"].each do |entry|
      msg = entry["message"]
      unless msg.include? "session:" or
        msg.include? "fetching logs" or
          msg.include? "execute script"
            messages << msg.scan(/handle(.*)$/)[-1][-1]
      end
    end
    messages
  end

end
