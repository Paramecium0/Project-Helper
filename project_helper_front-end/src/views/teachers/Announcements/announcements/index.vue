<template>
  <div>

    <div class="frame">
      <div class="an_title" style="">Announcements </div>
      <div style="margin-right: 200px; float:right; color:#ffffff;">
        <a href="/#/announcements/create">
          <el-button style="background-color: #c52d47;color: white; " plain icon="el-icon-edit" circle />
        </a>  Create
      </div>
      <div class="content">
        <!--eslint-disable-next-line-->
        <div v-for="an in announcements" @click="miao(an.ann_id)" class="announcetitle">
          <a href="/#/announcements/Content">

            Title: {{ an.ann_name }}
            <br>
            By: {{ an.teacher_name }}
            <br>
            Releace Time: {{ an.release_time }}
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { getToken } from '@/utils/auth'
import { get_announcement_teacher } from '@/api/teacher/announcement/'
// import { get_announcement_content } from '@/api/teacher/announcement/'

export default {
  data() {
    return {
      announcements: null
      // [
      // { name: 'check your progress', by: 'teacherA', time: '2020.11.12' },
      // { name: 'tips', by: 'teacherB', time: '2020.11.11' },
      // { name: 'announce DDL', by: 'teacherA', time: '2020.10.11' }
      // ]
    }
  },

  created() {
    this.get_announcementList()
  },
  methods: {
    miao(data) {
      // alert(data)
      window.localStorage.setItem('current_announcement', JSON.stringify(data))
    },
    get_announcementList() {
      get_announcement_teacher(getToken()).then(response => {
        this.announcements = response.data
      })
    }
  }
}
</script>

<style>

.announcetitle {
  font-weight: bold;
  color: #dddddd;
  font-size: 20px;
  transition: 0.2s ease-in-out;
  text-align: left;
  padding-top: 30px;
  margin-left: 150px;
}

.an_title {
  color: #ffffff;
}

.content::-webkit-scrollbar { /*滚动条整体*/
  width: 10px;
}

.content::-webkit-scrollbar-track { /*滚动条轨道*/
  background:#999;
  /*background: #c52d47;*/
  border-radius: 20px;

}

.content::-webkit-scrollbar-thumb { /*滚动条里面的滑块*/
  background: #c52d47;
  border-radius: 10px;
}

/*.content::-webkit-scrollbar-thumb:hover{!*滚动条鼠标事件，鼠标放上去出现的事件*!*/
/*  background:#ffffff;*/
/*}*/

.content::-webkit-scrollbar-corner { /*滚动条边角*/
  background: #c52d47;
}

.frame {
  height: 900px;
  box-shadow: 0 0 40px #c52d47;
  background: linear-gradient(215deg, #c52d47 0%, #a5325c 30%, #8b366d 100%);
  margin: 50px 15% 100px 10%;
  border-radius: 50px;
  transition: all 0.3s ease-in-out;
  transform: translate(0, 0);
}

.frame:hover {
  box-shadow: 12px 20px 20px #c52d47;
  transform: translate(-2px, -8px);
  transition: all 0.3s ease-in-out;
}

.an_title {
  font-size: 55px;
  text-align: center;
  padding-top: 40px;
  padding-bottom: 0px;
}
</style>
