import sys
import RPi.GPIO as GPIO
from time import sleep
 
GPIO.setmode(GPIO.BCM)

A=23
B=24
E=22
GPIO.setup(E,GPIO.OUT) 

def open_window():
	GPIO.setup(A,GPIO.OUT)
	GPIO.setup(B,GPIO.OUT)
	GPIO.setup(E,GPIO.OUT)

	GPIO.output(E,GPIO.HIGH)
	GPIO.output(A,GPIO.HIGH)
	GPIO.output(B,GPIO.LOW) 
	sleep(0.05)

def close_window():
	GPIO.setup(A,GPIO.OUT)
	GPIO.setup(B,GPIO.OUT)
	GPIO.setup(E,GPIO.OUT)

	GPIO.output(E,GPIO.HIGH)
	GPIO.output(A,GPIO.LOW)
	GPIO.output(B,GPIO.HIGH) 
	sleep(0.05)

f = open('window.txt','r')
a = f.read()
f.close()
f = open('window.txt','w')
if sys.argv[1]=='1':
	if a == '0':
		f.write("1")
		close_window()
		
		
		
if sys.argv[1]=='0':
	if a=='1':
		f.write("0")
		open_window()
		
f.close()


'''dir=sys.argv[1]
print dir 
print "Turning motor on"
if dir == "1":
	
else:
'''
print "Stopping motor"
GPIO.output(E,GPIO.LOW)
 
GPIO.cleanup()
