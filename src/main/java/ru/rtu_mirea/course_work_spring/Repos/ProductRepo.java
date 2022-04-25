package ru.rtu_mirea.course_work_spring.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtu_mirea.course_work_spring.Model.Product;
import ru.rtu_mirea.course_work_spring.Model.ProductType;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findAllByType(ProductType type);
}
