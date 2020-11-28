<template>
  <div>
    <el-row>
      <search-bar
        ref="searchBar"
        @search="searchResult"
      />
      <div class="book-container">
        <el-tooltip
          v-for="item in books.slice(
            (currentPage - 1) * pagesize,
            currentPage * pagesize
          )"
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
        <edit-form
          ref="edit"
          @submit="loadBooks()"
        />
      </div>
    </el-row>
    <el-row>
      <el-pagination
        :current-page="currentPage"
        :page-size.sync="pagesize"
        :total="books.length"
      />
    </el-row>
  </div>
</template>

<script>
import EditForm from './EditForm'
import SearchBar from './SearchBar'

export default {
  name: 'Content',
  components: { EditForm, SearchBar },
  data () {
    return {
      books: [],
      currentPage: 1,
      pagesize: 17
    }
  },
  mounted: function () {
    this.loadBooks()
  },
  methods: {
    loadBooks () {
      this.$axios.get('/books').then((resp) => {
        if (resp && resp.status === 200) {
          this.books = resp.data
        }
      })
    },
    // handleCurrentChange: function (currentPage) {
    //   this.currentPage = currentPage
    //   console.log(this.currentPage)
    // },
    searchResult () {
      this.$axios
        .get('/search?keywords=' + this.$refs.searchBar.keywords, {})
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
          this.$axios.post('/delete', { id: id }).then((resp) => {
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
      // alert(id)
    },
    editBook (item) {
      this.$refs.edit.dialogFormVisible = true
      this.$refs.edit.form = {
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
}
</style>
