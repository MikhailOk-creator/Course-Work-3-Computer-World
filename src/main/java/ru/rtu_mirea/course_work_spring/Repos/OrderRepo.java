package ru.rtu_mirea.course_work_spring.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtu_mirea.course_work_spring.Model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
