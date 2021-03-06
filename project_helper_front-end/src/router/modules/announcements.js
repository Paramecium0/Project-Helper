/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout/index'

const announcementsRouter = {
  path: '/announcements',
  component: Layout,
  // name: 'announcements',
  meta: {
    title: 'Announcement',
    icon: 'message',
    roles: ['teacher', 'student']
  },
  children: [
    {
      path: 'announcements',
      component: () => import('@/views/teachers/Announcements/announcements/index'),
      name: 'announcements',
      meta: { title: 'Announcements', roles: ['teacher'], icon: 'el-icon-folder' }
    },
    {
      path: 'Content',
      component: () => import('@/views/teachers/Announcements/special_one/index'),
      name: 'Content',
      meta: { title: 'Announcement', roles: ['teacher', 'student'], icon: 'el-icon-document' }
    },
    {
      path: 'create',
      component: () => import('@/views/teachers/Announcements/Create/index'),
      name: 'create',
      meta: { title: 'Creating', roles: ['teacher'], icon: 'el-icon-edit' }
    }
  ]
}

export default announcementsRouter
