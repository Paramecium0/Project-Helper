<template>
  <div class="components-container">
    <div id="t_border3_1">
      <div class="create_title">Authority</div>

      <el-form
        ref="ruleForm"
        :model="create_ruleForm"
        status-icon
        :rules="rules"
        label-width="120px"
        class="demo-ruleForm"
      >
        <!--        <el-form-item label="课程" prop="course">-->
        <!--          <el-select v-model="create_ruleForm.course" placeholder="请选择相应课程">-->
        <!--            <el-option label="课程一" value="shanghai" />-->
        <!--            <el-option label="课程二" value="beijing" />-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->

        <el-form-item label="项目名称" prop="name">
          <el-input v-model="create_ruleForm.name" disabled style="padding-right: 200px;" />
        </el-form-item>

        <el-form-item label="小组人数" prop="population" required>
          <el-slider
            v-model="create_ruleForm.population"
            range
            show-stops
            input-size="medium"
            :min="1"
            :max="6"
            style="padding-right: 200px;"
          />
        </el-form-item>

        <el-form-item label="答辩周" prop="time">

          <el-select v-model="create_ruleForm.time" multiple placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <!--          <el-cascader-->
          <!--            v-model="create_ruleForm.time"-->
          <!--            :options="options"-->
          <!--            :props="props"-->
          <!--            clearable-->
          <!--          />-->
        </el-form-item>

        <el-form-item label="跨班组队" prop="across_lab" required>
          <el-switch
            v-model="create_ruleForm.across_lab"
            style="display: block; margin-top: 7px;"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="允许"
            inactive-text="不允许"
          />
        </el-form-item>
        <el-form-item label="技能设定" prop="skills">
          <el-tag
            v-for="tag in dynamicTags"
            :key="tag"
            v-model="create_ruleForm.skills"
            closable
            :disable-transitions="false"
            @close="handleClose(tag)"
          >
            {{ tag }}
          </el-tag>
          <el-input
            v-if="inputVisible"
            ref="saveTagInput"
            v-model="inputValue"
            class="input-new-tag"
            size="small"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
          />
          <el-button v-else class="button-new-tag el-icon-circle-plus" plain type="primary" size="small" @click="showInput">  Add</el-button>
        </el-form-item>

        <el-form-item
          label="停止组队时间"
          style="padding-right: 200px;"
        >
          <el-col :span="11">
            <el-form-item prop="groupingEndTime">
              <el-date-picker
                v-model="create_ruleForm.groupingEndTime"
                type="date"
                placeholder="选择日期"
                style="width: 90%;"
              />
            </el-form-item>
          </el-col>
          <el-col class="line" :span="2"> ---</el-col>
          <el-col :span="11">
            <el-time-picker
              v-model="create_ruleForm.groupingEndTime2"
              placeholder="选择时间"
              style="width: 100%;"
            />
          </el-col>
        </el-form-item>

        <el-form-item label="强制加入" prop="grouping">
          <el-switch
            v-model="create_ruleForm.force"
            style="display: block; margin-top: 7px;"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="开启"
            inactive-text="禁用"
          />
        </el-form-item>

        <!--        <el-form-item label="答辩时间填写" prop="pre">-->
        <!--          <el-switch-->
        <!--            v-model="create_ruleForm.pre"-->
        <!--            style="display: block; margin-top: 7px;"-->
        <!--            active-color="#13ce66"-->
        <!--            inactive-color="#ff4949"-->
        <!--            active-text="开启"-->
        <!--            inactive-text="禁用"-->
        <!--          />-->
        <!--        </el-form-item>-->

        <el-form-item>
          <el-button type="primary" plain @click="submitForm('ruleForm')">确认更新</el-button>
          <el-button type="primary" @click="resetForm('ruleForm')">重置</el-button>
          <el-popconfirm
            confirm-button-text="确定"
            cancel-button-text="取消"
            icon="el-icon-info"
            icon-color="red"
            title="确定删除本Project吗？"
            placement="top"
            style="float: right;margin-right: 150px;"
            @onConfirm="deleteProject()"
          >
            <el-button slot="reference" plain type="danger">删除此Project</el-button>
          </el-popconfirm>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>

import { get_projectInfo } from '@/api/teacher/createProject'
import { update_projectInfo } from '@/api/teacher/createProject'
import { drop_project } from '@/api/teacher/createProject'


