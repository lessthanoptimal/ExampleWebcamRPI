import boofcv.gui.image.ImagePanel;
import boofcv.gui.image.ShowImages;
import boofcv.io.webcamcapture.UtilWebcamCapture;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.openimaj.OpenImajDriver;

public class ShowWebcamInWindow {
    static {
        // Slower driver for RPI only that give me about 10 Hz
        // Webcam.setDriver(new RaspividDriver());
        // Provides 30 FPS on RPI
        Webcam.setDriver(new OpenImajDriver());
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
