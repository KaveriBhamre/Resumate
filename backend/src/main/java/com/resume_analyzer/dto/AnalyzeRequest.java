package com.resume_analyzer.dto;

import lombok.Data;

@Data
public class AnalyzeRequest {
    private String jobDescription; // optional - can be null/empty
}