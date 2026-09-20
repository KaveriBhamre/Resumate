# 🎵 Resonate

**Make your resume resonate — with recruiters and with ATS systems.**

Resonate is a full-stack AI-powered resume analyzer built for students navigating 
placement season. Upload your resume, get an instant ATS score, discover missing 
skills, and see exactly how well you match a specific job description — all in 
one place.

---

## ✨ Why I built this

Placement season means juggling a dozen resume versions and application 
trackers scattered across spreadsheets and sticky notes. I wanted one tool 
that tells me — honestly — where my resume stands, what's missing, and 
whether it's actually a fit for the role I'm targeting. So I built it.

---

## 🚀 Features

- 🔐 **Secure Auth** — JWT-based signup/login with BCrypt password hashing
- 📄 **Resume Upload** — PDF parsing with automatic text extraction
- 🤖 **AI-Powered Analysis** — ATS score, missing skills, and actionable 
  suggestions via Google Gemini
- 🎯 **Job Description Matching** — paste a JD, get a tailored match score 
  and role-specific skill gaps
- 👤 **User Profiles** — simple account overview
- 🧭 *(In progress)* Application Tracker — track every application from 
  "Applied" to "Offer"

---

## 🛠️ Tech Stack

| Layer | Tech |
|---|---|
| **Backend** | Java 17, Spring Boot, Spring Security (JWT), Spring Data JPA / Hibernate |
| **Database** | MySQL |
| **Frontend** | React (Vite), React Router, Axios |
| **AI** | Google Gemini API |
| **File Parsing** | Apache PDFBox |

---

## 🏗️ Architecture


## 🧠 What I Learned

Building Resonate meant going beyond tutorials — designing a stateless 
JWT auth flow from scratch, engineering prompts for consistent structured 
LLM output, handling multipart file uploads, and debugging real-world 
integration issues. It's a small project, but every piece of it
is something I built, broke, and fixed myself.

---

## 📄 License

Licensed under the [Apache License 2.0](./LICENSE).

---

*Built by [Kaveri Bhamre](https://github.com/KaveriBhamre)*
