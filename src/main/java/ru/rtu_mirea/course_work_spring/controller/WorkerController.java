package ru.rtu_mirea.course_work_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.course_work_spring.entity.Order;
import ru.rtu_mirea.course_work_spring.service.WorkerService;

@Controller
@RequestMapping("/worker")
public class WorkerController {
    private final WorkerService service;

    public WorkerController(WorkerService service) {
        this.service = service;
    }


    @GetMapping
    public String getPage(Model model){
        return service.getPage(model);
    }

    @PostMapping("/changeStatus/{id}")
    public String changeStatus(@ModelAttribute Order order,
                               @PathVariable(name = "id") Long id){
        return service.changeStatus(order, id);
    }
}