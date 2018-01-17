
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Aman
 */
public class Video implements Serializable{
    private String name;
    private String path;
    private String course;
    private int likes;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }
    public Video(){
        
    }
 
    public Video(String name, String path,String course,int likes) {
        this.name = name;
        this.path = path;
        this.course=course;
        this.likes=likes;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getPath() {
        return path;
    }
 
    public void setPath(String path) {
        this.path = path;
    }
}
