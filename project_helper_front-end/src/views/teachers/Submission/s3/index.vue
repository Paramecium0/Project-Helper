<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-form-item style="margin-bottom: 40px;" prop="title" label="Title :">
              <el-input v-model="postForm.title" :maxlength="100" placeholder="Please input title" style="width: 50%" name="name" required>
                Title
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        <el-form-item style="margin-bottom: 40px;" label-width="70px" label="Send to:">-->
        <!--          <el-select v-model="postForm.content_short" multiple placeholder="Tag choices" style="float:left; width: 20%;">-->
        <!--            <el-option-->
        <!--              v-for="item in options2"-->
        <!--              :key="item.value"-->
        <!--              :label="item.label"-->
        <!--              :value="item.value"-->
        <!--            />-->
        <!--          </el-select>-->
        <!--          <div style="float: left;margin-left: 20px;margin-right: 20px;"> or</div>-->
        <!--          <el-input-->
        <!--            v-model="postForm.content_short"-->
        <!--            :rows="1"-->
        <!--            type="textarea"-->
        <!--            class="article-textarea"-->
        <!--            placeholder="Please input SID"-->
        <!--            autosize-->
        <!--            style="float: left;width: 40%;border-radius: 20px;"-->
        <!--          />-->
        <!--        </el-form-item>-->
        <el-form-item label="Deadline :">
          <el-date-picker
            v-model="postForm.time"
            format="yyyy-MM-dd HH:mm"
            type="datetime"
            placeholder="选择日期时间"
            style="float: left;"
          />
        </el-form-item>
        <el-form-item prop="content" style="margin-bottom: 30px;">
          <markdown-editor ref="markdownEditor" v-model="postForm.content" :language="language" height="300px" />
        </el-form-item>
      </div>
      <div class="transmit_upload">
        <el-upload
          class="upload-demo"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="5"
          :on-exceed="handleExceed"
          :file-list="fileList"
        >
          <el-button size="small" plain type="primary">添加附件</el-button>
          <!--                <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
        </el-upload>
      </div>
      <div style="float: right;margin-right: 100px;margin-bottom: 100px;">
        <el-button v-loading="loading" style="margin-left: 10px;" plain type="success" @click="submitForm">
          Send
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import MarkdownEditor from '@/components/MarkdownEditor'
// import Tinymce from '@/components/Tinymce'
// import Upload from '@/components/Upload/SingleImage3'
// import MDinput from '@/components/MDinput'
// import Sticky from '@/components/Sticky' // 粘性header组件
import { validURL } from '@/utils/validate'
import { fetchArticle } from '@/api/article'
import { searchUser } from '@/api/remote-search'
// import Warning from './Warning'
// import { CommentDropdown, PlatformDropdown, SourceUrlDropdown } from './Dropdown'

const defaultForm = {
  status: 'draft',
  title: '', // 文章题目
  content: '', // 文章内容
  to: '',
  source_uri: '', // 文章外链
  image_uri: '', // 文章图片
  time: undefined,
  id: undefined,
  platforms: ['a-platform'],
  comment_disabled: false,
  importance: 0
}

