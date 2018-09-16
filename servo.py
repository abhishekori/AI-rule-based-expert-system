import RPi.GPIO as GPIO
import time
import sys


GPIO.setmode(GPIO.BCM)
GPIO.setup(20, GPIO.OUT)

p = GPIO.PWM(20, 50)
in1 = sys.argv[1]
p.start(2.5)

def runServo(in1):
        duty1 = float(in1)/18 + 2.5
        p.ChangeDutyCycle(duty1)
        print "turning servo file"
        time.sleep(1)
        p.stop()
        GPIO.cleanup()

try:
        runServo(in1)

except KeyboardInterrupt:
        p.stop()
        GPIO.cleanup()