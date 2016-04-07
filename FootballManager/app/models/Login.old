package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

public class Login{
    public String userID;
    public String password;
    
    public String validate(){
        if(User.authenticate(userID, password) == null){
            return "Invalid user or password";
        }
        else{
            return null;
        }
    }
}