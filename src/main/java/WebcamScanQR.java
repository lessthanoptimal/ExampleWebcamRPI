import boofcv.abst.fiducial.QrCodeDetector;
import boofcv.alg.fiducial.qrcode.QrCode;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.webcamcapture.UtilWebcamCapture;
import boofcv.struct.image.GrayU8;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.raspberrypi.RaspiYUVDriver;

import java.awt.image.BufferedImage;
import java.util.List;


public class WebcamScanQR {

    // This is required for running on a Raspberry PI
    static {
        Webcam.setDriver(new RaspiYUVDriver());
    }

    public static void main(String[] args) {
        // Open the first webcam it sees at attempt to open it at 640x480
        Webcam webcam = UtilWebcamCapture.openDefault(640,480);

        System.out.println("Opened camera "+webcam.getName());

        // Create the detector
        QrCodeDetector<GrayU8> detector = FactoryFiducial.qrcode(null,GrayU8.class);

        int frame = 0;
        GrayU8 gray=null;
        while( true ) {
            frame++;
            BufferedImage image = webcam.getImage();
            gray = ConvertBufferedImage.convertFrom(image,gray);

            long time0 = System.nanoTime();
            detector.process(gray);
            long time1 = System.nanoTime();
            double milli = (time1-time0)*1e-6;

            // Print out results and some diagnostic info
            System.out.printf("%04d processed %d x %d in %4.2f (ms)\n",frame,gray.width,gray.height,milli);

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
