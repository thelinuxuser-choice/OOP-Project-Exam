// mock-data.js - Dummy JSON data to populate the UI

const mockData = {
    dashboardStats: {
        totalStudents: 1245,
        totalExams: 42,
        activeSessions: 3,
        resultsPublished: 156
    },
    
    users: [
        { id: 'U1001', username: 'johndoe', role: 'Student', email: 'john@university.edu', status: 'Active' },
        { id: 'U1002', username: 'janedoe', role: 'Lecturer', email: 'jane@university.edu', status: 'Active' },
        { id: 'U1003', username: 'admin01', role: 'Admin', email: 'admin@university.edu', status: 'Active' },
        { id: 'U1004', username: 'bobbysmith', role: 'Student', email: 'bobby@university.edu', status: 'Inactive' },
        { id: 'U1005', username: 'sarahconnor', role: 'Student', email: 'sarah@university.edu', status: 'Active' }
    ],

    exams: [
        { id: 'EX201', title: 'Data Structures Midterm', course: 'CS201', duration: '120 mins', marks: 100, date: '2026-05-15', status: 'Upcoming' },
        { id: 'EX202', title: 'Database Systems Final', course: 'CS304', duration: '180 mins', marks: 100, date: '2026-04-20', status: 'Completed' },
        { id: 'EX203', title: 'Software Engineering Quiz 1', course: 'CS401', duration: '45 mins', marks: 20, date: '2026-04-25', status: 'Active' },
        { id: 'EX204', title: 'Machine Learning Basics', course: 'CS450', duration: '90 mins', marks: 50, date: '2026-05-02', status: 'Draft' }
    ],

    questions: [
        { id: 'Q001', text: 'What does HTML stand for?', type: 'MCQ', marks: 2, options: ['Hyper Text Markup Language', 'High Tech Modern Language', 'Hyperlink and Text Markup Language', 'Home Tool Markup Language'], correct: 'A' },
        { id: 'Q002', text: 'Which data structure uses LIFO?', type: 'MCQ', marks: 2, options: ['Queue', 'Stack', 'Tree', 'Graph'], correct: 'B' },
        { id: 'Q003', text: 'Explain the concept of Polymorphism in OOP.', type: 'Short Answer', marks: 5, options: [], correct: '' }
    ],

    results: [
        { student: 'John Doe', exam: 'Data Structures Midterm', score: 85, total: 100, percentage: 85, grade: 'A' },
        { student: 'Sarah Connor', exam: 'Database Systems Final', score: 92, total: 100, percentage: 92, grade: 'A+' },
        { student: 'Bobby Smith', exam: 'Database Systems Final', score: 65, total: 100, percentage: 65, grade: 'C' }
    ],

    notifications: [
        { id: 1, title: 'System Maintenance', message: 'The system will be down for maintenance this Sunday from 2 AM to 4 AM.', date: '2026-04-24', type: 'warning' },
        { id: 2, title: 'Results Published', message: 'Results for Database Systems Final have been published.', date: '2026-04-23', type: 'success' },
        { id: 3, title: 'New Exam Scheduled', message: 'Machine Learning Basics has been scheduled for May 2nd.', date: '2026-04-22', type: 'info' }
    ]
};
