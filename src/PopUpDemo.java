
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

class PopUpDemo extends JPopupMenu {
    JMenuItem anItem;
    JList jList;
    ObjectOutputStream doout;
    FetchSubtopicData fata;
    public PopUpDemo(JList jList,ObjectOutputStream doout,FetchSubtopicData fat){
        this.jList=jList;
        this.fata=fat;
        this.doout=doout;
        //this.obj=obj;
        anItem = new JMenuItem("Delete");
        anItem.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
     
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Video vid=(Video)(jList.getModel().getElementAt(jList.getSelectedIndex()));
                FetchSubtopicData fD=new FetchSubtopicData();
                fD.setCourseName(fata.getCourseName());
                fD.setSubtopicName(fata.getSubtopicName());
                fD.setVideoName(vid.getPath());
                fD.setTeacherName(fata.getTeacherName());
                fD.setTeacherReference(fata.getTeacherReference());
                JOptionPane.showMessageDialog(null,((Video)(jList.getModel().getElementAt(jList.getSelectedIndex()))).getPath());
                //fata.setVideoName(vid.getPath());
                fD.setRequest(18);
                try {
                    doout.writeObject(fD);
                } catch (IOException ex) {
                    Logger.getLogger(PopUpDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                //JOptionPane.showMessageDialog(null,"Video deleted");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //Video vid=(Video)obj;
                
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        add(anItem);
    }

    PopUpDemo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}