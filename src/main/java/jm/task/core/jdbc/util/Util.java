package jm.task.core.jdbc.util;

//import com.sun.jdi.connect.spi.Connection;
//import org.hibernate.hql.internal.ast.tree.Statement;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Util {
    public static final String USER_NAME = "postgres";
    public static final String PASSWORD = "root";
    public static final String URL = "jdbc:postgresql://localhost:5432/testTask";
    private static final String DRIVER = "org.postgresql.Driver";
    public static Statement statement;
    public static Connection connection;


    public Util(){};


    public static Connection getConnection() {
        try{
            Class.forName(DRIVER);
            connection =
                    DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void setConnection(Connection connection) {
        Util.connection = connection;
    }

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USER_NAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

                settings.put(Environment.SHOW_SQL, "true");

//                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    // реализуйте настройку соеденения с БД
}
