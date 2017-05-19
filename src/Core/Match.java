package Core;

import java.time.ZonedDateTime;

/**
 * Created by Codrin on 4/23/2017.
 */
public class Match
{
    public Team t1;
    public Team t2;
    public int ScoreT1;
    public int ScoreT2;
    public ZonedDateTime matchDate;

    public Team getT1()
    {
        return t1;
    }
    public void setT1(Team t1)
    {
        this.t1 = t1;
    }


    public Team getT2()
    {
        return t2;
    }
    public void setT2(Team t2)
    {
        this.t2 = t2;
    }


    public int getScoreT1()
    {
        return ScoreT1;
    }
    public void setScoreT1(int ScoreT1)
    {
        this.ScoreT1 = ScoreT1;
    }


    public int getScoreT2()
    {
        return ScoreT2;
    }
    public void setScoreT2(int ScoreT2)
    {
        this.ScoreT2 = ScoreT2;
    }


    public ZonedDateTime getMatchDate()
    {
        return matchDate;
    }
    public void setMatchDate(ZonedDateTime matchDate)
    {
        this.matchDate = matchDate;
    }

}
