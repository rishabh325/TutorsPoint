
import java.io.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp-
 */
public class TeacherUIData extends Data implements Serializable{
    private String course_name;
    private String subtopic;

    public String getCourse_name() {
        return course_name;
    }

    public String getSubtopic() {
        return subtopic;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }
    
    
}
