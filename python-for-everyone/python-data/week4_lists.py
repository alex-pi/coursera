#fname = input("Enter file name: ")
fh = open('romeo.txt')
lst = list()
for line in fh:
    for w in line.rstrip().split():
        if w not in lst:
            lst.append(w)
lst.sort()
print(lst)
