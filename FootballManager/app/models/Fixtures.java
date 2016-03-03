package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Fixtures extends Model{
@Id
public int matchID;

@Constraints.Required
public String leagueName;

@Constraints.Required
public String startTime;
@Constraints.Required
public String endTime;
@Constraints.Required
public int teamID;

public int homeScore;
public int awayScore;

@Constraints.Required
public Boolean played;
@ManyToMany(cascade = CascadeType.ALL)
List<Team> tlist = new ArrayList<Team>();

public Fixtures(){
}

public Fixtures(int MatchID , String leagueName, String startTime,String endTime, int teamID , int homeScore,int awayScore,Boolean Played )
{
this.matchID = matchID;
this.leagueName = leagueName;
this.startTime = startTime;
this.endTime = endTime;
this.teamID = teamID;
this.homeScore = homeScore;
this.awayScore = awayScore;
this.played = played;
}
public static Model.Finder<Long,Fixtures> find = new Model.Finder<Long,Fixtures>(Long.class,Fixtures.class);

public static List<Fixtures> findAll(){
return Fixtures.find.all();
}
}
