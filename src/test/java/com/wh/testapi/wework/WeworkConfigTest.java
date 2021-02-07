package com.wh.testapi.wework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeworkConfigTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void load() {
        WeworkConfig.load("");
    }

    @Test
    void getInstance() {
        WeworkConfig.getInstance();
    }
}