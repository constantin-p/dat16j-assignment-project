package assignment.util;

import assignment.model.Match;
import assignment.model.Team;
import assignment.model.TeamStats;

import java.util.*;
import java.util.stream.Collectors;

public class Standings {

    private Standings() { }

    public static List<TeamStats> computeTable(List<Match> matches) {
        Map<String, TeamStats> resultMap = new HashMap<>();

        for (Match match : matches) {
            resultMap.putIfAbsent(match.getTeamA().getId(), new TeamStats(match.getTeamA()));
            resultMap.putIfAbsent(match.getTeamB().getId(), new TeamStats(match.getTeamB()));



            // The match result is set
            if (match.getDate() != null) {
                TeamStats teamAStats = resultMap.get(match.getTeamA().getId());
                TeamStats teamBStats = resultMap.get(match.getTeamB().getId());

                if (match.getGoalsTeamA() > match.getGoalsTeamB()) {
                    teamAStats.addWin(match.getGoalsTeamA(), match.getGoalsTeamB());
                    teamBStats.addLoss(match.getGoalsTeamB(), match.getGoalsTeamA());
                } else {
                    teamBStats.addWin(match.getGoalsTeamB(), match.getGoalsTeamA());
                    teamAStats.addLoss(match.getGoalsTeamA(), match.getGoalsTeamB());
                }

                resultMap.put(match.getTeamA().getId(), teamAStats);
                resultMap.put(match.getTeamB().getId(), teamBStats);
            }
        }

        return resultMap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(TeamStats::compare))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }
}
