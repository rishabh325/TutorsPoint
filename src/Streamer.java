
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import java.io.Serializable;
import java.net.ServerSocket;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aman
 */
public class Streamer implements Runnable,Serializable {
    String vid_name;
    int port;
    String HOST;
    static EmbeddedMediaPlayer emp2;
    public Streamer(String HOST,int port,String vid_name){
        this.vid_name=vid_name;
        this.port=port;
        this.HOST=HOST;
    } 
     public void run(){
         
        try {
    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (Exception e) {

}
        
        JFrame jf=new JFrame();
        jf.setTitle("VLC Media Player");
        ImageIcon img=new ImageIcon("ic_circle.png");
        jf.setIconImage(img.getImage());
        jf.setLocation(620,360);
        jf.setFocusable(false);
        jf.setSize(0,0);
        //jf.setDefaultCloseOpe/////ration(JFrame.EXIT_ON_CLOSE);
        
        Canvas c=new Canvas();
        c.setBackground(Color.black);
        JPanel jp=new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(c);
        
     
        jf.add(jp);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),"VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);
        MediaPlayerFactory mpf=new MediaPlayerFactory();
        EmbeddedMediaPlayer emp=mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(jf));
        emp.setVideoSurface(mpf.newVideoSurface(c));
        emp.setEnableMouseInputHandling(false);
        emp.setEnableKeyInputHandling(false);
        String file=vid_name;
        HOST=HOST.substring(1);
         System.out.println(HOST);
         //HOST="192.168.0.4";
         //port=5500;
        System.out.println(HOST+port);
        String[] localOptions = {formatRtpStream(HOST ,port), ":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all", ":sout-keep",};
        jf.setVisible(true);
        emp.playMedia(file, localOptions);
        emp.mute();
        emp2=emp;
        jf.setVisible(false);
    }
    private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#transcode{vcodec=mp4v,vb=2048,scale=1,acodec=mpga,ab=128,channels=2,samplerate=44100}:duplicate{dst=display,dst=rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        sb.append(",mux=ts}}");
        return sb.toString();
    }
    
}
