package ru.rtu_mirea.course_work_spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.course_work_spring.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
