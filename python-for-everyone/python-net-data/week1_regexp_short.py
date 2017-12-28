import re

print(sum([int(v) for v in re.findall('[0-9]+', open('regex_sum_59743.txt').read())]))
