<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback - UniExam Portal</title>
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
            <li class="active"><a href="/feedback"><i class="fas fa-comments"></i> Feedback</a></li>
            <li><a href="/notifications"><i class="fas fa-bell"></i> Notifications</a></li>
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
                <h2 class="h3 mb-0 text-gray-800">Feedback System</h2>
                <button class="btn btn-primary shadow-sm" data-bs-toggle="modal" data-bs-target="#feedbackModal">
                    <i class="fas fa-plus fa-sm text-white-50"></i> Submit Feedback
                </button>
            </div>

            <!-- Feedback Summary Cards -->
            <div class="row mb-4">
                <div class="col-xl-4 col-md-6 mb-4">
                    <div class="card border-left-primary shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Total Feedback</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">124</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-comments fa-2x text-gray-300 text-muted"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-4 col-md-6 mb-4">
                    <div class="card border-left-success shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1">Avg Rating</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">4.2 / 5.0</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-star fa-2x text-warning"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-4 col-md-6 mb-4">
                    <div class="card border-left-warning shadow h-100 py-2">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Unread Feedback</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">12</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-envelope fa-2x text-gray-300 text-muted"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Admin Feedback Moderation Table -->
            <div class="card shadow-sm mb-4">
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                    <h6 class="m-0 font-weight-bold text-primary">Recent Feedback Submissions</h6>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>User</th>
                                    <th>Exam/Topic</th>
                                    <th>Rating</th>
                                    <th>Comment Snippet</th>
                                    <th>Date</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${feedbackList}" var="fb">
                                <tr>
                                    <td>${fb.studentId}</td>
                                    <td>${fb.examId} (${fb.type})</td>
                                    <td>
                                        <span class="text-warning">
                                            <c:forEach begin="1" end="${fb.rating}">
                                                <i class="fas fa-star"></i>
                                            </c:forEach>
                                            <c:forEach begin="${fb.rating + 1}" end="5">
                                                <i class="far fa-star"></i>
                                            </c:forEach>
                                        </span>
                                    </td>
                                    <td>${fb.comment}</td>
                                    <td>${fb.createdDate}</td>
                                    <td>
                                        <span class="badge ${fb.status == 'PENDING' ? 'bg-warning' : 'bg-success'}">${fb.status}</span>
                                        <c:if test="${fb.status == 'PENDING'}">
                                            <a href="/feedback/review/${fb.feedbackId}" class="btn btn-sm btn-outline-success"><i class="fas fa-check"></i> Mark Read</a>
                                        </c:if>
                                        <a href="/feedback/delete/${fb.feedbackId}" class="btn btn-sm btn-outline-danger"><i class="fas fa-trash"></i></a>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <!-- Submit Feedback Modal -->
    <div class="modal fade" id="feedbackModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header bg-light">
            <h5 class="modal-title">Submit Feedback</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form id="feedbackForm" action="/feedback/add" method="POST">
                <div class="mb-3">
                    <label class="form-label">Student ID</label>
                    <input type="text" class="form-control" name="studentId" required placeholder="e.g. STU123">
                </div>
                <div class="mb-3">
                    <label class="form-label">Type</label>
                    <select class="form-select" name="type" required>
                        <option value="EXAM">Exam</option>
                        <option value="COURSE">Course</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Related Exam/Course ID</label>
                    <input type="text" class="form-control" name="examId" required placeholder="e.g. EX001 or CS101">
                </div>
                <div class="mb-3">
                    <label class="form-label">Rating</label>
                    <div class="rating d-flex gap-2 text-warning fs-3">
                        <i class="far fa-star" style="cursor:pointer;" onclick="setRating(1)"></i>
                        <i class="far fa-star" style="cursor:pointer;" onclick="setRating(2)"></i>
                        <i class="far fa-star" style="cursor:pointer;" onclick="setRating(3)"></i>
                        <i class="far fa-star" style="cursor:pointer;" onclick="setRating(4)"></i>
                        <i class="far fa-star" style="cursor:pointer;" onclick="setRating(5)"></i>
                    </div>
                    <input type="hidden" id="ratingValue" name="rating" required value="5">
                </div>
                <div class="mb-3">
                    <label class="form-label">Your Comment</label>
                    <textarea class="form-control" name="comment" rows="4" placeholder="Share your experience..." required></textarea>
                </div>
                <div class="text-end">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Submit Feedback</button>
                </div>
            </form>
          </div>

        </div>
      </div>
    </div>

    <!-- Bootstrap Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/main.js"></script>
    <script>
        function setRating(val) {
            document.getElementById('ratingValue').value = val;
            const stars = document.querySelectorAll('.rating i');
            stars.forEach((star, index) => {
                if(index < val) {
                    star.classList.remove('far');
                    star.classList.add('fas');
                } else {
                    star.classList.remove('fas');
                    star.classList.add('far');
                }
            });
        }
    </script>
</body>
</html>