export default {
  name: 'ArticleDetail',
  components: { MarkdownEditor },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        this.$message({
          message: rule.field + '为必传项',
          type: 'error'
        })
        callback(new Error(rule.field + '为必传项'))
      } else {
        callback()
      }
    }
    const validateSourceUri = (rule, value, callback) => {
      if (value) {
        if (validURL(value)) {
          callback()
        } else {
          this.$message({
            message: '外链url填写不正确',
            type: 'error'
          })
          callback(new Error('外链url填写不正确'))
        }
      } else {
        callback()
      }
    }
    return {
      postForm: Object.assign({}, defaultForm),
      loading: false,
      userListOptions: [],
      rules: {
        image_uri: [{ validator: validateRequire }],
        title: [{ validator: validateRequire }],
        content: [{ validator: validateRequire }],
        source_uri: [{ validator: validateSourceUri, trigger: 'blur' }]
      },
      tempRoute: {},
      fileList: [{
        name: 'food.jpeg',
        url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
      }, {
        name: 'food2.jpeg',
        url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
      }],
      options2: [
        {
          value: '全体学生',
          label: '全体学生'
        }, {
          value: 'lab1',
          label: 'Lab 1'
        }, {
          value: 'lab2',
          label: 'Lab 2'
        }, {
          value: 'lab3',
          label: 'Lab 3'
        }, {
          value: 'lab4',
          label: 'Lab 4'
        }, {
          value: '已完成组队小组',
          label: '已完成组队小组'
        }, {
          value: '未完成组队小组',
          label: '未完成组队小组'
        }, {
          value: '无效小组',
          label: '无效小组'
        }, {
          value: '有效小组',
          label: '有效小组'
        }, {
          value: '未组队个人',
          label: '未组队个人'
        }, {
          value: '已组队个人',
          label: '已组队个人'
        }
      ]
    }
  },
  computed: {
    contentShortLength() {
      return this.postForm.content_short.length
    },
    displayTime: {
      // set and get is useful when the data
      // returned by the back end api is different from the front end
      // back end return => "2013-06-25 06:59:25"
      // front end need timestamp => 1372114765000
      get() {
        return (+new Date(this.postForm.display_time))
      },
      set(val) {
        this.postForm.display_time = new Date(val)
      }
    }
  },
  created() {
    if (this.isEdit) {
      const id = this.$route.params && this.$route.params.id
      this.fetchData(id)
    }

    // Why need to make a copy of this.$route here?
    // Because if you enter this page and quickly switch tag, may be in the execution of the setTagsViewTitle function, this.$route is no longer pointing to the current page
    // https://github.com/PanJiaChen/vue-element-admin/issues/1221
    this.tempRoute = Object.assign({}, this.$route)
  },
  methods: {
    fetchData(id) {
      fetchArticle(id).then(response => {
        this.postForm = response.data

        // just for test
        this.postForm.title += `   Article Id:${this.postForm.id}`
        this.postForm.content_short += `   Article Id:${this.postForm.id}`

        // set tagsview title
        this.setTagsViewTitle()

        // set page title
        this.setPageTitle()
      }).catch(err => {
        console.log(err)
      })
    },
    setTagsViewTitle() {
      const title = 'Edit Article'
      const route = Object.assign({}, this.tempRoute, { title: `${title}-${this.postForm.id}` })
      this.$store.dispatch('tagsView/updateVisitedView', route)
    },
    setPageTitle() {
      const title = 'Edit Article'
      document.title = `${title} - ${this.postForm.id}`
    },
    submitForm() {
      console.log(this.postForm)
      this.$refs.postForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$notify({
            title: '成功',
            message: '发布文章成功',
            type: 'success',
            duration: 2000
          })
          this.postForm.status = 'published'
          this.loading = false
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    draftForm() {
      if (this.postForm.content.length === 0 || this.postForm.title.length === 0) {
        this.$message({
          message: '请填写必要的标题和内容',
          type: 'warning'
        })
        return
      }
      this.$message({
        message: '保存成功',
        type: 'success',
        showClose: true,
        duration: 1000
      })
      this.postForm.status = 'draft'
    },
    getRemoteUserList(query) {
      searchUser(query).then(response => {
        if (!response.data.items) return
        this.userListOptions = response.data.items.map(v => v.name)
      })
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";

.createPost-container {
  position: relative;

  .createPost-main-container {
    padding: 40px 45px 20px 50px;

    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;

      .postInfo-container-item {
        float: left;
      }
    }
  }

  .word-counter {
    width: 40px;
    position: absolute;
    right: 10px;
    top: 0px;
  }
}

.article-textarea ::v-deep {
  textarea {
    padding-right: 40px;
    border-radius: 20px;
    //width: 60%;
    //float: left;
    //resize: none;
    //border: none;
    //border-radius: 0px;
    //border-bottom: 1px solid #bfcbd9;
  }
}

.transmit_upload {
  float: left;
  margin-left: 100px;
}
</style>
