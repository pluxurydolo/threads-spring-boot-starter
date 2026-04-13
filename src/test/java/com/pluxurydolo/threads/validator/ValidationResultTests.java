package com.pluxurydolo.threads.validator;

import org.junit.jupiter.api.Test;

import static com.pluxurydolo.threads.validator.ValidationResult.FAILURE;
import static com.pluxurydolo.threads.validator.ValidationResult.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;

class ValidationResultTests {

    @Test
    void testFromBoolean() {
        ValidationResult success = ValidationResult.fromBoolean(true);
        ValidationResult failure = ValidationResult.fromBoolean(false);

        assertThat(success)
            .isEqualTo(SUCCESS);
        assertThat(failure)
            .isEqualTo(FAILURE);
    }
}
