//package ua.nure.library.util.migration;
//
//import lombok.extern.log4j.Log4j;
//import org.flywaydb.core.Flyway;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
///**
// * @author Artem Kudria
// */
//
//@WebListener
//@Log4j
//public class FlywayConfig implements ServletContextListener {
//    @Override
//    public void contextInitialized(ServletContextEvent event) {
//        String dataBaseProperties = "db.properties";
//        String propertiesUrl = "database.url";
//        String propertiesUser = "database.username";
//        String propertiesPass = "database.password";
//        Properties properties = new Properties();
//
//        try {
//            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(dataBaseProperties);
//            properties.load(input);
//            String dbUrl = properties.getProperty(propertiesUrl);
//            String dbUser = properties.getProperty(propertiesUser);
//            String dbPassword = properties.getProperty(propertiesPass);
//
//            Flyway flyway = Flyway.configure()
//                    .dataSource(dbUrl, dbUser, dbPassword)
//                    .load();
//            flyway.migrate();
//        } catch (IOException e) {
//            log.info("Error, unable to select properties {}" + e);
//        }
//    }
//}
