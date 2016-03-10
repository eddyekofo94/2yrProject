package models;
import com.avaje.ebean.Model;
import java.util.*;

public class leagueTable extends Model {

public Long teamID;
public String teamName;
public int wins;
public int loses ;
public int draws;
public int goalDifference;
public int pts;
public static List<leagueTable> league = new ArrayList();


public leagueTable(Long teamID)
{
this.teamID = teamID;
this.wins = 0;
this.loses = 0;
this.draws = 0;
this.goalDifference = 0;
this.pts = 0;

}
public leagueTable(Long teamID,int wins,int loses,int draws,int goalDifference,int pts)
{
this.teamID = teamID;
this.wins = wins;
this.loses = loses;
this.draws = draws;
this.goalDifference = goalDifference;
this.pts = pts;

}


public void addTeam()
{
boolean found = false;

for(int i =0;i<league.size();i++)
{
if(this.teamID == league.get(i).teamID)
{
found = true;

}

}
if(found == false)
{
league.add(this);
}
}

public static void updateLeague(Long teamID,int wins,int loses,int draws,int gd,int pts)
{
	for(int i = 0;i < league.size();i ++)
	{
	if(league.get(i).teamID == teamID)
	{
	  league.get(i).wins += wins;
	  league.get(i).loses += loses;
	  league.get(i).draws += draws;
	  league.get(i).goalDifference += gd;
	  league.get(i).pts += pts;
	  
	}
   }
}
 
 public static List<leagueTable> getLeague()
 {
 return league;
 }

}





