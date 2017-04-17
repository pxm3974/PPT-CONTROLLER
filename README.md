# PPT CONTROLLER

PPT CONTROLLER is a mobile application that acts as the remote control for powerpoints. It helps the presenter to handle the powerpoint using the mobile phone. 

There is two component for this application
1. Mobile application
2. Powerpoint controller server

# Intial screen 
User need to provide valid IP address and port number of the server. 
![initial](https://cloud.githubusercontent.com/assets/8714605/25080134/cb69b00c-2307-11e7-9e24-63c5fcde4eae.png)

Once you are successfully log in, you will be to see the remote control.
![control1](https://cloud.githubusercontent.com/assets/8714605/25080133/cb59cc78-2307-11e7-8d18-1039cec6dbd0.png)

Once you click start button, you will see the current slide on top and total slide at the bottom. You can use next and previous button to navigate through the slides. 
![control2](https://cloud.githubusercontent.com/assets/8714605/25080135/cb6a30cc-2307-11e7-8f63-98a9afa73ae5.png)

If you want to go to specific page, you can also input the slide number and it will navigate you to that page.
![control3](https://cloud.githubusercontent.com/assets/8714605/25080137/cb6dedde-2307-11e7-9064-73d94949e34d.png)
![control4](https://cloud.githubusercontent.com/assets/8714605/25080136/cb6bcb44-2307-11e7-9538-deee1d9d3104.png)

# Server side code
```sh

import socket
import sys
from pptController import pptController
HOST = ''  # Symbolic name, meaning all available interfaces
PORT = 8888  # Arbitrary non-privileged port

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print 'Socket created'

# Bind socket to local host and port
try:
    s.bind((HOST, PORT))
except socket.error as msg:
    print 'Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1]
    sys.exit()

print 'Socket bind complete'

# Start listening on socket
s.listen(10)
print 'Socket now listening'

# now keep talking with the client
while 1:
    # wait to accept a connection - blocking call
    conn, addr = s.accept()
    print 'Connected with ' + addr[0] + ':' + str(addr[1])
    msg = conn.recv(1024)
    print (msg)
    if msg:
        ppt=pptController()
        split=","
        if msg.endswith("Start"):   #start the presentation
            ppt.fullScreen()
           # r="thankyou"
            #conn.send(r)
            m=ppt.getActivePresentationSlideIndex()
            n=ppt.getActivePresentationSlideCount()
            conn.send(str(m)+split+str(n))
        if msg.endswith("Pause"):       # pause the presentation
            ppt.click()
            m = ppt.getActivePresentationSlideIndex()
            n = ppt.getActivePresentationSlideCount()
            conn.send(str(m) +split+ str(n))

        if msg.endswith("Next"):    # go to next slide
            ppt.nextPage()
            m = ppt.getActivePresentationSlideIndex()
            n = ppt.getActivePresentationSlideCount()
            conn.send(str(m) +split+ str(n))
        if msg.endswith("Previous"):    # go to previous slide
            ppt.prePage()
            m = ppt.getActivePresentationSlideIndex()
            n = ppt.getActivePresentationSlideCount()
            conn.send(str(m) +split+ str(n))

        if msg[-1].isdigit():    # go to specific slide
            if msg[-2].isdigit():
                c=int(msg[-2:])
            else:
                c=int(msg[-1])
            print c
            ppt.gotoSlide(c)
            m = ppt.getActivePresentationSlideIndex()
            n = ppt.getActivePresentationSlideCount()
            conn.send(str(m) +split+ str(n))

s.close()

```
```sh
# -*- coding: utf-8 -*-
# latin-1
# -*- coding: utf-42 -*-

import win32com.client
import win32api
import win32con
import pythoncom

VK_CODE = {
	'spacebar':0x1B,
	'down_arrow':0x28,
}
class pptController:
	def __init__(self):    # looks for the powerpoint thats already open
		pythoncom.CoInitialize()
		self.app = win32com.client.Dispatch("PowerPoint.Application")

	def fullScreen(self):   # to make fullscreen mode

		if self.hasActivePresentation():
			self.app.ActivePresentation.SlideShowSettings.Run()
			return self.getActivePresentationSlideIndex()

	def click(self):	# to exit from the full screen mode
		win32api.keybd_event(VK_CODE['spacebar'],0,0,0)
		win32api.keybd_event(VK_CODE['spacebar'],0,win32con.KEYEVENTF_KEYUP,0)
		return self.getActivePresentationSlideIndex()

	def gotoSlide(self,index):	# to go to the specific slide

		if self.hasActivePresentation():
			try:
				self.app.ActiveWindow.View.GotoSlide(index)
				return self.app.ActiveWindow.View.Slide.SlideIndex
			except:
				self.app.SlideShowWindows(1).View.GotoSlide(index)
				return self.app.SlideShowWindows(1).View.CurrentShowPosition

	def nextPage(self):		# go to next page
		if self.hasActivePresentation():
			count = self.getActivePresentationSlideCount()
			index = self.getActivePresentationSlideIndex()
			return index if index >= count else self.gotoSlide(index+1)

	def prePage(self):		# go to previous page
		if self.hasActivePresentation():
			index =  self.getActivePresentationSlideIndex()
			return index if index <= 1 else self.gotoSlide(index-1)

	def getActivePresentationSlideIndex(self):  # display current slide number

		if self.hasActivePresentation():
			try:
				index = self.app.ActiveWindow.View.Slide.SlideIndex
			except:
				index = self.app.SlideShowWindows(1).View.CurrentShowPosition
		return index

	def getActivePresentationSlideCount(self):		# display total file count
		print self.app.ActivePresentation.Slides.count
		return self.app.ActivePresentation.Slides.Count

	def getPresentationCount(self):		# count the total slide
		return self.app.Presentations.Count

	def hasActivePresentation(self):		# check whether there is any more slides
		return True if self.getPresentationCount() > 0 else False

```
