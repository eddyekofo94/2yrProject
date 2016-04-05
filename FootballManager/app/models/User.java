package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;


@Entity
public class User extends Model {

  @Id
  public Long userID;

  @Constraints.Required
  @Constraints.MaxLength(15)
  @Constraints.MinLength(4)
  public String name;

  @Constraints.Required
  @Constraints.MaxLength(15)
  @Constraints.MinLength(4)
  //Minimum 8 characters at least 1 Alphabet and 1 Number:
  @Constraints.Pattern(value = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
  public String loginName;


  @Constraints.Required
  @Constraints.MaxLength(15)
  @Constraints.MinLength(4)
  //Minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character:
  @Constraints.Pattern(value = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
  public String password;



  @OneToOne(mappedBy = "user")
  public Team team;

    public User() {

    }

    public User(Long userID, String password, String name, String loginName) {
        try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
          }
          password = sb.toString();
          this.password = password;
        }
        catch (Exception e) {

        }

        this.userID = userID;
        this.name = name;
        this.loginName = loginName;


    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class); //Not Ure

    public static List<User> findAll(){

        return User.find.all();
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
    // public Long getTeamID(){
    //      return team_id;
    //  }

    public Long getUserID(){
         return userID;
     }

    //  public void RegisterUser(){
    //     for(Team t : Team.<Team>findAll()){
    //     this.team.add(t);
    // }
    //
    // for(int i = 0;i < team.size();i++)
    // {
    //     if(team.get(i).getUserID() == null)
    //     {
    //
    //         this.team_id = team.get(i).getTeamID();
    //         break;
    //     }
    // }
    // }
}
