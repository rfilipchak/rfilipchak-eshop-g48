package ua.mainacademy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.mainacademy.service.UserService;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.mainacademy.prototype.UserPrototype.aNewUser;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @MockBean
    private UserService service;
    private UserController controller;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new UserController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void successCreateTest() throws Exception {
        when(service.create(aNewUser().toBuilder().id(null).build())).thenReturn(aNewUser());
        mockMvc.perform(put("/user")
                .content("{\"login\":\"roman123\",\"password\":\"1234\",\"firstName\":\"Roman\",\"lastName\":\"Fill\",\"email\":\"roman@gmail.com\",\"phone\":\"+321781\"}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("roman123"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.firstName").value("Roman"))
                .andExpect(jsonPath("$.lastName").value("Fill"))
                .andExpect(jsonPath("$.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$.phone").value("+321781"));
    }

    @Test
    void badRequestCreateTest() throws Exception {
        when(service.create(aNewUser().toBuilder().id(null).build())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/user")
                .content("{\"login\":\"roman123\",\"password\":\"1234\",\"firstName\":\"Roman\",\"lastName\":\"Fill\",\"email\":\"roman@gmail.com\",\"phone\":\"+321781\"}")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void badRequestForNullCreateTest() throws Exception {
        mockMvc.perform(put("/user")
                .content("")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successUpdateTest() throws Exception {
        when(service.update(aNewUser())).thenReturn(aNewUser());
        mockMvc.perform(post("/user")
                .content("{\"id\":\"1\",\"login\":\"roman123\",\"password\":\"1234\",\"firstName\":\"Roman\",\"lastName\":\"Fill\",\"email\":\"roman@gmail.com\",\"phone\":\"+321781\"}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("roman123"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.firstName").value("Roman"))
                .andExpect(jsonPath("$.lastName").value("Fill"))
                .andExpect(jsonPath("$.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$.phone").value("+321781"));
    }

    @Test
    void badRequestUpdateTest() throws Exception {
        when(service.update(aNewUser())).thenThrow(new RuntimeException());
        mockMvc.perform(post("/user")
                .content("{\"id\":\"1\",\"login\":\"roman123\",\"password\":\"1234\",\"firstName\":\"Roman\",\"lastName\":\"Fill\",\"email\":\"roman@gmail.com\",\"phone\":\"+321781\"}")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void badRequestForNullUpdateTest() throws Exception {
        mockMvc.perform(post("/user")
                .content("")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void successFindOneByIdTest() throws Exception {
        when(service.findOneById(1L)).thenReturn(aNewUser());
        mockMvc.perform(get("/user/1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.login").value("roman123"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.firstName").value("Roman"))
                .andExpect(jsonPath("$.lastName").value("Fill"))
                .andExpect(jsonPath("$.email").value("roman@gmail.com"))
                .andExpect(jsonPath("$.phone").value("+321781"));
    }

    @Test
    void badRequestFindOneById() throws Exception {
        when(service.findOneById(1L)).thenThrow(new RuntimeException());
        mockMvc.perform(get("/user/1")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void badRequestForNullFindOneById() throws Exception {
        Long id = null;
        mockMvc.perform(get("/user/" + id)
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findAllTest() throws Exception {
        when(service.findAll()).thenReturn(List.of(aNewUser()));
        mockMvc.perform(get("/user")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("[0].login").value("roman123"))
                .andExpect(jsonPath("[0].password").value("1234"))
                .andExpect(jsonPath("[0].firstName").value("Roman"))
                .andExpect(jsonPath("[0].lastName").value("Fill"))
                .andExpect(jsonPath("[0].email").value("roman@gmail.com"))
                .andExpect(jsonPath("[0].phone").value("+321781"));
    }

    @Test
    void findAllTestEmptyList() throws Exception {
        when(service.findAll()).thenReturn(emptyList());
        mockMvc.perform(get("/user")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void successDeleteById() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/user/" + id)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }
}