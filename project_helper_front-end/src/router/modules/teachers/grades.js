/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const gradesRouter = {
  path: '/grades',
  component: Layout,
  redirect: '/teachers/Grades/Grading',
  name: 'Grades',
  meta: {
    title: 'Grades',
    icon: 'form',
    roles: ['teacher']
  },
  children: [
    {
      path: 'data',
      component: () => import('@/views/teachers/Grades/Data/index'), // Parent router-view
      name: 'Data',
      meta: { title: 'Data' }
    },
    {
      path: 'grading',
      component: () => import('@/views/teachers/Grades/Online Grading/index'),
      name: 'Grading',
      meta: { title: 'Online Grading' }
    },
    {
      path: 'upload',
      component: () => import('@/views/teachers/Grades/Upload/index'),
      name: 'upload',
      meta: { title: 'Upload' }
    }
  ]
}

export default gradesRouter
