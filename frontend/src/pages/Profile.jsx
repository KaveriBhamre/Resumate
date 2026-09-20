function Profile() {
  const name = localStorage.getItem('name');
  const email = localStorage.getItem('email');

  return (
    <div className="container">
      <h2>My Profile</h2>
      <div className="card">
        <p><strong>Name:</strong> {name}</p>
        <p><strong>Email:</strong> {email}</p>
      </div>
    </div>
  );
}

export default Profile;