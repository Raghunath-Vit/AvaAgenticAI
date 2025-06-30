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
        expectedResponse.setId("1");
        expectedResponse.setName("Example");

        ExampleResponse actualResponse = exampleService.getExample();

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }

    @Test
    public void testCreateExample() {
        ExampleRequest request = new ExampleRequest();
        request.setName("Example");

        ExampleResponse expectedResponse = new ExampleResponse();
        expectedResponse.setId("1");
        expectedResponse.setName("Example");

        when(exampleRepository.save(any(ExampleEntity.class))).thenReturn(new ExampleEntity());

        ExampleResponse actualResponse = exampleService.createExample(request);

        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getName(), actualResponse.getName());
    }
}
