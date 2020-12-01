<template>
  <el-upload
    class="img-upload"
    action="http://localhost:8443/api/covers"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :on-success="handleSuccess"
    :on-exceed="handleExceed"
    :file-list="fileList"
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
</template>

<script>
export default {
  name: 'ImgUpload',
  data () {
    return {
      fileList: []
    }
  },
  methods: {
    handleRemove (file, fileList) {
    },
    handlePreview (file) {
    },
    handleExceed (files, fileList) {
      this.$message.warning('只能上传 1 个文件')
    },
    beforeRemove (file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    handleSuccess (response) {
      this.$emit('upload-succeed', response)
      this.$message.success('上传成功')
    }
    // clear () {
    //   this.$refs.upload.clearFiles()
    // }
  }
}
</script>

<style lang="less" scoped>
.img-upload {
    margin-top: 12px;

    .el-upload__tip {
        margin-top: 6px;
        line-height: normal;
    }
}
</style>
