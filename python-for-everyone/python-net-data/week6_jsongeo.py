import json
import urllib.request as req, urllib.parse as par

address = input("Enter location: ")
if len(address) < 1: address = 'San Francisco State University'

url = "http://py4e-data.dr-chuck.net/geojson?" + par.urlencode({'address': address})

data = json.loads(req.urlopen(url).read().decode())

if data['status'] != 'OK':
    print("Not found")
    quit()

print("Place id", data['results'][0]['place_id'])



