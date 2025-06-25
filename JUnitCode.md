### ExampleControllerTest.java
```java
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
                .content("{"name":"Example"}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Example"));
    }
}
```

### ExampleServiceTest.java
```java
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExampleServiceTest {

    @InjectMocks
    private ExampleService exampleService;

    @Mock
    private ExampleRepository exampleRepository;

    @Test
    public void testGetExample() {
        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setId(1L);
        exampleEntity.setName("Example");

        when(exampleRepository.findById(1L)).thenReturn(Optional.of(exampleEntity));

        ExampleResponse response = exampleService.getExample();

        assertEquals("1", response.getId());
        assertEquals("Example", response.getName());
    }

    @Test
    public void testCreateExample() {
        ExampleRequest request = new ExampleRequest();
        request.setName("Example");

        ExampleEntity exampleEntity = new ExampleEntity();
        exampleEntity.setName("Example");

        when(exampleRepository.save(exampleEntity)).thenReturn(exampleEntity);

        ExampleResponse response = exampleService.createExample(request);

        assertEquals("Example", response.getName());
    }
}
```