package me.DNFneca.leaderboard.utils.board;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.player.PlayerData;
import me.DNFneca.leaderboard.utils.player.PlayerScore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board {
    private String id;
    private BoardRow mainRow;
    private String eventName;
    private int maxLength;
    private ArrayList<BoardRow> rows = new ArrayList<BoardRow>(20);
    private String worldUUID;
    private double x;
    private double y;
    private double z;
    public Board(double x, double y, double z, String name, World world, int maxLength) {
        this.x = Math.floor(x + .5);
        this.y = Math.floor(y - 1);
        this.z = Math.floor(z + .5);
        this.maxLength = maxLength;
        worldUUID = world.getUID().toString();
        mainRow = new BoardRow(name, new Location(world, Math.floor(x + .5), Math.floor(y - 1), Math.floor(z + .5)));
        id = mainRow.getId();
        Leaderboard.boardDB.boardsCollection.addBoard(this);
    }

    public Board(Location location, String name, int maxLength) {
        worldUUID = location.getWorld().getUID().toString();
        this.x = Math.floor(location.getX()) + .5;
        this.y = Math.floor(location.getY()) - 1;
        this.z = Math.floor(location.getZ()) + .5;
        this.maxLength = maxLength;
        mainRow = new BoardRow(name, new Location(location.getWorld(), this.x, y, z));
        id = mainRow.getId();
        Leaderboard.boardDB.boardsCollection.addBoard(this);
    }

    public String getId() {
        return id;
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

    public void addRow(String rowContent) {
        bringUpRows();
        rows.add(new BoardRow(rowContent, getLoc()));
    }

    public void removeRow(int index) {
        Bukkit.getEntity(UUID.fromString(rows.get(index).getId())).remove();
        rows.remove(index);
        bringDownRows();
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
        if(Leaderboard.playerStats.entityScores == null) return;
        List<PlayerData> entityDataArrayList;
        entityDataArrayList = Leaderboard.playerStats.entityScores.getCollection();
        String tempI, tempJ;
        for (int i = 0; i < entityDataArrayList.size(); i++) {
            int minIndex = i;
            for (int j = i + 1; j < entityDataArrayList.size(); j++) {
                tempJ = entityDataArrayList.get(j).getScore("PlayerDeathEvent").getScore();
                tempI = entityDataArrayList.get(i).getScore("PlayerDeathEvent").getScore();
                if((tempJ != null && tempI != null)) {
                    if(Integer.parseInt(tempI) > Integer.parseInt(entityDataArrayList.get(minIndex).getScore("PlayerDeathEvent").getScore())) {
                        minIndex = j;
                    }
                    PlayerScore temp = entityDataArrayList.get(minIndex).getScore("PlayerDeathEvent");
                    entityDataArrayList.get(minIndex).editScore("PlayerDeathEvent", entityDataArrayList.get(i).getScore("PlayerDeathEvent"));
                    entityDataArrayList.get(i).editScore("PlayerDeathEvent", temp);
                }
            }
        }
        for (Board board : Leaderboard.boardDB.boardsCollection.getBoardList()) {
            System.out.println(board.rows.get(0).getText());
            System.out.println(board.rows.get(1).getText());
            for (int i = 0; i < board.getRows().size(); i++) {
                if(i >= entityDataArrayList.size()) {
                    if(board.getRows().size() > 4) {
                        board.rows.get(i).setText("None");
                    } else {
                        board.rows.get(i).setText("");
                    }
                } else {
                    System.out.println(board.rows.get(i));
                    System.out.println(board.rows.size());
                    board.rows.get(i).setText(i+1 + ". " + entityDataArrayList.get(i).getName() + ": " + entityDataArrayList.get(i).getScore("PlayerDeathEvent").getScore());
                }
            }
        }
        Leaderboard.boardDB.saveData();
    }
}
