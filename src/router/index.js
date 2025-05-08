import { createRouter, createWebHistory } from 'vue-router'

// Import views with lazy loading
const IndexView = () => import('@/views/index/index.vue')
const ClazzView = () => import('@/views/clazz/index.vue')
const DeptView = () => import('@/views/dept/index.vue')
const EmpView = () => import('@/views/emp/index.vue')
const LogView = () => import('@/views/log/index.vue')
const StuView = () => import('@/views/stu/index.vue')
const EmpReportView = () => import('@/views/report/emp/index.vue')
const StuReportView = () => import('@/views/report/stu/index.vue')
const LayoutView = () => import('@/views/layout/index.vue')
const LoginView = () => import('@/views/login/index.vue')
const NotFoundView = () => import('@/views/404/index.vue')

// Define child routes separately for better readability and maintainability
const childRoutes = [
  { path: 'index', name: 'index', component: IndexView },
  { path: 'clazz', name: 'clazz', component: ClazzView },
  { path: 'dept', name: 'dept', component: DeptView },
  { path: 'emp', name: 'emp', component: EmpView },
  { path: 'log', name: 'log', component: LogView },
  { path: 'stu', name: 'stu', component: StuView },
  { path: 'empReport', name: 'empReport', component: EmpReportView },
  { path: 'stuReport', name: 'stuReport', component: StuReportView }
]

// Create the router instance
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: '',
      component: LayoutView,
      redirect: '/index', 
      children: childRoutes 
    },
    { path: '/login', name: 'login', component: LoginView },
    { path: '/:pathMatch(.*)*', name: '404', component: NotFoundView } 

  ]
})

export default router

