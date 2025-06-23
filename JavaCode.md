```java
// Java Spring Boot code generated based on the provided LLD

// Step 1: Define the main application class
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// Step 2: Define the Controller class
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/data")
    public ResponseEntity<List<Data>> getData() {
        List<Data> data = apiService.getData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/data")
    public ResponseEntity<Data> createData(@RequestBody Data data) {
        Data createdData = apiService.createData(data);
        return new ResponseEntity<>(createdData, HttpStatus.CREATED);
    }
}

// Step 3: Define the Service class
@Service
public class ApiService {

    @Autowired
    private DataRepository dataRepository;

    public List<Data> getData() {
        return dataRepository.findAll();
    }

    public Data createData(Data data) {
        return dataRepository.save(data);
    }
}

// Step 4: Define the Repository interface
@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
}

// Step 5: Define the Data entity class
@Entity
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    // Getters and Setters
}

// Step 6: Define the Exception handling class
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// Step 7: Define the application properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

// Code Creation Completed
```