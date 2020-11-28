<template>
  <div>
    <i
      class="el-icon-circle-plus-outline"
      @click="dialogVisible = true"
    />
    <el-dialog
      title="添加/修改图书"
      width="30%"
      :visible.sync="dialogVisible"
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
        <el-button @click="dialogVisible = false">取 消</el-button>
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
  data () {
    return {
      dialogVisible: false,
      form: {
        id: '',
        title: '',
        author: '',
        date: '',
        press: '',
        cover: '',
        abs: '',
        category: {
          id: '',
          name: ''
        },
        fileList: [
          {
            name: 'food.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
          },
          {
            name: 'food2.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
          }
        ]
      },
      options: [
        {
          label: '文学',
          value: '1'
        },
        {
          label: '流行',
          value: '2'
        },
        {
          label: '文化',
          value: '3'
        },
        {
          label: '生活',
          value: '4'
        },
        {
          label: '经管',
          value: '5'
        },
        {
          label: '科技',
          value: '6'
        }
      ]
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
        })
      this.dialogVisible = false
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
