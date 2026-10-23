package service;

import org.junit.jupiter.api.Test;
import result.ClearResult;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    private static final ClearService clearService = new ClearService();

    @Test
    void clearSuccess() throws BadRequestException {
        assertEquals(new ClearResult(), clearService.clear());
    }
}