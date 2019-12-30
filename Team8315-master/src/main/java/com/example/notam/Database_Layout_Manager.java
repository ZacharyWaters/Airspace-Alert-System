package com.example.notam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database_Layout_Manager extends Database_Connection {

    public void createTables() {
        try{
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS notams (" +
                    "NOTAM_key VARCHAR(100) PRIMARY KEY NOT NULL, " +
                    "Airport VARCHAR(4) NOT NULL," +
                    "Type VARCHAR(100) NOT NULL, " +
                    "Coordinates VARCHAR(100)," +
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

    public void testAddEntry1(){
        try{
            PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO NOTAMS " +
                            "(NOTAM_key, " +
                            "Airport," +
                            "Type," +
                            "Coordinates, " +
                            "Altitude, " +
                            "Runway, " +
                            "Effective_Time, " +
                            "Created, " +
                            "Source, " +
                            "raw_NOTAM)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1,"11/330");
            st.setString(2,"!ATL");
            st.setString(3,"OBST TOWER OUT OF SERVICE");
            st.setString(4,"333332.90N0841407.20W");
            st.setString(5,"1067.3FT (260.2FT AGL)");
            st.setString(6, null);
            st.setString(7,"1811231352-181223135");
            st.setString(8,"23 Nov 2018 13:52:00");
            st.setString(9,"ATL");
            st.setString(10,"!ATL 11/330 ATL OBST TOWER " +
                    "LGT (ASR 1021647) 333332.90N0841407.20W " +
                    "(10.6NM ESE ATL) 1067.3FT (260.2FT AGL) " +
                    "OUT OF SERVICE 1811231352-1812231352 " +
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
                            "Coordinates, " +
                            "Altitude, " +
                            "Runway, " +
                            "Effective_Time, " +
                            "Created, " +
                            "Source, " +
                            "raw_NOTAM)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1,"11/371");
            st.setString(2,"!ATL");
            st.setString(3,"RWY CLSD");
            st.setString(4,null);
            st.setString(5,null);
            st.setString(6, "09R/27L");
            st.setString(7,"1811280500-181128113");
            st.setString(8,"27 Nov 2018 17:13:00");
            st.setString(9,"ATL");
            st.setString(10,"!ATL 11/368 ATL TWY N2" +
                    " SFC PAINTED HLDG PSN SIGNS OBSC " +
                    "1811271138-1812031130 CREATED: " +
                    "27 Nov 2018 11:38:00 SOURCE: ATL");
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
                            "Coordinates, " +
                            "Altitude, " +
                            "Runway, " +
                            "Effective_Time, " +
                            "Created, " +
                            "Source, " +
                            "raw_NOTAM)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1,"11/373");
            st.setString(2,"!JFK");
            st.setString(3,"RWY CLSD");
            st.setString(4,null);
            st.setString(5,null);
            st.setString(6, "09R/27L");
            st.setString(7,"1811280500-181128113");
            st.setString(8,"27 Nov 2018 17:13:00");
            st.setString(9,"JFK");
            st.setString(10,"!JFK 11/368 ATL TWY N2 " +
                    "SFC PAINTED HLDG PSN SIGNS OBSC " +
                    "1811271138-1812031130 CREATED:" +
                    " 27 Nov 2018 11:38:00 SOURCE: JFK");
            st.executeUpdate();
            st.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Counts the number of notams by number of row entries in db.
     * Prints notam count.
     * For testing.
     */
    void countEntres(){
        int number_of_rows = -1;
        try{
            PreparedStatement st = conn.prepareStatement(
                    "SELECT count(*) " +
                            "FROM notams");
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                number_of_rows = rs.getInt(1);
            }
            System.out.println("There are " + number_of_rows +" Rows");
        } catch(SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     *
     * @param airportCode the airport code associated with the airport that
     *                    the user has input and submitted to conduct
     *                    a notam search query.
     * @return query result of user's notam search by airport code.
     */
    public String testGetEntree(String airportCode){
        StringBuilder toReturn = new StringBuilder();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams " +
                            "WHERE Airport = '" +airportCode+ "'");
            while(rs.next()){
                for(int i = 1; i < 9; i++) {
                    toReturn.append(rs.getString(i)).append("$");
                }
                toReturn.append("@");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();
    }

    /**
     * Method used to test parser/notam data scraper.
     *
     * @param airportCode user input airport code
     * @return the first row entry in db with an airport
     * code matching the user's input.
     */

    public NotamModel testGetEntry(String airportCode){
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source" +
                            " FROM notams WHERE Airport = '" +airportCode+ "'");
            if(rs.next()) {
                return new NotamModel(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(8), rs.getString(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method used by parser/notam scraper.
     *
     * @param airportCode input by user in search query execution.
     * @return all notam entries having the airport code matching user
     * input, returned as an array of type 'Object[]'.
     */
    public Object[] testGetMultipleEntries(String airportCode) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source" +
                            " FROM notams " +
                            "WHERE Airport = '" +airportCode+ "'");
            ArrayList<NotamModel> multipleArrayList = new ArrayList<NotamModel>();
            while(rs.next()) {
                getNextMatchingEntry(
                        rs, multipleArrayList);
            }
            return multipleArrayList.toArray();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method used to test parser/notam data scraper.
     *
     * Passes in a single airport code input by a user from our
     * rest api, returning a String back to the rest api for
     * postmapping to ui, in response to front-end code http
     * request event, triggered by user search query.
     *
     * Lat and lng property:value mapping is formatted
     * into a JSON string, to be consumed by our frontend code using
     * JSON.parse(JSON_String). The JSON.parse method is inside of
     * the frontend function invoking the corresponding postmapping
     * method in our rest controller.
     *
     * @param airportCode entered by user to query corresponding
     *                    notam entry data.
     * @return String geo-coordinates formatted as a JSON string.
     */
    public String getAirportCoordinates(String airportCode){
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams " +
                            "WHERE Airport = '" +airportCode+ "'");
            if (rs.next()) {
                ObjectMapper objectMapper = new ObjectMapper();
                Coords coords = new Coords(null, null);
                coords.setLat(33.3333);
                coords.setLng(-84.4444);
                double lat = coords.getLat();
                double lng = coords.getLng();
                String coordsAsString;
                try {
                    coordsAsString = objectMapper.writeValueAsString(coords);
                } catch (JsonProcessingException e) {
                    System.out.println("Lat and Lng data not convertible to JSON");
                    coordsAsString = "{ \"lat\" : " + lat + ", \"lng\" : " + lng + " }";
                    System.out.println("coordsAsString = " + coordsAsString);
                }
                return coordsAsString;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the airport coordinates of all notam data entries in db.
     *
     * @param airportCodes array of airportCodes for all notam entries.
     * @return ArrayList<Object> of all notam airport codes,
     * including duplicate-airport-code-associated instances.
     * (i.e., notam having the same airport code as a different
     * notam in our database. Each notam has a unique key
     * for identification in our db, in the format of a notam key-as-string.
     */
    public ArrayList<Object> getAllAirportCoordinates(Object[] airportCodes) {
        ArrayList<Object> airportCodeList = new ArrayList<>();
        int i = airportCodes.length - 1;
        int j = -1;
        while (i > -1) {
            try {
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT NOTAM_key," +
                                "Airport," +
                                "Type," +
                                "Coordinates," +
                                "Altitude," +
                                "Runway," +
                                "Effective_Time," +
                                "Created," +
                                "Source FROM notams " +
                                "WHERE Airport = '" + airportCodes[++j] + "'");
                while (rs.next()) {
                    airportCodeList.add(rs.getString(4));
                }
                return airportCodeList;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            --i;
        }
        return null;
    }

    /**
     * Retrieves all column values of a queried notam entry,
     * with search conducted by a notam's unique key.
     *
     * @param key string value entered by user
     *                    to query matching notam instances
     *                    in our database.
     * @return Object[] an array of notam object model db
     * instances matching a user's input notam key. Should
     * return only a single notam instance.
     */
    public Object[] searchByNotamKey(String key) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams " +
                            "WHERE NOTAM_key = '" + key + "'");
            ArrayList<NotamModel> multipleArrayList
                    = new ArrayList<>();
            return getNextMatchingEntry(rs, multipleArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * Returns data values of all notam entries matching
     * a user's input airport code.
     *
     * @param airportCode string value entered by user
     *                    to query matching notam instances
     *                    in our database.
     * @return Object[] an array of notam object model db
     * instances matching a user's input airportCode.
     */
    public Object[] searchByNotamAirportCode(String airportCode){
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams " +
                            "WHERE Airport = '" + airportCode + "'");
            ArrayList<NotamModel> multipleArrayList
                    = new ArrayList<>();
            return getNextMatchingEntry(rs, multipleArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all column values of a queried notam entry,
     * with search conducted by notam type.
     *
     * @param type string value entered by user
     *                    to query matching notam instances
     *                    in our database.
     * @return Object[] an array of notam object model db
     * instances matching a user's input notam type.
     */

    public Object[] searchByNotamType(String type) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams " +
                            "WHERE Type = '" + type + "'");
            ArrayList<NotamModel> multipleArrayList
                    = new ArrayList<>();
            return getNextMatchingEntry(rs, multipleArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all column values of a queried notam entry,
     * with search conducted by a notam's effective date.
     *
     * @param effectiveDate string value entered by user
     *                    to query matching notam instances
     *                    in our database.
     * @return Object[] an array of notam object model db
     * instances matching a user's input notam effective date.
     */
    public Object[] searchByNotamEffectiveDate(String effectiveDate) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams " +
                            "WHERE Effective_Time = '" + effectiveDate + "'");
            ArrayList<NotamModel> multipleArrayList
                    = new ArrayList<>();
            return getNextMatchingEntry(rs, multipleArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all column values of a queried notam entry,
     * with search conducted by a notam's createdDate.
     *
     * @param createdDate value entered by user
     *                    to query matching notam instances
     *                    in our database.
     * @return Object[] an array of notam object model db
     * instances matching a user's input notam created date.
     */
    public Object[] searchByNotamCreatedDate(String createdDate) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams " +
                            "WHERE Created = '" + createdDate + "'");
            ArrayList<NotamModel> multipleArrayList
                    = new ArrayList<>();
            return getNextMatchingEntry(rs, multipleArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all column values of a queried notam entry,
     * with search conducted by a notam's source.
     *
     * @param source string value entered by user
     *                    to query matching notam instances
     *                    in our database.
     * @return Object[] an array of notam object model db
     * instances matching a user's input notam source.
     */
    public Object[] searchByNotamSource(String source) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams " +
                            "WHERE Source = '" + source + "'");
            ArrayList<NotamModel> multipleArrayList
                    = new ArrayList<>();
            return getNextMatchingEntry(rs, multipleArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * Returns all rows of notam data entry values in our db.
     *
     * @return Object[] all rows of notam data entry values in our db.
     */
    public Object[] getAllNotams() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT NOTAM_key," +
                            "Airport," +
                            "Type," +
                            "Coordinates," +
                            "Altitude," +
                            "Runway," +
                            "Effective_Time," +
                            "Created," +
                            "Source " +
                            "FROM notams ");
            ArrayList<NotamModel> multipleArrayList
                    = new ArrayList<>();
            return getNextMatchingEntry(rs, multipleArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * Private helper method.
     *
     * @param rs iterable ResultSet of notam data.
     * @param multArrayList array list which has notam entry data
     *                      added to it upon each applicable
     *                      iterative pass of the
     *                      while loop below (applicable
     *                      in cases where the airport code
     *                      entered by the user matches the
     *                      airport code in a row of entry data
     *                      in our db, and applicable in cases
     *                      where the queried data intentionally
     *                      includes all notam entries in the db,
     *                      e.g. getAllNotams()).
     * @return Object[] cumulative NotamModel instances
     * resulting from a user's search query.
     * @throws SQLException if one is thrown by a method invoking this helper.
     */
    public Object[] getNextMatchingEntry(
            ResultSet rs,
            ArrayList<NotamModel> multArrayList) throws SQLException {
        while(rs.next()) {
            multArrayList.add(
                    new NotamModel(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getString(9)));
        }
        return multArrayList.toArray();
    }

    /**
     *
     * Method used for rendering notam data for display to a user, when that
     * user mouseclicks a notam key's value.
     *
     * @param key unique notam key corresponding with a single notam entry
     * @return String concatenation of notam data-turned-to-string
     * to render for display when a user mouseclicks
     * the notam key value (value in first column) of any row of notam
     * data previously returned upon that user's submitted search query.
     */
    public String getRawNotamData(String key){
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT raw_notam " +
                            "FROM notams " +
                            "WHERE NOTAM_key = '" +key+ "'");
            if(rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}