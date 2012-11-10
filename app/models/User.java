package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class User extends Model {

  @Id
	public String email;
	public String password;
	public String fullname;
	public boolean isAdmin;

	public User(String email, String password, String fullname) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
	}
		
	public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);

  public static List<User> findAll() {
    return find.all();
  }
	
  public static User findByEmail(String email) {
    return find.where().eq("email", email).findUnique();
  }
	public static User authenticate(String email, String password) {
		return find.where().eq("email", email).eq("password", password).findUnique(); 
	}
}
