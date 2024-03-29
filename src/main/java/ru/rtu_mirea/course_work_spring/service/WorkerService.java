package ru.rtu_mirea.course_work_spring.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.rtu_mirea.course_work_spring.entity.Order;
import ru.rtu_mirea.course_work_spring.entity.OrderStatus;
import ru.rtu_mirea.course_work_spring.repo.OrderRepo;

import java.util.List;

@Service
public class WorkerService {
    private final OrderRepo orderRepo;
    private final MailService mailService;

    public WorkerService(OrderRepo orderRepo, MailService mailService) {
        this.orderRepo = orderRepo;
        this.mailService = mailService;
    }

    /**
     * A method that returns the main page with orders and statuses.
     * @param model - MVC class in which attributes are added for display on the page (orders, statuses).
     * @return page with orders and statuses.
     * @see Order
     * @see OrderStatus
     **/
    public String getPage(Model model) {
        List<Order> orderList = orderRepo.findAll();
        model.addAttribute("orders", orderList);

        OrderStatus[] statuses = OrderStatus.values();
        model.addAttribute("statuses", statuses);
        return "workerFrom";
    }

    /**
     * A method that changes order status and notifies the user about it.
     * @param order - order in which the new status is stored.
     * @param id - unique identifier of the order whose status should be changed.
     * @return Home page with the changed order.
     * @see Order
     * @see #mailService
     **/
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
