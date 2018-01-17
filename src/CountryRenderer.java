
import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aman
 */
/*public class CountryRenderer extends JLabel implements ListCellRenderer<Video>{
     public CountryRenderer() {
        setOpaque(true);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends Video> list,Video country, int index, boolean isSelected, boolean cellHasFocus) {
       // String path = (country).getPath();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("ic_circle.png"));
        setIcon(imageIcon);
        setText((country).getName());
        list.setBackground(Color.white);
        if (isSelected) {
            setBackground(Color.GRAY);
            setForeground(Color.BLACK);
        } else {
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        }
        //add()
        //add(new JLabel(new IcongetClass().getResource("ic_circle.png")));
        return this;
        
        //To change body of generated methods, choose Tools | Templates.
    }
    
}*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aman
 */
public class CountryRenderer extends JLabel implements ListCellRenderer<Video>{
     JSeparator jSep;
    public CountryRenderer() {
        setOpaque(true);
        jSep=new JSeparator(JSeparator.HORIZONTAL);
    }
    
    @Override
    public Component getListCellRendererComponent(JList<? extends Video> list,Video country, int index, boolean isSelected, boolean cellHasFocus) {
       // String path = (country).getPath();
        if(country.getName().equals("SEP")){
        return jSep;    
        }
        JPanel jp=new JPanel(new GridBagLayout());
        //jp.setSize(400, 400);
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("play.png"));
        JLabel jl1=new JLabel();
        jl1.setIcon(imageIcon);
        jl1.setText((country).getName());
        list.setBackground(Color.white);
        if (isSelected) {
            setBackground(Color.GRAY);
            setForeground(Color.BLACK);
        } else {
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        }
        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.EAST;
        GridBagConstraints right = new GridBagConstraints();
        right.weightx = 2.0;
        right.fill = GridBagConstraints.HORIZONTAL;
        right.gridwidth = GridBagConstraints.REMAINDER;

         
         JLabel jl2=new JLabel("Likes: "+country.getLikes());
         jl2.setHorizontalAlignment(CENTER);
         jp.add(jl1,left);
         jp.add(jl2,right);
                 
        return jp;
       
        
        
        //To change body of generated methods, choose Tools | Templates.
    }
    
}

