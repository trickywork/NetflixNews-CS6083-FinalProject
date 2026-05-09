/**
 * Netflix News System - Common Utilities
 */

// Toast notification system
const Toast = {
    container: null,
    
    init() {
        if (!this.container) {
            this.container = document.createElement('div');
            this.container.className = 'toast-container';
            document.body.appendChild(this.container);
        }
    },
    
    show(message, type = 'success', duration = 3000) {
        this.init();
        
        const toast = document.createElement('div');
        toast.className = `toast ${type}`;
        toast.innerHTML = `
            <span>${this.getIcon(type)}</span>
            <span>${message}</span>
        `;
        
        this.container.appendChild(toast);
        
        // Trigger animation
        setTimeout(() => toast.classList.add('show'), 10);
        
        // Auto remove
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => toast.remove(), 300);
        }, duration);
    },
    
    getIcon(type) {
        const icons = {
            success: '✓',
            error: '✕',
            warning: '⚠',
            info: 'ℹ'
        };
        return icons[type] || icons.info;
    },
    
    success(message) { this.show(message, 'success'); },
    error(message) { this.show(message, 'error'); },
    warning(message) { this.show(message, 'warning'); },
    info(message) { this.show(message, 'info'); }
};

// Loading overlay
const Loading = {
    overlay: null,
    
    show() {
        if (!this.overlay) {
            this.overlay = document.createElement('div');
            this.overlay.className = 'loading-overlay';
            this.overlay.innerHTML = '<div class="spinner"></div>';
        }
        document.body.appendChild(this.overlay);
    },
    
    hide() {
        if (this.overlay && this.overlay.parentNode) {
            this.overlay.parentNode.removeChild(this.overlay);
        }
    }
};

// Modal management
const Modal = {
    show(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.classList.add('active');
        }
    },
    
    hide(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.classList.remove('active');
        }
    },
    
    create(options) {
        const { id, title, content, footer } = options;
        
        const modal = document.createElement('div');
        modal.id = id;
        modal.className = 'modal-overlay';
        modal.innerHTML = `
            <div class="modal">
                <div class="modal-header">
                    <h3 class="modal-title">${title}</h3>
                    <button class="modal-close" onclick="Modal.hide('${id}')">&times;</button>
                </div>
                <div class="modal-body">${content}</div>
                ${footer ? `<div class="modal-footer">${footer}</div>` : ''}
            </div>
        `;
        
        // Close on overlay click
        modal.addEventListener('click', (e) => {
            if (e.target === modal) {
                Modal.hide(id);
            }
        });
        
        document.body.appendChild(modal);
        return modal;
    }
};

// Check authentication and role
const Auth = {
    isLoggedIn() {
        return !!TokenManager.getToken();
    },
    
    getUser() {
        return TokenManager.getUser();
    },
    
    isEmployee() {
        const user = this.getUser();
        return user && user.role === 'employee';
    },
    
    isCustomer() {
        const user = this.getUser();
        return user && user.role === 'customer';
    },
    
    requireAuth() {
        if (!this.isLoggedIn()) {
            window.location.href = '/login.html';
            return false;
        }
        return true;
    },
    
    requireEmployee() {
        if (!this.isLoggedIn() || !this.isEmployee()) {
            Toast.error('Access denied. Employee privileges required.');
            window.location.href = '/index.html';
            return false;
        }
        return true;
    },
    
    logout() {
        TokenManager.clear();
        window.location.href = '/login.html';
    }
};

