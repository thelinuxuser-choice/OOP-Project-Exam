// main.js - Shared application logic

document.addEventListener('DOMContentLoaded', function() {
    // Sidebar toggle functionality
    const sidebarCollapse = document.getElementById('sidebarCollapse');
    const sidebar = document.getElementById('sidebar');
    
    if (sidebarCollapse && sidebar) {
        sidebarCollapse.addEventListener('click', function() {
            sidebar.classList.toggle('active');
            
            // Adjust content on larger screens if needed
            if (window.innerWidth > 768) {
                const content = document.getElementById('content');
                if (sidebar.classList.contains('active')) {
                    sidebar.style.marginLeft = '-250px';
                    content.style.marginLeft = '0';
                    content.style.width = '100%';
                } else {
                    sidebar.style.marginLeft = '0';
                    content.style.marginLeft = '250px';
                    content.style.width = 'calc(100% - 250px)';
                }
            }
        });
    }

    // Set active class on sidebar based on current page
    const currentPath = window.location.pathname.split('/').pop();
    const navLinks = document.querySelectorAll('#sidebar ul li a');
    
    navLinks.forEach(link => {
        const href = link.getAttribute('href');
        if (href === currentPath || (currentPath === '' && href === 'dashboard.html')) {
            link.parentElement.classList.add('active');
        }
    });
});

// Utility function to simulate fetching data (can be replaced with real AJAX/fetch in JSP later)
function simulateFetch(data, delay = 500) {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve(data);
        }, delay);
    });
}
