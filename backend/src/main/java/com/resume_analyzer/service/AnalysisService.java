package com.resume_analyzer.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.resume_analyzer.dto.AnalysisResponse;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class AnalysisService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public AnalysisResponse analyzeResume(String resumeText, String jobDescription) throws Exception {
        String prompt = buildPrompt(resumeText, jobDescription);

        Map<String, Object> requestBody = Map.of(
            "contents", new Object[] {
                Map.of("parts", new Object[] { Map.of("text", prompt) })
            }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String url = apiUrl + "?key=" + apiKey;
        String rawResponse = restTemplate.postForObject(url, entity, String.class);

        return parseGeminiResponse(rawResponse);
    }

    private String buildPrompt(String resumeText, String jobDescription) {
        boolean hasJD = jobDescription != null && !jobDescription.isBlank();

        String jdSection = hasJD
            ? """
              Also compare this resume against the following job description
              and include these ADDITIONAL fields in the JSON:
              - "jdMatchScore": <number 0-100 representing how well the resume matches this specific JD>
              - "jdMissingSkills": [skills/keywords mentioned in the JD but missing from the resume]

              Job Description:
              %s
              """.formatted(jobDescription)
            : """
              Do not include jdMatchScore or jdMissingSkills fields since no job
              description was provided - omit them entirely from the JSON.
              """;

        return """
            You are an ATS (Applicant Tracking System) resume analyzer.
            Analyze the following resume text and respond ONLY with valid JSON,
            no extra text, no markdown formatting, no code blocks.

            Return this JSON structure:
            {
              "atsScore": <number between 0-100>,
              "missingSkills": ["skill1", "skill2", ...],
              "suggestions": ["suggestion1", "suggestion2", ...]
            }

            Rules:
            - atsScore reflects general ATS-optimization (formatting, structure, keyword clarity)
            - missingSkills lists commonly expected skills for this candidate's apparent
              field that are NOT mentioned in the resume
            - suggestions are specific, actionable improvements

            %s

            Resume text:
            %s
            """.formatted(jdSection, resumeText);
    }

    private AnalysisResponse parseGeminiResponse(String rawResponse) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(rawResponse);

        String generatedText = root
                .path("candidates").get(0)
                .path("content")
                .path("parts").get(0)
                .path("text")
                .asString();

        // Gemini sometimes wraps JSON in ```json ... ``` even when told not to - strip it defensively
        String cleanJson = generatedText
                .replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();

        return mapper.readValue(cleanJson, AnalysisResponse.class);
    }
}