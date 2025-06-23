# Generated Java Springboot Code
```java
@RestController
@RequestMapping("/api")
public class SampleController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello, World!");
    }
}

@Service
public class SampleService {

    public String getGreeting() {
        return "Hello, World!";
    }
}

@Entity
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Getters and Setters
}

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, Long> {
}

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

@Slf4j
@Component
public class LoggingComponent {

    public void logInfo(String message) {
        log.info(message);
    }
}

@ConfigurationProperties(prefix = "sample")
public class SampleProperties {

    private String property;

    // Getters and Setters
}

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }
}

@RestController
@RequestMapping("/api/integration")
public class IntegrationController {

    private final RestTemplate restTemplate;

    public IntegrationController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/external")
    public ResponseEntity<String> callExternalService() {
        String response = restTemplate.getForObject("https://external-service/api", String.class);
        return ResponseEntity.ok(response);
    }
}

// Code Creation Completed
```