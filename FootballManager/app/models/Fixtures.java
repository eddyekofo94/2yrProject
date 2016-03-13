package models;

import java.util.*;
import javax.persistence.*;


import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;




@Entity
public class Fixtures extends Model implements Comparable<Fixtures>{
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
public boolean played;



@ManyToMany(cascade = CascadeType.ALL)
public List<Team> tList = new ArrayList<Team>();



public Fixtures(){
}

public Fixtures(long MatchID , String leagueName, int week, long homeTeamID , int homeScore,long awayTeamID,int awayScore)
{
this.matchID = matchID;
this.leagueName = leagueName;
this.week = week;
this.homeTeamID = homeTeamID;
this.homeScore = homeScore;
this.awayTeamID = awayTeamID;
this.awayScore = awayScore;
this.played = false;


}


public static Model.Finder<Long,Fixtures> find = new Model.Finder<Long,Fixtures>(Long.class,Fixtures.class);

public static List<Fixtures> findAll(){
return Fixtures.find.all();
}

public long getHomeTeamID()
{
return homeTeamID;
}

    public int getWeek() {
        return week;
    }

    public long getAwayTeamID(){

return awayTeamID;

}

public int getawayScore(){
return awayScore;
}

public int gethomeScore(){
return homeScore;
}

@Override
    public int compareTo (Fixtures f1) {
    //sort by week
        return this.week - f1.week;
}

    public void setWeek(int week) {
        this.week = week;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }
}
