<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notifications - UniExam Portal</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <!-- Sidebar Layout -->
    <nav id="sidebar">
        <div class="sidebar-header">
            <i class="fas fa-university"></i> UniExam Portal
        </div>

        <ul class="list-unstyled components">
            <p class="text-center mb-0 border-bottom pb-3"><small>Main Navigation</small></p>
            <li><a href="dashboard.html"><i class="fas fa-tachometer-alt"></i> Dashboard</a></li>
            <li><a href="users.html"><i class="fas fa-users"></i> Users</a></li>
            <li><a href="exams.html"><i class="fas fa-file-alt"></i> Exams</a></li>
            <li><a href="questions.html"><i class="fas fa-question-circle"></i> Questions</a></li>
            <li><a href="exam-session.html"><i class="fas fa-laptop-code"></i> Exam Sessions</a></li>
            <li><a href="results.html"><i class="fas fa-chart-bar"></i> Results</a></li>
            <li><a href="/feedback"><i class="fas fa-comments"></i> Feedback</a></li>
            <li class="active"><a href="/notifications"><i class="fas fa-bell"></i> Notifications</a></li>
        </ul>

        <ul class="list-unstyled mt-auto mb-4 w-100 position-absolute bottom-0">
            <li><a href="login.html" class="text-danger"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
        </ul>
    </nav>

    <!-- Main Content -->
    <div id="content">
        <!-- Top Navbar -->
        <nav class="top-navbar">
            <button type="button" id="sidebarCollapse" class="navbar-btn">
                <i class="fas fa-bars"></i>
            </button>
            <div class="user-profile-nav">
                <span class="d-none d-md-block text-muted">Welcome, <strong>Admin User</strong></span>
                <div class="avatar">A</div>
            </div>
        </nav>

        <!-- Page Content -->
        <div class="page-content">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="h3 mb-0 text-gray-800">Announcement Board</h2>
                <button class="btn btn-primary shadow-sm" data-bs-toggle="modal" data-bs-target="#notificationModal">
                    <i class="fas fa-bullhorn fa-sm text-white-50"></i> Create Announcement
                </button>
            </div>

            <!-- Notifications List -->
            <div class="card shadow-sm mb-4 border-0">
                <div class="card-body p-0">
                        <c:forEach items="${notificationList}" var="notif">
                            <div class="list-group-item list-group-item-action p-4 ${notif.status == 'ACTIVE' ? 'bg-success bg-opacity-10' : 'bg-secondary bg-opacity-10'}">
                                <div class="d-flex w-100 justify-content-between align-items-center mb-2">
                                    <h5 class="mb-0">
                                        <i class="fas fa-info-circle me-2 ${notif.status == 'ACTIVE' ? 'text-success' : 'text-secondary'}"></i>
                                        ${notif.title}
                                    </h5>
                                    <small class="text-muted"><i class="far fa-clock me-1"></i>${notif.createdDate}</small>
                                </div>
                                <p class="mb-1 text-secondary ms-4">${notif.message}</p>
                                <div class="text-end mt-2">
                                    <span class="badge bg-primary me-2">To: ${notif.targetRole}</span>
                                    <span class="badge ${notif.status == 'ACTIVE' ? 'bg-success' : 'bg-secondary'} me-3">${notif.status}</span>
                                    <c:if test="${notif.status == 'ACTIVE'}">
                                        <a href="/notifications/expire/${notif.notificationId}" class="btn btn-sm btn-outline-warning"><i class="fas fa-ban"></i> Expire</a>
                                    </c:if>
                                    <a href="/notifications/delete/${notif.notificationId}" class="btn btn-sm btn-outline-danger"><i class="fas fa-trash"></i> Delete</a>
                                </div>
                            </div>
                        </c:forEach>
                </div>
            </div>

        </div>
    </div>

    <!-- Create Notification Modal -->
    <div class="modal fade" id="notificationModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header bg-light">
            <h5 class="modal-title">Create Announcement</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form id="notificationForm" action="/notifications/add" method="POST">
                <div class="mb-3">
                    <label class="form-label">Title</label>
                    <input type="text" class="form-control" name="title" required placeholder="e.g. System Maintenance">
                </div>
                <div class="mb-3">
                    <label class="form-label">Target Audience</label>
                    <select class="form-select" name="targetRole" required>
                        <option value="ALL">All Users</option>
                        <option value="STUDENT">Students Only</option>
                        <option value="LECTURER">Lecturers Only</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Message Content</label>
                    <textarea class="form-control" name="message" rows="4" required></textarea>
                </div>
                <div class="text-end">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Broadcast</button>
                </div>
            </form>
          </div>

        </div>
      </div>
    </div>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/mock-data.js"></script>
    <script src="js/main.js"></script>
    <script>
    </script>
</body>
</html>
