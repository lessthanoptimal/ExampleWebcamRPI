import boofcv.gui.image.ImagePanel;
import boofcv.gui.image.ShowImages;
import boofcv.io.webcamcapture.UtilWebcamCapture;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.raspberrypi.RaspividDriver;

public class ShowWebcamInWindow {
    // This is required for running on a Raspberry PI
    static {
        Webcam.setDriver(new RaspividDriver());
    }

    public static void main(String[] args) {
        Webcam webcam = UtilWebcamCapture.openDefault(640,480);
        System.out.println("Opened camera "+webcam.getName());

        ImagePanel gui = new ImagePanel();
        gui.setPreferredSize(webcam.getViewSize());
        ShowImages.showWindow(gui,"Webcam",true);
        while( true ) {
            gui.setImageRepaint(webcam.getImage());
        }
    }
}
