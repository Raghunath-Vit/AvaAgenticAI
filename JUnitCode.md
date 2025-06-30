# JUnit Test Cases

## ExampleControllerTest.java
```java
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(ExampleController.class)
public class ExampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExampleService exampleService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ExampleController()).build();
    }

    @Test
    public void testGetExample() throws Exception {
        ExampleResponse response = new ExampleResponse();
        response.setId('1');
        response.setName('Example');

        when(exampleService.getExample()).thenReturn(response);

        mockMvc.perform(get('/api/v1/example'))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateExample() throws Exception {
        ExampleRequest request = new ExampleRequest();
        request.setName('Example');

        ExampleResponse response = new ExampleResponse();
        response.setId('1');
        response.setName('Example');

        when(exampleService.createExample(request)).thenReturn(response);

        mockMvc.perform(post('/api/v1/example')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
```

## ExampleServiceTest.java
```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ExampleServiceTest {

    @InjectMocks
    private ExampleService exampleService;

    @Mock
    private ExampleRepository exampleRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetExample() {
        ExampleResponse expectedResponse = new ExampleResponse();
        expectedResponse.setId('1');
        expectedResponse.setName('Example');

        ExampleResponse actualResponse = exampleService.getExample();

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test
    public void testCreateExample() {
        ExampleRequest request = new ExampleRequest();
        request.setName('Example');

        ExampleResponse expectedResponse = new ExampleResponse();
        expectedResponse.setId('1');
        expectedResponse.setName('Example');

        when(exampleRepository.save(any(ExampleEntity.class))).thenReturn(new ExampleEntity());

        ExampleResponse actualResponse = exampleService.createExample(request);

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }
}
```