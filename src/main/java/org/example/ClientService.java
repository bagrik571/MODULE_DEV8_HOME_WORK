package org.example;

import org.example.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ClientService {
    private static final Logger LOGGER = Logger.getLogger(ClientService.class.getName());
    private static final String CREATE_CLIENT = "INSERT INTO CLIENT (NAME) VALUES (?)";
    private static final String GET_CLIENT_BY_ID = "SELECT ID, NAME FROM CLIENT WHERE ID=?";
    private static final String UPDATE_NAME_BY_ID = "UPDATE CLIENT SET NAME = ? WHERE ID=?";
    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM CLIENT WHERE ID=?";
    private static final String GET_ALL_CLIENT = "SELECT ID, NAME FROM CLIENT";

    private Connection connection;

    private PreparedStatement insertStatement;
    private PreparedStatement selectByStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteByStatement;
    private PreparedStatement selectAllStatement;

    public ClientService(Connection connection) {
        this.connection = connection;
        try {
            this.insertStatement = connection.prepareStatement(CREATE_CLIENT, Statement.RETURN_GENERATED_KEYS);
            this.selectByStatement = connection.prepareStatement(GET_CLIENT_BY_ID);
            this.updateStatement = connection.prepareStatement(UPDATE_NAME_BY_ID);
            this.deleteByStatement = connection.prepareStatement(DELETE_CLIENT_BY_ID);
            this.selectAllStatement = connection.prepareStatement(GET_ALL_CLIENT);
        } catch (SQLException e){
           LOGGER.warning("Client Service construction exception. Reason: " + e.getMessage());
        }
    }

    public long create (String name){
        if (name == null || name.length() > 100) {
            LOGGER.warning("create: Parameter exception: name must be String and max 100 symbols!");
            throw new IllegalArgumentException("create: Illegal argument");
        }
        long id = -1;
        try{
          this.insertStatement.setString(1, name);
            int createdRows = this.insertStatement.executeUpdate();

            if (createdRows == 0) {
                throw new SQLException("Creating failed, no rows to create.");
            }
            try (ResultSet generatedKeys = this.insertStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating failed, no ID obtained.");
                }
            } catch (SQLException e){
                LOGGER.warning("Create Client name exception. Reason: " + e.getMessage());
            }
        } catch (SQLException e){
            LOGGER.warning("Create Client name exception. Reason: " + e.getMessage());
        }
        return id;
    }

    public String getById(long id){
        if (id < 1) {
            LOGGER.warning("getById: Parameter exception: id < 0");
            throw new IllegalArgumentException("getById: Illegal argument");
        }
        String clientName = "No Name";
        try{
            this.selectByStatement.setLong(1, id);
            try (ResultSet resultSet = this.selectByStatement.executeQuery()) {
                while(resultSet.next()){
                    clientName = resultSet.getString("name");
                }
            } catch (SQLException e){
                LOGGER.warning("Create Client exception. Reason: " + e.getMessage());
            }
        } catch (SQLException e){
            LOGGER.warning("Create Client exception. Reason: " + e.getMessage());
        }
        return clientName;
    }

    public void setName(long id, String name){
        if (id < 1 || name == null || name.length() > 1000) {
            LOGGER.warning("setName: Parameter exception");
            throw new IllegalArgumentException("setName: Illegal argument for 'id' or 'name'.");
        }
        try{
            this.updateStatement.setString(1, name);
            this.updateStatement.setLong(2, id);
            int updatedRows = this.updateStatement.executeUpdate();
            if (updatedRows == 0) {
                throw new SQLException("Update failed, no rows for update");
            }
        }catch(SQLException e){
            LOGGER.warning("Update Client exception. Reason: " + e.getMessage());
        }
    }

    public void deleteById(long id){
        if (id < 1) {
            LOGGER.warning("getById: Parameter exception: id < 0");
            throw new IllegalArgumentException("getById: Illegal argument");
        }
        try{
            this.deleteByStatement.setLong(1, id);
            int deletedRows = this.deleteByStatement.executeUpdate();
            if (deletedRows == 0) {
                throw new SQLException("Deleting failed, no rows for delete.");
            }
        }catch(SQLException e){
            LOGGER.warning("Delete Client exception. Reason: " + e.getMessage());
        }
    }

    public List<Client> listAll(){
        List<Client> clients = new ArrayList<>();
        try(ResultSet resultSet = this.selectAllStatement.executeQuery()){
            while(resultSet.next()){
                Client client = new Client(resultSet.getLong("ID"), resultSet.getString("NAME"));
                clients.add(client);
            }
        }catch(SQLException e){
            LOGGER.warning("Select All User Exception. Reason: " + e.getMessage());
        }
        return clients;
    }
}
