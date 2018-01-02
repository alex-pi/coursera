from urllib.request import urlopen
from bs4 import BeautifulSoup

url = input("Enter - ")
if len(url) < 1: url = 'http://py4e-data.dr-chuck.net/comments_59745.html'

soup = BeautifulSoup(urlopen(url).read(), "html.parser")
# print(soup.select('span[class="comments"]'))
nums = [int(tag.contents[0]) for tag in soup.select('span[class="comments"]')]
print("Count", len(nums))
print("Sum", sum(nums))

