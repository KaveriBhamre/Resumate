package com.resume_analyzer.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.resume_analyzer.dto.AnalysisResponse;
import com.resume_analyzer.dto.AnalyzeRequest;
import com.resume_analyzer.dto.ResumeResponse;
import com.resume_analyzer.entity.Resume;
import com.resume_analyzer.repository.ResumeRepository;
import com.resume_analyzer.service.AnalysisService;
import com.resume_analyzer.service.ResumeService;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {
	
	@Autowired
	private AnalysisService analysisService;

	@Autowired
	private ResumeRepository resumeRepository;

	@PostMapping("/{id}/analyze")
	public AnalysisResponse analyzeResume(@PathVariable Long id,
	                                       @RequestBody(required = false) AnalyzeRequest request) throws Exception {
	    Resume resume = resumeRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Resume not found"));

	    String jd = (request != null) ? request.getJobDescription() : null;

	    return analysisService.analyzeResume(resume.getExtractedText(), jd);
	}

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResumeResponse uploadResume(@RequestParam("file") MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();

        return resumeService.uploadResume(file, userEmail);
    }
}