import re

#handle = open('regex_sum_59743.txt')
handle = open('regex_sum_42.txt')

total = 0
for line in handle:
    for v in re.findall('[0-9]+', line):
        total += int(v)

print(total)