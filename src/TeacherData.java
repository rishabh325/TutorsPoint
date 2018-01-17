
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
public class TeacherData extends Data implements Serializable {
    private String reference;

  private String password,name,email;
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReferece() {
        return reference;
    }
    
    
    
}
