package me.DNFneca.leaderboard.utils.board;

import me.DNFneca.leaderboard.Leaderboard;
import me.DNFneca.leaderboard.utils.player.PlayerData;
import me.DNFneca.leaderboard.utils.player.PlayerScore;

import java.util.ArrayList;
import java.util.List;

public class BoardsCollection {
    private List<Board> boardList = new ArrayList<Board>();

    public List<Board> getBoardList() {
        return this.boardList;
    }
    public BoardsCollection(List<Board> boardList) {
        this.boardList = boardList;
    }

    public void addBoard(Board playerData) {
        this.boardList.add(playerData);
        Leaderboard.boardDB.saveData();
    }

    public static void update() {
        if(Leaderboard.playerStats.entityScores == null) return;
        List<PlayerData> playerDataList;
        playerDataList = Leaderboard.playerStats.entityScores.getCollection();
        for (int i = 0; i < playerDataList.size() - 1; i++) {
            for (int j = 0; j < playerDataList.size() - 1 - i; j++) {
                if (Integer.parseInt(playerDataList.get(j).getScore("PlayerDeathEvent").getScore()) < Integer.parseInt(playerDataList.get(j + 1).getScore("PlayerDeathEvent").getScore())) {
                    PlayerData tempPlayerData = playerDataList.get(j);
                    playerDataList.set(j, playerDataList.get(j + 1));
                    playerDataList.set(j + 1, tempPlayerData);
                }
            }
        }
        Leaderboard.playerStats.entityScores.setCollection(playerDataList);
        int numberOfPlayers = playerDataList.size();


        for (Board board : Leaderboard.boardDB.boardsCollection.getBoardList()) {
            System.out.println(neededRows(board, playerDataList) - board.getRows().size());
            if(neededRows(board, playerDataList) < board.getRows().size()) {
                for (int i = 0; i < board.getRows().size() - neededRows(board, playerDataList); i++) {
                    board.removeRow(board.getRows().size() - 1);
                }
            }
            for (int i = 0; i < numberOfPlayers; i++) {
                if(isPlayerSupposedToBeOnBoard(board.getId(), playerDataList.get(i).getId(), playerDataList)) {
                    if(isPlayerOnBoard(board.getId(), playerDataList.get(i).getId(), playerDataList)) {
                        Integer j = getPlayerPositionIndex(playerDataList.get(i).getId(), playerDataList);
                        if(j == null) return;
                        board.getRow(j).setText(j+1 + ". " + playerDataList.get(i).getName() + ": " + playerDataList.get(i).getScore("PlayerDeathEvent").getScore());
                    } else {
                        if(playerDataList.get(i) == null) return;
                        if(playerDataList.get(i).getScore("PlayerDeathEvent") == null) return;
                        board.addRow(i+1 + ". " + playerDataList.get(i).getName() + ": " + playerDataList.get(i).getScore("PlayerDeathEvent").getScore());
                    }
                }
            }
        }
        Leaderboard.boardDB.saveData();
    }

    private static boolean isPlayerOnBoard(String boardId, String playerId, List<PlayerData> playerDataList) {
        for (Board board : Leaderboard.boardDB.boardsCollection.getBoardList()) {
            if(board.getId().equals(boardId)) {
                for (int i = 0; i < board.getRows().size(); i++) {
                    if(playerDataList.get(i).getId().equals(playerId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static int neededRows(Board board, List<PlayerData> playerDataList) {
        return Math.min(playerDataList.size(), board.getMaxLength());
    }

    private static boolean isPlayerSupposedToBeOnBoard(String boardId, String playerId, List<PlayerData> playerDataList) {
        for (Board board : Leaderboard.boardDB.boardsCollection.getBoardList()) {
            if(board.getId().equals(boardId)) {
                for (int i = 0; i < board.getMaxLength(); i++) {
                    if(playerDataList.get(i).getId().equals(playerId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static Integer getPlayerPositionIndex(String playerId, List<PlayerData> playerDataList) {
        for (Board board : Leaderboard.boardDB.boardsCollection.getBoardList()) {
            for (int i = 0; i < board.getMaxLength(); i++) {
                if (playerDataList.get(i).getId().equals(playerId)) {
                    return i;
                }
            }
        }
        return null;
    }

}
