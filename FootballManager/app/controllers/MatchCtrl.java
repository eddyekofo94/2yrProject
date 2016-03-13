package controllers;

import java.util.*;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.data.Form.*;
import java.sql.*;

import play.db.*;




import views.html.*;

import models.Fixtures;
import models.Match;
import models.Team;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Phillip on 12/03/2016.
 */
public class MatchCtrl extends Controller {
    private static int curWeek = 1;
    private int homeTeamIndex;
    private int awayTeamIndex;
    private int[] scores = new int[2];





    public Result Match() {

       playMatch();

        return redirect("/fixtures");
    }

    public  void playMatch(){
        List<models.Fixtures> weekFixtures = new ArrayList();
        List<models.Team> teamsPlaying = new ArrayList<>();
//populate this weeks Fixtures
        for (Fixtures f : Fixtures.<Fixtures>findAll()) {
            if (f.getWeek() == curWeek) {
                weekFixtures.add(f);
            }
        }

        for (Team t : Team.<Team>findAll()) {
            teamsPlaying.add(t);
        }

        for (int i = 0; i < weekFixtures.size(); i++) {
            for (int j = 0; j < teamsPlaying.size(); j++) {
                if ((weekFixtures.get(i).getHomeTeamID() == teamsPlaying.get(j).getTeamID())) {
                    this.homeTeamIndex = j;
                } else if (weekFixtures.get(i).getAwayTeamID() == teamsPlaying.get(j).getTeamID()) {
                    this.awayTeamIndex = j;
                }

            }
            Match m1 = new Match(teamsPlaying.get(homeTeamIndex).getTeamScore(),teamsPlaying.get(awayTeamIndex).getTeamScore());
            m1.calcMatch();
            scores = m1.getScores();
            weekFixtures.get(i).setHomeScore(scores[0]);
            weekFixtures.get(i).setAwayScore(scores[1]);
            weekFixtures.get(i).setPlayed(true);
            weekFixtures.get(i).save();

        }
        curWeek++;






    }

    public static void setCurWeek(int curWeek) {
        MatchCtrl.curWeek = curWeek;
    }
}
