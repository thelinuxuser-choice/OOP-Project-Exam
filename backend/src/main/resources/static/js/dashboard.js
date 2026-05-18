// dashboard.js - Script for rendering dashboard charts using Chart.js

document.addEventListener('DOMContentLoaded', function() {
    // Populate stats from mock data
    if (typeof mockData !== 'undefined' && document.getElementById('totalStudents')) {
        document.getElementById('totalStudents').textContent = mockData.dashboardStats.totalStudents;
        document.getElementById('totalExams').textContent = mockData.dashboardStats.totalExams;
        document.getElementById('activeSessions').textContent = mockData.dashboardStats.activeSessions;
        document.getElementById('resultsPublished').textContent = mockData.dashboardStats.resultsPublished;
    }

    // Initialize Charts if canvas elements exist
    const participationCtx = document.getElementById('participationChart');
    if (participationCtx) {
        new Chart(participationCtx, {
            type: 'line',
            data: {
                labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5'],
                datasets: [{
                    label: 'Exam Participation (%)',
                    data: [65, 78, 85, 92, 95],
                    borderColor: '#0d47a1',
                    backgroundColor: 'rgba(13, 71, 161, 0.1)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'top',
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });
    }

    const averageCtx = document.getElementById('averageChart');
    if (averageCtx) {
        new Chart(averageCtx, {
            type: 'bar',
            data: {
                labels: ['CS201', 'CS304', 'CS401', 'CS450'],
                datasets: [{
                    label: 'Average Score',
                    data: [75, 82, 68, 88],
                    backgroundColor: [
                        'rgba(255, 179, 0, 0.8)',
                        'rgba(25, 118, 210, 0.8)',
                        'rgba(46, 125, 50, 0.8)',
                        'rgba(198, 40, 40, 0.8)'
                    ],
                    borderWidth: 0
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });
    }
});
