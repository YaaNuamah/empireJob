package io.turntabl.empireJob;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class jobProcess {
    public static void getstatus(Integer project_id,String url, String method, Integer endpoint_id){

        String result = "";
        int code;
        try{
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(3000);
            connection.connect();

            code = connection.getResponseCode();
            postData(endpoint_id, project_id, code);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void  postData(Integer endpoint_id, Integer project_id, Integer status ) throws IOException, InterruptedException {
        Map<String, Integer> values = new HashMap<String, Integer>() {{
            put("endpoint_id", endpoint_id);
            put("project_id", project_id);
            put("status", status);
        }};

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://192.168.8.122:8050/api/v1/addStatus"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

}
