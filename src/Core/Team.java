package Core;

/**
 * Created by Codrin on 4/23/2017.
 */
public class Team
{
    public Player p1;
    public Player p2;
    public String teamName = (p1.name + " | " + p2.name);


    public Player getP1()
    {
        return p1;
    }
    public void setP1(Player p1)
    {
        this.p1 = p1;
    }


    public Player getP2()
    {
        return p2;
    }
    public void setP2(Player p2)
    {
        this.p2 = p2;
    }


    public String getTeamName()
    {
        return teamName;
    }
    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

}
