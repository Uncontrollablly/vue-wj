<template>
  <div>
    <i
      class="el-icon-circle-plus-outline"
      @click="$emit('update:dialogVisible', true)"
    />
    <el-dialog
      title="添加/修改图书"
      width="30%"
      :visible="dialogVisible"
      :before-close="close"
      @close="clear"
    >
      <el-form
        :model="form"
        label-width="70px"
      >
        <el-form-item label="书名">
          <el-input
            v-model="form.title"
            autocomplete="off"
            placeholder="不加《》"
          />
        </el-form-item>
        <el-form-item label="作者">
          <el-input
            v-model="form.author"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="出版日期">
          <el-input
            v-model="form.date"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="出版社">
          <el-input
            v-model="form.press"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="封面">
          <el-upload
            class="upload"
            action="https://localhost:8443"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :on-exceed="handleExceed"
            :file-list="form.fileList"
          >
            <el-button
              size="small"
              type="primary"
            >
              点击上传
            </el-button>
            <div
              slot="tip"
              class="el-upload__tip"
            >
              只能上传jpg/png文件，且不超过500kb
            </div>
          </el-upload>
        </el-form-item>
        <el-form-item label="简介">
          <el-input
            v-model="form.abs"
            type="textarea"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label="分类">
          <el-select
            v-if="form.category"
            v-model="form.category.id"
            placeholder="请选择分类"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="close">取 消</el-button>
        <el-button
          type="primary"
          @click="onSubmit"
        >
          确 定
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'EditForm',
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
      required: true
    },
    initialFormData: {
      type: Object,
      default: () => {}
    },
    initialOptions: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      form: this.initialFormData,
      options: this.initialOptions
    }
  },
  mounted () {
    // 发送请求获取数据，设置data.form
  },
  methods: {
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePreview (file) {
      console.log(file)
    },
    handleExceed (files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    close () {
      this.$emit('update:dialogVisible', false)
    },
    clear () {
      this.form = {
        id: '',
        title: '',
        author: '',
        date: '',
        press: '',
        cover: '',
        abs: '',
        category: ''
      }
    },
    onSubmit () {
      this.$axios
        .post('/books', {
          id: this.form.id,
          cover: this.form.cover,
          title: this.form.title,
          author: this.form.author,
          date: this.form.date,
          press: this.form.press,
          abs: this.form.abs,
          category: this.form.category
        }).then(resp => {
          if (resp && resp.status === 200) {
            this.$emit('submit')
          }
        }).finally(() => {
          this.close()
        })
    }
  }
}
</script>

<style scoped lang="less">
  .el-icon-circle-plus-outline {
    margin: 50px 0 0 20px;
    font-size: 100px;
    color: black;
    float: left;
    cursor: pointer;
  }
</style>
