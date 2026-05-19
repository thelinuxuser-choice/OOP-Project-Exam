document.addEventListener('DOMContentLoaded', function() {



    // Sidebar toggle functionality

    const sidebarCollapse = document.getElementById('sidebarCollapse');

    const sidebar = document.getElementById('sidebar');

    

    if (sidebarCollapse && sidebar) {

        sidebarCollapse.addEventListener('click', function() {

            sidebar.classList.toggle('active');

            

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



    // Active page highlight

    const currentPath = window.location.pathname.split('/').pop();

    const navLinks = document.querySelectorAll('#sidebar ul li a');

    

    navLinks.forEach(link => {

        const href = link.getAttribute('href');

        if (href === currentPath || (currentPath === '' && href === 'dashboard.html')) {

            link.parentElement.classList.add('active');

        }

    });



    // ===============================

    // ROLE-BASED SIDEBAR NAVIGATION

    // ===============================

    const role = localStorage.getItem("role");



    const usersLinks = document.querySelectorAll('a[href="users.html"]');



    usersLinks.forEach(link => {

        if (role === "ADMIN") {

            link.setAttribute("href", "admin-users.html");

        } else {

            link.setAttribute("href", "users.html");

        }

    });



});



// ===============================

// LOGOUT

// ===============================

function logout() {



    localStorage.removeItem("userId");

    localStorage.removeItem("role");



    window.location.href = "login.html";

}

