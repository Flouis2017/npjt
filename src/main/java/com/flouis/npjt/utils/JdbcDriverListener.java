package com.flouis.npjt.utils;

import java.sql.Driver;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author Flouis
 * @date 2018/09/21
 * @description TODO
 **/

public class JdbcDriverListener implements ServletContextListener{



    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) { }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        try {
            while (drivers.hasMoreElements()) {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
//                logger.info(" 消除数据库连接驱动 --> : Driver {} deregistered", d);
            }
        } catch (SQLException ex) {
//            logger.error("Error: deregistering driver {} exceptionName:{} detail:{}", d, ex.getClass().getName(), ex.getMessage());
        }finally {
            try {
                AbandonedConnectionCleanupThread.shutdown();
            } catch (InterruptedException e) {
//                logger.error("Error: SEVERE problem cleaning up: " + e.getMessage());
            }
        }
    }
}
