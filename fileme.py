f = open('window.txt','r')
a = f.read()
f.close()
f = open('window.txt','w')
if a == '1':
	f.write('0')
else:
	f.write('1')
f.close()