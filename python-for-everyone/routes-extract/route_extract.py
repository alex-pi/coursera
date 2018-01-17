from bs4 import BeautifulSoup
import codecs, re

fn = input("Enter html file: ")
if len(fn) < 1: fn = "11a-routes1.html"

base_name = re.findall('\S+-routes', fn)[0]
count = 1
outcsv = codecs.open(base_name+".csv", 'w', "utf-8")
while True:
    try:
        fc = open(fn).read()
    except IOError:
        print("Can't open", fn, ",finishing process.")
        break
    soup = BeautifulSoup(fc, "html.parser")
    fhand = codecs.open(base_name+str(count)+"_b.html", 'w', "utf-8")

    area = soup.select("#single-area-picker-name")[0].string
    table = soup.select('table[class="table table-striped route-table hidden-xs-down"]')[0]
    fhand.write(table.prettify())
    fhand.close()

    for row in table.select('tr[class="route-row"]'):
        #print(row.prettify())
        cols = row.select('td')
        name = cols[0].select('strong')[0].contents[0]
        wall = ">".join([loc.string for loc in cols[1].select('a')])
        stars = len(cols[2].select('img'))
        grade = cols[3].select('span[class="rateYDS"]')[0].contents[0]
        line = "{}|{}|{}|{}|{}\n".format(name, wall, stars, grade, area)
        outcsv.write(line)
    count += 1
    fn = "{}{}.html".format(base_name,count)

outcsv.close()