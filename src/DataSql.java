
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp-
 */
public class DataSql implements Serializable{
    
    public static String signupStudent(StudentData sd){
        String username=sd.getUsername();
        String name =sd.getName();
        String password=sd.getPassword();
        String email=sd.getEmail();
        String query="";
        if(username.isEmpty()||name.isEmpty()||password.isEmpty()||email.isEmpty())
            query="null";
        else 
            query="Insert into students (`username`,`name`,`password`,`email`) values (\""+username+"\",\""+name+"\",\""+password+"\",\""+email+"\")";
        return query;
    }
    public static String loginStudent(StudentData sd){
        String username=sd.getUsername();
        String password=sd.getPassword();
        String query="Select * from students where username=\""+username+"\"and password=\""+password+"\"";
        return query;
    }
    public static String loginTeacher(TeacherData sd){
        String ref=sd.getReferece();
        String password=sd.getPassword();
        String query="Select * from teachers where ref=\""+ref+"\"and password=\""+password+"\"";
        return query;
    }
    public static String signupTeacher(TeacherData sd){
        int ref=0;
        if(!sd.getReferece().isEmpty())
        ref=Integer.parseInt(sd.getReferece().toString());
        String name =sd.getName();
        String password=sd.getPassword();
        String email=sd.getEmail();
        String query="";
        if(sd.getReferece().isEmpty()||name.isEmpty()||password.isEmpty()||email.isEmpty())
            query="null";
        else 
        query="Update teachers set name='"+name+"',password='"+password+"',email='"+email+"',signup=1 where ref="+ref+" and signup=0";
        return query;
    }
    
}
