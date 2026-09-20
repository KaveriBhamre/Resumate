package com.resume_analyzer.dto;

import java.util.List;

import lombok.Data;

@Data
public class AnalysisResponse {
    private int atsScore;
    private List<String> missingSkills;
    private List<String> suggestions;
    
    private Integer jdMatchScore;
    private List<String> jdMissingSkills;
}