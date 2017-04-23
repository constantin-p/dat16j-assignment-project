package Core;

import java.time.ZonedDateTime;

/**
 * Created by Codrin on 4/23/2017.
 */
public class Player
{
    public String name;
    public ZonedDateTime dateOfBirth;
    public String email;
    public String rank;


    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }


    public ZonedDateTime getDateOfBirth()
    {
        return dateOfBirth;
    }
    public void setDateOfBirth(ZonedDateTime dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }


    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }


    public String getRank()
    {
        return rank;
    }
    public void setRank(String rank)
    {
        this.rank = rank;
    }

}
