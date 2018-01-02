from urllib.request import urlopen
import re

url = input("Enter - ")
if len(url) < 1: url = 'http://py4e-data.dr-chuck.net/comments_59745.html'

nums = [int(v) for v in re.findall('<span class="comments">([0-9]+)', urlopen(url).read().decode())]
print("Count", len(nums))
print("Sum", sum(nums))
