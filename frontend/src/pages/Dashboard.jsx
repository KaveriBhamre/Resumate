import { Link } from 'react-router-dom';

function Dashboard() {
  const name = localStorage.getItem('name');

  return (
    <div className="container">
      <h2>Welcome, {name} 👋</h2>

      <div className="card">
        <h3>AI Resume Analyzer</h3>
        <p style={{ color: 'var(--text-light)' }}>
          <span className="step-badge">1</span> Upload Resume
          <span style={{ margin: '0 10px' }}>→</span>
          <span className="step-badge">2</span> Add Job Description
          <span style={{ margin: '0 10px' }}>→</span>
          <span className="step-badge">3</span> Analyze
        </p>
        <Link to="/resume-upload">
          <button>Get Started</button>
        </Link>
      </div>

      <div className="card">
        <h3>My Profile</h3>
        <p style={{ color: 'var(--text-light)' }}>View your account details</p>
        <Link to="/profile">
          <button className="btn-secondary">View Profile</button>
        </Link>
      </div>
    </div>
  );
}

export default Dashboard;