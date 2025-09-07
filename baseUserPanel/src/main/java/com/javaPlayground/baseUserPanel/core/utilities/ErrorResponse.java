package com.javaPlayground.baseUserPanel.core.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String title;
    private String detail;
    private int status;
    private LocalDateTime timestamp;
}
