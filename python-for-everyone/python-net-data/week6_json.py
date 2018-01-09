import json
import urllib.request as req

url = input("Enter URL: ")
if len(url) < 1: url = 'http://py4e-data.dr-chuck.net/comments_59748.json'

counts = [int(c["count"]) for c in json.loads(req.urlopen(url).read().decode())["comments"]]

print("Count: ", len(counts))
print("Sum: ", sum(counts))