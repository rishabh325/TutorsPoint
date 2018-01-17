
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectOutputStream;
import javax.swing.JList;

class PopClickListener extends MouseAdapter {
    JList jList;
    ObjectOutputStream doout;
    FetchSubtopicData fata;
    public PopClickListener(JList jList,ObjectOutputStream doout,FetchSubtopicData fata){
        this.jList=jList;
        this.doout=doout;
        this.fata=fata;
    }
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
        jList.setSelectedIndex(jList.locationToIndex(e.getPoint()));
        
        
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUpDemo menu = new PopUpDemo(jList,doout,this.fata);
        menu.show(e.getComponent(), e.getX(), e.getY());
    
    }
}