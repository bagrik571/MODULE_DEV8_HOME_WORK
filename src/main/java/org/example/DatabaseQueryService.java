package org.example;

import org.example.elements.LongestProjectCount;
import org.example.elements.MaxProjectsCountClient;
import org.example.elements.MaxSalaryCountWorker;
import org.example.elements.YoungestEldestCountWorkers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private static final String LONGEST_PROJECTS_QUERY;
    private static final String MAX_PROJECTS_QUERY;
    private static final String MAX_SALARY_QUERY;
    private static final String YOUNGEST_ELDEST_QUERY;

    static {
        try {
            LONGEST_PROJECTS_QUERY = new String(Files.readAllBytes(Paths.get("src/sql/find_longest_project.sql")));
            MAX_PROJECTS_QUERY = new String(Files.readAllBytes(Paths.get("src/sql/find_max_projects_client.sql")));
            MAX_SALARY_QUERY = new String(Files.readAllBytes(Paths.get("src/sql/find_max_salary_worker.sql")));
            YOUNGEST_ELDEST_QUERY = new String(Files.readAllBytes(Paths.get("src/sql/find_youngest_eldest_workers.sql")));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read SQL files");
        }
    }

    public int printProjectPrices(){return 0;}

    public List<LongestProjectCount> findLongestProject(){
        List<LongestProjectCount> longestProjects = new ArrayList<>();
        try {
            Connection connection = Database.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(LONGEST_PROJECTS_QUERY);

            while (resultSet.next()) {
                LongestProjectCount project = new LongestProjectCount(resultSet.getInt("PROJECT_ID"),resultSet.getInt("month_count"));
                longestProjects.add(project);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return longestProjects;
    }

    public List<MaxProjectsCountClient> findMaxProjectClient(){
        List<MaxProjectsCountClient> maxProjectsCountClients = new ArrayList<>();
        try {
            Connection connection = Database.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(MAX_PROJECTS_QUERY);

            while (resultSet.next()) {
                MaxProjectsCountClient project = new MaxProjectsCountClient(resultSet.getString("NAME"),resultSet.getInt("PROJECT_COUNT"));
                maxProjectsCountClients.add(project);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxProjectsCountClients;
    }

    public List<MaxSalaryCountWorker> findMaxSalaryWorker(){
        List<MaxSalaryCountWorker> maxSalaryCountWorkers = new ArrayList<>();
        try {
            Connection connection = Database.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(MAX_SALARY_QUERY);

            while (resultSet.next()) {
                MaxSalaryCountWorker project = new MaxSalaryCountWorker(resultSet.getString("NAME"),resultSet.getString("SALARY"));
                maxSalaryCountWorkers.add(project);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxSalaryCountWorkers;
    }

    public List<YoungestEldestCountWorkers> findYoungestEldestWorkers(){
        List<YoungestEldestCountWorkers> youngestEldestCountWorkers = new ArrayList<>();
        try {
            Connection connection = Database.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(YOUNGEST_ELDEST_QUERY);

            while (resultSet.next()) {
                YoungestEldestCountWorkers project = new YoungestEldestCountWorkers(resultSet.getString("TYPE"),resultSet.getString("NAME"),resultSet.getDate("BIRTHDAY"));
                youngestEldestCountWorkers.add(project);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return youngestEldestCountWorkers;
    }
}
