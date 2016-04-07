package models.users;

public class Login{
    public String userid;
    public String password;
    
    public String validate(){
        if(User.authenticate(userid, password) == null){
            return "Invalid user or password";
        }
        else{
            return null;
        }
    }
}


