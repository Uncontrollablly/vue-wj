package com.yq.wjbackend.service;

import com.yq.wjbackend.dao.BookDAO;
import com.yq.wjbackend.pojo.Book;
import com.yq.wjbackend.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookDAO bookDAO;
    @Autowired
    CategoryService categoryService;

    public List<Book> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return bookDAO.findAll(sort);
    }

    public void addOrUpdate(Book book) {
        // save: 当主键存在时更新数据，当主键不存在时插入数据
        bookDAO.save(book);
    }

    public void deleteById(int id) {
        bookDAO.deleteById(id);
    }

    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Book> result = bookDAO.findAllByCategory(category, sort);
        return result;
    }

    // DAO 里是两个参数，搜索时无论输入的是作者还是书名，都会对两个字段进行匹配。
    public List<Book> Search(String keywords) {
        return bookDAO.findAllByTitleLikeOrAuthorLike('%' + keywords + '%', '%' + keywords + '%');
    }
}
