/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DEEPANSHU
 */
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Server implements Serializable{
    public static int i=9999;
    public static String HOST=null;
    public static int PORT;
    public static void MultiInputPane()
        {
            JTextField hostField = new JTextField(5);
          JTextField portField = new JTextField(5);

          JPanel myPanel = new JPanel();
          myPanel.add(new JLabel("HOST:"));
          myPanel.add(hostField);
          myPanel.add(Box.createHorizontalStrut(15)); // a spacer
          myPanel.add(new JLabel("PORT:"));
          myPanel.add(portField);

          int result = JOptionPane.showConfirmDialog(null, myPanel, 
                   "Please Enter The Valid Values", JOptionPane.OK_CANCEL_OPTION);
          if (result == JOptionPane.OK_OPTION) {
             System.out.println("x value: " + hostField.getText());
             System.out.println("y value: " + portField.getText());
        }
          HOST=hostField.getText();
          PORT=Integer.parseInt(portField.getText());
        }
    
    public static void main(String[] args) {
    Connection myConn;
    Statement myStmt=null;
    //HOST="";
    PORT=5000;
    MultiInputPane();
    
    String url1 = "jdbc:mysql://localhost:3306/tutors_point?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "Akshay@123";
        
        try {
            myConn = DriverManager.getConnection(url1, user, password);
            if(myConn!=null){
                JOptionPane.showMessageDialog(null,"connected");
            }
            myStmt = myConn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServerSocket listener=null; 
        try{
        
        listener=new ServerSocket(PORT);}
        catch(Exception e){
        }
        Socket s=null;
        String message="";
        while(true){
        
        try {
            s = listener.accept();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connected");
        try {
            ObjectOutputStream outToclient=new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream inFromClient=new ObjectInputStream(s.getInputStream());
            DataOutputStream out=new DataOutputStream(s.getOutputStream());
            DataInputStream in=new DataInputStream(s.getInputStream());
            
            new Thread(new ServerListener(s,in, out, inFromClient, outToclient, myStmt,HOST)).start();
            System.out.println("Hello");
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        } 
        
    
}
}

         //To change body of generated methods, choose Tools | Templates.
    
