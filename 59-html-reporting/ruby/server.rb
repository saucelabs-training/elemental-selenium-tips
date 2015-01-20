require 'sinatra'

set :public_folder, 'output'

get '/' do
  redirect '/index.html'
end
