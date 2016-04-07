package models.users;

public class Login{
    public String suserid;
    public String password;
    
    public String validate(){
        if(SuperUser.authenticate(suserid, password) == null){
            return "Invalid user or password";
        }
        else{
            return null;
        }
    }
}