export default {
  name: 'DndListDemo',
  data() {
    var checkdate1 = (rule, value, callback) => {
      // console.log(value)
      const d = new Date()
      setTimeout(() => {
        if (value < d) {
          return callback(new Error('请选择当前时间之后的时间'))
        } else {
          callback()
        }
      }, 500
      )
    }

    return {
      props: { multiple: true },
      dynamicTags: [],
      inputVisible: false,
      inputValue: '',
      create_ruleForm: {
        // course: '',
        name: localStorage.getItem('current_project').substring(1, (localStorage.getItem('current_project').length - 1)),
        population: [2, 4],
        time: [],
        groupingEndTime: '',
        groupingEndTime2: '',
        across_lab: true,
        force: true,
        skills: []
      },
      rules: {
        course: [
          { required: true, message: '请输入课程名称', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入项目名称', trigger: 'blur' }
        ],
        time: [
          { required: true, message: '请选择可答辩时间', trigger: 'blur' }
        ],
        groupingEndTime: [
          { validator: checkdate1, trigger: 'blur' }
        ]
      },
      options: [{
        value: 'Week 1',
        label: 'Week 1'
      }, {
        value: 'Week 2',
        label: 'Week 2'
      }, {
        value: 'Week 3',
        label: 'Week 3'
      }, {
        value: 'Week 4',
        label: 'Week 4'
      }, {
        value: 'Week 5',
        label: 'Week 5'
      }, {
        value: 'Week 6',
        label: 'Week 6'
      }, {
        value: 'Week 7',
        label: 'Week 7'
      }, {
        value: 'Week 8',
        label: 'Week 8'
      }, {
        value: 'Week 9',
        label: 'Week 9'
      }, {
        value: 'Week 10',
        label: 'Week 10'
      }, {
        value: 'Week 11',
        label: 'Week 11'
      }, {
        value: 'Week 12',
        label: 'Week 12'
      }, {
        value: 'Week 13',
        label: 'Week 13'
      }, {
        value: 'Week 14',
        label: 'Week 14'
      }, {
        value: 'Week 15',
        label: 'Week 15'
      }, {
        value: 'Week 16',
        label: 'Week 16'
      }, {
        value: 'Week 17',
        label: 'Week 17'
      }]
    }
  },
  computed: {},
  created() {
    this.get_project()
  },
  methods: {
    get_project() {
      get_projectInfo(localStorage.getItem('current_project_id')).then(response => {
        this.create_ruleForm.population = [response.data[0].min, response.data[0].max]
        this.create_ruleForm.across_lab = response.data[0].across_lab
        this.create_ruleForm.force = response.data[0].force_join
        this.dynamicTags = response.data[0].all_tags
        this.create_ruleForm.time = response.data[0].project_pre_week
        this.create_ruleForm.groupingEndTime = response.data[0].project_ddl.substring(0,9)
        this.create_ruleForm.groupingEndTime2 = response.data[0].project_ddl

      })
    },


    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          update_projectInfo({
            max: this.create_ruleForm.population[1],
            min: this.create_ruleForm.population[0],
            project_id: localStorage.getItem('current_project_id'),
            project_name: localStorage.getItem('current_project').substring(1, (localStorage.getItem('current_project').length - 1)),
            pro_grouping_endDay: this.create_ruleForm.groupingEndTime,
            pro_grouping_endHms: this.create_ruleForm.groupingEndTime2,
            across_lab: this.create_ruleForm.across_lab,
            force_join: this.create_ruleForm.force,
            all_tags:   this.create_ruleForm.skills,
            project_pre_week: this.create_ruleForm.time
          }).then(response => {
            
          })
          this.$message({
            message: '修改成功！',
            type: 'success'
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    deleteProject() {
      drop_project(localStorage.getItem('current_project_id')).then(response => {
      })
      this.$message({
        message: '删除成功！',
        type: 'success'
      })
    },
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1)
    },

    showInput() {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },

    handleInputConfirm() {
      const inputValue = this.inputValue
      if (inputValue) {
        this.dynamicTags.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
      this.create_ruleForm.skills = this.dynamicTags
      // console.log(this.create_ruleForm.skills)
    }
  }
}
</script>

<style lang="scss">
@import "~@/styles/variables.scss";

.components-container {
  position: relative;
  height: 100vh;
}

.el-tag, .el-button, .el-input__inner, .el-textarea__inner {
  border-radius: 20px !important;
}

.el-popover {
  border-radius: 20px;
  box-shadow: 0 0 40px #CCCCCC;
}

#t_border3_1 {
  height: 100%;
  width: 60%;
  border: 2px solid $primary;
  margin-left: 300px;
  border-radius: 50px;
  transform: translate(0, 0);
  transition: all 0.3s ease-in-out;
  box-shadow: 10px 10px 20px $primary;
}

#t_border3_1:hover {
  box-shadow: 20px 20px 20px $primary;
  transform: translate(-5px, -5px);
  transition: 0.3s ease-in-out;
}

.create_title {
  font-weight: bold;
  color: $primary;
  font-size: 40px;
  transition: 0.2s ease-in-out;
  text-align: center;
  padding-top: 20px;
  padding-bottom: 40px;

}

</style>
