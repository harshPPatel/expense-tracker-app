import Vue from 'vue';
import VueRouter from 'vue-router';

import Auth from '../API/Auth';
import store from '../store';
import Home from '../views/Home.vue';

Vue.use(VueRouter);

if (localStorage.token) {
  store.commit('User/setToken', localStorage.token);
}

const loginRoutePushOptions = {
  name: 'login',
  params: {
    error: {
      errorCode: '401',
      message: 'Please Login to get access',
    },
  },
};

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/About.vue'),
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import('../views/SignUp.vue'),
    beforeEnter: async (to, from, next) => {
      if (localStorage.token) {
        await Auth.validateToken()
          .then(() => next('/dashboard'))
          .catch(() => next());
      } else {
        next();
      }
    },
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue'),
    beforeEnter: async (to, from, next) => {
      if (localStorage.token) {
        await Auth.validateToken()
          .then(() => next('/dashboard'))
          .catch(() => next());
      } else {
        next();
      }
    },
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: () => import('../views/Dashboard.vue'),
    beforeEnter: (to, from, next) => {
      if (store.state.User.isLoggedIn) {
        next();
      } else {
        next(loginRoutePushOptions);
      }
    },
  },
  {
    path: '/expenses',
    name: 'expenses',
    component: () => import('../views/Expenses.vue'),
    beforeEnter: (to, from, next) => {
      if (store.state.User.isLoggedIn) {
        next();
      } else {
        next(loginRoutePushOptions);
      }
    },
  },
  {
    path: '/incomes',
    name: 'incomes',
    component: () => import('../views/Incomes.vue'),
    beforeEnter: (to, from, next) => {
      if (store.state.User.isLoggedIn) {
        next();
      } else {
        next(loginRoutePushOptions);
      }
    },
  },
  {
    path: '/statements',
    name: 'statements',
    component: () => import('../views/Statements.vue'),
    beforeEnter: (to, from, next) => {
      if (store.state.User.isLoggedIn) {
        next();
      } else {
        next(loginRoutePushOptions);
      }
    },
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
