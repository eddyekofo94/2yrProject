package models;

import java.util.Random;

/**
 * Created by Phillip on 12/03/2016.
 * to calc the match result
 */
public class Match {




    private int homeTeamScore;
    private int awayTeamScore;
    private int[] scores = new int[2];
    private int homeTeamRand;
    private int awayTeamRand;
    private int drawScoreRand;

     private Random rand = new Random();

    public Match(int homeTeamScore,int awayTeamScore)
    {
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;

    }

    public int[] getScores() {
        return scores;
    }

    public void calcMatch()
    {
        homeTeamRand = rand.nextInt((5))+homeTeamScore;
        awayTeamRand = rand.nextInt((5))+awayTeamScore;
        //check if the two numbers are the same if so it is a draw
        if(homeTeamRand == awayTeamRand )
        {
          drawScoreRand = rand.nextInt(3);
            scores[0] = drawScoreRand;
            scores[1] = drawScoreRand;
        }
        //else it is not a draw so find out who won check if homeTeam won
        else if(homeTeamRand > awayTeamRand){
            scores[0] = homeTeamRand - awayTeamRand;
            scores[1] = rand.nextInt(scores[0]);
        }
        //else away team won

        else
        {
            scores[1] = awayTeamRand - homeTeamRand;
            scores[0] = rand.nextInt(scores[1]);
        }

    }




}
