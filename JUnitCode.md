# JUnit Test Cases for Java Spring Boot Application

## SampleControllerTest.java

```java
package com.example.demo.controller;

import com.example.demo.service.SampleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SampleControllerTest {

    @InjectMocks
    private SampleController sampleController;

    @Mock
    private SampleService sampleService;

    public SampleControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSampleData() {
        String expectedData = "Sample Data";
        when(sampleService.getSampleData()).thenReturn(expectedData);

        ResponseEntity<String> response = sampleController.getSampleData();
        assertEquals(expectedData, response.getBody());
    }

    @Test
    public void testGetSampleData_NotFound() {
        when(sampleService.getSampleData()).thenReturn(null);

        ResponseEntity<String> response = sampleController.getSampleData();
        assertEquals(404, response.getStatusCodeValue());
    }
}
```

## SampleServiceTest.java

```java
package com.example.demo.service;

import com.example.demo.repository.SampleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class SampleServiceTest {

    @InjectMocks
    private SampleService sampleService;

    @Mock
    private SampleRepository sampleRepository;

    public SampleServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSampleData() {
        String expectedData = "Sample Data";
        when(sampleRepository.findSampleData()).thenReturn(expectedData);

        String actualData = sampleService.getSampleData();
        assertEquals(expectedData, actualData);
    }

    @Test
    public void testGetSampleData_NotFound() {
        when(sampleRepository.findSampleData()).thenReturn(null);

        String actualData = sampleService.getSampleData();
        assertEquals(null, actualData);
    }
}
```

## SampleRepositoryTest.java

```java
package com.example.demo.repository;

import com.example.demo.entity.SampleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class SampleRepositoryTest {

    @Autowired
    private SampleRepository sampleRepository;

    @Test
    public void testFindSampleData() {
        SampleEntity sampleEntity = new SampleEntity();
        sampleEntity.setData("Sample Data");
        sampleRepository.save(sampleEntity);

        Optional<SampleEntity> result = sampleRepository.findById(sampleEntity.getId());
        assertEquals("Sample Data", result.get().getData());
    }
}
```

## GlobalExceptionHandlerTest.java

```java
package com.example.demo.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleNotFoundException() {
        ResponseEntity<String> response = globalExceptionHandler.handleNotFoundException(new RuntimeException("Not Found"));
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not Found", response.getBody());
    }

    @Test
    public void testHandleInternalServerError() {
        ResponseEntity<String> response = globalExceptionHandler.handleInternalServerError(new RuntimeException("Internal Server Error"));
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Internal Server Error", response.getBody());
    }
}
```

## AppConfigTest.java

```java
package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppConfigTest {

    @Test
    public void testAppConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        assertNotNull(context.getBean("sampleBean"));
        context.close();
    }
}
```

## LoggingComponentTest.java

```java
package com.example.demo.logging;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LoggingComponentTest {

    @InjectMocks
    private LoggingComponent loggingComponent;

    public LoggingComponentTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogMessage() {
        assertDoesNotThrow(() -> loggingComponent.logMessage("Test Message"));
    }
}
```

## SamplePropertiesTest.java

```java
package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EnableConfigurationProperties(SampleProperties.class)
public class SamplePropertiesTest {

    @Test
    public void testSampleProperties() {
        SampleProperties sampleProperties = new SampleProperties();
        sampleProperties.setProperty("Test Property");

        assertEquals("Test Property", sampleProperties.getProperty());
    }
}
```

## SecurityConfigTest.java

```java
package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SecurityConfigTest extends WebSecurityConfigurerAdapter {

    @Test
    public void testConfigure() {
        SecurityConfig securityConfig = new SecurityConfig();
        assertDoesNotThrow(() -> securityConfig.configure(new HttpSecurity()));
    }
}
```

## IntegrationControllerTest.java

```java
package com.example.demo.controller;

import com.example.demo.service.IntegrationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class IntegrationControllerTest {

    @InjectMocks
    private IntegrationController integrationController;

    @Mock
    private IntegrationService integrationService;

    public IntegrationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetIntegrationData() {
        String expectedData = "Integration Data";
        when(integrationService.getIntegrationData()).thenReturn(expectedData);

        ResponseEntity<String> response = integrationController.getIntegrationData();
        assertEquals(expectedData, response.getBody());
    }

    @Test
    public void testGetIntegrationData_NotFound() {
        when(integrationService.getIntegrationData()).thenReturn(null);

        ResponseEntity<String> response = integrationController.getIntegrationData();
        assertEquals(404, response.getStatusCodeValue());
    }
}
```