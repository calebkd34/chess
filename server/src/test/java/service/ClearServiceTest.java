package service;

import org.junit.jupiter.api.Test;
import result.ClearResult;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    private static final ClearService CLEAR_SERVICE = new ClearService();

    @Test
    void clearSuccess() {
        assertEquals(new ClearResult(), CLEAR_SERVICE.clear());
    }
}