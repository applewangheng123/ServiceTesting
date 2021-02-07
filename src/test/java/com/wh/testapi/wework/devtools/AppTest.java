package com.wh.testapi.wework.devtools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void getDefaultRequestSpecification() {
    }

    @Test
    void listApp() {
        App app=new App();
        app.listApp();
    }
}