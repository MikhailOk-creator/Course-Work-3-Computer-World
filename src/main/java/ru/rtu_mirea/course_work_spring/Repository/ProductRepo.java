package ru.rtu_mirea.course_work_spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.course_work_spring.Model.Product;
import ru.rtu_mirea.course_work_spring.Model.ProductType;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findAllByType(ProductType type);
}
