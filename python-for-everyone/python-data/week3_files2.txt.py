#mbox-short.txt
fname = input("Enter file name: ")
fh = open(fname)
count, total = 0, 0.0
for line in fh:
    if not line.startswith("X-DSPAM-Confidence:"): continue
    dspam = float(line[line.find(':')+1:].strip())
    count = count + 1
    total += dspam
print('Average spam confidence:', total / count)