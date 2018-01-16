
while True:
    p = input("Enter a phrase: ")
    if "quit" == p: break
    r = "".join([w[1:] + w[0:1] + "ay " for w in p.split()])

    print(r)