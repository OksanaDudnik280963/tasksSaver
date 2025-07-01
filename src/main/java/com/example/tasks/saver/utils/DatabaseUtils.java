package com.example.tasks.saver.utils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static com.example.tasks.saver.global.InstallConstants.*;

@Slf4j
public class DatabaseUtils {
    static final String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;

    private DatabaseUtils() {
    }

    public static boolean createDatabase() {
        //STEP 1: Register JDBC driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //STEP 2: Open a connection
        log.info("Connecting to database...");

        try(
                Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
        ) {
            //STEP 3: Execute a query
            log.info("Creating database...");
            stmt.executeUpdate(createDatabaseSql);
            log.info("Database created successfully...");

/*
            stmt.execute(FileReader.readFromFileFromResources("database/tasks.sql"));
            log.info("Table tasks created successfully...");
            stmt.execute(FileReader.readFromFileFromResources("database/operations.sql"));
            log.info("Table operations created successfully...");
*/

        } catch (Exception e) {
            log.error("Database exist already!");
        }
        return false;
    }
}
