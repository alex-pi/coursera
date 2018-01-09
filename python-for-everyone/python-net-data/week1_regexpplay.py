import re

a = 'X-DSPAM-Confidence: 0.8509 X-DSPAM-Probability: 0.0000 Y-Another: 3.5222 X-Another: 3.66666666'
print('By default * is greedy, so it tries the longest match.')
print(re.findall('X-.*:', a))
print('With ? we made it non-greedy so it stops with the first match. Here we have 2 occurrences: ')
print(re.findall('X-.*?:', a))
print('We can extract all the numbers easy.')
print(re.findall('[0-9.]+', a))
print('But what if we only want the ones under X- :')
print(re.findall('X-.+?:\s*([0-9.]+)', a))

b = 'From stephen.marquard@uct.ac.za Sat Jan  5 09:14:16 2008'
print('This extracts the email because it is greedy, it matches as many non-blank characters as it can.')
print(re.findall('\S+@(\S+)', b))
print('This gets stephen.marquard@u only. Being non-greedy, it stops as soon as it gets the first satisfactory match.')
print('Note that the first ? does not stop the regexp engine from getting all the chars before @')
print('Any subset of "stephen.marquard" is NOT a match for \S+@, so the engine keeps collecting the characters from '
      'left to right as long as it is a potential match.')
print(re.findall('\S+?@\S+?', b))
print('Knowing this, the pattern to split a text is evident: ')
print(re.findall('\S+', a))
csv = 'X-DSPAM-Confidence:,0.8509,X-DSPAM-Probability:,0.0000,Y-Another:,3.5222,X-Another:,3.66666666'
print('A classic CSV text can be split too')
print(re.findall('[^,]+', csv))
