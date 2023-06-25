package com.example.timelist.error;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
    String error;
    String code;
    String massage;
    String invalidValue;
    String field;
}
