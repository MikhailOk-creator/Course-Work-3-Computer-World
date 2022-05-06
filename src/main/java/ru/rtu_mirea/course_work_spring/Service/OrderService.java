package ru.rtu_mirea.course_work_spring.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.rtu_mirea.course_work_spring.Model.*;
import ru.rtu_mirea.course_work_spring.Repos.OrderRepo;
import ru.rtu_mirea.course_work_spring.Repos.ProductRepo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class OrderService {
    private final CartService cartService;
    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final MailService mailService;

    @Value("${cookie.orders.name}")
    private String orderName;

    public OrderService(CartService cartService, OrderRepo orderRepo, ProductRepo productRepo, MailService mailService) {
        this.cartService = cartService;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.mailService = mailService;
    }

    /**
     * Checkout method.
     * @param user - user who places the order.
     * @param order - order.
     * @param errors - errors that come from the front in case something goes wrong with the order.
     * @param cart - cookie string that stores the id of items in the cart, separated by '_'.
     * @param orderCookie - string that stores order items.
     * @param model - MVC class to which attributes are added for display on the page
     * @param response - the response that is returned to the user (the string in the cookie).
     * @param attributes - attributes that are filled in and later given to the user on the page.
     * @return Several options: if the database has the proper amount of products, the order is completed with the user's fields and
     * status, the order is stored in the database and the user is notified by email, the cart is cleared. Service returns the main page.
     * Another option - there are not enough products in the database, so the attributes attribute 'numberErr' is added and the checkout page is returned.
     * @see User
     * @see Order
     * @see CartService
     **/
    public String placeOrder(User user, Order order, Errors errors, String cart, String orderCookie, Model model, HttpServletResponse response, RedirectAttributes attributes) {
        if(errors.hasErrors()){
            List<FieldError> list = errors.getFieldErrors();
            for (FieldError f : list) {
                attributes.addFlashAttribute(f.getField(), f.getDefaultMessage());
            }
            return "redirect:/order";
        }

        Map<String, List<Product>> resultMap = cartService.getProductsMap(cart);
        if (!resultMap.get("err").isEmpty()){
            attributes.addFlashAttribute("numberErr", "The number of these items in stock has changed. " +
                    "Change the number of items or replace them with alternatives.");
            attributes.addFlashAttribute("list", new HashSet<>(resultMap.get("err")));
            return "redirect:/order";
        }

        order.setUser(user);
//        order.setProducts(resultMap.get("res"));
        order.setStatus(Collections.singleton(OrderStatus.PROCESSING));
        orderRepo.save(order);

        for(Product product : resultMap.get("res")){
            product.setOrder(order);
            productRepo.save(product);
        }

        sendMessage(user, order);

        Map<Integer, Product> changeProductMap = new HashMap<>();

        for (Product product: new HashSet<>(resultMap.get("res"))){
            changeProductMap.put(Collections.frequency(resultMap.get("res"), product), product);
        }

        for(Map.Entry<Integer, Product> entry: changeProductMap.entrySet()){
            Product productBD = productRepo.findById(entry.getValue().getId()).get();
            productBD.setNumber(productBD.getNumber() - entry.getKey());
            productRepo.save(productBD);
        }

        cartService.clear(response);
        Cookie cookie = new Cookie(orderName, (orderCookie != null ? orderCookie : "") + order.getId() + "_");
        cookie.setPath("/");
        cookie.setMaxAge(2147483647);
        response.addCookie(cookie);

        return "redirect:/";
    }

    private void sendMessage(User user, Order order){
        List<Product> products = orderRepo.findById(order.getId()).get().getProducts();
        String message = String.format("Thank you for choosing us!\n" +
                "Your order number : " + order.getId() + '.' +
                "\nThe amount of your order : " + getFullyOrderCost(products) + '.' +
                "\nProducts : " + getNameForMail(products) +
                "\nOur operator will contact you soon, expect a call.");
        String email = "";
        if(user != null){
            if(user.getEmail() != null || !user.getEmail().isEmpty())
                email = user.getEmail();
        } else {
            email = order.getUser_email();
        }
        mailService.send(email, "New order", message);
    }

    private String getNameForMail(List<Product> products){
        String resultStr = "";

        for (Product product : products){
            resultStr += product.getName() + "\n";
        }
        return resultStr;
    }

    private Long getFullyOrderCost(List<Product> products){
        Long sum = products.stream().mapToLong(Product::getPrice).sum();
        return sum;
    }

    /**
     * A method that returns the ordering page. The logic depends on whether the user is authorized or not (for an authorized
     * user orders are taken from the database, for an unauthorized - from a cookie).
     * @param user - user whose orders you want to view.
     * @param cookie - string that contains orders of unauthorized user.
     * @param model - MVC class in which attributes are added for display on the page (orders).
     * @return Page with all orders.
     * @see User
     * @see Order
     **/
    public String getOrderList(User user, String cookie, Model model) {
        List<Order> orders = new ArrayList<>();
        if (user != null){
            orders = user.getOrders();
        } else {
            if(cookie != null || !cookie.isEmpty()){
                List<String> orderIDs = getOrderIDs(cookie);
                for(String id: orderIDs){
                    if(!id.isEmpty()){
                        orders.add(orderRepo.findById(Long.parseLong(id)).get());
                    }
                }
            }
        }
        model.addAttribute("orders", orders);
        return "userOrderList";
    }

    private List<String> getOrderIDs(String cookie){
        return Arrays.asList(cookie.split("_"));
    }

    /**
     * A method that deletes an order by its unique identifier, the logic is divided depending on whether
     * whether the user is authorized (deleted from the database) or not (deleted from the cookie).
     * @param id - the unique identifier of the order to be deleted.
     * @param cookie - string that contains orders of an unauthorized user.
     * @param user - system user
     * @param response - the response that is returned to the user (a string in the cookie).
     * @return The page that contains the system user's orders.
     **/
    public String deleteOrder(Long id, String cookie, User user, HttpServletResponse response) {
        if (user != null){
            orderRepo.deleteById(id);
        } else {
            List<String> fixedOrderIDs = getOrderIDs(cookie);
            List<String> orderIDs = new ArrayList<>(fixedOrderIDs);

//            orderIDs.remove(String.valueOf(id));
            for(String stringID : fixedOrderIDs){
                if(stringID.equals(Long.toString(id))){
                    orderIDs.remove(stringID);
                }
            }

            String newCookieStr = "";

            for (String stringId: orderIDs){
                newCookieStr += stringId + '_';
            }
            Cookie newCookie = new Cookie(orderName,
                    (newCookieStr));
            newCookie.setPath("/");
            newCookie.setMaxAge(2147483647);
            response.addCookie(newCookie);
        }

        return "redirect:/order/list";
    }
}
