package me.DNFneca.leaderboard.utils.db;

import java.util.ArrayList;
import java.util.List;

public class DBCollection<collectionType> {
    private List<collectionType> collection = new ArrayList<collectionType>();

    public List<collectionType> getCollection() {
        return this.collection;
    }

    public void setCollection(List<collectionType> collection) {
        this.collection = collection;
    }

    public DBCollection(List<collectionType> collection) {
        this.collection = collection;
    }

    public void add(collectionType playerData) {
        this.collection.add(playerData);
    }
}
