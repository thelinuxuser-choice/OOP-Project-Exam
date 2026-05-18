// Role-Based Access Control Script
// Include this on all pages after main.js

const ACCESS_RULES = {
    // Admin only pages
    'dashboard.html': ['ADMIN'],
    'admin-users.html': ['ADMIN'],

    // Admin + Lecturer only pages
    'exams.html': ['ADMIN', 'LECTURER'],
    'questions.html': ['ADMIN', 'LECTURER'],

    // Public pages (all authenticated users)
    'users.html': ['ADMIN', 'LECTURER', 'STUDENT'],
    'exam-session.html': ['ADMIN', 'LECTURER', 'STUDENT'],
    'exam-list.html': ['ADMIN', 'LECTURER', 'STUDENT'],
    'exam-taking.html': ['ADMIN', 'LECTURER', 'STUDENT'],
    'results.html': ['ADMIN', 'LECTURER', 'STUDENT'],
    'feedback.html': ['ADMIN', 'LECTURER', 'STUDENT'],
    'notifications.html': ['ADMIN', 'LECTURER', 'STUDENT']
};

const REDIRECT_MAP = {
    'STUDENT': 'exam-session.html',
    'LECTURER': 'exams.html',
    'ADMIN': 'dashboard.html'
};

// IMMEDIATE CHECK - Run this before DOM is ready
(function immediateAccessCheck() {
    const params = new URLSearchParams(window.location.search);
    const urlRole = params.get('role');

    // Save role from URL if present
    if (urlRole) {
        localStorage.setItem('role', urlRole.toUpperCase());
        localStorage.setItem('userId', params.get('userId'));
    }

    const role = localStorage.getItem('role');
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';

    // Not logged in
    if (!role && currentPage !== 'login.html' && currentPage !== 'register.html' && currentPage !== 'index.html') {
        window.location.href = 'login.html';
        return;
    }

    if (role) {
        const upperRole = role.toUpperCase();
        const allowedRoles = ACCESS_RULES[currentPage];

        if (allowedRoles && !allowedRoles.includes(upperRole)) {
            // Redirect immediately before page renders
            const redirect = REDIRECT_MAP[upperRole] || 'login.html';
            window.location.replace(redirect); // use replace so back button doesn't go to forbidden page
            return;
        }
    }
})();

function checkAccess() {
    const role = localStorage.getItem('role');
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';

    // Not logged in
    if (!role) {
        window.location.href = 'login.html';
        return false;
    }

    const upperRole = role.toUpperCase();
    const allowedRoles = ACCESS_RULES[currentPage];

    // Page not in rules (allow by default)
    if (!allowedRoles) return true;

    // Check if role is allowed
    if (!allowedRoles.includes(upperRole)) {
        // Redirect to default page for this role
        const redirect = REDIRECT_MAP[upperRole] || 'login.html';
        window.location.replace(redirect);
        return false;
    }

    return true;
}

// Add CSS to hide sidebar items initially
const style = document.createElement('style');
style.textContent = `
    /* Initially hide all nav items, JS will show allowed ones */
    #sidebar .components li { visibility: hidden; }
    #sidebar .components li.always-show { visibility: visible !important; }
`;
document.head.appendChild(style);

function setupSidebar() {
    const role = (localStorage.getItem('role') || '').toUpperCase();

    // Define which menu items to show for each role
    const menuVisibility = {
        // Admin-only menu items
        'dashboard': ['ADMIN'],
        'admin-users': ['ADMIN'],

        // Admin+Lecturer menu items
        'exams': ['ADMIN', 'LECTURER'],
        'questions': ['ADMIN', 'LECTURER'],

        // Public menu items (all users)
        'users': ['ADMIN', 'LECTURER', 'STUDENT'],
        'exam-session': ['ADMIN', 'LECTURER', 'STUDENT'],
        'exam-taking': ['ADMIN', 'LECTURER', 'STUDENT'],
        'results': ['ADMIN', 'LECTURER', 'STUDENT'],
        'feedback': ['ADMIN', 'LECTURER', 'STUDENT'],
        'notifications': ['ADMIN', 'LECTURER', 'STUDENT']
    };

    // Hide/show menu items based on role
    document.querySelectorAll('#sidebar .components li').forEach(li => {
        const link = li.querySelector('a');
        if (!link) return;

        const href = link.getAttribute('href');
        let shouldShow = true;

        // Determine which menu item this is
        if (href === 'dashboard.html') {
            shouldShow = menuVisibility['dashboard'].includes(role);
        } else if (href === 'admin-users.html') {
            shouldShow = menuVisibility['admin-users'].includes(role);
            // Redirect users.html link to admin-users.html for admin
            if (role === 'ADMIN' && href === 'users.html') {
                link.setAttribute('href', 'admin-users.html');
            }
        } else if (href === 'users.html') {
            shouldShow = menuVisibility['users'].includes(role);
        } else if (href === 'exams.html') {
            shouldShow = menuVisibility['exams'].includes(role);
        } else if (href === 'questions.html') {
            shouldShow = menuVisibility['questions'].includes(role);
        } else if (href === 'exam-session.html') {
            shouldShow = menuVisibility['exam-session'].includes(role);
        } else if (href === 'results.html') {
            shouldShow = menuVisibility['results'].includes(role);
        } else if (href === 'feedback.html') {
            shouldShow = menuVisibility['feedback'].includes(role);
        } else if (href === 'notifications.html') {
            shouldShow = menuVisibility['notifications'].includes(role);
        }

        // Logout is always visible
        if (href && (href.includes('logout') || href.includes('login.html'))) {
            shouldShow = true;
        }

        // Use visibility instead of display for smoother rendering
        li.style.visibility = shouldShow ? 'visible' : 'hidden';
        li.style.display = shouldShow ? '' : 'none';
    });

    // Update nav username/avatar
    const navUserName = document.getElementById('navUserName');
    const navAvatar = document.getElementById('navAvatar');

    if (navUserName) navUserName.innerText = role || 'User';
    if (navAvatar) navAvatar.innerText = (role || 'U').charAt(0);
}

// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    if (checkAccess()) {
        setupSidebar();
    }
});

function logout() {
    localStorage.clear();
    window.location.href = 'login.html';
}
