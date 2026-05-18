// mock data for UI design - 

const mockData = {
    dashboardStats: {
        totalStudents: 345,
        totalExams: 12,
        activeSessions: 1,
        resultsPublished: 89
    },

    users: [
        { id: 'S104592', username: 'jsmith92', role: 'Student', email: 'j.smith92@university.edu', status: 'Active' },
        { id: 'L83011', username: 'dr_atv', role: 'Lecturer', email: 'a.turing@university.edu', status: 'Active' },
        { id: 'A001', username: 'sysadmin', role: 'Admin', email: 'it-support@university.edu', status: 'Active' },
        { id: 'S104593', username: 'mwilliams', role: 'Student', email: 'm.williams@university.edu', status: 'Inactive' },
        { id: 'S104594', username: 'kchen', role: 'Student', email: 'k.chen@university.edu', status: 'Active' }
    ],

    exams: [
        { id: 'EX101', title: 'CS204 Object Oriented Programming Final', course: 'CS204', duration: '180 mins', marks: 100, date: '2026-05-18', status: 'Upcoming' },
        { id: 'EX102', title: 'CS301 Algorithms & Data Structures Lab', course: 'CS301', duration: '120 mins', marks: 50, date: '2026-04-20', status: 'Completed' },
        { id: 'EX103', title: 'CS204 Midterm Assessment', course: 'CS204', duration: '60 mins', marks: 30, date: '2026-03-15', status: 'Completed' },
        { id: 'EX104', title: 'CS450 Software Engineering Project Defense', course: 'CS450', duration: '45 mins', marks: 100, date: '2026-05-10', status: 'Draft' }
    ],

    questions: [
        { id: 'Q001', text: 'Which of the following access modifiers makes a member visible only within its own class?', type: 'MCQ', marks: 2, options: ['public', 'protected', 'private', 'default'], correct: 'C' },
        { id: 'Q002', text: 'Explain the difference between method overloading and method overriding in Java, providing a short code example for each.', type: 'Short Answer', marks: 10, options: [], correct: '' },
        { id: 'Q003', text: 'What is the primary purpose of an abstract class in Object Oriented Design?', type: 'MCQ', marks: 2, options: ['To prevent inheritance', 'To provide a base class with some default implementation', 'To create instances directly', 'To allow multiple inheritance in Java'], correct: 'B' }
    ],

    results: [
        { student: 'James Smith', exam: 'CS204 Midterm Assessment', score: 26, total: 30, percentage: 86.6, grade: 'A' },
        { student: 'Kevin Chen', exam: 'CS204 Midterm Assessment', score: 28, total: 30, percentage: 93.3, grade: 'A+' },
        { student: 'Mary Williams', exam: 'CS204 Midterm Assessment', score: 18, total: 30, percentage: 60.0, grade: 'C' }
    ],

    notifications: [
        { id: 1, title: 'Server Maintenance Window', message: 'The examination server will undergo routine maintenance and security patching on Saturday, May 2nd, from 00:00 to 04:00 AM. Please ensure all sessions are saved before this period.', date: '2026-04-24', type: 'warning' },
        { id: 2, title: 'Final Grades Published', message: 'The final results for CS301 Algorithms & Data Structures have been verified by the examination board and are now published on your dashboard.', date: '2026-04-23', type: 'success' },
        { id: 3, title: 'Room Change for CS204 Final', message: 'Please be advised that the venue for the CS204 Object Oriented Programming Final Examination has been moved from Hall A to the Main Computing Laboratory.', date: '2026-04-22', type: 'info' }
    ]
};
