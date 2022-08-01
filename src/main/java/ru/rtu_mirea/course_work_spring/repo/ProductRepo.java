package ru.rtu_mirea.course_work_spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.course_work_spring.entity.Product;
import ru.rtu_mirea.course_work_spring.entity.ProductType;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findAllByType(ProductType type);
}
