<template>
  <div>
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
          <el-input
            v-model="form.cover"
            autocomplete="off"
            placeholder="图片 URL"
          />
          <img-upload
            @upload-succeed="updateCoverUrl"
          />
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
import ImgUpload from '@/components/library/ImgUpload'

export default {
  name: 'EditForm',
  components: { ImgUpload },
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
  methods: {
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
    },
    updateCoverUrl (url) {
      this.form.cover = url
    }
  }
}
</script>

<style scoped lang="less">

</style>
