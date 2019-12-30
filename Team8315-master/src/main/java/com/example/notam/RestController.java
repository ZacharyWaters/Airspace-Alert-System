package com.example.notam;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/hello")
    String hello() {
        return "hello";
    }

    @GetMapping("/database_test")
    String database_test() {

        //Creating initial database_connection
        Database_Layout_Manager database_connection =  new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();

        //Count Entrees
        database_connection.countEntres();

        database_connection.testAddEntry2();
        database_connection.testAddEntry3();

        //Disconnect from Database
        database_connection.disconnect();
        return "Tested_database";
    }

    @PostMapping("/hello")
    String helloName(@RequestBody String name) {
        return "hello " + name;
    }


    /**
     * Method used by parser/notam web scraper.
     *
     * @param AirportCode IATA or ICAO airport code as String
     * @return a row instance in the form of a notam model entry in the db
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/AirportCode")
    NotamModel airportcodestring(@RequestBody String AirportCode) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection =  new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();

        //Getting String Value from Matching Notams
        NotamModel results = database_connection.testGetEntry(AirportCode);

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    /**
     * Method used by parser/notam web scraper.
     *
     * @param AirportCode IATA or ICAO airport code as String
     * @return an instance of an entry's column value for geocoordinates.
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/LongandLatfromCoords")
    String longAndLatFromCoords(@RequestBody String AirportCode) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection = new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();

        //Getting String Value from Matching Notams
        String results = database_connection.getAirportCoordinates(AirportCode);
        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    /**
     * Method used by parser/notam web scraper.
     *
     * @param AirportCode IATA or ICAO airport code as String
     * @return all instances of entries having the user's input
     * airport code, as an array of type 'Object[]'.
     */
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/AirportCodeMultiple")
    Object[] airportcodemultiplestring(@RequestBody String AirportCode) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection =  new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();

        //Getting String Value from Matching Notams
        Object[] results = database_connection.testGetMultipleEntries(AirportCode);

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/GetAllNotams")
    Object[] getAllNotamsInDatabase() {
        //Creating initial database_connection
        Database_Layout_Manager database_connection =  new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();

        //Getting String Value from Matching Notams
        Object[] results = database_connection.getAllNotams();

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/RawNotamFromKey")
    String rawnotamstring(@RequestBody String NotamKey) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection =  new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();

        //Getting String Value from Matching Notams
        String results = database_connection.getRawNotamData(NotamKey.toUpperCase());

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/AllNotamCoords")
    ArrayList<Object> getAllNotamCoords(@RequestBody Object[] airportCodes) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection = new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();
        // results are arraylist of airport coordinates in JSON string format
        ArrayList<Object> results;
        results = database_connection.getAllAirportCoordinates(airportCodes);

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/populateMapByKey")
    /**
     * @param key unique key in db
     * @return NOTAM having unique key input by user to execute
     * in search query. Should return a single notam since key
     * is unique key in db
     */
    Object searchNotamKey(@RequestBody String key) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection = new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();
        Object result;
        result = database_connection.searchByNotamKey(key);

        //Disconnect from Database
        database_connection.disconnect();
        return result;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/populateMapByAirportCode")
    /**
     * @param airportCode of NOTAM
     * @return NOTAMs associated with a specific airport code, input by user
     * to query db according to airport code.
     */
    Object[] searchNotamAirportCode(@RequestBody String airportCode) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection = new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();
        Object[] results;
        results = database_connection.searchByNotamAirportCode(airportCode);

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/populateMapByType")
    /**
     * @param type of NOTAM
     * @return NOTAMs that are of a a type queried by user.
     */
    Object[] searchNotamType(@RequestBody String type) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection = new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();
        Object[] results;
        results = database_connection.searchByNotamType(type);

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/populateMapByEffectiveDate")
    /**
     * @param effectiveDate of NOTAM
     * @return NOTAMs that are of an effective date queried by user.
     */
    Object[] searchNotamEffectiveDate(@RequestBody String effectiveDate) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection = new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();
        Object[] results;
        results = database_connection.searchByNotamEffectiveDate(effectiveDate);

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/populateMapByCreatedDate")
    /**
     * @param createdDate of NOTAM
     * @return NOTAMs that are of a created date queried by user.
     */
    Object[] searchNotamCreatedDate(@RequestBody String createdDate) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection = new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();
        Object[] results;
        results = database_connection.searchByNotamCreatedDate(createdDate);

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/populateMapBySource")
    /**
     * @param source of NOTAM
     * @return NOTAMs that are from a source queried by user.
     */
    Object[] searchNotamSource(@RequestBody String source) {
        //Creating initial database_connection
        Database_Layout_Manager database_connection = new Database_Layout_Manager();

        //Establish Connection to Database
        database_connection.connect();
        Object[] results;
        results = database_connection.searchByNotamSource(source);

        //Disconnect from Database
        database_connection.disconnect();
        return results;
    }


    @GetMapping("/atlanta")
    String atlanta() { return "atlanta"; }
}