# JUnit Test Cases

## ApiControllerTest

```java
@RunWith(SpringRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiService apiService;

    @Test
    public void testGetData() throws Exception {
        List<Data> dataList = Arrays.asList(new Data(1L, "Test Data"));
        Mockito.when(apiService.getData()).thenReturn(dataList);

        mockMvc.perform(get("/api/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Data"));
    }

    @Test
    public void testCreateData() throws Exception {
        Data data = new Data(1L, "Test Data");
        Mockito.when(apiService.createData(Mockito.any(Data.class))).thenReturn(data);

        mockMvc.perform(post("/api/data")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Data\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Data"));
    }
}
```

## ApiServiceTest

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceTest {

    @Autowired
    private ApiService apiService;

    @MockBean
    private DataRepository dataRepository;

    @Test
    public void testGetData() {
        List<Data> dataList = Arrays.asList(new Data(1L, "Test Data"));
        Mockito.when(dataRepository.findAll()).thenReturn(dataList);

        List<Data> result = apiService.getData();
        assertEquals(1, result.size());
        assertEquals("Test Data", result.get(0).getName());
    }

    @Test
    public void testCreateData() {
        Data data = new Data(1L, "Test Data");
        Mockito.when(dataRepository.save(Mockito.any(Data.class))).thenReturn(data);

        Data result = apiService.createData(data);
        assertEquals("Test Data", result.getName());
    }
}
```

## GlobalExceptionHandlerTest

```java
@RunWith(SpringRunner.class)
@WebMvcTest
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHandleException() throws Exception {
        mockMvc.perform(get("/api/invalid"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("Internal Server Error")));
    }
}
```