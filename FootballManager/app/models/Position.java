package models;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Model;

@Entity
public class Position extends Model{
    @Id
    public Long id;
    @Constraints.Required
    public String position;
    
    // Position has many players
    
    @OneToMany
    public List<Player> players;
    
    //Default constructor
    public Position(){
        
    }
    //Overloaded constructor
    public Position(Long id, String position, List<Player> players){
        this.id = id;
        this.position = position;
        this.players = players;
    }
    
    public static Finder<Long, Position> find = new Finder<Long, Position>(Long.class, Position.class);

}