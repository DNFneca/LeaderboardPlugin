package me.DNFneca.leaderboard.utils.version;

import com.google.gson.Gson;
import me.DNFneca.leaderboard.Leaderboard;

import java.io.*;

public class Manager {

    public static void Initialize() {
        String writtenVersion = getWrittenVersion();
        String currentVersion = Leaderboard.getInstance().getDescription().getVersion();
        if (writtenVersion == null) {
            Leaderboard.getInstance().log.info("Unknown version");
            setWrittenVersion(currentVersion);
            return;
        }
        if(!currentVersion.equals(writtenVersion)) {
            setWrittenVersion(currentVersion);
        }
        Leaderboard.getInstance().log.info(currentVersion);
        int currentMajorRelease = Integer.parseInt(currentVersion.split("\\.")[0]);
        int currentMinorRelease = Integer.parseInt(currentVersion.split("\\.")[1]);
        int currentBuildNumber = Integer.parseInt(currentVersion.split("\\.")[2]);
        int lastMajorRelease = Integer.parseInt(writtenVersion.split("\\.")[0]);
        int lastMinorRelease = Integer.parseInt(writtenVersion.split("\\.")[1]);
        int lastBuildNumber = Integer.parseInt(writtenVersion.split("\\.")[2]);
    }

    public static String getWrittenVersion() {
        try (Reader reader = new FileReader("./plugins/Leaderboard/version.json")) {
            Gson gson = new Gson();
            return gson.fromJson(reader, String.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setWrittenVersion(String version) {

        try (Writer writer = new FileWriter("./plugins/Leaderboard/version.json")) {
            Gson gson = new Gson();
            gson.toJson(version, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
