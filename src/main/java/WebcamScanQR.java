import boofcv.abst.fiducial.QrCodeDetector;
import boofcv.alg.fiducial.qrcode.QrCode;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.webcamcapture.UtilWebcamCapture;
import boofcv.struct.image.GrayU8;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.raspberrypi.RaspividDriver;

import java.awt.image.BufferedImage;
import java.util.List;


public class WebcamScanQR {

    // This is required for running on a Raspberry PI
    static {
        Webcam.setDriver(new RaspividDriver());
    }

    public static void main(String[] args) {
        // Open the first webcam it sees at attempt to open it at 640x480
        Webcam webcam = UtilWebcamCapture.openDefault(640,480);

        System.out.println("Opened camera "+webcam.getName());

        // Create the detector
        QrCodeDetector<GrayU8> detector = FactoryFiducial.qrcode(null,GrayU8.class);

        double averageFPS = 0.0; // fading average FPS
        long previousTime = System.currentTimeMillis();
        int countFrames = 0;
        GrayU8 gray=null;
        while( true ) {
            countFrames++;
            BufferedImage image = webcam.getImage();
            gray = ConvertBufferedImage.convertFrom(image,gray);

            // Process and compute how long it took BoofCV to scan for the QR code
            long time0 = System.nanoTime();
            detector.process(gray);
            long time1 = System.nanoTime();
            double milli = (time1-time0)*1e-6;

            // Compute a fading average of the total FPS including image capture here
            averageFPS = averageFPS*0.85 + 0.15/((System.currentTimeMillis()-previousTime)*1e-3);
            previousTime = System.currentTimeMillis();

            // Print out results and some diagnostic info
            System.out.printf("%04d processed %d x %d in %4.2f (ms) FPS %4.1f\n",
                    countFrames,gray.width,gray.height,milli,averageFPS);

            List<QrCode> detected = detector.getDetections();
            if( detected.isEmpty() ) {
                System.out.println("  Nothing detected");
                continue;
            }
            System.out.println("  Found "+detected.size());
            for( QrCode qr : detected ) {
                System.out.println("     QR \""+qr.message+"\"");
            }
        }
    }
}
