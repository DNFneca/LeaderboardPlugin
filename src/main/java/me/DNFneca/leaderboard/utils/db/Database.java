package me.DNFneca.leaderboard.utils.db;

import com.google.gson.Gson;

import java.io.*;

public class Database<DBCollectionType> {
    private String databaseFilePath;
    private DBCollection<DBCollectionType> data;

    public Database(String databaseFilePath) {
        File file = new File(databaseFilePath);

        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    System.out.println("Failed to create file");
                } else {
                    System.out.println("File created successfully");
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
        this.databaseFilePath = databaseFilePath;
        update();
    }

    public String getDatabaseFilePath() {
        return databaseFilePath;
    }

    public DBCollection<DBCollectionType> getData() {
        return data;
    }

    public void setData(DBCollection<DBCollectionType> data) {
        this.data = data;
    }

    public void setDatabaseFilePath(String databaseFilePath) {
        this.databaseFilePath = databaseFilePath;
    }

    public void saveData() {
        try (Writer writer = new FileWriter(databaseFilePath)) {
            Gson gson = new Gson();
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadData() {
        try (Reader reader = new FileReader(databaseFilePath)) {
            Gson gson = new Gson();
            data = gson.fromJson(reader, DBCollection.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    
    public void update() {
        loadData();
        saveData();
    }
}
