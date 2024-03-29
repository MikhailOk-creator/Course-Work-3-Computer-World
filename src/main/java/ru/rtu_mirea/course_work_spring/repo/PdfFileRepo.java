package ru.rtu_mirea.course_work_spring.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.rtu_mirea.course_work_spring.entity.PdfFile;

import java.util.Optional;

@Repository
public interface PdfFileRepo extends CrudRepository<PdfFile, Long> {
    Optional<PdfFile> findById(Long id);
}
