package me.DNFneca.leaderboard.utils.board;

import me.DNFneca.leaderboard.Leaderboard;

import java.util.ArrayList;
import java.util.List;

public class BoardsCollection {
    private ArrayList<Board> boardList = new ArrayList<Board>(1);

    public ArrayList<Board> getBoardList() {
        return this.boardList;
    }

    public void setBoardList(ArrayList<Board> boardList) {
        this.boardList = boardList;
    }

    public Board getBoardById(String id) {
        for (Board board : boardList) {
            if(board.getId().equals(id)) {
                return board;
            }
            for (BoardRow boardRow : board.getRows()) {
                if (boardRow.getId().equals(id)) {
                    return board;
                }
            }
        }
        return null;
    }

    public BoardsCollection(ArrayList<Board> boardList) {
        this.boardList = boardList;
    }

    public void addBoard(Board playerData) {
        this.boardList.add(playerData);
        Leaderboard.boardDB.saveData();
    }

    public void removeBoard(Board playerData) {
        this.boardList.remove(playerData);
        Leaderboard.boardDB.saveData();
    }

    public void update() {
        for (Board board : boardList) {
            board.update();
        }
    }

}
