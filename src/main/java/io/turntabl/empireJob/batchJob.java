package io.turntabl.empireJob;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
public class batchJob {
@Autowired
    JdbcTemplate template;

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
        var values = new HashMap<String, Integer>() {{
            put("endpoint_id", endpoint_id);
            put("project_id", project_id);
            put("status", status);
        }};

        var objectMapper = new ObjectMapper();
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



    @CrossOrigin
    @ApiOperation("Get all Endpoints")
    @GetMapping("/api/v1/statuscode")
    public List<String> viewstatuscode() {
        List<EndpointTO> response  =  this.template.query(
                "select endpoint_url from endpoints",
                new BeanPropertyRowMapper<EndpointTO>(EndpointTO.class)
        );

        response.stream().map(endpoint -> endpoint.getEndpoint_url()).collect(Collectors.toList()).forEach(entry -> {
            getstatus(1, "asdasd",entry, 5);
        });
        return  response.stream().map(endpoint -> endpoint.getEndpoint_url()).collect(Collectors.toList());
    }
}
