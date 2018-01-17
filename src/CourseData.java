
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
public class CourseData extends Data implements Serializable{
    
    private String courseName;
    private int rating;

    public String getCourseName() {
        return courseName;
    }

    public int getRating() {
        return rating;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public CourseData(String courseName, int rating) {
        this.courseName = courseName;
        this.rating = rating;
    }
    
    
    
}
