import { useState } from 'react';
import api from '../api/axiosConfig';

function ResumeUpload() {
  const [file, setFile] = useState(null);
  const [jobDescription, setJobDescription] = useState('');
  const [uploading, setUploading] = useState(false);
  const [analyzing, setAnalyzing] = useState(false);
  const [error, setError] = useState('');
  const [resumeId, setResumeId] = useState(null);
  const [analysis, setAnalysis] = useState(null);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setAnalysis(null);
    setError('');
  };

  const handleUpload = async (e) => {
    e.preventDefault();
    if (!file) {
      setError('Please select a PDF file first');
      return;
    }

    setUploading(true);
    setError('');

    const formData = new FormData();
    formData.append('file', file);

    try {
      const response = await api.post('/resume/upload', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
      });
      setResumeId(response.data.id);
    } catch (err) {
      setError("Upload failed. Make sure it's a valid PDF.");
    } finally {
      setUploading(false);
    }
  };

  const handleAnalyze = async () => {
    if (!resumeId) return;

    setAnalyzing(true);
    setError('');

    try {
      const response = await api.post(`/resume/${resumeId}/analyze`, {
        jobDescription: jobDescription.trim() || null,
      });
      setAnalysis(response.data);
    } catch (err) {
      setError('Analysis failed. Please try again.');
    } finally {
      setAnalyzing(false);
    }
  };

  return (
    <div className="container">
      <h2>Resume Analyzer</h2>

      {/* Step 1: Upload */}
      <div className="card">
        <h3><span className="step-badge">1</span>Upload Resume</h3>
        <form onSubmit={handleUpload}>
          <input type="file" accept=".pdf" onChange={handleFileChange} />
          <button type="submit" disabled={uploading}>
            {uploading ? 'Uploading...' : 'Upload'}
          </button>
        </form>
        {resumeId && <p style={{ color: 'var(--success)' }}>✓ Resume uploaded (ID: {resumeId})</p>}
      </div>

      {/* Step 2: Job Description (optional) */}
      {resumeId && (
        <div className="card">
          <h3><span className="step-badge">2</span>Add Job Description (optional)</h3>
          <textarea
            placeholder="Paste the job description here to get a tailored match score..."
            value={jobDescription}
            onChange={(e) => setJobDescription(e.target.value)}
          />
        </div>
      )}

      {/* Step 3: Analyze */}
      {resumeId && (
        <div className="card">
          <h3><span className="step-badge">3</span>Analyze</h3>
          <button onClick={handleAnalyze} disabled={analyzing}>
            {analyzing ? 'Analyzing...' : 'Analyze Resume'}
          </button>
        </div>
      )}

      {error && <p className="error-text">{error}</p>}

      {/* Results */}
      {analysis && (
        <div className="card">
          <div className="score-box">
            <h2>{analysis.atsScore}/100</h2>
            <p>ATS Score</p>
          </div>

          {analysis.jdMatchScore != null && (
            <div className="score-box">
              <h2>{analysis.jdMatchScore}/100</h2>
              <p>Job Description Match</p>
            </div>
          )}

          <h4>Missing Skills</h4>
          <ul>
            {analysis.missingSkills.map((skill, i) => <li key={i}>{skill}</li>)}
          </ul>

          {analysis.jdMissingSkills && analysis.jdMissingSkills.length > 0 && (
            <>
              <h4>Missing Skills (from Job Description)</h4>
              <ul>
                {analysis.jdMissingSkills.map((skill, i) => <li key={i}>{skill}</li>)}
              </ul>
            </>
          )}

          <h4>Suggestions</h4>
          <ul>
            {analysis.suggestions.map((s, i) => <li key={i}>{s}</li>)}
          </ul>
        </div>
      )}
    </div>
  );
}

export default ResumeUpload;