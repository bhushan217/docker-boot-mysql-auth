package in.b2k.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.b2k.request.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
/*
    public HttpRequestTest(int port, TestRestTemplate restTemplate) {
        this.port = port;
        this.restTemplate = restTemplate;
    }*/

    final String loginPath = "/public/users/login";
    final UserVO userAdmin = UserVO.builder().name("amit").username("amit").password("bhushan").build();

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        final var baseURL = "http://localhost:" + port ;
        log.info(baseURL);
        HttpEntity<String> request = new HttpEntity<String>(getJSON_userAmit(), getHttpHeaders_APPJSON());
        assertThat(this.restTemplate
                .withBasicAuth("amit", "bhushan")
                .postForObject(baseURL + loginPath, request,String.class))
                .toString()
        ;
    }

    private String getJSON_userAmit() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        final var userAmit = objectMapper.writeValueAsString(userAdmin);
        return userAmit;
    }

    private HttpHeaders getHttpHeaders_APPJSON() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
