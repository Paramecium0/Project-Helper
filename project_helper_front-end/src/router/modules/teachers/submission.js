/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const submissionRouter = {
  path: '/submission',
  component: Layout,
  children: [
    {
      path: 'submission',
      component: () => import('@/views/teachers/Submission/submission/index'), // Parent router-view
      name: 'submission',
      meta: { title: 'Submission', icon: 'clipboard', roles: ['teacher'] }
    }
  ]
}

export default submissionRouter
