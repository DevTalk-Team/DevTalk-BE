package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByCategory(String category);
}
