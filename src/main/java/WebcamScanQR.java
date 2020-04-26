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

/**
 * @author Peter Abeles
 */
public class WebcamScanQR {

    // This is required for running on a Raspberry PI
    static {
        Webcam.setDriver(new RaspiYUVDriver());
    }

    public static void main(String[] args) {
        QrCodeDetector<GrayU8> detector = FactoryFiducial.qrcode(null,GrayU8.class);

        Webcam webcam = UtilWebcamCapture.openDefault(640,480);

        int frame = 0;
        GrayU8 gray=null;
        while( true ) {
            frame++;
            BufferedImage image = webcam.getImage();
            gray = ConvertBufferedImage.convertFrom(image,gray);

            detector.process(gray);
            List<QrCode> detected = detector.getDetections();
            if( detected.isEmpty() ) {
                System.out.println(frame+" Nothing detected");
                continue;
            }
            System.out.println(frame+" Detected Total "+detected.size());
            for( QrCode qr : detected ) {
                System.out.println("Found: "+qr.message);
            }
        }
    }
}
