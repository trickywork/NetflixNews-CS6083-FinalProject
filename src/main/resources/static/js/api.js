/**
 * Netflix News System - API Client
 * Handles all API communications with the backend
 */

const API_BASE_URL = '/api';

// Token management
const TokenManager = {
    getToken: () => localStorage.getItem('token'),
    setToken: (token) => localStorage.setItem('token', token),
    removeToken: () => localStorage.removeItem('token'),
    getUser: () => {
        const user = localStorage.getItem('user');
        return user ? JSON.parse(user) : null;
    },
    setUser: (user) => localStorage.setItem('user', JSON.stringify(user)),
    removeUser: () => localStorage.removeItem('user'),
    clear: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    }
};

// API client with authentication
const api = {
    /**
     * Make an authenticated API request
     */
    async request(endpoint, options = {}) {
        const token = TokenManager.getToken();
        const headers = {
            'Content-Type': 'application/json',
            ...options.headers
        };
        
        if (token) {
            headers['Authorization'] = `Bearer ${token}`;
        }
        
        try {
            const response = await fetch(`${API_BASE_URL}${endpoint}`, {
                ...options,
                headers
            });
            
            const data = await response.json();
            
            // Handle unauthorized responses
            if (response.status === 401 || response.status === 403) {
                if (data.code === 401) {
                    TokenManager.clear();
                    window.location.href = '/login.html';
                    return;
                }
            }
            
            return data;
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    },
    
    // GET request
    get(endpoint) {
        return this.request(endpoint, { method: 'GET' });
    },
    
    // POST request
    post(endpoint, data) {
        return this.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(data)
        });
    },
    
    // PUT request
    put(endpoint, data) {
        return this.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(data)
        });
    },
    
    // DELETE request
    delete(endpoint) {
        return this.request(endpoint, { method: 'DELETE' });
    }
};

// Auth API
const AuthAPI = {
    login: (username, password) => api.post('/auth/login', { username, password }),
    register: (data) => api.post('/auth/register', data),
    getCurrentUser: () => api.get('/auth/me'),
    changePassword: (oldPassword, newPassword) => 
        api.post(`/auth/change-password?oldPassword=${encodeURIComponent(oldPassword)}&newPassword=${encodeURIComponent(newPassword)}`)
};

// Web Series API
const WebSeriesAPI = {
    getAll: () => api.get('/web-series'),
    getPage: (page = 1, size = 10) => api.get(`/web-series/page?page=${page}&size=${size}`),
    getById: (id) => api.get(`/web-series/${id}`),
    search: (keyword) => api.get(`/web-series/search?keyword=${encodeURIComponent(keyword)}`),
    getByType: (type) => api.get(`/web-series/type/${encodeURIComponent(type)}`),
    getTypes: () => api.get('/web-series/types'),
    getTopRated: (limit = 10) => api.get(`/web-series/top-rated?limit=${limit}`),
    getViewersByType: () => api.get('/web-series/stats/viewers-by-type'),
    create: (data) => api.post('/web-series', data),
    update: (id, data) => api.put(`/web-series/${id}`, data),
    delete: (id) => api.delete(`/web-series/${id}`)
};

// Country API
const CountryAPI = {
    getAll: () => api.get('/countries'),
    getById: (id) => api.get(`/countries/${id}`),
    create: (data) => api.post('/countries', data),
    update: (id, data) => api.put(`/countries/${id}`, data),
    delete: (id) => api.delete(`/countries/${id}`)
};

// Production House API
const ProductionHouseAPI = {
    getAll: () => api.get('/production-houses'),
    getPage: (page = 1, size = 10) => api.get(`/production-houses/page?page=${page}&size=${size}`),
    getById: (id) => api.get(`/production-houses/${id}`),
    search: (keyword) => api.get(`/production-houses/search?keyword=${encodeURIComponent(keyword)}`),
    create: (data) => api.post('/production-houses', data),
    update: (id, data) => api.put(`/production-houses/${id}`, data),
    delete: (id) => api.delete(`/production-houses/${id}`)
};

