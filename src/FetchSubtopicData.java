
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
public class FetchSubtopicData extends Data implements Serializable{
    
    private String courseName;
    private String subtopicName;
    private String teacherReference;
    private String teacherName;
    private boolean likes;
    private String videoName;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
    
    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }
    

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    

    public FetchSubtopicData() {
        courseName="";
        subtopicName="";
        teacherReference="";
    }

    public String getTeacherReference() {
        return teacherReference;
    }

    public void setTeacherReference(String teacherReference) {
        this.teacherReference = teacherReference;
    }
    
    public void setSubtopicName(String subtopicName) {
        this.subtopicName = subtopicName;
    }

    public String getSubtopicName() {
        return subtopicName;
    }
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String course_name) {
        this.courseName = course_name;
    }
    
}
