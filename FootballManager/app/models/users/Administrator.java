package models.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
// This is a SuperUser of type admin
@DiscriminatorValue("admin")

// Administrator inherits from the SuperUser class
public class Administrator extends User {
	public Administrator(User user)
	{
		super(user.userid,user.name,user.loginname,user.password);
	}
	public Administrator(Long id, String password, String name, String loginName)
	{
		super(id, password, name, loginName);
	}
	
} 