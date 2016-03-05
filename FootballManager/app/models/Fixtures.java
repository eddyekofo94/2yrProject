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
public long matchID;

@Constraints.Required
public String leagueName;

@Constraints.Required
public int week;

@Constraints.Required
public long homeTeamID;
@Constraints.Required
public long awayTeamID;

public int homeScore;
public int awayScore;


@ManyToMany(cascade = CascadeType.ALL)
public List<Team> tList = new ArrayList<Team>();


public Fixtures(){
}

public Fixtures(long MatchID , String leagueName, int week, long homeTeamID , int homeScore,long awayTeamID,int awayScore )
{
this.matchID = matchID;
this.leagueName = leagueName;
this.week = week;
this.homeTeamID = homeTeamID;
this.homeScore = homeScore;
this.awayTeamID = awayTeamID;
this.awayScore = awayScore;

}
public static Model.Finder<Long,Fixtures> find = new Model.Finder<Long,Fixtures>(Long.class,Fixtures.class);

public static List<Fixtures> findAll(){
return Fixtures.find.all();
}

}
