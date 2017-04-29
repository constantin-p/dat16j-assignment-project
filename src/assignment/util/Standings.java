package assignment.util;

import assignment.model.Match;
import assignment.model.Team;
import assignment.model.TeamStats;

import java.util.*;
import java.util.stream.Collectors;

public class Standings {

    private Standings() { }

    public static List<TeamStats> computeTable(List<Match> matches) {
        Map<Team, TeamStats> resultMap = new HashMap<Team, TeamStats>();

        matches.forEach(match -> {
            if (resultMap.containsKey(match.getTeamA())) {
                resultMap.put(match.getTeamA(), new TeamStats(match.getTeamA()));
            } else {
                resultMap.put(match.getTeamA(), new TeamStats(match.getTeamA()));

                if (resultMap.containsKey(match.getTeamB())) {
                    resultMap.put(match.getTeamB(), new TeamStats(match.getTeamB()));
                } else {
                    resultMap.put(match.getTeamB(), new TeamStats(match.getTeamB()));
                }
            }


            // The match result is set
            if (match.getDate() != null) {
                TeamStats teamAStats = resultMap.get(match.getTeamA());
                TeamStats teamBStats = resultMap.get(match.getTeamB());

                if (match.getGoalsTeamA() > match.getGoalsTeamB()) {
                    teamAStats.addWin(match.getGoalsTeamA(), match.getGoalsTeamB());
                    teamBStats.addLoss(match.getGoalsTeamB(), match.getGoalsTeamA());
                } else {
                    teamBStats.addWin(match.getGoalsTeamB(), match.getGoalsTeamA());
                    teamAStats.addLoss(match.getGoalsTeamA(), match.getGoalsTeamB());
                }

                resultMap.replace(match.getTeamA(), teamAStats);
                resultMap.replace(match.getTeamB(), teamBStats);
            }
        });

        return resultMap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(TeamStats::compare))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }
}
