package net.ramuremo.savannahole.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.function.Supplier;

public class SideBar {
    public final SideBarHandler handler;

    public final Scoreboard scoreboard;
    public final Objective objective;
    public final Player player;
    private Map<Integer, Supplier<String>> lines;
    private String title;

    public SideBar(SideBarHandler handler, Player player, String title) {
        this.handler = handler;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        final String id = "SavannaCorePaper.sideBar." + player.getUniqueId();
        this.objective = scoreboard.registerNewObjective(id, "dummy");
        this.player = player;
        this.lines = new HashMap<>();
        this.title = title;

        initialize();
    }

    public String getTitle() {
        return title;
    }

    public SideBar setTitle(String title) {
        this.title = title;
        return this;
    }

    public Map<Integer, Supplier<String>> getLines() {
        return lines;
    }

    public SideBar setLines(Map<Integer, Supplier<String>> lines) {
        this.lines = lines;
        return this;
    }

    public SideBar setLine(int index, Supplier<String> line) {
        lines.put(index, line);
        return this;
    }

    public SideBar addLine(Supplier<String> line) {
        if (lines.isEmpty()) {
            lines.put(0, line);
            return this;
        }

        lines.put(Collections.max(lines.keySet()) + 1, line);
        return this;
    }

    public SideBar addBlankLine() {
        return addLine(ChatColor.RESET::toString);
    }

    public Map<Integer, Supplier<String>> getFixedLines() {
        List<Supplier<String>> lineList = new ArrayList<>();
        lines.forEach(lineList::add);

        Collections.reverse(lineList);

        Map<Integer, Supplier<String>> lineMap = new HashMap<>();
        for (int i = 0; i < lineList.size(); i++)
            lineMap.put(i, lineList.get(i));

        return lineMap;
    }

    public SideBar show() {
        player.setScoreboard(scoreboard);
        handler.setSideBar(this);
        return this;
    }

    public SideBar hide() {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        return this;
    }

    public boolean isShowing() {
        return player.getScoreboard().equals(scoreboard);
    }

    private boolean isMatchLines() {
        Set<Integer>
                currentIndexList = new HashSet<>(getFixedLines().keySet()),
                teamIndexList = new HashSet<>();
        scoreboard.getTeams().forEach(team -> teamIndexList.add(Integer.parseInt(team.getName())));

        return currentIndexList.equals(teamIndexList);
    }

    private String getFakeEntryName(int index) {
        String name = ChatColor.RESET.toString();

        for (int i = 0; i < index; i++) {
            name += ChatColor.RESET.toString();
        }

        return name;
    }

    public void initialize() {
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        if (!scoreboard.getTeams().isEmpty())
            scoreboard.getTeams().forEach(Team::unregister);

        getFixedLines().forEach((index, line) -> {
            Team team = scoreboard.registerNewTeam(String.valueOf(index));
            team.addEntry(getFakeEntryName(index));
            team.setPrefix(line.get());
            objective.getScore(getFakeEntryName(index)).setScore(index);
        });
    }

    public void update() {
        if (!isMatchLines())
            initialize();

        if (!objective.getDisplayName().equals(title))
            objective.setDisplayName(title);

        getFixedLines().forEach((index, line) -> {
            Team team = scoreboard.getTeam(String.valueOf(index));
            if (team != null && !team.getPrefix().equals(line.get()))
                Objects.requireNonNull(scoreboard.getTeam(Integer.toString(index))).setPrefix(line.get());
        });
    }

    public void remove() {
        scoreboard.clearSlot(DisplaySlot.SIDEBAR);
        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }
        objective.unregister();
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}
