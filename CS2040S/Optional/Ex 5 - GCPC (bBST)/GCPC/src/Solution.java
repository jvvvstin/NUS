import java.util.*;

public class Solution {
    // TODO: Include your data structures here
    private class Team implements Comparable<Team> {
        private int teamId;
        private int score;
        private long penalty;

        public Team(int teamId, int score, long penalty) {
            this.teamId = teamId;
            this.score = score;
            this.penalty = penalty;
        }

        public void update(long newPenalty) {
            this.penalty = newPenalty;
            this.score += 1;
        }

        @Override
        public int compareTo(Team t) {
            if (this.score != t.score) {
                return t.score - this.score;
            } else {
                if (this.penalty != t.penalty) {
                    return (int) (this.penalty - t.penalty);
                } else {
                    return this.teamId - t.teamId;
                }
            }
        }
    }

    TreeMap<Team, Integer> tree = new TreeMap<>();
    Team[] teams;

    public Solution(int numTeams) {
        // TODO: Construct/Initialise your data structures here
        this.teams = new Team[numTeams + 1];
        for (int i = 1; i < numTeams + 1; i++) {
            Team newTeam = new Team(i, 0 ,0);
            teams[i] = newTeam;
            tree.put(newTeam, i);
        }
    }

    public int update(int team, long newPenalty){
        // TODO: Implement your update function here
        this.tree.remove(teams[team]);
        teams[team].update(newPenalty);
        this.tree.put(teams[team], team);
        while (this.tree.lastKey() != teams[1]) {
            Team max = this.tree.lastKey();
            this.tree.remove(max);
        }
//        if (team == 1) {
//            while (!this.tree.isEmpty()) {
//                Team t = this.tree.lastKey();
//                if (teams[1].compareTo(t) < 0) {
//                    this.tree.remove(t);
//                } else {
//                    break;
//                }
//            }
//        } else {
//            if (teams[1].compareTo(teams[team]) > 0) {
//                this.tree.put(teams[team], team);
//            }
//        }

        return this.tree.size();
    }

    public static void main(String[] args) {
        Solution solution = new Solution(3);
        System.out.println(solution.update(2, 7));
        System.out.println(solution.update(3, 5));
        System.out.println(solution.update(1, 6));
        System.out.println(solution.update(1, 9));
    }
}
