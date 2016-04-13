package models.users;

import models.*;

public class Login {
    public String loginname;
    public String password;

    public String validate() {

        CalcSHA cs = new CalcSHA();
        String md = cs.calcPassword(this.password);
        this.password = md;

        if (User.authenticate(loginname, password) == null) {
            return "Invalid user or password";
        } else {
            return null;
        }
    }
}


