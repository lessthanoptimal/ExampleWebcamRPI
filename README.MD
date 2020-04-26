Minimalist example for running BoofCV on a Raspberry PI. Please follow the latest set up instructions, which can be found at the link below:

https://boofcv.org/index.php?title=Raspberry_PI

## Quick and Dirty Instructions

This has been tested on Raspbian running on a Raspberry PI 3B+

1) Seriously follow the instructions at [BoofCV](https://boofcv.org) or else it won't work or will be extremely slow
2) `./gradlew scanqrJar`
3) `java -jar scanqr.jar`
4) To stop hit `control-c` in the terminal

Running `gradlew` (step 2) can be a bit slow, takes about 2 to 5 minutes on my RPI. On a desktop this is a couple of seconds.
When you launch the jar (step 3) it scans for QR codes using whatever webcam it finds first and print out the results. Capturing images does seem very slow on my system about 200ms. Processing it is faster about 30ms to 50ms.
You might be better off using a different library to stream the webcam.

## Debugging / Viewing Webcam

If it just doesn't seem to want to scan the QR code you're holding in front of the camera you can open a window and view the webcam using this application. You must have your RPI running a desktop for this to work!

1) `./gradlew showJar`
2) `java -jar show.jar`
3) To exit close the window or hit `control-c` in the terminal

Cheers!
Peter Abeles