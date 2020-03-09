package io.turntabl.empireJob;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api
@RestController
public class batchJob {
@Autowired
    JdbcTemplate template;

    @CrossOrigin
    @ApiOperation("Get all Endpoints")
    @GetMapping("/api/v1/statuscode")
    public List<String> viewstatuscode() {
        List<EndpointTO> response  =  this.template.query(
                "select endpoint_url from endpoints",
                new BeanPropertyRowMapper<EndpointTO>(EndpointTO.class)
        );
        response.stream().map(endpoint -> endpoint.getEndpoint_url()).collect(Collectors.toList()).forEach(entry -> {
           jobProcess.getstatus(1, "https://timeentry002.herokuapp.com/v1/api/getloggedsick",entry, 5);
        });
        return  response.stream().map(endpoint -> endpoint.getEndpoint_url()).collect(Collectors.toList());
    }
}
