package models;
import com.avaje.ebean.Model;
import java.util.*;

public class LeagueTable extends Model implements Comparable<LeagueTable> {

public Long teamID;
public String teamName;
public int wins;
public int loses ;
public int draws;
public int goalDifference;
public int pts;
public static List<LeagueTable> league = new ArrayList();


public LeagueTable(Long teamID)
{
this.teamID = teamID;
this.wins = 0;
this.loses = 0;
this.draws = 0;
this.goalDifference = 0;
this.pts = 0;

}
public LeagueTable(Long teamID,int wins,int loses,int draws,int goalDifference,int pts)
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
	if((league.get(i).teamID == teamID))
	{
	  league.get(i).wins += wins;
	  league.get(i).loses += loses;
	  league.get(i).draws += draws;
	  league.get(i).goalDifference += gd;
	  league.get(i).pts += pts;

	}

   }
	Collections.sort(league);
}

	public  void resetLeague()
	{
		this.wins = 0;
		this.loses = 0;
		this.draws = 0;
		this.goalDifference = 0;
		this.pts = 0;
	}


 public static void clearLeague()
 {
 league.clear();
 }
 public static List<LeagueTable> getLeague()
 {
 return league;
 }

	public int compareTo(LeagueTable l1)
	{
		int result;
		//check if points are the same
		if(l1.pts != this.pts) {
			result = l1.pts - this.pts;
		}
        //if they are sort by goalDifference
		else
		{
			result = l1.goalDifference - this.goalDifference;
		}
		return result;

	}

}





