package memorygame.dao;

import java.util.Objects;

public class Score implements Comparable<Score> {
    private String name;
    private int tries;
    private int time;
    private int totalPairs;

    public Score(String name, int tries, int time, int totalPairs) {
        this.name = name;
        this.tries = tries;
        this.time = time;
        this.totalPairs = totalPairs;
    }

    public String getName() {
        return name;
    }

    public int getTries() {
        return tries;
    }

    public int getTime() {
        return time;
    }

    public int getTotalPairs() {
        return totalPairs;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Score other = (Score) obj;
        if (this.tries != other.tries) {
            return false;
        }
        if (this.time != other.time) {
            return false;
        }
        if (this.totalPairs != other.totalPairs) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Score{" + "name=" + name + ", tries=" + tries + ", time=" + time + ", totalPairs=" + totalPairs + '}';
    }

    @Override
    public int compareTo(Score t) {
        return this.time - t.time;
    }
     
}
