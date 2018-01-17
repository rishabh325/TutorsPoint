/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;
import javax.swing.UIManager.*;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.border.Border;
import static mediaplayer.MediaPlayer.js;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author Aman
 */
public class MediaPlayer {
    public static EmbeddedMediaPlayer emp;
    public static JSlider js;
    public static JLabel timer;
    private static int port;
    private static String vid_name;
    public static void main(String[] args) {
        vid_name=args[0];
        try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (Exception e) {
}
        JFrame jf=new JFrame();
        jf.setTitle("VLC Media Player");
        ImageIcon img=new ImageIcon("play.png");
        jf.setIconImage(img.getImage());
        jf.setLocation(100,100);
        jf.setSize(600,400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        Canvas c=new Canvas();
        c.setBackground(Color.black);
        JPanel jp=new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(c,BorderLayout.CENTER);
        JPanel controlsPane = new JPanel();
        controlsPane.setLayout(new BorderLayout());
        JPanel seekBarPane = new JPanel();
        seekBarPane.setLayout(new BorderLayout());
        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(Color.gray);
        js=new JSlider();
        js.setValue(0);
        seekBarPane.add(js);
        controlsPane.add(seekBarPane,BorderLayout.NORTH);
        JButton pauseButton = new JButton("Pause");
        buttonPane.add(pauseButton);
        JButton rewindButton = new JButton("Rewind");
        buttonPane.add(rewindButton);
        JButton skipButton = new JButton("Skip");
        buttonPane.add(skipButton);
        JButton fs=new JButton("Full Scr");
        buttonPane.add(fs);
        controlsPane.add(buttonPane,BorderLayout.SOUTH);
        jp.add(controlsPane,BorderLayout.SOUTH);
        JPanel titlePane=new JPanel();
        titlePane.setLayout(new BorderLayout());
        JLabel titleText=new JLabel();
        titleText.setFont(new java.awt.Font("Arial",0,18));
        titleText.setForeground(new java.awt.Color(255,255,255));
        titleText.setText("Video Title");
        titlePane.setBackground(Color.black);
        titlePane.add(titleText,BorderLayout.CENTER);
        
        timer=new JLabel();
        timer.setForeground(new java.awt.Color(255,255,255));
        timer.setText("00:00");
        titlePane.add(timer,BorderLayout.EAST);
        
        jp.add(titlePane,BorderLayout.NORTH);
        jf.add(jp);
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),"VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(),LibVlc.class);
        MediaPlayerFactory mpf=new MediaPlayerFactory();
        emp=mpf.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(jf));
        emp.setVideoSurface(mpf.newVideoSurface(c));
        emp.setEnableMouseInputHandling(false);
        emp.setEnableKeyInputHandling(false);
        
        
        try {
            Socket s=new Socket("127.0.0.1",5555);
            DataOutputStream dout=new DataOutputStream(s.getOutputStream());
            DataInputStream din=new DataInputStream(s.getInputStream());
            port=din.readInt();
            dout.writeUTF(vid_name);
            System.out.println(port);
        } catch (IOException ex) {
            Logger.getLogger(MediaPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
                
        String check="rtp://@127.0.0.1:"+port;
        jf.setVisible(true);
        Thread th=new Thread(new SliderThread());
        th.start();
        
        emp.playMedia(check);
        
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if(emp.isPlaying()){
                    if (source instanceof JButton) {
                        JButton btn = (JButton)source;
                        btn.setText("Play");
                    }
                    emp.pause();
                }
                
                else{
                    if (source instanceof JButton) {
                        JButton btn = (JButton)source;
                        btn.setText("Pause");
                    }
                    emp.play();
                }
            }

            
        });
        
        
        rewindButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(emp.getTime()>=10000){
                    emp.skip(-10000);
                }
                
                else{
                    emp.setTime(0);
                }
            }

            
        });
        
       skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(emp.getLength()-emp.getTime()>=10000){
                    emp.skip(10000);
                }
                
                else{
                    emp.setTime(emp.getLength());
                }
            }

            
        });
       fs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emp.toggleFullScreen();
            }

            
        });
    }
    
}
class SliderThread implements Runnable{
    static float i=.1f;
    @Override
    public void run() {
        while(true){
            try {
                //MediaPlayer.timer.setText(MediaPlayer.emp.getLength()+"");
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(SliderThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            MediaPlayer.js.setValue((int)i);
            i+=.1f;
            //JOptionPane.showMessageDialog(js.getParent(), );
        }  
    }
}