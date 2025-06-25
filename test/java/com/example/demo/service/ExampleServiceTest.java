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
