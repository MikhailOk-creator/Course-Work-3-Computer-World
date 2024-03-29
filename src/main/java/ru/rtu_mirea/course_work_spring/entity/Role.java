package ru.rtu_mirea.course_work_spring.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER, WORKER, PR5;
    @Override
    public String getAuthority() {
        return name();
    }
}
