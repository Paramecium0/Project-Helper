import request from '@/utils/request'


export function get_scoreList(project_id) {
  return request({
    url: '/vue-element-admin/teacher/grade/score_list',
    method: 'get',
    params: {project_id}
  })
}

export function post_scoreList(data) {
    return request({
      url: '/vue-element-admin/teacher/grade/update_score_list',
      method: 'post',
      data
    })
  }


  export function get_grades_distribution(project_id) {
    return request({
      url: '/vue-element-admin/teacher/grade/grades_stat',
      method: 'post',
      params: {project_id}
    })
  }




