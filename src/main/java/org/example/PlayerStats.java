package org.example;

import java.util.Objects;

public class PlayerStats {
    private int usedSkips;
    private int totalTruths;
    private int totalDares;
    private String favoriteTruthOrDare;

    public int getUsedSkips() {
        return usedSkips;
    }

    public void setUsedSkips(int usedSkips) {
        this.usedSkips = usedSkips;
    }

    public int getTotalTruths() {
        return totalTruths;
    }

    public void setTotalTruths(int totalTruths) {
        this.totalTruths = totalTruths;
    }

    public int getTotalDares() {
        return totalDares;
    }

    public void setTotalDares(int totalDares) {
        this.totalDares = totalDares;
    }

    public String getFavoriteTruthOrDare() {
        return favoriteTruthOrDare;
    }

    public void setFavoriteTruthOrDare(String favoriteTruthOrDare) {
        this.favoriteTruthOrDare = favoriteTruthOrDare;
    }

    @Override
    public String toString() {
        return "PlayerStats{" +
                "usedSkips=" + usedSkips +
                ", totalTruths=" + totalTruths +
                ", totalDares=" + totalDares +
                ", favoriteTruthOrDare='" + favoriteTruthOrDare + '\'' +
                '}';
    }

    public PlayerStats(int usedSkips, int totalTruths, int totalDares, String favoriteTruthOrDare) {
        this.usedSkips = usedSkips;
        this.totalTruths = totalTruths;
        this.totalDares = totalDares;
        this.favoriteTruthOrDare = favoriteTruthOrDare;
    }

    public PlayerStats() {
        this.usedSkips = 0;
        this.totalTruths = 0;
        this.totalDares = 0;
        this.favoriteTruthOrDare = "None";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStats that = (PlayerStats) o;
        return usedSkips == that.usedSkips && totalTruths == that.totalTruths && totalDares == that.totalDares && Objects.equals(favoriteTruthOrDare, that.favoriteTruthOrDare);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedSkips, totalTruths, totalDares, favoriteTruthOrDare);
    }
}
