package io.turntabl.empireJob;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
public class batchJob {
@Autowired
    JdbcTemplate template;

    public  String getstatus(String url){

        String result = "";
        int code = 200;
        try{
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();

            code = connection.getResponseCode();
            if (code == 200) {

                System.out.println("green \t" + url);

            }
            else {
                System.out.println("red \t" + url);

            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  result;
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
            getstatus(entry);
        });
        return  response.stream().map(endpoint -> endpoint.getEndpoint_url()).collect(Collectors.toList());
    }
}
