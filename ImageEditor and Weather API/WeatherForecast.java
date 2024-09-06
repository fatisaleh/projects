import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.annotation.processing.Filer;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * class for getting the weather report
 */
class WeatherForecast {
    /**
     * retrieves weather report for the upcoming week
     * @param args is the input arguments
     */

    public static void main(String[] args) {

        try{
            String latitude = "39";
            String longitude = "-86";
            String hourly = "temperature_2m";
            String temperature = "fahrenheit";
            String timezone = "EST";

            String finalURL = "https://api.open-meteo.com/v1/forecast?" + "latitude=" + latitude + "&longitude=" + longitude + "&hourly=" + hourly + "&temperature_unit=" + temperature + "&timezone=" + timezone;

            URL url = new URL(finalURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode()==200){

                InputStream inputStream = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String s;
                StringBuilder sb = new StringBuilder();
                while((s = br.readLine()) != null){
                    sb.append(s);

                }
                JsonElement jsonE = JsonParser.parseString(sb.toString());
                JsonObject jsonRoot = jsonE.getAsJsonObject();
                JsonObject hourlyObject = jsonRoot.getAsJsonObject("hourly");
                JsonArray times = hourlyObject.getAsJsonArray("time");
                JsonArray temperatures = hourlyObject.getAsJsonArray("temperature_2m");

                System.out.println("Bloomington 7-Day Forecast in Fahrenheit:");
                for (int i = 0; i < 7; i++){
                    String t = times.get(i*24).getAsString(); 
                    System.out.println("Forecast for " + t.substring(0,t.indexOf("T"))+":");

                    for(int j = 0; j < 8; j++){
                        String time = times.get(j * 3).getAsString();
                        System.out.println(time.substring(time.indexOf("T")+1) + ": " + temperatures.get(i+ j * 3).getAsDouble() + "°F");
                    }
                }

            }else{
                System.out.println("error");
                throw new IOException();
            }
        }
        catch(IOException e){
            e.getMessage();
        }

        }
}
