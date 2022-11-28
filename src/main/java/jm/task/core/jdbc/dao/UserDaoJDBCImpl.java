package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

            public void createUsersTable() {
    try(Statement st = connection.createStatement()){
        st.executeUpdate("CREATE TABLE IF NOT EXISTS usrs_db " +
                "(id SERIAL PRIMARY KEY NOT NULL," +
                "name VARCHAR(255) NOT NULL," +
                "lastname VARCHAR(255) NOT NULL," +
                "age INT NOT NULL) ");
        System.out.println("Table created");

    } catch (SQLException e) {
       e.printStackTrace();
        System.out.println("Create fail, donnerwetter! Exeption " + e.getMessage());
    }
    }

    public void dropUsersTable() {
        try (Statement st = connection.createStatement()){
            st.executeUpdate("DROP TABLE IF EXISTS usrs_db");
            System.out.println("Table dropped");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Drop fail, donnerwetter! Exeption " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("INSERT INTO usrs_db (name, lastName, age) " +
                    "VALUES ('" + name + "', '" + lastName + "', " + age + ")");
            System.out.println("User - " + name + " was added to DB");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add fail, donnerwetter! Exeption " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
            try (Statement st = connection.createStatement()) {
                st.executeUpdate("DELETE FROM usrs_db WHERE id = " + id);
                System.out.println("User  id =" + id + "was deleted from DB");
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Delete fail, donnerwetter! Exeption " + e.getMessage());
            }
    }

    public List<User> getAllUsers() {
            List<User> allUsers = new ArrayList<>();
            try (Statement st = connection.createStatement()) {
                ResultSet rs = st.executeQuery("SELECT * FROM usrs_db");
                while (rs.next()) {
                    User us = new User();
                    us.setId(rs.getLong(1));
                    us.setName(rs.getString(2));
                    us.setLastName(rs.getString(3));
                    us.setAge(rs.getByte(4));
                    allUsers.add(us);
                }
                System.out.println("Method getAllUsers is OK" );
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Method getAllUsers is NOT OK! Exeption " + e.getMessage());
            }
            return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("TRUNCATE TABLE usrs_db");
            System.out.println("Users table cleaned");
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("usrs_db was not cleaned! Exeption " + e.getMessage());
        }
    }
}
