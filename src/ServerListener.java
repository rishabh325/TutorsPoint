
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp-
 */
class ServerListener  implements Serializable,Runnable{
    //Socket s;
    ObjectOutputStream outToclient;
    ObjectInputStream inFromClient;
    DataOutputStream out;
    DataInputStream in;
    String HOST;
    Statement myStmt;
    private Socket socket;
    public ServerListener(Socket socket,DataInputStream din,DataOutputStream dout,ObjectInputStream doin,ObjectOutputStream doout ,Statement mySmt,String HOST){
        this.socket=socket;
        this.in=din;
        this.out=dout;
        this.outToclient=doout;
        this.inFromClient=doin;
        this.myStmt=mySmt;
        this.HOST=HOST;
        //this.run();
        //ReceiveThread rt=new ReceiveThread(s);
        //rt.start();
      
    }

    @Override
    public void run() {
        
      String query="";
        while(true)
        {
         Data d=null;    
          try{
              try{
              d=(Data)inFromClient.readObject();
              }
              catch(EOFException ex){
                  socket.close();
                  break;
              }
        if(d.getRequest()==1)
         {
             query=DataSql.signupStudent((StudentData)d);
             
             if(query.compareTo("null")==0)
             {
                 out.writeUTF("0");
             }
             else {
                try{
                 int ra=myStmt.executeUpdate(query);
                 if(ra==1){
                 out.writeUTF("1");
                 }
                 else out.writeUTF("0");
                }catch(Exception e){
                    out.writeUTF("0");
                }
             }
         }
        else if(d.getRequest()==0)
         {
             query=DataSql.loginStudent((StudentData)d);
             try{
                 int e=0;
                 String studentName=null;
                 ResultSet myRs=myStmt.executeQuery(query);
                 while (myRs.next()) {
                     studentName=myRs.getString("name");
                e++;
            }   
                 if(e==1){
                     out.writeUTF("1");
                     out.writeUTF(studentName);
                 }
                 else                 
                     out.writeUTF("0");


             }
             catch(Exception e)
             {
                 out.writeUTF("0");
             }
         }
        else if(d.getRequest()==3)
        {
            query=DataSql.loginTeacher((TeacherData)d);
             try{
                 int e=0;
                 String name=null;
                 ResultSet myRs=myStmt.executeQuery(query);
                 while (myRs.next()) {
                     name=myRs.getString("name");
                e++;
            }   
                 if(e==1){
                     out.writeUTF("1");
                     out.writeUTF(name);
                 }
                 else                 
                     out.writeUTF("0");
    
             }
             catch(Exception e)
             {
                 out.writeUTF("0");
             }
        }
        else if (d.getRequest()==4)
        {
            query=DataSql.signupTeacher((TeacherData)d);
            TeacherData tr=(TeacherData)d;
           // String query2="Create table Teacher"+tr.getReferece().toString()+"(`Courses` varchar(20) NOT NULL PRIMARY KEY)";
            //String query3=""
          //  String query2="Create table teachers"+tr.getName()+"(`Videos` varchar(70) PRIMARY KEY NOT NULL)";
            if(query.compareTo("null")==0)
             {
                 out.writeUTF("0");
             }
             else {
            try{
                 int ra=myStmt.executeUpdate(query);
                 int ra2=1;
                 //if(ra==1)
                 //ra2=myStmt.executeUpdate(query2);
                   //System.out.println(ra+" "+ra2);
                 if(ra==1){
                 out.writeUTF("1");
                 
                 }
                 else out.writeUTF("0");
                     
             }
             catch(Exception e)
             {
                 out.writeUTF("0");
             }
            }
        }
            else if(d.getRequest()==5)
            {
                    FetchSubtopicData fData=(FetchSubtopicData)d;
                    System.out.println("Before");
                     new ClientWorker(in,out);
                     System.out.println("After");
                     String fileName=(String)in.readUTF();
                     System.out.println(fData.getTeacherName());
                     query="Insert into  "+fData.getCourseName()+fData.getSubtopicName()+"(`Video`,`Course`,`Path`,`Likes`,`Teacher`) values(\""+fileName+" BY "+fData.getTeacherName()+"\",\""+fData.getCourseName()+"\",\""+fileName+"\",0,"+fData.getTeacherReference()+")";
                     String query2= "Insert into  Videos(`Video`,`Teacher`) values(\""+fileName+"\","+fData.getTeacherReference()+")";
                     System.out.println(query);
                     System.out.println(query2);
                  try{
                 int ra=myStmt.executeUpdate(query);
                 if(ra==1){
                 
                 out.writeUTF("1");
                 myStmt.executeUpdate(query2);
                 }
                 else out.writeUTF("0");
                }
                  catch(Exception e){
                    out.writeUTF("0");
                } 
                     
            }
         else if(d.getRequest()==6){
            FetchSubtopicData tr=(FetchSubtopicData)d;
           // TeacherData tr=(TeacherData)d;
            String query2="Create table "+tr.getCourseName().toString()+tr.getSubtopicName().toString()+"(`Video` varchar(70) primary key, `Course` varchar(20) NOT NULL , `Path` varchar(60) NOT NULL,`Likes` int NOT NULL  , `Teacher` int default 100001)";
            System.out.println(query2);
            query="Insert into  "+tr.getCourseName()+"(`Courses`) values(\""+tr.getSubtopicName().toString()+"\")";
            System.out.println(query);
                  try{
                 int ra=myStmt.executeUpdate(query);
                 if(ra==1){
                 myStmt.executeUpdate(query2);
                 out.writeUTF("1");
                 }
                 else out.writeUTF("0");
                }
                  catch(Exception e){
                    out.writeUTF("0");
                }        
        }
       
        else if(d.getRequest()==7){
            TeacherData tr=(TeacherData)d;
            query="Select * from courses";//+tr.getReferece();
            System.out.println(query);
            try{
                 int e=0;
                 //DefaultListModel model=new DefaultListModel();
                 System.out.println("Executing Query");
                 ResultSet myRs=myStmt.executeQuery(query);
                 System.out.println("Query Executed");
                 DefaultListModel mod=new DefaultListModel();
              
                 while (myRs.next()) {
                       mod.add(e,myRs.getString("Courses"));   
                       
                e++;
            } 
                 //list.setModel(mod);
             
                 if(e!=0){System.out.println("M Good");
                     out.writeUTF("1");
                     outToclient.writeObject(mod);
                 }
                 else{
                     System.out.println("M Bad");
                     out.writeUTF("0");
                 }

             }
             catch(Exception e)
             {
                 System.out.println("Exception here");
                 out.writeUTF("0");
             }
        }
        else if(d.getRequest()==8){
            FetchSubtopicData tr=(FetchSubtopicData)d;
            query="Select * from "+tr.getCourseName();
            System.out.println(query);
            try{
                 int e=0;
                 //DefaultListModel model=new DefaultListModel();
                 ResultSet myRs=myStmt.executeQuery(query);
                 DefaultListModel mod=new DefaultListModel();
              
                 while (myRs.next()) {
                       mod.add(e,myRs.getString("Courses"));   
                       
                e++;
            } 
                 //list.setModel(mod);
             
                 if(e!=0){
                     out.writeUTF("1");
                     outToclient.writeObject(mod);
                 }
                 else                 
                     out.writeUTF("0");


             }
             catch(Exception e)
             {
                 out.writeUTF("0");
             }
            
        }
        else if(d.getRequest()==9){
            FetchSubtopicData tr=(FetchSubtopicData)d;
           // TeacherData tr=(TeacherData)d;
            String query2="Create table "+tr.getCourseName().toString()+"(`Courses` varchar(20) NOT NULL PRIMARY KEY)";
            System.out.println(query2);
            query="Insert into  courses(`Courses`,`Rating`,`Counts`) values(\""+tr.getCourseName()+"\",0,0)";
            System.out.println(query);
                  try{
                 int ra=myStmt.executeUpdate(query);
                 if(ra==1){
                 myStmt.executeUpdate(query2);
                 out.writeUTF("1");
                 }
                 else out.writeUTF("0");
                }
                  catch(Exception e){
                    out.writeUTF("0");
                }        
        }
        else if(d.getRequest()==10){
            FetchSubtopicData fData=(FetchSubtopicData)d;
            query="Select * from "+fData.getCourseName()+fData.getSubtopicName()+" where Teacher="+fData.getTeacherReference()+" order by Likes DESC";//+tr.getReferece();
            System.out.println(query);
            try{
                 int e=0;
                 //DefaultListModel model=new DefaultListModel();
                 ResultSet myRs=myStmt.executeQuery(query);
                 DefaultListModel<Video> mod=new DefaultListModel<Video>();
              
                 while (myRs.next()) {
                       mod.add(e,new Video(myRs.getString("Video"),myRs.getString("Path"),fData.getCourseName(),Integer.parseInt(myRs.getString("Likes")))); 
                        e++;
                        mod.add(e,new Video("SEP",myRs.getString("Path"),fData.getCourseName(),Integer.parseInt(myRs.getString("Likes")))); 
                       
                       //mod.add(e,new Video("SEPARATOR",myRs.getString("Path"),fData.getCourseName(),0)); 
                        e++;
            } 
                 //list.setModel(mod);
             
                 if(e!=0){
                     out.writeUTF("1");
                     outToclient.writeObject(mod);
                 }
                 else                 
                     out.writeUTF("0");

             }
             catch(Exception e)
             {
                 e.printStackTrace();
                 out.writeUTF("0");
             }
        }
        else if(d.getRequest()==11)
        {
            out.writeInt(Server.i);
            
            String videoName=(String)in.readUTF();
            new Thread(new Streamer(socket.getInetAddress().toString(),Server.i, videoName)).start();
                    Server.i++;
        
                    
        //s.close();
        }
         else if(d.getRequest()==12){
            StudentData studentData=(StudentData)d;
            query="Select * from courses order by Rating DESC";//+tr.getReferece();
            System.out.println(query);
            try{
                 int e=0;
                 //DefaultListModel model=new DefaultListModel();
                 System.out.println("Executing Query");
                 ResultSet myRs=myStmt.executeQuery(query);
                 System.out.println("Query Executed");
                 DefaultListModel<CourseData> mod=new DefaultListModel<>();
              
                 while (myRs.next()) {
                       mod.add(e,new CourseData(myRs.getString("Courses"),Integer.parseInt(myRs.getString("Rating"))));   
                       
                e++;
            } 
                 //list.setModel(mod);
             
                 if(e!=0){System.out.println("M Good");
                     out.writeUTF("1");
                     outToclient.writeObject(mod);
                 }
                 else{
                     System.out.println("M Bad");
                     out.writeUTF("0");
                 }

             }
             catch(Exception e)
             {
                 System.out.println("Exception here");
                 out.writeUTF("0");
             }
        }
        else if(d.getRequest()==13){
            FetchSubtopicData fData=(FetchSubtopicData)d;
            query="Select * from "+fData.getCourseName()+fData.getSubtopicName()+" order by Likes DESC";
            System.out.println(query);
            try{
                 int e=0;
                 //DefaultListModel model=new DefaultListModel();
                 ResultSet myRs=myStmt.executeQuery(query);
                 DefaultListModel<Video> mod=new DefaultListModel<Video>();
              
                 while (myRs.next()) {
                       mod.add(e,new Video(myRs.getString("Video"),myRs.getString("Path"),fData.getCourseName(),Integer.parseInt(myRs.getString("Likes")))); 
                        e++;
                        mod.add(e,new Video("SEP",myRs.getString("Path"),fData.getCourseName(),Integer.parseInt(myRs.getString("Likes")))); 
                       
                       //mod.add(e,new Video("SEPARATOR",myRs.getString("Path"),fData.getCourseName(),0)); 
                        e++;
            } 
                 //list.setModel(mod);
             
                 if(e!=0){
                     out.writeUTF("1");
                     outToclient.writeObject(mod);
                 }
                 else                 
                     out.writeUTF("0");

             }
             catch(Exception e)
             {
                 e.printStackTrace();
                 out.writeUTF("0");
             }
        }
          else if(d.getRequest()==14)
         {
             AdminData admin=(AdminData)d;
             query="Select * from admin where username=\""+admin.getUsername()+"\" and password=\""+admin.getPassword()+"\"";
             try{
                 int e=0;
                 
                 ResultSet myRs=myStmt.executeQuery(query);
                 while (myRs.next()) {
                 
                e++;
            }   
                 if(e==1){
                     out.writeUTF("1");
                     //out.writeUTF(studentName);
                 }
                 else                 
                     out.writeUTF("0");


             }
             catch(Exception e)
             {
                 out.writeUTF("0");
             }
         }
        else if (d.getRequest()==15)
        {
            TeacherData teacher = (TeacherData)d;
            query="Insert into teachers(`ref`) values(\""+teacher.getReferece()+"\")";
            System.out.println(query);
            if(query.compareTo("null")==0)
             {
                 out.writeUTF("0");
             }
             else {
            try{
                 int ra=myStmt.executeUpdate(query);
                 int ra2=1;
                 //if(ra==1)
                 //ra2=myStmt.executeUpdate(query2);
                   //System.out.println(ra+" "+ra2);
                 if(ra==1)
                 out.writeUTF("1");
                 else out.writeUTF("0");
                     
             }
             catch(Exception e)
             {
                 out.writeUTF("0");
             }
            }
        }
        else if (d.getRequest()==16)
        {
            TeacherData teacher = (TeacherData)d;
            query="Delete from teachers where ref="+teacher.getReferece();
            System.out.println(query);
            if(query.compareTo("null")==0)
             {
                 out.writeUTF("0");
             }
             else {
            try{
                 int ra=myStmt.executeUpdate(query);
                 if(ra==1)
                 out.writeUTF("1");
                 else out.writeUTF("0");
                     
             }
             catch(Exception e)
             {
                 out.writeUTF("0");
             }
            }
        }
        else if(d.getRequest()==17)
        {
            FetchSubtopicData fData=(FetchSubtopicData)d;
            query="update "+fData.getCourseName()+fData.getSubtopicName()+" set Likes=Likes+1 where Video LIKE \""+fData.getVideoName()+"%\"";
            System.out.println(query);
            int ra=myStmt.executeUpdate(query);
            Streamer.emp2.stop();
            
           
        }
        else if(d.getRequest()==18)
        {
            FetchSubtopicData fData=(FetchSubtopicData)d;
            query="Delete from "+fData.getCourseName()+fData.getSubtopicName()+" where Video LIKE \""+fData.getVideoName()+"%\"";
            System.out.println(query);
            String query2="Delete from videos where Video LIKE \""+fData.getVideoName()+"%\"";
            File fil=new File(fData.getVideoName());
            fil.delete();
            int ra=myStmt.executeUpdate(query);
            System.out.println(query2);
            int ra2=myStmt.executeUpdate(query2);
        }
        else if(d.getRequest()==19)
        {
            CourseData courseData=(CourseData)d;
            query="update courses set rating= (rating * counts+"+courseData.getRating()+")/(counts+1), counts= counts+1 where courses=\""+courseData.getCourseName()+"\"";
            
            System.out.println(query);
            int ra=myStmt.executeUpdate(query);
        }
        else if(d.getRequest()==20){
            FetchSubtopicData fData=(FetchSubtopicData)d;
            String query2="Select * from teachers where UPPER(name) LIKE UPPER(\"%"+fData.getTeacherName()+"%\")";
            System.out.println(query2);
            int reference=0;
            
            String r=null;
        
            try{
                ResultSet myRs=myStmt.executeQuery(query2);
                int e=0;
                while(myRs.next())
                {
                    e++;
                    reference=myRs.getInt("ref");
                }
                System.out.println(e);
            }
            catch(Exception e)
            {
                System.out.println("Query couldn't work!!");
                e.printStackTrace();
            }
                query="Select * from Videos where Teacher="+reference;
                    System.out.println(query);
            try{
                 int e=0;
                 //DefaultListModel model=new DefaultListModel();
                 ResultSet myRs=myStmt.executeQuery(query);
                 DefaultListModel<Video> mod=new DefaultListModel<Video>();
              
                 while (myRs.next()) {
                       mod.add(e,new Video(myRs.getString("Video"),myRs.getString("Video"),"",0)); 
                        e++;
                        //mod.add(e,new Video("SEP",myRs.getString("Path"),fData.getCourseName(),Integer.parseInt(myRs.getString("Likes")))); 
                       
                       //mod.add(e,new Video("SEPARATOR","","",0)); 
                       // e++;
            } 
                 //list.setModel(mod);
             
                 if(e!=0){
                     out.writeUTF("1");
                     outToclient.writeObject(mod);
                 }
                 else                 
                     out.writeUTF("0");

             }
             catch(Exception e)
             {
                 e.printStackTrace();
                 out.writeUTF("0");
             }
        }
        else if(d.getRequest()==21)
        {
            Streamer.emp2.stop();   
           
        }
        
          }
          
       catch(Exception e){
       e.printStackTrace();
           //System.out.println("Server.main() error");
       }
          
        }
    }
}
