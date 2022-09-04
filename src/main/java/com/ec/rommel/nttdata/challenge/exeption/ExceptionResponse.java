package com.ec.rommel.nttdata.challenge.exeption;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private String message;
}
