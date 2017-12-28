name = input("Enter file:")
if len(name) < 1 : name = "mbox-short.txt"
handle = open(name)

stats = {}
for line in handle:
    if not line.startswith('From '): continue
    h = line.split()[5].split(':')[0]
    stats[h] = stats.get(h, 0) + 1

for k, v in sorted([(k, v) for k, v in stats.items()]):
    print(k, v)
