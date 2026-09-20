import { Link, useNavigate } from 'react-router-dom';

function Navbar() {
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <div className="navbar">
      <Link to={token ? '/dashboard' : '/login'} className="brand">
        Resume Analyzer
      </Link>
      <div>
        {token ? (
          <>
            <Link to="/profile" style={{ marginRight: '16px' }}>My Profile</Link>
            <button className="btn-secondary" onClick={handleLogout}>Logout</button>
          </>
        ) : (
          <>
            <Link to="/login" style={{ marginRight: '16px' }}>Login</Link>
            <Link to="/signup">Signup</Link>
          </>
        )}
      </div>
    </div>
  );
}

export default Navbar;