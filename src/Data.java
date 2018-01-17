
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
import java.io.*;
public class Data implements Serializable{
    private int request;

    public void setRequest(int request) {
        this.request = request;
    }
    
    public int getRequest() {
        return request;
    }

    
}
