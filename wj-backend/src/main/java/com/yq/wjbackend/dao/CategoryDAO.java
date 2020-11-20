package com.yq.wjbackend.dao;

import com.yq.wjbackend.pojo.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {

}
