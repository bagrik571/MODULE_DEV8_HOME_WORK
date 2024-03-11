package org.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabasePopulateService {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        //database.execute("src/sql/populate_db.sql");
    }

    public static final String PS_INSERT_WORKERS = "insert into worker (\"ID\", \"NAME\", \"BIRTHDAY\", \"LEVEL\", \"SALARY\") values(?, ?, ?, ?, ?)";
    public static final String PS_INSERT_CLIENTS = "insert into client (\"ID\", \"NAME\") values(?, ?)";
    public static final String PS_INSERT_PROJECTS = "insert into project (\"ID\",\"CLIENT_ID\",\"START_DATE\",\"FINISH_DATE\") values(?, ?, ?, ?)";
    public static final String PS_INSERT_PROJECT_WORKERS = "insert into project_worker (\"PROJECT_ID\", \"WORKER_ID\") values(?, ?)";
    private static Connection connection = Database.getInstance().getConnection();
    private static PreparedStatement pStatementWorker;
    private static PreparedStatement pStatementWorker2;
    private static PreparedStatement pStatementClient;
    private static PreparedStatement pStatementProject;
    private static PreparedStatement pStatementProjectWorker;

    public static void insertWorkers() throws SQLException {
        System.out.println("Prepare to insert...");
        List<Integer> id = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> name = List.of("Anton", "Viktor", "Vlad", "Valeria", "Volodymyr", "Oksana", "Petro", "Yurii", "Viktoria", "Vitalii");
        List<Date> birthday = List.of(
                Date.valueOf("1991-07-02"),
                Date.valueOf("2003-09-18"),
                Date.valueOf("1998-03-21"),
                Date.valueOf("2001-08-18"),
                Date.valueOf("1993-10-10"),
                Date.valueOf("1998-09-19"),
                Date.valueOf("1995-12-12"),
                Date.valueOf("2000-02-20"),
                Date.valueOf("2001-01-01"),
                Date.valueOf("1992-09-18"));
        List<String> level = List.of("Senior", "Junior", "Middle", "Junior", "Trainee", "Senior", "Middle", "Middle", "Middle", "Senior");
        List<Integer> salary = List.of(6000, 800, 4500, 850, 500, 5500, 3000, 3000, 3500, 7500);
        try {
            pStatementWorker = connection.prepareStatement(PS_INSERT_WORKERS);
            pStatementWorker2 = connection.prepareStatement(PS_INSERT_WORKERS);

            connection.setAutoCommit(false);
            for (int i = 0; i < name.size(); i++) {
                pStatementWorker.setInt(1, id.get(i));
                pStatementWorker.setString(2, name.get(i));
                pStatementWorker.setDate(3, birthday.get(i));
                pStatementWorker.setString(4, level.get(i));
                pStatementWorker.setInt(5, salary.get(i));
                pStatementWorker.addBatch();
            }
            pStatementWorker2.setInt(1, 11);
            pStatementWorker2.setString(2, "Alosha");
            pStatementWorker2.setDate(3, Date.valueOf("2000-01-20"));
            pStatementWorker2.setString(4, "Trainee");
            pStatementWorker2.setInt(5, 200);
            pStatementWorker2.addBatch();
            try {
                pStatementWorker.executeBatch();
                pStatementWorker2.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                System.out.println("TRANSACTIONAL FAIL. Rollback changes. Reason: " + e.getMessage());
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println("TRANSACTIONAL method SQL exception. Reason: " + e.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static void insertClients() {
        System.out.println("Prepare to insert...");
        List<Integer> clientId = List.of(1, 2, 3, 4, 5);
        List<String> clientName = List.of("John", "Bill", "Jenifer", "Roman", "Elise");
        try {
            pStatementClient = connection.prepareStatement(PS_INSERT_CLIENTS);

            for (int i = 0; i < clientName.size(); i++) {
                pStatementClient.setInt(1, clientId.get(i));
                pStatementClient.setString(2, clientName.get(i));
                pStatementClient.executeUpdate();
            }
            System.out.println("Inserted successfully.");
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
        } finally {
            try {
                if (pStatementClient != null) pStatementClient.close();
                //if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close resources: " + e.getMessage());
            }
        }
    }

    public static void insertProjects() {
        System.out.println("Prepare to insert...");
        List<Integer> projectId = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> clientsId = List.of(2, 1, 3, 5, 5, 3, 2, 1, 2, 1);
        List<Date> startDate = List.of(
                Date.valueOf("2024-03-01"),
                Date.valueOf("2024-02-15"),
                Date.valueOf("2024-03-03"),
                Date.valueOf("2024-05-11"),
                Date.valueOf("2024-02-28"),
                Date.valueOf("2024-06-09"),
                Date.valueOf("2024-05-10"),
                Date.valueOf("2024-01-01"),
                Date.valueOf("2024-03-13"),
                Date.valueOf("2024-08-08"));
        List<Date> finishDate = List.of(
                Date.valueOf("2024-05-01"),
                Date.valueOf("2024-03-15"),
                Date.valueOf("2024-09-03"),
                Date.valueOf("2024-09-11"),
                Date.valueOf("2024-04-28"),
                Date.valueOf("2024-10-09"),
                Date.valueOf("2024-08-10"),
                Date.valueOf("2025-01-01"),
                Date.valueOf("2024-05-13"),
                Date.valueOf("2024-12-08"));
        try {
            pStatementProject = connection.prepareStatement(PS_INSERT_PROJECTS);

            for (int i = 0; i < projectId.size(); i++) {
                pStatementProject.setInt(1, projectId.get(i));
                pStatementProject.setInt(2, clientsId.get(i));
                pStatementProject.setDate(3, startDate.get(i));
                pStatementProject.setDate(4, finishDate.get(i));
                pStatementProject.executeUpdate();
            }
            System.out.println("Inserted successfully.");
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
        } finally {
            try {
                if (pStatementProject != null) pStatementProject.close();
                //if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close resources: " + e.getMessage());
            }
        }
    }

    public static void insertProjectWorkers() {
        System.out.println("Prepare to insert...");
        List<Integer> idProject = List.of(8, 8, 8, 8, 8, 1, 2, 2, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 7, 7, 9, 9, 9, 10, 10);
        List<Integer> idWorker = List.of(1, 3, 7, 4, 5, 9, 10, 7, 8, 2, 7, 1, 3, 6, 5, 3, 6, 9, 4, 8, 10, 7, 4, 3, 6);
        try {
            pStatementProjectWorker = connection.prepareStatement(PS_INSERT_PROJECT_WORKERS);

            for (int i = 0; i < idProject.size(); i++) {
                pStatementProjectWorker.setInt(1, idProject.get(i));
                pStatementProjectWorker.setInt(2, idWorker.get(i));
                pStatementProjectWorker.executeUpdate();
            }
            System.out.println("Inserted successfully.");
        } catch (SQLException e) {
            System.out.println("SQL exception: " + e.getMessage());
        } finally {
            try {
                if (pStatementProjectWorker != null) pStatementProjectWorker.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Failed to close resources: " + e.getMessage());
            }
        }
    }
}
