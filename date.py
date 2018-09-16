import sys
import os
mydate="date -s '2016-"+sys.argv[1]+"-09 12:34:56' "
print mydate
os.system(mydate)