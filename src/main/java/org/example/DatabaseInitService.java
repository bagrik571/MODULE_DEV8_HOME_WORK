package org.example;


import java.sql.Connection;

public class DatabaseInitService {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        database.execute("src/sql/init_db.sql");
        //DatabaseQueryService databaseQueryService = new DatabaseQueryService();
       // System.out.println(databaseQueryService.findLongestProject().toString());
       // System.out.println(databaseQueryService.findMaxProjectClient().toString());
       // System.out.println(databaseQueryService.findMaxSalaryWorker().toString());
       // System.out.println(databaseQueryService.findYoungestEldestWorkers().toString());
    }
}