// Format utilities
const Format = {
    date(dateString) {
        if (!dateString) return '-';
        const date = new Date(dateString);
        return date.toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric'
        });
    },
    
    datetime(dateString) {
        if (!dateString) return '-';
        const date = new Date(dateString);
        return date.toLocaleString('en-US', {
            year: 'numeric',
            month: 'short',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    },
    
    number(num) {
        if (num === null || num === undefined) return '-';
        return num.toLocaleString();
    },
    
    currency(amount) {
        if (amount === null || amount === undefined) return '-';
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(amount);
    },
    
    rating(rating) {
        if (!rating) return '-';
        return '★'.repeat(Math.round(rating)) + '☆'.repeat(5 - Math.round(rating));
    },
    
    truncate(str, length = 50) {
        if (!str) return '';
        return str.length > length ? str.substring(0, length) + '...' : str;
    }
};

// DOM utilities
const DOM = {
    $(selector) {
        return document.querySelector(selector);
    },
    
    $$(selector) {
        return document.querySelectorAll(selector);
    },
    
    create(tag, attrs = {}, children = []) {
        const el = document.createElement(tag);
        Object.entries(attrs).forEach(([key, value]) => {
            if (key === 'className') {
                el.className = value;
            } else if (key === 'innerHTML') {
                el.innerHTML = value;
            } else if (key.startsWith('on')) {
                el.addEventListener(key.slice(2).toLowerCase(), value);
            } else {
                el.setAttribute(key, value);
            }
        });
        children.forEach(child => {
            if (typeof child === 'string') {
                el.appendChild(document.createTextNode(child));
            } else {
                el.appendChild(child);
            }
        });
        return el;
    },
    
    clear(element) {
        while (element.firstChild) {
            element.removeChild(element.firstChild);
        }
    }
};

// Pagination helper
const Pagination = {
    render(containerId, currentPage, totalPages, onPageChange) {
        const container = document.getElementById(containerId);
        if (!container) return;
        
        let html = '';
        
        // Previous button
        html += `<button ${currentPage <= 1 ? 'disabled' : ''} onclick="${onPageChange}(${currentPage - 1})">« Prev</button>`;
        
        // Page numbers
        const startPage = Math.max(1, currentPage - 2);
        const endPage = Math.min(totalPages, currentPage + 2);
        
        if (startPage > 1) {
            html += `<button onclick="${onPageChange}(1)">1</button>`;
            if (startPage > 2) html += '<button disabled>...</button>';
        }
        
        for (let i = startPage; i <= endPage; i++) {
            html += `<button class="${i === currentPage ? 'active' : ''}" onclick="${onPageChange}(${i})">${i}</button>`;
        }
        
        if (endPage < totalPages) {
            if (endPage < totalPages - 1) html += '<button disabled>...</button>';
            html += `<button onclick="${onPageChange}(${totalPages})">${totalPages}</button>`;
        }
        
        // Next button
        html += `<button ${currentPage >= totalPages ? 'disabled' : ''} onclick="${onPageChange}(${currentPage + 1})">Next »</button>`;
        
        container.innerHTML = html;
    }
};

// Change Password Modal
const PasswordModal = {
    init() {
        // Create modal if not exists
        if (!document.getElementById('password-modal')) {
            const modalHtml = `
                <div id="password-modal" class="modal-overlay">
                    <div class="modal">
                        <div class="modal-header">
                            <h3 class="modal-title">Change Password</h3>
                            <button class="modal-close" onclick="PasswordModal.hide()">&times;</button>
                        </div>
                        <div class="modal-body">
                            <form id="password-form">
                                <div class="form-group">
                                    <label class="form-label">Current Password *</label>
                                    <input type="password" id="old-password" class="form-input" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">New Password *</label>
                                    <input type="password" id="new-password" class="form-input" required minlength="6">
                                    <small style="color: #757575;">At least 6 characters</small>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">Confirm New Password *</label>
                                    <input type="password" id="confirm-password" class="form-input" required>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-outline" onclick="PasswordModal.hide()">Cancel</button>
                            <button class="btn btn-primary" onclick="PasswordModal.submit()">Change Password</button>
                        </div>
                    </div>
                </div>
            `;
            document.body.insertAdjacentHTML('beforeend', modalHtml);
        }
    },
    
    show() {
        this.init();
        document.getElementById('password-form').reset();
        document.getElementById('password-modal').classList.add('active');
    },
    
    hide() {
        document.getElementById('password-modal').classList.remove('active');
    },
    
    async submit() {
        const oldPassword = document.getElementById('old-password').value;
        const newPassword = document.getElementById('new-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        
        if (!oldPassword || !newPassword || !confirmPassword) {
            Toast.error('Please fill in all fields');
            return;
        }
        
        if (newPassword.length < 6) {
            Toast.error('New password must be at least 6 characters');
            return;
        }
        
        if (newPassword !== confirmPassword) {
            Toast.error('New passwords do not match');
            return;
        }
        
        Loading.show();
        try {
            const result = await AuthAPI.changePassword(oldPassword, newPassword);
            if (result.code === 200) {
                Toast.success('Password changed successfully!');
                this.hide();
            } else {
                Toast.error(result.message || 'Failed to change password');
            }
        } catch (error) {
            Toast.error('Failed to change password');
        } finally {
            Loading.hide();
        }
    }
};

// Initialize navigation
function initNavigation() {
    const user = Auth.getUser();
    const navUser = document.getElementById('nav-user');
    const navLinks = document.getElementById('nav-links');
    
    if (navUser) {
        if (user) {
            navUser.innerHTML = `
                <div class="user-menu">
                    <div class="user-info" onclick="toggleUserDropdown()">
                        <div class="user-avatar">${user.username.charAt(0).toUpperCase()}</div>
                        <span>${user.username}</span>
                        <span class="user-role">${user.role}</span>
                        <span style="margin-left: 5px;">▼</span>
                    </div>
                    <div class="user-dropdown" id="user-dropdown">
                        <a href="javascript:void(0)" onclick="PasswordModal.show()">🔒 Change Password</a>
                        <a href="javascript:void(0)" onclick="Auth.logout()">🚪 Logout</a>
                    </div>
                </div>
            `;
        } else {
            navUser.innerHTML = `
                <a href="/login.html" class="btn btn-outline btn-sm">Login</a>
                <a href="/register.html" class="btn btn-primary btn-sm">Sign Up</a>
            `;
        }
    }
    
    // Add employee-only navigation items
    if (navLinks && user && user.role === 'employee') {
        const employeeLinks = `
            <li><a href="/production-houses.html">Production Houses</a></li>
            <li><a href="/producers.html">Producers</a></li>
            <li><a href="/contracts.html">Contracts</a></li>
            <li><a href="/accounts.html">Accounts</a></li>
        `;
        navLinks.insertAdjacentHTML('beforeend', employeeLinks);
    }
    
    // Highlight current page
    const currentPath = window.location.pathname;
    document.querySelectorAll('.nav-links a').forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });
}

// Toggle user dropdown menu
function toggleUserDropdown() {
    const dropdown = document.getElementById('user-dropdown');
    if (dropdown) {
        dropdown.classList.toggle('show');
    }
}

// Close dropdown when clicking outside
document.addEventListener('click', (e) => {
    const dropdown = document.getElementById('user-dropdown');
    const userMenu = e.target.closest('.user-menu');
    if (dropdown && !userMenu) {
        dropdown.classList.remove('show');
    }
});

// Initialize on DOM ready
document.addEventListener('DOMContentLoaded', () => {
    initNavigation();
});

// Export utilities
window.Toast = Toast;
window.Loading = Loading;
window.Modal = Modal;
window.Auth = Auth;
window.Format = Format;
window.DOM = DOM;
window.Pagination = Pagination;
window.PasswordModal = PasswordModal;
window.toggleUserDropdown = toggleUserDropdown;
