

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.s0pringframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.assertj.core.api.AssertionsForClassTypes;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LizunovaVOTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    long id = 2;

    @BeforeEach
    void printApplicationContext() {
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(name -> applicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void testBlockUser() throws Exception {
        mockMvc.perform(get("/blockUser/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/users/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testLogin() {
        AssertionsForClassTypes
                .assertThat(this.restTemplate.postForEntity("http://localhost:" + 8080 + "/login", new LoginRequestLizunovaVO("vika_ll", "123456"),
                        UserLizunovaVO.class));
    }

    @Test
    public void testRegisterUserAccount() {
        AssertionsForClassTypes
                .assertThat(this.restTemplate.postForEntity("http://localhost:" + 8080 + "/registration", new UserLizunovaVO(0, "", "", false, new RoleLizunovaVO(1, "Администратор"), new ProfileLizunovaVO(0, "", "", "")),
                        UserLizunovaVO.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTasks() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/tasks/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void addTask() throws Exception {
        mockMvc.perform(post("/tasks/").content("{\n" +
                "        \"task_name\": \"Изменить цвет иконки на кнопке \\\"Войти\\\" на главном экране\",\n" +
                "        \"complexity_score\": 1,\n" +
                "        \"modules_count\": 1,\n" +
                "        \"priority_score\": 1,\n" +
                "        \"performance_score\": 20,\n" +
                "        \"requirements_specification_score\": 2,\n" +
                "        \"time\": 5,\n" +
                "        \"developmentArea\": {\n" +
                "            \"development_area_id\": 1,\n" +
                "            \"development_area_name\": \"Mobile\"\n" +
                "        },\n" +
                "        \"status\": {\n" +
                "            \"status_name\": \"Готова\",\n" +
                "            \"status_id\": 1\n" +
                "        },\n" +
                "        \"employee\": {\n" +
                "            \"employee_id\": 1,\n" +
                "            \"employee_name\": \"Виктория \",\n" +
                "            \"employee_second_name\": \"Лизунова\",\n" +
                "            \"employee_last_name\": \"Олеговна\",\n" +
                "            \"position\": {\n" +
                "                \"position_id\": 1,\n" +
                "                \"position_name\": \"IOS\",\n" +
                "                \"department\": {\n" +
                "                    \"department_id\": 1,\n" +
                "                    \"department_name\": \"Mobile\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"project\": {\n" +
                "            \"project_id\": 1,\n" +
                "            \"project_name\": \"TipRanks\"\n" +
                "        }\n" +
                "    }"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void removeTask() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete("/tasks/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void editTask() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/tasks/" + id).content("{\n" +
                "        \"task_name\": \"Изменить цвет иконки на кнопке \\\"Войти\\\" на главном экране\",\n" +
                "        \"complexity_score\": 1,\n" +
                "        \"modules_count\": 1,\n" +
                "        \"priority_score\": 1,\n" +
                "        \"performance_score\": 20,\n" +
                "        \"requirements_specification_score\": 2,\n" +
                "        \"time\": 5,\n" +
                "        \"developmentArea\": {\n" +
                "            \"development_area_id\": 1,\n" +
                "            \"development_area_name\": \"Mobile\"\n" +
                "        },\n" +
                "        \"status\": {\n" +
                "            \"status_name\": \"Готова\",\n" +
                "            \"status_id\": 1\n" +
                "        },\n" +
                "        \"employee\": {\n" +
                "            \"employee_id\": 1,\n" +
                "            \"employee_name\": \"Виктория \",\n" +
                "            \"employee_second_name\": \"Лизунова\",\n" +
                "            \"employee_last_name\": \"Олеговна\",\n" +
                "            \"position\": {\n" +
                "                \"position_id\": 1,\n" +
                "                \"position_name\": \"IOS\",\n" +
                "                \"department\": {\n" +
                "                    \"department_id\": 1,\n" +
                "                    \"department_name\": \"Mobile\"\n" +
                "                }\n" +
                "            }\n" +
                "        },\n" +
                "        \"project\": {\n" +
                "            \"project_id\": 1,\n" +
                "            \"project_name\": \"TipRanks\"\n" +
                "        }\n" +
                "    }"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEmployees() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/employees/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testChangeEmployees() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/employees/" + id).content("{\n" +
                "        \"employee_id\": 1,\n" +
                "        \"employee_name\": \"Викторияccccc\",\n" +
                "        \"employee_second_name\": \"Лизунова\",\n" +
                "        \"employee_last_name\": \"Олеговна\",\n" +
                "        \"position\": {\n" +
                "            \"position_id\": 1,\n" +
                "            \"position_name\": \"IOS\",\n" +
                "            \"department\": {\n" +
                "                \"department_id\": 1,\n" +
                "                \"department_name\": \"Mobile\"\n" +
                "            }\n" +
                "        }\n" +
                "    }"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/employees/" + id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }
}

class LoginRequestLizunovaVO implements Serializable {
    private String login;
    private String password;

    public LoginRequestLizunovaVO(String login, String password) {
        this.login = login;
        this.password = password;
    }
}

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Retention(RetentionPolicy.RUNTIME)
@interface MockMvcTest {}