package ru.rtu_mirea.course_work_spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.course_work_spring.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByName(String name);
    User findUserByEmail(String email);
}
