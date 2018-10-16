require 'nokogiri'
require 'open-uri'
require 'uri'
require 'csv'

url_base = 'https://ja.wikipedia.org/wiki/'
CSV.open('data.csv', 'w:UTF-8') do |_csv|
    id = 1
    File.open('list.txt', 'r:UTF-8').each do |item|
        begin
            puts item
            uri = url_base + URI.encode(item.chomp)
            doc = Nokogiri::HTML(open(uri))
            _csv << [id, item.chomp, Random.rand(900) + 100, doc.css('div.mw-parser-output > p')[0].inner_text.chomp]
            id += 1
        rescue => e
            puts 'error in ' + item
            puts e
        end
        sleep 0.2
    end
end