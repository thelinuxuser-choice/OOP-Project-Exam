const API_BASE_URL = 'http://localhost:8080';

// Global state
let examsList = [];
let coursesList = [];
let examModalInstance = null;

document.addEventListener('DOMContentLoaded', () => {
    examModalInstance = new bootstrap.Modal(document.getElementById('examModal'));
    
    // Load data
    loadCourses();
    loadExams();
});

// --- COURSES --- //

function loadCourses() {
    fetch(`${API_BASE_URL}/courses`)
        .then(response => response.json())
        .then(data => {
            coursesList = data;
            renderCoursesList();
            populateCourseDropdown();
        })
        .catch(error => console.error('Error loading courses:', error));
}

function renderCoursesList() {
    const list = document.getElementById('coursesList');
    list.innerHTML = '';
    coursesList.forEach(course => {
        const li = document.createElement('li');
        li.className = 'list-group-item d-flex justify-content-between align-items-center';
        li.innerHTML = `
            ${course.courseCode} - ${course.courseName}
            <button class="btn btn-sm btn-danger" onclick="deleteCourse('${course.courseCode}')">
                <i class="fas fa-times"></i>
            </button>
        `;
        list.appendChild(li);
    });
}

function populateCourseDropdown() {
    const select = document.getElementById('examCourse');
    select.innerHTML = '<option value="">Select Course...</option>';
    coursesList.forEach(course => {
        const option = document.createElement('option');
        option.value = course.courseCode;
        option.textContent = `${course.courseCode} - ${course.courseName}`;
        select.appendChild(option);
    });
}

function addCourse() {
    const code = document.getElementById('newCourseCode').value;
    const name = document.getElementById('newCourseName').value;
    
    if(!code || !name) {
        alert("Please enter both course code and name.");
        return;
    }

    fetch(`${API_BASE_URL}/courses/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ courseCode: code, courseName: name })
    })
    .then(response => {
        if(response.ok) {
            document.getElementById('newCourseCode').value = '';
            document.getElementById('newCourseName').value = '';
            loadCourses();
        } else {
            alert('Failed to add course.');
        }
    })
    .catch(error => console.error('Error adding course:', error));
}

function deleteCourse(code) {
    if(!confirm(`Are you sure you want to delete course ${code}?`)) return;

    fetch(`${API_BASE_URL}/courses/delete/${code}`, { method: 'DELETE' })
        .then(response => {
            if(response.ok) {
                loadCourses();
            } else {
                alert('Failed to delete course.');
            }
        })
        .catch(error => console.error('Error deleting course:', error));
}

// --- EXAMS --- //

function loadExams() {
    fetch(`${API_BASE_URL}/exams`)
        .then(response => response.json())
        .then(data => {
            examsList = data;
            renderExamsTable();
        })
        .catch(error => console.error('Error loading exams:', error));
}

function renderExamsTable() {
    const tbody = document.getElementById('examsTableBody');
    tbody.innerHTML = '';
    
    examsList.forEach(exam => {
        let badgeClass = 'bg-secondary';
        if (exam.status === 'Active') badgeClass = 'bg-success';
        else if (exam.status === 'Upcoming') badgeClass = 'bg-warning text-dark';
        else if (exam.status === 'Completed') badgeClass = 'bg-info text-dark';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${exam.id}</strong></td>
            <td>${exam.title}</td>
            <td>${exam.courseCode}</td>
            <td>${exam.duration} mins</td>
            <td>${exam.marks}</td>
            <td>${exam.date}</td>
            <td><span class="badge ${badgeClass}">${exam.status}</span></td>
            <td>
                <a href="questions.html?exam=${exam.id}" class="btn btn-sm btn-outline-info me-1" title="Manage Questions"><i class="fas fa-list-ol"></i></a>
                <button class="btn btn-sm btn-outline-primary me-1" title="Edit" onclick="openEditExamModal('${exam.id}')"><i class="fas fa-edit"></i></button>
                <button class="btn btn-sm btn-outline-danger" title="Delete" onclick="deleteExam('${exam.id}')"><i class="fas fa-trash"></i></button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function openCreateExamModal() {
    document.getElementById('examForm').reset();
    document.getElementById('examId').value = '';
    document.getElementById('examModalTitle').textContent = 'Create New Exam';
    examModalInstance.show();
}

function openEditExamModal(id) {
    const exam = examsList.find(e => e.id === id);
    if(exam) {
        document.getElementById('examId').value = exam.id;
        document.getElementById('examTitle').value = exam.title;
        document.getElementById('examCourse').value = exam.courseCode;
        document.getElementById('examDuration').value = exam.duration;
        document.getElementById('examMarks').value = exam.marks;
        document.getElementById('examDate').value = exam.date;
        document.getElementById('examStatus').value = exam.status;
        document.getElementById('examInstructions').value = exam.instructions;
        
        document.getElementById('examModalTitle').textContent = 'Edit Exam';
        examModalInstance.show();
    }
}

function saveExam() {
    const id = document.getElementById('examId').value;
    const isEdit = id !== '';
    
    const payload = {
        title: document.getElementById('examTitle').value,
        courseCode: document.getElementById('examCourse').value,
        duration: document.getElementById('examDuration').value,
        marks: document.getElementById('examMarks').value,
        date: document.getElementById('examDate').value,
        status: document.getElementById('examStatus').value,
        instructions: document.getElementById('examInstructions').value
    };

    if(isEdit) {
        payload.id = id;
    }

    const url = isEdit ? `${API_BASE_URL}/exams/update/${id}` : `${API_BASE_URL}/exams/add`;
    const method = isEdit ? 'PUT' : 'POST';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
    .then(response => {
        if(response.ok) {
            examModalInstance.hide();
            loadExams();
        } else {
            alert('Failed to save exam.');
        }
    })
    .catch(error => console.error('Error saving exam:', error));
}

function deleteExam(id) {
    if(!confirm(`Are you sure you want to delete exam ${id}?`)) return;

    fetch(`${API_BASE_URL}/exams/delete/${id}`, { method: 'DELETE' })
        .then(response => {
            if(response.ok) {
                loadExams();
            } else {
                alert('Failed to delete exam.');
            }
        })
        .catch(error => console.error('Error deleting exam:', error));
}
