package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeeLoggerDAO {

    Connection conn;

    public EmployeeLoggerDAO(String db_file_name_prefix) throws Exception {

        // Load the HSQL Database Engine JDBC driver
        // hsqldb.jar should be in the class path or made part of the current
        // jar
        Class.forName("org.hsqldb.jdbcDriver");

        // connect to the database. This will load the db files and start the
        // database if it is not already running.
        // db_file_name_prefix is used to open or create files that hold the
        // state of the db.
        // It can contain directory names relative to the
        // current working directory
        conn = DriverManager.getConnection(
                "jdbc:hsqldb:" + db_file_name_prefix, // filenames
                "student", // username
                "Student007"); // password
        
        Statement st = null;
        try {
            // make an empty table
            String exp = "CREATE TABLE usertimes "
                    + "(username VARCHAR(50) NOT NULL, type VARCHAR(5) NOT NULL, "
                    + "time DATETIME NOT NULL)";
            st = conn.createStatement();
            st.executeUpdate(exp);
        } catch (SQLException ex) {
            // ignore ex.printStackTrace(); 
            // second time we run program should throw exception since table
            // already there, this will have no effect on the db
        } finally {
            if (st != null) { st.close(); }
        }
    }

    public void shutdown() throws SQLException {

        Statement st = conn.createStatement();

        // db writes out to files and performs clean shuts down
        // otherwise there will be an unclean shutdown
        // when program ends
        st.execute("SHUTDOWN");
        conn.close(); // if there are no other open connection
    }
    
    public synchronized List<EmployeeLoggerBean> queryAll(String type) throws SQLException {
        List<EmployeeLoggerBean> logs = new ArrayList<>();

        String exp = "SELECT * FROM usertimes WHERE UPPER(type)=?";
        PreparedStatement st = conn.prepareStatement(exp);
        st.setString(1, type.toUpperCase());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .format(rs.getTimestamp(3));
            logs.add(new EmployeeLoggerBean(rs.getString(1), time));
        }

        st.close();
        
        return logs;
    }
    
    public synchronized List<TimeBean> queryByName(String type, String name) throws SQLException {
        List<TimeBean> times = new ArrayList<>();
        
        String exp = "SELECT * FROM usertimes WHERE UPPER(type)=?"
                + " AND UPPER(username)=?";

        PreparedStatement st = conn.prepareStatement(exp);
        st.setString(1, type.toUpperCase());
        st.setString(2, name.toUpperCase());
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .format(rs.getTimestamp(3));
            times.add(new TimeBean(time));
        }
        
        st.close();
        
        return times;
    }

    public synchronized void insertCheck(String name, String type, long time) throws SQLException {
        String exp = "INSERT INTO usertimes (username, type, time) "
                + "VALUES (?, ?, ?)";
        
        PreparedStatement st = conn.prepareStatement(exp);
        st.setString(1, name);
        st.setString(2, type);
        st.setTimestamp(3, new java.sql.Timestamp(time));
        int i = st.executeUpdate();
        
        if (i == -1) {
            System.out.println("db error : " + exp);
        }

        st.close();
    }
}