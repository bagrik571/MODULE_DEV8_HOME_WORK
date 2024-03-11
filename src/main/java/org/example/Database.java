package org.example;


import org.example.entity.Client;
import org.flywaydb.core.Flyway;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    private static final Database INSTANCE = new Database();
    private static Connection connection;
    private Database (){
        String url = getConnectionUrlForPostgres();
        String user = getUserForPostgres();
        String pass = getPasswordForPostgres();

        try {
            connection = DriverManager.getConnection(url, user, pass);
            flywayMigration(url, user, pass);
        } catch (SQLException e) {
            System.out.println(String.format("SQL exception. Can not create connection. Reason: %s", e.getMessage()));
            throw new RuntimeException("Can not create connection!");
        }
    }
    public static Database getInstance(){
        return INSTANCE;
    }

    public static Connection getConnection() {
        return connection;
    }
    public static String getConnectionUrlForPostgres(){
        try (InputStream input = DatabaseInitService.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            Properties prop = new Properties();

            if (input == null){
                System.out.println("Sorry, not find file");
                return null;
            }
            prop.load(input);

            return new StringBuilder("jdbc:postgresql://")
                    .append(prop.getProperty("postgres.db.host"))
                    .append(":")
                    .append(prop.getProperty("postgres.db.port"))
                    .append("/")
                    .append(prop.getProperty("postgres.db.database"))
                    .append("?currentSchema=public")
                    .toString();
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static String getUserForPostgres(){
        try (InputStream input = DatabaseInitService.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            Properties prop = new Properties();

            if (input == null){
                System.out.println("Sorry, not find file");
                return null;
            }
            prop.load(input);

            return prop.getProperty("postgres.db.username");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static String getPasswordForPostgres(){
        try (InputStream input = DatabaseInitService.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            Properties prop = new Properties();

            if (input == null){
                System.out.println("Sorry, not find file");
                return null;
            }
            prop.load(input);

            return prop.getProperty("postgres.db.password");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public int executeUpdate (String query) {
        try(Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(String.format("Exception. Reason: %s", e.getMessage()));
            throw new RuntimeException("Can not run query!");
        }
    }

    public void executeResult(String query){
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Data data = new Data(resultSet.getInt("ID"), resultSet.getString("NAME"));
                System.out.println("postgres -> " + data.toString());
            }
            } catch(SQLException e){
                System.out.println(String.format("Exception. Reason: %s", e.getMessage()));
                throw new RuntimeException("Can not run query!");
            }
    }

public void execute (String fileName){
        try(Statement statement = connection.createStatement()){
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            statement.execute(content);
        } catch (SQLException e){
            System.out.println(String.format("Exception. Reason: %s", e.getMessage()));
            throw new RuntimeException("Can not run query!");
        } catch (IOException e){
            throw new RuntimeException(e);
        }
}
public void closeConnection(){
        try{ connection.close();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
}
public void flywayMigration(String url, String user, String pass){
    Flyway flyway = Flyway.configure().dataSource(url, user, pass).locations("classpath:/migration").load();
    flyway.migrate();

}
}
