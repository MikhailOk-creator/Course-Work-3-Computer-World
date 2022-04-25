package ru.rtu_mirea.course_work_spring.Model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER, WORKER;
    @Override
    public String getAuthority() {
        return name();
    }
}
