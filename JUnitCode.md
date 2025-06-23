# JUnit Test Cases

## ApiControllerTest

```java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;

// Test cases for ApiController class
public class ApiControllerTest {

    @InjectMocks
    private ApiController apiController;

    @Mock
    private ApiService apiService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetData() {
        List<String> mockData = Arrays.asList("data1", "data2");
        when(apiService.getData()).thenReturn(mockData);

        ResponseEntity<List<String>> response = apiController.getData();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockData, response.getBody());
    }

    @Test
    public void testCreateData() {
        String newData = "newData";
        when(apiService.createData(newData)).thenReturn(newData);

        ResponseEntity<String> response = apiController.createData(newData);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newData, response.getBody());
    }
}
```

## ApiServiceTest

```java
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

// Test cases for ApiService class
public class ApiServiceTest {

    private ApiService apiService;

    @BeforeEach
    public void setUp() {
        apiService = new ApiService();
    }

    @Test
    public void testGetData() {
        List<String> data = apiService.getData();
        assertNotNull(data);
        assertTrue(data.size() > 0);
    }

    @Test
    public void testCreateData() {
        String newData = "newData";
        String result = apiService.createData(newData);
        assertEquals(newData, result);
    }
}
```