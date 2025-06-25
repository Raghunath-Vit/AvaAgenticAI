import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(ExampleController.class)
public class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExampleService exampleService;

    @Test
    public void testGetExample() throws Exception {
        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId("1");
        exampleResponse.setName("Example");

        when(exampleService.getExample()).thenReturn(exampleResponse);

        mockMvc.perform(get("/api/v1/example"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Example"));
    }

    @Test
    public void testCreateExample() throws Exception {
        ExampleRequest exampleRequest = new ExampleRequest();
        exampleRequest.setName("Example");

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId("1");
        exampleResponse.setName("Example");

        when(exampleService.createExample(exampleRequest)).thenReturn(exampleResponse);

        mockMvc.perform(post("/api/v1/example")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Example\"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Example"));
    }
}
