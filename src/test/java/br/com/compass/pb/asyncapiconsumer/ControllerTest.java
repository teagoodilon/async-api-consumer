package br.com.compass.pb.asyncapiconsumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test get Ok")
    void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test insert all posts")
    void testInsertPosts() throws Exception {
        for (int id = 1; id <= 100; id++) {
            mockMvc.perform(MockMvcRequestBuilders.post("/posts/" + id))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }

    @Test
    @DisplayName("Test insert out of index post")
    void testInsertFailedPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts/" + 101))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    @DisplayName("Test delete out of index post")
    void testDeleteFailedPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/" + 101))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    @DisplayName("Test update out of index post")
    void testUpdateFailedPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/posts/" + 101))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }
}
