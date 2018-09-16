	#!/usr/bin/env python

from time import sleep
from subprocess import call
import os
import RPi.GPIO as GPIO
from io import open
GPIO.setmode(GPIO.BCM)
GPIO.setup(14, GPIO.IN)
state= GPIO.input(14)
#fd="/home/pi/Jess71p2/bin/SmartHome/src/rain.txt"
#fil= open(fd,'w')
#fil.write(u'0.4')
#fil.close()
def moisture():
	GPIO.setup(16,GPIO.IN)
	state=GPIO.input(16)
	prevState=state
	state=GPIO.input(16)
	if state==0:
		print "moisture in moisture sensor"
		return 1
	else:
		print "no moisture in moisture sensor"
		return 0

	
os.system("sudo javac -cp ./jess.jar ./Test.java")
while True:
#	prevState = state
	state = GPIO.input(14)
	if state == 0:
    		print "Water detected in rain sensor! trying to close"
		os.system("sudo java -cp .:./jess.jar Test 1")
	else:
    		print "Water not detected in rain sensor"
		result=moisture()
		print "moisture "+ str(result)
		if result == 1:
				os.system("sudo java -cp .:./jess.jar Test 0")
		else:
				print " trying to open"
				os.system("python servo.py 180") 
				GPIO.setup(14,GPIO.IN)
				state=GPIO.input(14)
#	os.system("sudo javac -cp ./jess.jar Test.java")
#	if(prevState!=state):
#	if(state==0):
#			fil.write(u'0.6')
#		os.system("sudo java -cp .:./jess.jar Test 1")
#	else:
		
#			fil.write(u'0.4')
#		fil.close()
		
	sleep(2.5)

