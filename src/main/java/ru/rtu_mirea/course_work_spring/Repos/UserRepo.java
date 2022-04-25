package ru.rtu_mirea.course_work_spring.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtu_mirea.course_work_spring.Model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByName(String name);
}
