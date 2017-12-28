name = input("Enter file:")
if len(name) < 1 : name = "mbox-short.txt"
handle = open(name)

counts = {}
for line in handle:
    if not line.startswith('From: '): continue
    sender = line.split()[1]
    counts[sender] = counts.get(sender, 0) + 1

msender = None
mcount = -1
for k, v in counts.items():
    if msender is None or v > mcount:
        msender, mcount = k, v

print(msender, mcount)
