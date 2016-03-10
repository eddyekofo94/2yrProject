package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;

/*
@OneToOne(mappedBy="part")
public VendorPart getVendorPart() {
    return vendorPart;
}
Here is the relationship mapping in VendorPart:

@OneToOne
@JoinColumns({
    @JoinColumn(name="PARTNUMBER",
        referencedColumnName="PARTNUMBER"),
    @JoinColumn(name="PARTREVISION",
        referencedColumnName="REVISION")
})
public Part getPart() {
    return part;
}
 */


@Entity
public class User extends Model {

    @Id
    public Long userID;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String loginName;
    @OneToOne
    public Long teamID;

    public User() {

    }

    public User(Long userID, String password, String name, String loginName) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.loginName = loginName;
    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class); //Not Ure

    public static List<User> findAll(){

        return User.find.all();
    }



    public static User getLoggedIn(String id){
        if(id == null)
            return null;

        else
            return find.byId(id);
  }

  public static User authenticate(String userID, String password){
       return find.where().eq("userID", userID).eq("password", password).findUnique();
   }

  public static User getLoggedIn(String id){
     if(id == null)
          return null;

      else
          return find.byId(id);
   }
}