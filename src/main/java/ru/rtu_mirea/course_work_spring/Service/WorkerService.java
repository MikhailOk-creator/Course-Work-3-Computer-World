package ru.rtu_mirea.course_work_spring.Service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.rtu_mirea.course_work_spring.Model.MailService;
import ru.rtu_mirea.course_work_spring.Model.Order;
import ru.rtu_mirea.course_work_spring.Model.OrderStatus;
import ru.rtu_mirea.course_work_spring.Repos.OrderRepo;

import java.util.List;

@Service
public class WorkerService {
    private final OrderRepo orderRepo;
    private final MailService mailService;

    public WorkerService(OrderRepo orderRepo, MailService mailService) {
        this.orderRepo = orderRepo;
        this.mailService = mailService;
    }

    public String getPage(Model model) {
        List<Order> orderList = orderRepo.findAll();
        model.addAttribute("orders", orderList);

        OrderStatus[] statuses = OrderStatus.values();
        model.addAttribute("statuses", statuses);
        return "workerFrom";
    }

    public String changeStatus(Order order, Long id) {
        Order orderBD = orderRepo.findById(id).get();
        orderBD.setStatus(order.getStatus());
        orderRepo.save(orderBD);

        String emailTo = orderBD.getUser_email();
        String messageTo = "Your order status(" + orderBD.getId() + ") was changed to " + orderBD.getStatus() +
                ". Thanks for your wait.";

        mailService.send(emailTo, "Updating order status", messageTo);
        return "redirect:/worker";
    }
}
