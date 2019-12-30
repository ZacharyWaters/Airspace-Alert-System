package com.example.notam;
public class Parser{
    private String NotamCategory = "";
    private String airport = "";
    private String TargetAirport = "";
    private String NotamNumber = "";
    private String remainder = "";

    private String type = "";
    private String rawnotam = "";

    private String runtax = "";
    private String Source = "";
    private String Created = "";
    private String WhenEffect = "";
    private String OutOfService = "";
    private String LatLong = "";
    private String Altitude = "";
    //private boolean Obscured = false;

    public Parser(String s1) {

        //Taking of the Source and Created portion of the NOTAM
        //plan to index from the back eventually to make things quicker
        //and to give us a smaller working size for the rest of this
        rawnotam = s1;
        remainder = s1;


        //take the source out of the NOTAM
        parseSource(remainder);

        //Take the Created
        parseCreated(remainder);


        //Taking off ICAO code for airport, will
        parseAirport(remainder);



        //taking off NOTAM number
        parseNotamNumber(remainder);

        //search for obstacle/runway/taxiway
        parseType(remainder);




        //get out the when in effect date
        parseWEF(remainder);


        //System.out.println(s1.contains("OUT OF SERVICE"));

        // get out of service out of there
        parseOutOfService(remainder);


        //looking for latitude and longitude

        parseLatLong(remainder);

        // String[] d = getLatitudeLongitude(LatLong);
        // System.out.println(d[0]);
        // System.out.println(d[1]);




        //taking out the altitude
        parseAltitude(remainder);



        //Is the taxiway/runway obscured
        //parseObsc(remainder);




    }


    public String getRemainder() {
        return remainder;
    }

    public String getNotamCategory() {
        return NotamCategory;
    }

    public String getAirport() {
        return airport;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }


    public String getNotamNumber() {
        return NotamNumber;
    }

    public String getType() {
        return type;
    }

    public String getRunway() {
        return runtax;
    }

    public String getSource() {
        return Source;
    }

    public String getCreated() {
        return Created;
    }

    public String getWhenInEffect() {
        return WhenEffect;
    }

    public String getService() {
        return OutOfService;
    }

    public String getCoords() {
        return LatLong;
    }

    public String getAltitude() {
        return Altitude;
    }

    public String getRawNotam() { return rawnotam; }

    // public boolean getObscured() {
    // 	return Obscured;
    // }




    /////// NOT GETTER METHODS

    //Should always correctly get the source of the notam.
    //Might need some testing
    public void parseSource(String s) {
        int num = s.indexOf("SOURCE");
        Source = s.substring(num, s.length());
        s = s.replace((" " + Source), "");
        remainder = s;
    }

    //Should always strip off the created date of the NOTAM.
    //Might also need some testing.
    public void parseCreated(String s) {
        int num = s.indexOf("CREATED");
        Created = s.substring(num, s.length());
        s = s.replace((" " + Created), "");
        remainder = s;
    }


    //IndexOf('!'') should be constant across NOTAMs.
    //THIS SHOULD BE DONE NOW.
    //NEEDS SOME TESTING.
    public void parseAirport(String s) {
        int air = s.indexOf('!');
        if (s.substring(air + 1, air + 3).equals("FDC")) {
            if (s.contains("TEMPORARY FLIGHT RESTRICTION")) {
                this.NotamCategory = "TFR";
            } else {
                this.NotamCategory = "FDC";
            }
            s = s.substring(air + 4, s.length());
            remainder = s;
        } else {
            this.NotamCategory = "General";
            this.airport = s.charAt(air) + s.substring(air + 1, air + 4);
            s = s.substring(air + 5, s.length());
            remainder = s;
        }
    }

    //This should get the NOTAM number
    //This should always work given that NOTAM number appears at the beginning of the NOTAM
    //THIS MIGHT BE DONE NOW. NEEDS TESTING
    public void parseNotamNumber(String s) {
        int num = s.indexOf('/');
        int digit = num + 1;
        while (isNumeric(Character.toString(s.charAt(digit)))) {
            digit++;
        }
        digit--;
        if (num - 2 < 0) {
            NotamNumber = s.substring(num - 1, digit + 1);
            s = s.substring(digit + 2, s.length());
            remainder = s;
        } else {
            NotamNumber = s.substring(num - 2, digit + 1);
            s = s.substring(digit + 2, s.length());
            remainder = s;
        }

        //This should pull off the target airport


    }


    ///This method is not good.
    //IDK Why I have the type named replace.
    //This method will need the most expanding
    //This is the hardest thing we will have to do.

    public void parseType(String s) {
        //Seeing if type is OBST
        int num = s.indexOf("OBST");
        if (num == -1) {
            //num = s.indexOf("RWY");
        } else {
            s = s.replace(" OBST", "");
            type = "OBST";
        }



//        //Seeing if type is TWY
//        if (num == -1) {
//            num = s.indexOf("TWY");
//        } else if (type.equals("")) {
//            s = s.replace(" RWY", "");
//            if(s.contains("CLSD")) {
//                s = s.replace(" CLSD", "");
//                type = "RWY CLSD";
//            } else {
//                type = "RWY";
//            }
//        }


        //Seeing if the NOTAM type is Communications
        if (num == -1) {
            num = s.indexOf(" COM ");
        } else if (type.equals("")) {
            s = s.replace(" TWY", "");
            type = "TWY";

        }

        //Seeing if NOTAM is type Airspace
        if (num == -1) {
            num = s.indexOf("AIRSPACE");
        } else if (type.equals("")) {
            s = s.replace(" COM", "");
            type = "COM";

        }

        //Seeing if NOTAM is type NAV
        if (num == -1 || (type == "RWY" && s.contains("NAV"))) {
            num = s.indexOf("NAV");
        } else if (type.equals("")) {
            s = s.replace(" AIRSPACE", "");
            type = "AIRSPACE";

        }


        //pulling off
        if (num == -1) {
            num = -1;
        } else if (num == s.indexOf("NAV")){
            s = s.replace(" NAV", "");
            type = "NAV";
        }

        //seeing if NOTAM is type APRON
        if (num == -1) {
            num = s.indexOf("APRON");
            if(num != -1) {
                s = s.replace(" APRON", "");
                type = "APRON";
            }
        }


        //Seeing if it is aerodrome
        if (num == -1) {
            num = s.indexOf(" AD ");
            if(num != -1) {
                s = s.replace(" AD ", "");
                type = "AD";
            }
        }

        //Pulling off services
        if(num == -1) {
            num = s.indexOf(" SVC");
            if(num != -1) {
                s = s.replace(" SVC", "");
                type = "SVC";
            }
        }







        remainder = s;
//        //if runway and taxiway, get next string
//        if (type.equals("RWY") || type.equals("TWY") || type.equals("RWY CLSD")) {
//            int digit = num;
//            //System.out.println(s1.charAt(digit));
//            while (!(s.charAt(digit) == ' ' && !(s.charAt(digit) == ','))) {
//                digit+=1;
//            }
//
//            runtax = s.substring(num, digit);
//            //System.out.println(runtax);
//            s = s.replace((" " + runtax), "");
//            remainder = s;
//        }




        //because runway and taxiways are hard, I am going to do it in another method.
        parseRunwayTaxiway(s);



    }

