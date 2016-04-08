package models.users;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

//https://www.playframework.com/documentation/2.2.x/JavaGuide4

// Product entity managed by Ebean
@Entity
// specify mapped table name
@Table(name = "user")
// Map inherited classes to a single table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// Discriminator column used to define user type
@DiscriminatorColumn(name = "userType")
// This user type is user
@DiscriminatorValue("user")

public class User extends Model {

    //@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//public Long id;

	@Constraints.Required
    @Id
    public String userid;

    @Constraints.Required
		@Constraints.MaxLength(15)
    @Constraints.MinLength(4)
    public String name;

    @Constraints.Required
		@Constraints.MaxLength(15)
    @Constraints.MinLength(4)
		//Minimum 8 characters at least 1 Alphabet and 1 Number:
		@Constraints.Pattern(value = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    public String loginname;

		@Constraints.Required
		@Constraints.MaxLength(15)
		@Constraints.MinLength(4)
		//Minimum 8 characters at least 1 Alphabet, 1 Number and 1 Special Character:
    @Constraints.Pattern(value = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$")
    public String password;



    // Default constructor
    public User() {
    }
    // Constructor to initialise object
    public User(String userid, String password, String name, String loginname) {
        this.userid = userid;
        this.password = password;
        this.name = name;
        this.loginname = loginname;
    }
public static List<User> findAll(){

        return User.find.all();
    }
	//Generic query helper for entity SuperUser with unique id String
    public static Finder<String,User> find = new Finder<String,User>(String.class, User.class);


//    public static List<SuperUser> findAny(){
//
//        return SuperUser.find.all();
//    }

    // Static method to authenticate based on username and password
    // Returns user object if found, otherwise NULL
    //public	static SuperUser authenticate(String email, String password)
    public	static User authenticate(String userid, String password) {
        // If found return the user object with matching username and password
        return find.where().eq("userid", userid).eq("password", password).findUnique();
    }

    // Check if a user is logged in (by id - suserid)
    public static User getLoggedIn(String id) {
        if (id == null)
                return null;
        else
            // Find user by id and return object
            return find.byId(id);
    }

    public String getid()
    {
        return this.userid;
    }

    // Get the user type - from the discriminator value
    // http://stackoverflow.com/questions/3005383/how-to-get-the-discriminatorvalue-at-run-time
    // http://stackoverflow.com/questions/541749/how-to-determine-an-objects-class-in-java
    @Transient
    public String getUserType(){
        DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );
        return val == null ? null : val.value();
    }

}
