/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblockz.portalbot.urlshorteners;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by portalBlock on 11/27/2014.
 */
public class GooGl extends URLShortener {

    @Override
    public String shorten(String url) {
        StringBuilder response = new StringBuilder();
        try{
            URL req = new URL("https://www.googleapis.com/urlshortener/v1/url");

            HttpsURLConnection con = (HttpsURLConnection) req.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("{\"longUrl\": \"" + url + "\"}");
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject res = new JSONObject(response.toString());
            if(res.optString("id") != null) return res.getString("id");

        }catch (Exception ignored){
            ignored.printStackTrace();
            System.out.print(response.toString());
        }
        return null;
    }
}
