package models.users;

import java.util.*;
import javax.persistence.*;
import models.*;

import play.data.validation.*;
import com.avaje.ebean.*;


@Entity
// Map inherited classes to a single table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// This is a Customer of type admin
@DiscriminatorValue("manager")

// Customer inherits from the SuperUser class

public class Manager extends User {


//    @OneToOne
//    public Long teamID;

    
 //   @OneToOne(mappedBy="manager", cascade = CascadeType.ALL)
   // public Team team;

public int bankaccount;
	

    public Manager() {
		

    }

    public Manager(Long userid,String password, String name, String loginName) {
        super(userid, password, name, loginName);
//        this.suserid = suserid;
//        this.password = password;
//        this.name = name;
//        this.loginname = loginname;
			
    }
	
	public Manager(User user)
	{
		super(user.userid,user.name,user.loginname,user.password);
		this.bankaccount = 80000;
	}

//    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class); //Not Ure

    //Generic query helper for entity SuperUser with unique id String
    public static Finder<String,User> find = new Finder<String,User>(User.class);

    public static List<User> findAll(){

        return User.find.all();
    }


    // Check if a user is logged in (by id - email address)
    public static User getLoggedIn(String id) {
        if (id == null)
            return null;
        else
            // Find user by id and return object
            return find.byId(id);
    }
//public Long getUserid()
//{
 //  return  super.getid();
//}

    // Check if a user is logged in (by id - email address)
//    public static User getLoggedIn(String id) {
//        if (id == null)
//            return null;
//        else
//            // Find user by id and return object
//            return find.byId(id);
//    }


//  public static User authenticate(String suserid, String password){
//       return find.where().eq("suserid", suserid).eq("password", password).findUnique();
//   }
//
//  public static User getLoggedIn(String id){
//     if(id == null)
//          return null;
//
//      else
//          return find.byId(id);
//   }
public int getBankaccount()
	{
		return this.bankaccount;
	}
	public void updateBankaccount(int ammount)
	{
		if(this.bankaccount-ammount >= 0)
		{
			this.bankaccount -= ammount;
		}
		
	}
	
}
