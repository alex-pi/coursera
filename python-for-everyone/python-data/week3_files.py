fname = input("Enter file name: ")
fh = open(fname)
# print(fh.read().upper())

for line in fh:
    print(line.rstrip().upper())