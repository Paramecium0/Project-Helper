import request from '@/utils/request'

export function get_joinableProject(token) {
    return request({
      url: '/vue-element-admin/student/join/joinable_project',
      method: 'get',
      params: {token}
    })
  }

  export function join_project(token, project_id) {
    return request({
      url: '/vue-element-admin/student/join/join_project',
      method: 'post',
      params: {token, project_id}
    })
  }