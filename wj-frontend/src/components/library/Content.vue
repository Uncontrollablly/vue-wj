<template>
  <div>
    <el-row>
      <search-bar
        @search="searchResult"
      />
      <div class="book-container">
        <el-tooltip
          v-for="item in currentBooks"
          :key="item.id"
          effect="dark"
          placement="right-start"
        >
          <template slot="content">
            <p style="font-size: 14px; margin-bottom: 6px">
              {{ item.title }}
            </p>
            <p style="font-size: 13px; margin-bottom: 6px">
              <span>{{ item.author }}</span> / <span>{{ item.date }}</span> /
              <span>{{ item.press }}</span>
            </p>
            <p
              style="width: 300px"
              class="abstract"
            >
              {{ item.abs }}
            </p>
          </template>
          <el-card
            class="book"
            body-style="padding:10px"
            shadow="hover"
          >
            <div
              class="cover"
              @click="editBook(item)"
            >
              <img
                :src="item.cover"
                alt="封面"
              >
            </div>
            <div class="content">
              <div class="info">
                <div class="title">
                  <a href="">{{ item.title }}</a>
                </div>
                <div class="author">
                  {{ item.author }}
                </div>
              </div>
              <i
                class="el-icon-delete"
                @click="deleteBook(item.id)"
              />
            </div>
          </el-card>
        </el-tooltip>
        <div class="dialog">
          <i
            class="el-icon-circle-plus-outline"
            @click="dialogVisible = true"
          />
          <edit-form
            v-if="dialogVisible"
            :initial-form-data="form"
            :initial-options="options"
            :dialog-visible.sync="dialogVisible"
            @submit="loadBooks"
          />
        </div>
      </div>
    </el-row>
    <el-row>
      <el-pagination
        :current-page.sync="currentPage"
        :page-size.sync="pagesize"
        :total="books.length"
      />
    </el-row>
  </div>
</template>

<script>
import EditForm from './EditForm'
import SearchBar from './SearchBar'
import { OPTIONS } from '@/constants/index'

export default {
  name: 'Content',
  components: { EditForm, SearchBar },
  props: {
    category: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      books: [],
      currentPage: 1,
      pagesize: 25,
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
      options: OPTIONS
    }
  },
  computed: {
    currentBooks () {
      return this.books.slice(
        (this.currentPage - 1) * this.pagesize,
        this.currentPage * this.pagesize
      )
    }
  },
  watch: {
    category (newVal) {
      this.listByCategory(newVal)
    }
  },
  mounted: function () {
    this.loadBooks()
  },
  methods: {
    /**
     * 前端发请求修改后端的数据后有两种选择：
     * 1.在接收到后端返回的成功代码后，直接利用前端的数据刷新显示，
     * 2.重新执行查询以显示修改后的数据。
     * 第一种方式如果代码不够严谨，可能出现未能按期望修改数据库却返回成功代码的情况，会造成数据的不一致，
     * 第二种方式在修改数据后又一次执行查询请求更新数据，利用双向绑定更新显示。
     */
    loadBooks () {
      this.$axios.get('/books').then((resp) => {
        if (resp && resp.status === 200) {
          this.books = resp.data
        }
      })
    },
    searchResult (keywords) {
      this.$axios
        .get('/search?keywords=' + keywords)
        .then((resp) => {
          if (resp && resp.status === 200) {
            this.books = resp.data
          }
        })
    },
    deleteBook (id) {
      this.$confirm('此操作将永久删除该书籍, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$axios.post('/delete', { id }).then((resp) => {
            if (resp && resp.status === 200) {
              this.loadBooks()
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    editBook (item) {
      this.form = {
        id: item.id,
        cover: item.cover,
        title: item.title,
        author: item.author,
        date: item.date,
        press: item.press,
        abs: item.abs,
        category: {
          id: item.category.id.toString(),
          name: item.category.name
        }
      }
      this.dialogVisible = true
    },
    listByCategory (cid) {
      const url = 'categories/' + cid + '/books'
      this.$axios.get(url).then(resp => {
        if (resp && resp.status === 200) {
          this.books = resp.data
        }
      })
    }
  }
}
</script>

<style scoped lang="less">
.book-container {
  display: flex;
  flex-wrap: wrap;

  .book {
    width: 150px;
    height: 233px;
    margin-bottom: 20px;
    margin-right: 15px;

    .cover {
      margin-bottom: 5px;
    }

    img {
      width: 100%;
      height: 172px;
    }

    .content {
      display: flex;
      justify-content: space-between;

      .info {
        .title {
          font-size: 14px;
          text-align: left;

          a {
            text-decoration: none;

            &:link,
            &:visited,
            &:focus {
              color: #3377aa;
            }
          }
        }

        .author {
          color: #333;
          font-size: 12px;
          text-align: left;
        }
      }
    }

    .el-icon-delete {
      cursor: pointer;

      &::before {
        vertical-align: super;
      }
    }
  }

  .abstract {
    line-height: 17px;
  }

  .dialog {
  .el-icon-circle-plus-outline {
    margin: 50px 0 0 20px;
    font-size: 100px;
    color: black;
    float: left;
    cursor: pointer;
  }
}
}
</style>
