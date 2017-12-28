text = "X-DSPAM-Confidence:    0.8475 "
print(text[text.find('0'):].strip())
print(text[text.find(':')+1:].strip())

x = 'From marquard@uct.ac.za'

print(x[14:17])
