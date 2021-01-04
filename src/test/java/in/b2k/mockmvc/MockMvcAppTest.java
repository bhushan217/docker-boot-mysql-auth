package in.b2k.mockmvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.b2k.request.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class MockMvcAppTest {
    @LocalServerPort
    private int port;
    @Autowired
    public MockMvc mockMvc;


    final String loginPath = "/public/users/login";
    final String registerPath = "/public/users/register";
    final String baseURL = "http://localhost:" + port ;
    final UserVO userAdmin = UserVO.builder().name("Amit").username("amit").password("bhushan").build();

    @Test
    void A_register() throws Exception {
        log.info(baseURL);
        HttpEntity<String> request = new HttpEntity<String>(getJSON_userAmit(), getHttpHeaders_APPJSON());
        this.mockMvc.perform(post(baseURL+registerPath)
                .headers(getHttpHeaders_APPJSON())
                .content(getJSON_userAmit())
        ).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void B_login() throws Exception {
        log.info(baseURL);
        this.mockMvc.perform(post(baseURL+loginPath)
                .headers(getHttpHeaders_APPJSON())
                .content(getJSON_userAmit())
        ).andDo(print()).andExpect(status().isOk());
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
