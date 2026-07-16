package com.example.reentry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReEntryApplicationTests {

    @Autowired
    private org.springframework.context.ApplicationContext context;

    @Test
    void shouldLoadApplicationContext() {
        Assertions.assertNotNull(context, "The application context should have loaded.");
    }
}
