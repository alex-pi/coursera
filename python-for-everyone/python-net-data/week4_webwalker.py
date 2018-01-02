from urllib.request import urlopen
from bs4 import BeautifulSoup


def getnexturl(curl):
    soup = BeautifulSoup(urlopen(curl).read(), "html.parser")
    return soup.find_all('a')[position-1].get('href')


url = input("Enter URL: ")
if len(url) < 1: url = 'http://py4e-data.dr-chuck.net/known_by_Dennan.html'
count = input("Enter count: ")
if len(count) < 1:
    count = 7
else:
    count = int(count)
position = input("Enter position: ")
if len(position) < 1:
    position = 18
else:
    position = int(position)

for i in range(0, count+1):
    print('Retrieving: ', url)
    url = getnexturl(url)
