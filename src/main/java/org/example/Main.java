package org.example;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        //database.execute("src/sql/init_db.sql");
//        DatabasePopulateService.insertWorkers();
//        DatabasePopulateService.insertClients();
//        DatabasePopulateService.insertProjects();
//        DatabasePopulateService.insertProjectWorkers();

        //CreateReadUpdateDelete(CRUD)
        Connection connection = Database.getConnection();
        ClientService clientService = new ClientService(connection);

//        database.flywayMigration(Database.getConnectionUrlForPostgres(),
//                Database.getUserForPostgres(),
//                Database.getPasswordForPostgres());

        System.out.println("\nStart to create new client...");
        System.out.println("Created new client!" + clientService.create("ISG"));
        System.out.println("Start get client by id...");
        System.out.println("Get client: " + clientService.getById(5));
        System.out.println("Start to update client name by id...");
        clientService.setName(2, "Klara");
        System.out.println("Start to delete client by id...");
        clientService.deleteById(4);
        System.out.println("Start to get list all client...");
        System.out.println("Result: " + clientService.listAll().toString());
    }
}