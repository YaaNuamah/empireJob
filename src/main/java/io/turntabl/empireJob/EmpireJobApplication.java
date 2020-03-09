package io.turntabl.empireJob;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@EnableSwagger2
@SpringBootApplication
public class EmpireJobApplication {
	@Autowired
	JdbcTemplate template;

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		String endpoints_location = System.getenv("GET_ENDPOINTS_LIST_URL=");
		HttpClient client = HttpClient.newBuilder().build();
		URI url;
		url = new URI(endpoints_location);

		HttpRequest request = HttpRequest.newBuilder(url).build();
		HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString(Charset.defaultCharset()));

		parseJson(res.body()).stream().forEach(e -> {
			jobProcess.getstatus(e.getProject_id(), e.getEndpoint_url(), e.getRequest_method(), e.getEndpoint_id());

		});
	}

	public static List<EndpointTO> parseJson(String json) throws IOException {
		ObjectMapper map = new ObjectMapper();
		List<EndpointTO> myObjects = Arrays.asList(map.readValue(json, EndpointTO[].class));
		return myObjects;

	}
}
