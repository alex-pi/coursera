def computepay(h, r):
    eh = h - 40
    if eh < 0:
        eh = 0
    return ((h - eh) * r) + (eh * r * 1.5)

hrs = input("Enter Hours:")
rate = input("Enter Rate:")
p = computepay(float(hrs), float(rate))
print(p)