package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


    public class UserDaoJDBCImpl implements UserDao {
        public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users" +
                "(id mediumint not null auto_increment," +
                " name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age tinyint, " +
                "PRIMARY KEY (id))";
        public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users;";
        public static final String SAVE_USER = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
        public static final String REMOVE_USER = "DELETE FROM users WHERE id = ?";
        public static final String GET_ALL_USERS = "SELECT * FROM users";
        public static final String CLEAN_USERS_TABLE = "TRUNCATE TABLE users";
        private static final Connection connection = Util.getConnection();

        public UserDaoJDBCImpl() {

        }

        public void createUsersTable() {
            try (Statement s = connection.createStatement()) {
                s.executeUpdate(CREATE_USERS_TABLE);
                System.out.println("Table created");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void dropUsersTable() {
            try (Statement s = connection.createStatement()) {
                s.executeUpdate(DROP_USERS_TABLE);
                System.out.println("Table deleted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void saveUser(String name, String lastName, byte age) {
            try (PreparedStatement ps = connection.prepareStatement(SAVE_USER)) {
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setInt(3, age);
                ps.executeUpdate();
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void removeUserById(long id) {
            try (PreparedStatement ps = connection.prepareStatement(REMOVE_USER)) {
                ps.setLong(1, id);
                ps.executeUpdate();
                System.out.println("User removed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public List<User> getAllUsers() {
            List<User> users = new LinkedList<>();
            try (Statement s = connection.createStatement()) {
                ResultSet rs = s.executeQuery(GET_ALL_USERS);
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setLastName(rs.getString("lastName"));
                    user.setAge(rs.getByte("age"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return users;
        }

        public void cleanUsersTable() {
            try (Statement s = connection.createStatement()) {
                s.executeUpdate(CLEAN_USERS_TABLE);
                System.out.println("Table cleared");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }