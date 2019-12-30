package com.example.notam;
import java.util.ArrayList;
import java.util.LinkedList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {


    /**
     *  Connect to database first
     */

    /**
     * Scrapes the notams from pilotweb of Types GPS,TFR,CARF,SPECIAL
     * depending on the input type you provide to the method.
     *
     * @param  type whether you return types GPS,TFR,CARF,SPECIAL
     *              GPS = default, any number but 1,2,3
     *              TFR = 1
     *              CARF = 2
     *              SPECIAL = 3
     * @return      the String Linked list of all the notams
     */
    public static LinkedList < String > scraper_Pilotweb(int type) {
        String url;
        //Default URL Assignment
        url = "https://pilotweb.nas.faa.gov/PilotWeb/noticesAction.do?queryType=ALLGPS&formatType=DOMESTIC";
        if(type == 1) {
            url = "https://pilotweb.nas.faa.gov/PilotWeb/noticesAction.do?queryType=ALLTFR&formatType=DOMESTIC";
        } else if(type == 2) {
            url = "https://pilotweb.nas.faa.gov/PilotWeb/noticesAction.do?queryType=ALLCARF&formatType=DOMESTIC";
        } else if(type == 3) {
            url = "https://pilotweb.nas.faa.gov/PilotWeb/noticesAction.do?queryType=ALLSPECIALNOTICES&formatType=DOMESTIC";
        }
        LinkedList<String> resultlist = new LinkedList<String>();
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/17.0").get();
            Element resultsHomeLeft = doc.body().child(0).child(4).child(0).child(1).child(0).child(0).child(0);
            boolean brakes = false;
            int counter = 5;
            while(brakes != true){
                Element resultsChildren = resultsHomeLeft.child(counter);
                Element notamRight = resultsChildren.getElementById("notamRight");
                if(notamRight != null){
                    resultlist.add(notamRight.text());
                }
                //This loops through all the NOTAMS
                if(resultsChildren.child(0).text().contains("Number of NOTAMs")){
                    brakes = true;
                } else {
                    counter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultlist;
    }

    /**
     * Returns a String Linked List of all the NOTAMS
     * from the faa.gov.dinsQueryWeb website, that have
     * the provided airport code.
     *
     * @param  airport_code  the airport whose notams you want to scrape
     * @param  type whether you want to run in headless or visible mode
     *              VISIBLE MODE = DEFAULT, any number but 1
     *              INVISIBLE MODE = 1
     * @return      the String list of all the notams
     */
    public static LinkedList<String> scraper_Chrome_dinsQueryWeb(String airport_code, int type) {
        //Handles incorrect inputs
        if(airport_code == null) {
            throw new IllegalArgumentException("no input value was provided for scraper_Chrome_dinsQueryWeb");
        }//This Gets the Executable for the Chrome Driver
        //System.setProperty("webdriver.chrome.driver","C:\\Users\\toddw\\Desktop\\Library\\chromedriver_win32\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Mac\\Documents\\juniordesigns2\\NOTAM-scraper\\NOTAM Scraper\\exe\\chromedriver.exe");
        //this creates the Chrome Driver
        ChromeOptions headlessChromeOptions = new ChromeOptions();
        if(type == 1) {
            headlessChromeOptions.setHeadless(true);
        }
        WebDriver driver = new ChromeDriver(headlessChromeOptions);
        //this goes to the website
        driver.get("https://www.notams.faa.gov/dinsQueryWeb/");
        WebElement noticeButton = driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[3]/button[1]"));
        noticeButton.click();
        WebElement rawNotamRadioButton = ((ChromeDriver) driver).findElement(By.xpath("/html[1]/body[1]/table[3]/" +
                "tbody[1]/tr[1]/td[1]/table[1]/tbody[1]/tr[1]/td[1]/form[1]/table[1]/tbody[1]/tr[1]/td[2]/table[1]/" +
                "tbody[1]/tr[3]/td[1]/input[2]"));
        rawNotamRadioButton.click();
        WebElement textbox = driver.findElement(By.xpath("/html[1]/body[1]/table[3]/tbody[1]/tr[1]/td[1]/table[1]/" +
                "tbody[1]/tr[1]/td[1]/form[1]/table[1]/tbody[1]/tr[1]/td[2]/table[1]/tbody[1]/tr[4]/td[1]/textarea[1]"));
        String airportCode = airport_code;
        textbox.sendKeys(airportCode);
        WebElement searchButton = driver.findElement(By.xpath("/html[1]/body[1]/table[3]/tbody[1]/tr[1]/td[1]/table[1]/" +
                "tbody[1]/tr[1]/td[1]/form[1]/table[1]/tbody[1]/tr[1]/td[2]/table[1]/tbody[1]/tr[5]/td[1]/input[1]"));
        searchButton.click();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window((tabs.get(1)));
        Document doc = Jsoup.parse(driver.getPageSource());
        driver.quit();
        int i = 1;
        boolean brakes = false;
        LinkedList<String> resultlist = new LinkedList<String>();
        try {
            while (brakes != true) {
                Element notam = doc.body().child(5).child(3).child(0).child(0).child(0).child(0).child(2).child(0).child(i).child(1);
                String notamtext = notam.text();
                notamtext = notamtext.replace("\n", " ");
                Parser p = new Parser(notamtext);
                p.printNotam();
                resultlist.add(notamtext);
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
            brakes = true;
        }
        return resultlist;
    }

    public static void display_Linked_list(LinkedList<String> linkedlist){
        int num = 0;
        while (linkedlist.size() > num) {
            System.out.println(linkedlist.get(num));
            System.out.print("======================================");
            System.out.print("======================================");
            System.out.println("");
            num++;
        }
    }

    public static void main(String[] args) {
        Database_Connection myconnect =  new Database_Layout_Manager();

        //Establish Connection to Database
        myconnect.connect();


        //Disconnect from Database
        LinkedList<String> list = scraper_Chrome_dinsQueryWeb("KLAX",1);
        while(!list.isEmpty()) {
            try {
                Parser p = new Parser(list.pop());
                ((Database_Layout_Manager) myconnect).addEntry(p.getNotamNumber(), p.getAirport(), p.getType(), p.getCoords(), p.getAltitude(), p.getRunway(), p.getWhenInEffect(), p.getCreated(), p.getSource(), p.getRawNotam());
            } catch (Exception e) {

            }
        }

        //Parser p = new Parser(list.get(0));
        //p.printNotam();
        myconnect.disconnect();
}}
