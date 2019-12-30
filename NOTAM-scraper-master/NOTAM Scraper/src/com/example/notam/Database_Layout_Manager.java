package com.example.notam;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database_Layout_Manager extends com.example.notam.Database_Connection {


    public void createTables(){
        try{
            stmt = conn.createStatement();
            //this is the columns of the table
            String sql = "CREATE TABLE IF NOT EXISTS notams (" +
                    "NOTAM_key VARCHAR(100) PRIMARY KEY NOT NULL, " +
                    "Airport VARCHAR(4) NOT NULL," +
                    "Type VARCHAR(100) NOT NULL, " +
                    "Cordinates VARCHAR(100)," +
                    "Altitude VARCHAR(100)," +
                    "Runway VARCHAR(100)," +
                    "Effective_Time VARCHAR(100)," +
                    "Created VARCHAR(100) NOT NULL, " +
                    "Source VARCHAR(100) NOT NULL, " +
                    "raw_NOTAM VARCHAR(1000) NOT NULL )";

            stmt.executeUpdate(sql);
            stmt.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEntry(String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, String s9, String s10) {
        try{
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO NOTAMS " +
                            "(NOTAM_key, " +
                            "Airport," +
                            "Type," +
                            "coordinates, " +
                            "Altitude, " +
                            "Runway, " +
                            "Effective_Time, " +
                            "Created, " +
                            "Source, " +
                            "raw_NOTAM)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1,s1);
            st.setString(2,s2);
            st.setString(3,s3);
            st.setString(4,s4);
            st.setString(5,s5);
            st.setString(6,s6);
            st.setString(7,s7);
            st.setString(8,s8);
            st.setString(9,s9);
            st.setString(10,s10);
            st.executeUpdate();
            st.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void testAddEntry1(){
        try{
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO NOTAMS " +
                            "(NOTAM_key, " +
                            "Airport," +
                            "Type," +
                            "Cordinates, " +
                            "Altitude, " +
                            "Runway, " +
                            "Effective_Time, " +
                            "Created, " +
                            "Source, " +
                            "raw_NOTAM)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1,"11/330");
            st.setString(2,"ATL");
            st.setString(3,"OBST TOWER OUT OF SERVICE");
            st.setString(4,"333332.90N0841407.20W");
            st.setString(5,"1067.3FT (260.2FT AGL)");
            st.setString(6, null);
            st.setString(7,"1811231352-181223135");
            st.setString(8,"23 Nov 2018 13:52:00");
            st.setString(9,"ATL");
            st.setString(10,"!ATL 11/330 ATL OBST TOWER LGT (ASR 1021647) 333332.90N0841407.20W " +
                    "(10.6NM ESE ATL) 1067.3FT (260.2FT AGL) OUT OF SERVICE 1811231352-1812231352 " +
                    "CREATED: 23 Nov 2018 13:52:00 SOURCE: ATL");
            st.executeUpdate();
            st.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void testAddEntry2(){
        try {
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO NOTAMS " +
                            "(NOTAM_key, " +
                            "Airport," +
                            "Type," +
                            "Cordinates, " +
                            "Altitude, " +
                            "Runway, " +
                            "Effective_Time, " +
                            "Created, " +
                            "Source, " +
                            "raw_NOTAM)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1,"11/371");
            st.setString(2,"ATL");
            st.setString(3,"RWY CLSD");
            st.setString(4,null);
            st.setString(5,null);
            st.setString(6, "09R/27L");
            st.setString(7,"1811280500-181128113");
            st.setString(8,"27 Nov 2018 17:13:00");
            st.setString(9,"ATL");
            st.setString(10,"!ATL 11/368 ATL TWY N2 SFC PAINTED HLDG PSN SIGNS OBSC " +
                    "1811271138-1812031130 CREATED: 27 Nov 2018 11:38:00 SOURCE: ATL");
            st.executeUpdate();
            st.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void testAddEntry3(){
        try {
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO NOTAMS " +
                            "(NOTAM_key, " +
                            "Airport," +
                            "Type, " +
                            "Cordinates, " +
                            "Altitude, " +
                            "Runway, " +
                            "Effective_Time, " +
                            "Created, " +
                            "Source, " +
                            "raw_NOTAM)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1,"11/373");
            st.setString(2,"JFK");
            st.setString(3,"RWY CLSD");
            st.setString(4,null);
            st.setString(5,null);
            st.setString(6, "09R/27L");
            st.setString(7,"1811280500-181128113");
            st.setString(8,"27 Nov 2018 17:13:00");
            st.setString(9,"ATL");
            st.setString(10,"!ATL 11/368 ATL TWY N2 SFC PAINTED HLDG PSN SIGNS OBSC " +
                    "1811271138-1812031130 CREATED: 27 Nov 2018 11:38:00 SOURCE: ATL");
            st.executeUpdate();
            st.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void countEntres(){
        int number_of_rows = -1;
        try{
            PreparedStatement st = conn.prepareStatement(
                    "SELECT count(*) FROM notams");
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                number_of_rows = rs.getInt(1);
            }
            System.out.println("There are " + number_of_rows +" Rows");
        } catch(SQLException e) {
            e.printStackTrace();

        }
    }

    public String testGetEntree(String airportcode){
        String toreturn = "";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOTAM_key,Airport,Type,Cordinates,Altitude,Runway,Effective_Time,Created,Source" +
                    " FROM notams WHERE Airport = 'ATL'");
            while(rs.next()){
                for(int i = 1; i < 9; i++) {
                    toreturn = toreturn + rs.getString(i) + "$";
                }
                toreturn = toreturn + "@";
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return toreturn;
    }

    public com.example.notam.NotamModel testGetEntry(String airportCode){
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOTAM_key,Airport,Type,Cordinates,Altitude,Runway,Effective_Time,Created,Source" +
                    " FROM notams WHERE Airport = '" +airportCode+ "'");
            if(rs.next())
                return new com.example.notam.NotamModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object[] testGetMultipleEntries(String airportCode){
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT NOTAM_key,Airport,Type,Cordinates,Altitude,Runway,Effective_Time,Created,Source" +
                    " FROM notams WHERE Airport = '" +airportCode+ "'");
            ArrayList<com.example.notam.NotamModel> multipleArrayList = new ArrayList<com.example.notam.NotamModel>();
            while(rs.next())
                multipleArrayList.add(new com.example.notam.NotamModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9)));
            return multipleArrayList.toArray();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String testGetRaw(String key){
        try {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT raw_notam" +
                    " FROM notams WHERE NOTAM_key = '" +key+ "'");
            if(rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}