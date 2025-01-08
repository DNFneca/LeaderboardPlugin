package me.DNFneca.leaderboard.utils.board;

import me.DNFneca.leaderboard.Leaderboard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.EntityType;

import java.util.*;

public class Board {
    private final String id;
    String statisticType;
    private BoardRow mainRow;
    private Statistic statistic;
    private Material statisticMaterial;
    private EntityType statisticEntityType;
    private int maxLength;
    private ArrayList<BoardRow> rows = new ArrayList<BoardRow>(20);
    private String worldUUID;
    private double x;
    private double y;
    private double z;

    public Board(Location location, String name, int maxLength) {
        worldUUID = location.getWorld().getUID().toString();
        this.x = Math.floor(location.getX()) + .5;
        this.y = Math.floor(location.getY()) - 1;
        this.z = Math.floor(location.getZ()) + .5;
        this.maxLength = maxLength;
        mainRow = new BoardRow(Component.text(name).color(TextColor.fromHexString("#00ffff")), new Location(location.getWorld(), this.x, y, z));
        id = mainRow.getId();
        Leaderboard.boardDB.boardsCollection.addBoard(this);
    }

    public String getId() {
        return id;
    }

    public BoardRow getMainRow() {
        return mainRow;
    }

    public void setMainRow(BoardRow mainRow) {
        this.mainRow = mainRow;
    }

    public Location getLoc() {
        return new Location(Bukkit.getWorld(UUID.fromString(worldUUID)), x, y, z);
    }

    public BoardRow getRow(int index) {
        return rows.get(index);
    }

    public ArrayList<BoardRow> getRows() {
        return rows;
    }

    public void addRow(Component rowContent) {
        bringUpRows();
        rows.add(new BoardRow(rowContent, getLoc()));
    }

    public void removeRow(int index) {
        if (Bukkit.getEntity(UUID.fromString(rows.get(index).getId())) == null) return;
        Bukkit.getEntity(UUID.fromString(rows.get(index).getId())).remove();
        rows.remove(index);
        bringDownRows();
    }

    public String getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(String statisticType) {
        this.statisticType = statisticType;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
        this.statisticMaterial = null;
        this.statisticEntityType = null;
        this.statisticType = "";
        update();
    }

    public void setStatistic(Statistic statistic, Material statisticMaterial) {
        this.statistic = statistic;
        this.statisticMaterial = statisticMaterial;
        this.statisticEntityType = null;
        this.statisticType = "";
        update();
    }

    public void setStatistic(Statistic statistic, EntityType statisticEntityType) {
        this.statistic = statistic;
        this.statisticEntityType = statisticEntityType;
        this.statisticMaterial = null;
        this.statisticType = "";
        update();
    }

    public EntityType getStatisticEntityType() {
        return statisticEntityType;
    }

    public Material getStatisticMaterial() {
        return statisticMaterial;
    }

    public void removeBoard() {
        for (int i = 0; i < rows.size(); i++) {
            removeRow(i);
        }
        Bukkit.getEntity(UUID.fromString(mainRow.getId())).remove();
        Leaderboard.boardDB.boardsCollection.removeBoard(this);
    }

    public void setRow(int index, BoardRow row) {
        this.rows.set(index, row);
    }

    private void bringUpRows() {
        for (BoardRow boardRow : rows) {
            boardRow.setY(boardRow.getY() + .25);
        }
        mainRow.setY(mainRow.getY() + .25);
    }

    private void bringDownRows() {
        for (BoardRow boardRow : rows) {
            boardRow.setY(boardRow.getY() - .25);
        }
        mainRow.setY(mainRow.getY() - .25);
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void update() {
        ArrayList<Map.Entry<String, Integer>> scores = new ArrayList<>();
        if (statisticMaterial != null) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                scores.add(Map.entry(player.getName(), player.getStatistic(statistic, statisticMaterial)));
            }
        } else if (statisticEntityType != null) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                scores.add(Map.entry(player.getName(), player.getStatistic(statistic, statisticEntityType)));
            }
        } else if (statistic != null) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                scores.add(Map.entry(player.getName(), player.getStatistic(statistic)));
            }
        } else {
            Leaderboard.boardDB.saveData();
            return;
        }
        for (int i = 0; i < scores.size() - 1; i++) {
            for (int j = 0; j < scores.size() - 1 - i; j++) {
                if (scores.get(j).getValue() < scores.get(j + 1).getValue()) {
                    Map.Entry<String, Integer> tempPlayerData = scores.get(j);
                    scores.set(j, scores.get(j + 1));
                    scores.set(j + 1, tempPlayerData);
                }
            }
        }

        if (neededRows(scores) < rows.size()) {
            for (int i = 0; i < rows.size() - neededRows(scores); i++) {
                removeRow(rows.size() - 1);
            }
        }

        for (int i = 0; i < neededRows(scores) && i < maxLength - 1; i++) {
            if (rows.size() < neededRows(scores))  {
                addRow(Component.text(i + 1 + ". " + scores.get(i).getKey() + ": " + scores.get(i).getValue()).color(TextColor.fromHexString("#00ffff")));
            } else {
                getRow(i).setText(Component.text(i + 1 + ". " + scores.get(i).getKey() + ": " + scores.get(i).getValue()).color(TextColor.fromHexString("#00ffff")));
            }
        }

        for (Map.Entry<String, Integer> entry : scores) {
            Leaderboard.getInstance().log.info(entry.getKey() + ": " + entry.getValue());
        }
        Leaderboard.boardDB.saveData();
    }

    //
    private int neededRows(List<Map.Entry<String, Integer>> playerDataList) {
        return Math.min(playerDataList.size(), getMaxLength());
    }
}