// Producer API
const ProducerAPI = {
    getAll: () => api.get('/producers'),
    getPage: (page = 1, size = 10) => api.get(`/producers/page?page=${page}&size=${size}`),
    getById: (id) => api.get(`/producers/${id}`),
    search: (keyword) => api.get(`/producers/search?keyword=${encodeURIComponent(keyword)}`),
    create: (data) => api.post('/producers', data),
    update: (id, data) => api.put(`/producers/${id}`, data),
    delete: (id) => api.delete(`/producers/${id}`),
    linkToProductionHouse: (producerId, productionHouseId) => 
        api.post(`/producers/${producerId}/production-houses/${productionHouseId}`),
    unlinkFromProductionHouse: (producerId, productionHouseId) => 
        api.delete(`/producers/${producerId}/production-houses/${productionHouseId}`)
};

// Contract API
const ContractAPI = {
    getAll: () => api.get('/contracts'),
    getPage: (page = 1, size = 10) => api.get(`/contracts/page?page=${page}&size=${size}`),
    getById: (id) => api.get(`/contracts/${id}`),
    getByWebSeriesId: (webSeriesId) => api.get(`/contracts/web-series/${webSeriesId}`),
    getByProductionHouseId: (productionHouseId) => api.get(`/contracts/production-house/${productionHouseId}`),
    getTotalValue: () => api.get('/contracts/stats/total-value'),
    create: (data) => api.post('/contracts', data),
    update: (id, data) => api.put(`/contracts/${id}`, data),
    delete: (id) => api.delete(`/contracts/${id}`)
};

// Schedule API
const ScheduleAPI = {
    getAll: () => api.get('/schedules'),
    getPage: (page = 1, size = 10) => api.get(`/schedules/page?page=${page}&size=${size}`),
    getById: (id) => api.get(`/schedules/${id}`),
    getByWebSeriesId: (webSeriesId) => api.get(`/schedules/web-series/${webSeriesId}`),
    getByDateRange: (start, end) => api.get(`/schedules/range?start=${start}&end=${end}`),
    getUpcoming: (limit = 10) => api.get(`/schedules/upcoming?limit=${limit}`),
    create: (data) => api.post('/schedules', data),
    update: (id, data) => api.put(`/schedules/${id}`, data),
    delete: (id) => api.delete(`/schedules/${id}`)
};

// Feedback API
const FeedbackAPI = {
    getAll: () => api.get('/feedback'),
    getPage: (page = 1, size = 10) => api.get(`/feedback/page?page=${page}&size=${size}`),
    getById: (id) => api.get(`/feedback/${id}`),
    getMyFeedback: () => api.get('/feedback/my'),
    getByWebSeriesId: (webSeriesId) => api.get(`/feedback/web-series/${webSeriesId}`),
    getRecent: (limit = 5) => api.get(`/feedback/recent?limit=${limit}`),
    getRatingDistribution: () => api.get('/feedback/stats/rating-distribution'),
    create: (data) => api.post('/feedback', data),
    delete: (id) => api.delete(`/feedback/${id}`)
};

// Account API
const AccountAPI = {
    getAll: () => api.get('/accounts'),
    getPage: (page = 1, size = 10) => api.get(`/accounts/page?page=${page}&size=${size}`),
    getById: (id) => api.get(`/accounts/${id}`),
    search: (keyword) => api.get(`/accounts/search?keyword=${encodeURIComponent(keyword)}`),
    update: (id, data) => api.put(`/accounts/${id}`, data),
    delete: (id) => api.delete(`/accounts/${id}`)
};

// User API
const UserAPI = {
    getAll: () => api.get('/users'),
    getByRole: (role) => api.get(`/users/role/${role}`),
    getById: (id) => api.get(`/users/${id}`),
    createEmployee: (username, password) => 
        api.post(`/users/employee?username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`),
    delete: (id) => api.delete(`/users/${id}`)
};

// Statistics API
const StatisticsAPI = {
    getDashboard: () => api.get('/statistics/dashboard'),
    getViewersByType: () => api.get('/statistics/viewers-by-type'),
    getRatingDistribution: () => api.get('/statistics/rating-distribution'),
    getWebSeriesTypes: () => api.get('/statistics/web-series-types')
};

// Export for use in other scripts
window.TokenManager = TokenManager;
window.api = api;
window.AuthAPI = AuthAPI;
window.WebSeriesAPI = WebSeriesAPI;
window.CountryAPI = CountryAPI;
window.ProductionHouseAPI = ProductionHouseAPI;
window.ProducerAPI = ProducerAPI;
window.ContractAPI = ContractAPI;
window.ScheduleAPI = ScheduleAPI;
window.FeedbackAPI = FeedbackAPI;
window.AccountAPI = AccountAPI;
window.UserAPI = UserAPI;
window.StatisticsAPI = StatisticsAPI;
