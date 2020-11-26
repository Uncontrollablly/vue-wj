<template>
  <div>
    <el-row style="height: 840px">
      <search-bar
        ref="searchBar"
        @onSearch="searchResult"
      />
      <el-tooltip
        v-for="item in books.slice(
          (currentPage - 1) * pagesize,
          currentPage * pagesize
        )"
        :key="item.id"
        effect="dark"
        placement="right-start"
      >
        <p
          slot="content"
          style="font-size: 14px; margin-bottom: 6px"
        >
          {{ item.title }}
        </p>
        <p
          slot="content"
          style="font-size: 13px; margin-bottom: 6px"
        >
          <span>{{ item.author }}</span> / <span>{{ item.date }}</span> /
          <span>{{ item.press }}</span>
        </p>
        <p
          slot="content"
          style="width: 300px"
          class="abstract"
        >
          {{ item.abs }}
        </p>
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
        @onSubmit="loadBooks()"
      />
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
      books: [
        {
          cover: 'https://i.loli.net/2019/04/10/5cada7e73d601.jpg',
          title: '三体',
          author: '刘慈欣',
          date: '2019-05-05',
          press: '重庆出版社',
          abs:
            '文化大革命如火如荼进行的同时。军方探寻外星文明的绝秘计划“红岸工程”取得了突破性进展。但在按下发射键的那一刻，历经劫难的叶文洁没有意识到，她彻底改变了人类的命运。地球文明向宇宙发出的第一声啼鸣，以太阳为中心，以光速向宇宙深处飞驰……'
        }
      ],
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
.cover {
  margin-bottom: 5px;
}

img {
  width: 115px;
  height: 172px;
}

.content {
  display: flex;
  justify-content: space-between;

  .info {
    .title {
      font-size: 14px;
      text-align: left;
    }

    .author {
      color: #333;
      font-size: 12px;
      text-align: left;
    }
  }

  .el-icon-delete {
    cursor: pointer;
  }
}

.abstract {
  line-height: 17px;
}

a {
  text-decoration: none;
}

a:link,
a:visited,
a:focus {
  color: #3377aa;
}

.book {
  width: 135px;
  height: 233px;
  margin-bottom: 20px;
  margin-right: 15px;
}
</style>
