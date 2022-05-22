package ru.rtu_mirea.course_work_spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.course_work_spring.Model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}