    //gets the When in effect date for the notam
    //
    //Assumes that a dash occurs in them. Searches for all dashes and area before and after dashes.
    //NEED TO VERIFY VALIDITY
    //
    //
    //NEED TO ADD CODE TO HANDLE TIMEZONES MAYBE
    //
    public void parseWEF(String s) {

        int num = s.indexOf("-");
        boolean notFound = true;
        while(num != -1 && notFound) {
            if((s.charAt(num + 1)=='P') && (s.charAt(num + 2)=='E') && (s.charAt(num + 3)=='R' && (s.charAt(num + 4)=='M'))) {
                WhenEffect = s.substring(num - 10, num + 5);
                s = s.replace(WhenEffect, "");
                remainder = s;
                notFound = false;
                WhenEffect = formatTime(WhenEffect);
                //System.out.println("loop");
            }
            else if (Character.isDigit(s.charAt(num-1)) && Character.isDigit(s.charAt(num-2)) && Character.isDigit(s.charAt(num-3)) && Character.isDigit(s.charAt(num-4)) && Character.isDigit(s.charAt(num-5)) && Character.isDigit(s.charAt(num-6)) && Character.isDigit(s.charAt(num-7)) && Character.isDigit(s.charAt(num-8)) && Character.isDigit(s.charAt(num-9)) && Character.isDigit(s.charAt(num - 10))) {
                WhenEffect = s.substring(num - 10, num + 11);
                s = s.replace(WhenEffect, "");
                remainder = s;
                notFound = false;
                WhenEffect = formatTime(WhenEffect);
            }

            num = s.indexOf("-", num + 1);
        }

        // //System.out.println(s1.charAt(num));
        // if (Character.isDigit(s.charAt(num)) && Character.isDigit(s.charAt(num-1)) && Character.isDigit(s.charAt(num-2)) && Character.isDigit(s.charAt(num-3)) && Character.isDigit(s.charAt(num-4)) && Character.isDigit(s.charAt(num-5)) && Character.isDigit(s.charAt(num-6)) && Character.isDigit(s.charAt(num-7)) && Character.isDigit(s.charAt(num-8)) && Character.isDigit(s.charAt(num-9))) {
        // 	//System.out.println("lknfdlf");
        // 	WhenEffect = s.substring(num - 20, num + 1);
        // 	//System.out.println(WhenEffect);
        // 	s = s.substring(0, num - 21);
        // 	remainder = s;
        // 	WhenEffect = formatTime(WhenEffect);


        // }


    }

    //Useless according to what our client told us. Im leaving it in for now though.
    public void parseOutOfService(String s) {
        if (s.contains("OUT OF SERVICE")) {
            OutOfService = "OUT OF SERVICE";
            s = s.replace(" OUT OF SERVICE", "");
            remainder = s;
        }
    }

    //Parse out lat long by looking for periods with N/S close to it with numbers around it.
    public void parseLatLong(String s) {

        int num = s.indexOf(".");
        boolean notfound = true;

        while(num != -1 && notfound == true) {
            if(s.charAt(num + 3) == 'N' || s.charAt(num + 3) == 'S' && Character.isDigit(s.charAt(num + 1)) && Character.isDigit(s.charAt(num - 1))) {
                LatLong = s.substring(num - 6, num + 15);
                s = s.substring(0, num - 7) + s.substring(num + 15, s.length());
                remainder = s;
                LatLong = LatLong.substring(0, 4) + LatLong.charAt(9) + " " + LatLong.substring(10, 14) + LatLong.charAt(20);
            } else {
                num = s.indexOf(".", num + 1);
            }
        }
    }


    //This probably does not work, and we arent using altitude right now. Some notams have many different FT listings in it.
    public void parseAltitude(String s) {

        int num  = s.indexOf("FT");
        if (num != -1) {
            int digit = num;
            while ((s.charAt(digit) != ' ') && digit > 0) {

                digit--;
                //System.out.println(digit);
            }
            digit++;

            num += 2;
            if (s.charAt(num + 1) == '(') {
                //System.out.println(digit);

                while (s.charAt(num) != ')' && num < s.length()) {
                    num++;
                }
            }

            Altitude = s.substring(digit, num + 1);
            s = s.replace(" " + Altitude, "");
            remainder = s;
        }
    }

    // public void parseObsc(String s) {
    // 	if (s.contains("OBSC")) {
    // 		Obscured = true;
    // 		s = s.replace(" OBSC", "");
    // 		remainder = s;
    // 	}

    // }


