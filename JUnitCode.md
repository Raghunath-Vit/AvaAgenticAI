# JUnit Test Cases for Java Spring Boot Project

## ApiControllerTest

```java
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(ApiController.class)
@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiService apiService;

    @InjectMocks
    private ApiController apiController;

    @Test
    public void testGetData() throws Exception {
        Data data1 = new Data();
        data1.setId(1L);
        data1.setName("Data1");

        Data data2 = new Data();
        data2.setId(2L);
        data2.setName("Data2");

        when(apiService.getData()).thenReturn(Arrays.asList(data1, data2));

        mockMvc.perform(get("/api/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Data1"))
                .andExpect(jsonPath("$[1].name").value("Data2"));
    }

    @Test
    public void testCreateData() throws Exception {
        Data data = new Data();
        data.setId(1L);
        data.setName("Data1");

        when(apiService.createData(data)).thenReturn(data);

        mockMvc.perform(post("/api/data")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{"name":"Data1"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Data1"));
    }
}
```

## ApiServiceTest

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ApiServiceTest {

    @Mock
    private DataRepository dataRepository;

    @InjectMocks
    private ApiService apiService;

    @Test
    public void testGetData() {
        Data data1 = new Data();
        data1.setId(1L);
        data1.setName("Data1");

        Data data2 = new Data();
        data2.setId(2L);
        data2.setName("Data2");

        when(dataRepository.findAll()).thenReturn(Arrays.asList(data1, data2));

        List<Data> dataList = apiService.getData();
        assertEquals(2, dataList.size());
        assertEquals("Data1", dataList.get(0).getName());
        assertEquals("Data2", dataList.get(1).getName());
    }

    @Test
    public void testCreateData() {
        Data data = new Data();
        data.setId(1L);
        data.setName("Data1");

        when(dataRepository.save(data)).thenReturn(data);

        Data createdData = apiService.createData(data);
        assertEquals("Data1", createdData.getName());
    }
}
```