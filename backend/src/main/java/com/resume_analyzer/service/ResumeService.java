package com.resume_analyzer.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.resume_analyzer.dto.ResumeResponse;
import com.resume_analyzer.entity.Resume;
import com.resume_analyzer.entity.User;
import com.resume_analyzer.repository.ResumeRepository;
import com.resume_analyzer.repository.UserRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    public ResumeResponse uploadResume(MultipartFile file, String userEmail) throws IOException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String extractedText = extractText(file);

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setExtractedText(extractedText);
        resume.setUploadedAt(LocalDateTime.now());
        resume.setUser(user);

        resumeRepository.save(resume);

        return new ResumeResponse(resume.getId(), resume.getFileName(), resume.getExtractedText());
    }

    private String extractText(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}