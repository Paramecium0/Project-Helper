/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout/index'

const teacher_presentationRouter = {
  path: '/teacher_presentation',
  component: Layout,
  name: 'Presentation',
  meta: {
    title: 'Presentation',
    icon: 'el-icon-mic',
    roles: ['teacher', 'student']
  },
  children: [
    {
      path: 'presentation',
      component: () => import('@/views/teachers/Presentation/presentation/index'), // Parent router-view
      name: 'P1',
      meta: { title: 'Setting', icon: 'el-icon-mic', roles: ['teacher'] }
    }, {
      path: 'presentation2',
      component: () => import('@/views/teachers/Presentation/p2/index'), // Parent router-view
      name: 'P2',
      meta: { title: 'Pre Registration', icon: 'el-icon-mic' }
    }
  ]
}

export default teacher_presentationRouter

