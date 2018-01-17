import urllib.request, urllib.parse

'''http://www.mountainproject.com/route-finder?
selectedIds=107788017
&type=rock
&diffMinrock=4600
&diffMinboulder=20000
&diffMinaid=70000
&diffMinice=30000
&diffMinmixed=50000
&diffMaxrock=4800
&diffMaxboulder=21400
&diffMaxaid=75260
&diffMaxice=38500
&diffMaxmixed=60000
&is_sport_climb=1
&stars=0
&pitches=0
&sort1=popularity+desc
&sort2=rating'''

base_url="https://www.mountainproject.com/route-finder?"

url = base_url + urllib.parse.urlencode({
    'selectedIds': '107788017',
    'type': 'rock',
    'diffMinrock': '4600',
    'diffMinboulder': '20000',
    'diffMinaid': '70000',
    'diffMinice': '30000',
    'diffMinmixed': '50000',
    'diffMaxrock': '4800',
    'diffMaxboulder': '21400',
    'diffMaxaid': '75260',
    'diffMaxice': '38500',
    'diffMaxmixed': '60000',
    'is_sport_climb': '1',
    'stars': '0',
    'pitches': '0',
    'sort1': 'popularity',
    'sort2': 'rating',
})

print('Retrieving', url)

uh = urllib.request.urlopen(url)
data = uh.read()
print(data.decode())
