package com.yq.wjbackend.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.yq.wjbackend.pojo.Book;
import com.yq.wjbackend.pojo.Category;

public interface BookDAO extends JpaRepository<Book,Integer> {
    // findAllByCategory() 之所以能实现，是因为在Book类中有@ManyToOne\@JoinColumn注解
    // 实际上是把 category 对象的 id 属性作为 cid 进行了查询
    List<Book> findAllByCategory(Category category);
    // 根据标题或作者进行模糊查询”，参数是两个 String，分别对应标题或作者
    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}
