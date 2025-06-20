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
```
