import urllib.request as req, urllib.parse, urllib.error
import xml.etree.ElementTree as ET

url = input("Enter URL: ")
if len(url) < 1: url = 'http://py4e-data.dr-chuck.net/comments_59747.xml'

allcounts = [int(c.text) for c in ET.fromstring(req.urlopen(url).read().decode()).findall('.//count')]

print("Count: ", len(allcounts))
print("Sum: ", sum(allcounts))

