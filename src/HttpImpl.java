import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpImpl {


    private static final String USER_AGENT = "Mozilla/5.0";

    public HttpImpl() {
    }


    public SimplePoll sendGET(int id) throws IOException {
        String GET_URL = "http://localhost:8080/polls/" + id;
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            Gson gson = new Gson();
            SimplePoll poll = gson.fromJson(response.toString(), SimplePoll.class);
            System.out.println(poll.getQuestion());
            return poll;
        } else {
            System.out.println("GET request not worked");
        }

        return null;
    }

    public static void sendPOST(int pollId, ArrayList<Answer> votes) throws IOException {
        for(Answer a : votes) {
            if (a != null) {
                String POST_URL = "http://localhost:8080/votes";
                URL obj = new URL(POST_URL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                Gson gson = new Gson();


                // For POST only - START
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();


                String out = "{\"answer\":\"" + a.toString() + "\",\"pollId\":\"" + pollId + "\"}";
                byte[] output = out.getBytes();
                os.write(output);
                os.flush();
                os.close();
                // For POST only - END

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());
                } else {
                    System.out.println("POST request not worked");
                }
            }
        }
    }

}

