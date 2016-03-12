package models;

import java.util.Random;

/**
 * Created by cytex on 12/03/2016.
 */
public class Match {


    private static int week = 1;
    private int curWeek;
    private int homeTeamScore;
    private int awayTeamScore;
    private int[] scores = new int[2];
    private int winnerScore;
    private int loserScore;
    private int homeTeamRand;
    private int awayTeamRand;
    private int drawScoreRand;
    private int loserRandScore;
    private int finalHomeScore;
    private int finalAwayScore;

    Random rand = new Random();

    public Match(int homeTeamScore,int awayTeamScore)
    {
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.curWeek = week;
    }

    public int[] calcMatch()
    {
        homeTeamRand = rand.nextInt((99)+1)+homeTeamScore;
        awayTeamRand = rand.nextInt((99)+1)+awayTeamScore;
//check if the two numbers are the same if so it is a draw
        if(homeTeamRand == awayTeamRand )
        {
          drawScoreRand = rand.nextInt(3);
            scores[0] = drawScoreRand;
            scores[1] = drawScoreRand;
        }
        //else it is not a draw so find out who won check if homeTeam won
        else if(homeTeamRand > awayTeamRand){
            scores[0] = homeTeamRand -awayTeamRand;
            scores[1] = rand.nextInt(scores[0]);
        }
        //else away team won

        else
        {
            scores[1] = awayTeamRand - homeTeamRand;
            scores[0] = rand.nextInt(scores[1]);
        }


        return scores;
    }




}
