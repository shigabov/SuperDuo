package barqsoft.footballscores;

import android.content.Context;

/**
 * Created by yehya khaled on 3/3/2015.
 */
public class Utilies
{
    public static final String BUNDESLIGA1 = "394";
    public static final String PREMIER_LEAGUE = "398";
    public static final String PRIMERA_DIVISION = "399";
    public static final String SERIE_A = "401";
    public static final String EREDIVISIE = "404";
    public static final String LIGUE_1 = "396";
    public static final String CHAMP_LEAGUE = "405";
    public static final String SEGUNDA = "400";

    public static String getLeague(String league_num, Context c)
    {
        switch (league_num)
        {
            case SERIE_A : return c.getString(R.string.seria_a);
                    
            case PREMIER_LEAGUE : return c.getString(R.string.premier_league);
            case CHAMP_LEAGUE : return c.getString(R.string.champions_league);
            case PRIMERA_DIVISION : return c.getString(R.string.primera);
            case BUNDESLIGA1 : return c.getString(R.string.bundesliga);
            case EREDIVISIE : return c.getString(R.string.eredivisie);
            case LIGUE_1:  return c.getString(R.string.ligue1);
            case SEGUNDA: return c.getString(R.string.segunda);
            default: return c.getString(R.string.no_league);
        }
    }
    public static String getMatchDay(int match_day,String league_num, Context c)
    {
        if(league_num .equals( CHAMP_LEAGUE))
        {
            if (match_day <= 6)
            {
                return c.getString(R.string.group_Stage)+match_day;
            }
            else if(match_day == 7 || match_day == 8)
            {
                return c.getString(R.string.first_knockout_round);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return c.getString(R.string.quaterfinal);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return c.getString(R.string.semi_final);
            }
            else
            {
                return c.getString(R.string.final_text);
            }
        }
        else
        {
            return c.getString(R.string.matchday) + String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
            //as you go.
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
            case "Chelsea FC" : return R.drawable.chelsea;
            default: return R.drawable.no_icon;
        }
    }
}
