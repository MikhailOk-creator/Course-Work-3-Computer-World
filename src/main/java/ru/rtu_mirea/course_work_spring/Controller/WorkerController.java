package ru.rtu_mirea.course_work_spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.rtu_mirea.course_work_spring.Model.Order;
import ru.rtu_mirea.course_work_spring.Service.WorkerService;

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