    public String formatTime(String s) {
        String startday = "";
        String startmonth = "";
        String startyear = "";
        String startutctime = "";

        String endday = "";
        String endmonth = "";
        String endyear = "";
        String endutctime = "";

        startyear = "20" + s.substring(0, 2);
        //System.out.println(startyear);
        startmonth = s.substring(2, 4);
        //System.out.println(startmonth);
        startday = s.substring(4, 6);
        //System.out.println(startday);
        startutctime = s.substring(6, 10);
        //System.out.println(startutctime);
        if(s.charAt(11) == 'P') {
            endmonth = "PERM";
            s = startmonth + "/" + startday + "/" + startyear + "/" + startutctime + "-" + endmonth;
        } else {

            endyear = "20" + s.substring(11, 13);
            endmonth = s.substring(13, 15);
            endday = s.substring(15, 17);
            endutctime = s.substring(17, 21) + ""; //+ WhenEffect.charAt(20);
            s = startmonth + "/" + startday + "/" + startyear + "/" + startutctime + "-" + endmonth + "/" + endday + "/" + endyear + "/" + endutctime;
        }
        return s;
    }

    public String getLatLong(String s) {
        String Coords = "";
        Coords = s.substring(0, 4) + s.charAt(9) + " " + s.substring(10, 14) + s.charAt(20);
        return Coords;
    }

    public String[] getLatitudeLongitude(String s) {
        String[] coordinates = new String[2];
        int i = 0;
        boolean boo = true;
        while(i < s.length() && boo) {
            if (s.charAt(i)=='N' || s.charAt(i)=='S') {
                coordinates[0] = s.substring(0, i + 1);
                coordinates[1] = s.substring(i + 1, s.length());
            }
            i+=1;
        }
        return coordinates;

    }

    public void addEntry() {
        //Database_Layout_Manager.addEntry()
    }

    public void parseRunwayTaxiway(String s) {
        int num = s.indexOf("RWY");
        if(num != -1) {
            if (this.type == "") {
                this.type = "RWY";
            } else {
                this.type += " RWY";
            }
            if(s.contains("CLSD")) {
                s.replace("CLSD ", "");
            }
        }
        while (num != -1) {
            int digit = num + 4;
            while(s.charAt(digit) != ' ' && s.charAt(digit) != ',') {
                digit += 1;
            }
            runtax += " " + s.substring(num + 4, digit);
            s = s.replace(s.substring(num + 4, digit), "");
            num = s.indexOf("RWY", num + 1);
        }

        num = s.indexOf("TWY");
        if(num != -1) {
            if(this.type == "") {
                this.type = "TWY";
            } else {
                this.type += " TWY";
            }
            if(s.contains("CLSD")) {
                s.replace("CLSD ", "");
            }
        }
        while (num != -1) {
            int digit = num + 4;
            while(s.charAt(digit) != ' ' && s.charAt(digit) != ',') {
                digit += 1;
            }
            runtax += " " + s.substring(num + 4, digit);
            s = s.replace(s.substring(num + 4, digit), "");
            num = s.indexOf("TWY", num + 1);
        }

        num = 0;
        while (num < this.type.length() && this.type.charAt(num) == ' ' ) {
            num++;
        }
        this.type = this.type.substring(num, this.type.length());

        while (num < this.runtax.length() && this.runtax.charAt(num) == ' ' ) {
            num++;
        }
        this.runtax = this.runtax.substring(num, this.runtax.length());
    }


    public void printNotam() {
        System.out.println("Reamining Text: " + getRemainder());
        System.out.println();
        System.out.println("Airport: " + getAirport());
        System.out.println("NOTAM #: " + getNotamNumber());
        System.out.println("NOTAM type: " + getType());
        System.out.println("Runway/Taxiway/Obstacle: " + getRunway());
        System.out.println("Service: " + getService());
        System.out.println("Coordinates: " + getCoords());
        System.out.println("Altitude: " + getAltitude());
        //System.out.println("Obscured: " + j.getObscured());
        System.out.println("Effective Time: " + getWhenInEffect());
        System.out.println(getCreated());
        System.out.println(getSource());
        System.out.println("");
    }




}