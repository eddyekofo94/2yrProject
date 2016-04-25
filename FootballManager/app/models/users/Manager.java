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

    
    public int bankaccount;
	public boolean ready;
	

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
		this.bankaccount = 8000;
		ready = false;
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

    public int getBankaccount()
        {
            return this.bankaccount;
        }
	public void updateBankaccount(int ammount)
	{
		
			this.bankaccount -= ammount;
		
		
	}
	public void setBankaccount(int ammount)
	{
		this.bankaccount += ammount;
	}
	
	public void setReady(boolean ready)
	{
		this.ready = ready;
	}
	public boolean getReady()
	{
		return this.ready;
	}
	
}
