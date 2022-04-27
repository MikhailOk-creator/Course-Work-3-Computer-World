package ru.rtu_mirea.course_work_spring.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.rtu_mirea.course_work_spring.DTO.CartProduct;
import ru.rtu_mirea.course_work_spring.Model.Product;
import ru.rtu_mirea.course_work_spring.Repos.ProductRepo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/** A class containing shopping cart logic for both authorized and unauthorized users. **/
@Service
public class CartService {
    @Value("${cookies.name}")
    private String cartName;

    private final ProductRepo productRepo;

    public CartService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    /**
     * Method of adding an item to cart.
     * @param cart - list of items (their id) already added to cart, separated by underscore.
     * @param id - unique identifier of the new item to be added.
     * @param response - response returned to the user (the cookie will store the new value).
     * @return Returns the user to the home page.
     * @see Cookie
     **/
    public String add (String cart, Long id, HttpServletResponse response) {
        Cookie cookie = new Cookie(cartName, (cart != null ? cart : "") + id + "_");
        cookie.setPath("/");
        cookie.setMaxAge(2147483647);
        response.addCookie(cookie);
        return  "redirect:/";
    }

    private List<String> getListOfIDs(String cookie) {
        return Arrays.asList(cookie.split(("_")));
    }

    /**
     * A method that searches for products whose id is contained in the shopping cart.
     * @param cookie is a string containing the id of the products in the cart, separated by underscore.
     * @return Returns a map that contains two parameters: the first, whose key is "res", and value is the list of products, the number
     * which is in the database; the second, whose key is "err" and whose value is a sheet of products whose quantity is not in the database.
     * @see Product
     **/
    public Map<String, List<Product>> getProductsMap(String cookie){
        List<String> listOfIDs = new ArrayList<>(getListOfIDs(cookie));

        Map<Long, Integer> mapIDs = new HashMap<>();
        for (int i = 0; i < listOfIDs.size(); i++) {
            Long temp = Long.parseLong(listOfIDs.get(i));
            if (!mapIDs.containsKey(temp)) {
                mapIDs.put(temp, 1);
            } else {
                mapIDs.put(temp, mapIDs.get(temp) + 1);
            }
        }
        List<Product> errorProducts = new ArrayList<>();
        List<Product> resultProducts = new ArrayList<>();

        Map<String, List<Product>> resultMap = new LinkedHashMap<>();
        for (Map.Entry<Long, Integer> entry : mapIDs.entrySet()) {
            Product product = productRepo.findById(entry.getKey()).get();

            if(product != null) {
                if (product.getNumber() < entry.getValue()) {
                    errorProducts.add(product);
                } else {
                    for (int i = 0; i < entry.getValue(); i++) {
                        resultProducts.add(product);
                    }
                }
            }
        }
        resultMap.put("res", resultProducts);
        resultMap.put("err", errorProducts);
        return resultMap;
    }

    /**
     * A method that returns the size of a filled cart.
     * @param cart - string of cookie with id of items separated by '_'.
     * @param response - response that is returned to the user (a new value will be stored in the cookie).
     * @return Cart size (in case there are fewer products in the database, the size of the cart will change)
     * {@link #getListOfIDs(String)}
     * @see Cookie
     **/
    public Integer getSize(String cart, HttpServletResponse response){
        if(cart != null) {
            boolean changes = false;

            List<String> listOfIDs = getListOfIDs(cart);
            Map<Long, Integer> mapIDs = new HashMap<>();

            for (String stringID : listOfIDs) {
                Long temp = Long.parseLong(stringID);
                if (!mapIDs.containsKey(temp)) {
                    try {
                        if (productRepo.findById(temp) == null)
                            changes = true;
                        else
                            mapIDs.put(temp, 1);
                    } catch (Exception e){
                        changes = true;
                    }

                } else {
                    if (productRepo.findById(temp).get().getNumber() > mapIDs.get(temp))
                        mapIDs.put(temp, mapIDs.get(temp) + 1);
                    else
                        changes = true;
                }
            }
            if(changes){
                StringBuilder stringBuilder = new StringBuilder();
                int count = 0;
                for(Map.Entry<Long, Integer> entry : mapIDs.entrySet()){
                    for(int i = 0; i < entry.getValue(); i++){
                        stringBuilder.append(entry.getKey()).append("_");
                        count++;
                    }
                }
                Cookie cookie = new Cookie(cartName, stringBuilder.toString());
                cookie.setPath("/");
                cookie.setMaxAge(2147483647);
                response.addCookie(cookie);
                return count;
            }
            return listOfIDs.size();
        }
        return 0;
    }

    /**
     * Method that returns the product cart page.
     * @param cart - cookie string with the id of products separated by '_'.
     * @param model - MVC class in which attributes are added for display on the page (emptyProdErr).
     * @param response - response that will be returned to user (string in cookie).
     * @return Returns the product cart page (if the cart is empty, the emptyCart attribute will be added to the model, or if
        the item * no longer exists in the database, it will be removed from the cart and the emptyProdErr attribute will be added to the model).
     * @see CartProduct
     * @seeProduct
     **/
    public String getPage(String cart, Model model, HttpServletResponse response) {
        if(cart == null){
            model.addAttribute("visibility", false);
            model.addAttribute("emptyCart", "Корзина пуста. Добавьте товары и возвращайтесь)");
        } else{
            model.addAttribute("visibility", true);
            List<String> listOfIDs = getListOfIDs(cart);
            Map<Long, Long> mapIDs = new HashMap<>();
            boolean changes = false;

            for (String stringID : listOfIDs) {
                Long temp = Long.parseLong(stringID);
                if (!mapIDs.containsKey(temp)) {
                    mapIDs.put(temp, 1L);
                } else {
                    mapIDs.put(temp, mapIDs.get(temp) + 1);
                }
            }

            List<CartProduct> toUser = new ArrayList<>();
            boolean changeCookieFlag = false;
            for(Map.Entry<Long, Long> entry : mapIDs.entrySet()){
                Optional<Product> optionalBD = productRepo.findById(entry.getKey());
                if(!optionalBD.isPresent()){
                    changeCookieFlag = true;
                    changing(entry.getKey(), 0L, cart, response);
                    continue;
                }
                Product productBD = productRepo.findById(entry.getKey()).get();
                Long price = 0L;

                CartProduct tempProduct = new CartProduct();
                tempProduct.setId(entry.getKey());
                tempProduct.setType(productBD.getType());
                tempProduct.setName(productBD.getName());
                tempProduct.setDescription(productBD.getDescription());
                tempProduct.setImageUrl(productBD.getImageUrl());
                tempProduct.setNumberInBD(productBD.getNumber());
                tempProduct.setNumberInCart(entry.getValue());

                for(int i = 0; i < entry.getValue(); i++){
                    price += productBD.getPrice();
                }
                tempProduct.setPrice(price);
                toUser.add(tempProduct);
            }
            if(changeCookieFlag)
                model.addAttribute("emptyProdErr",
                        "Некоторые категории товаров были удалены из вашей корзины, " +
                                "поскольку данные товары не содержатся больше в магазине." +
                                " Приносим свои извинения)");
            model.addAttribute("products", toUser);
        }

        return "cart";
    }

    /**
     * Method for changing the quantity of an item in the cart.
     * @param id - unique identifier of the product whose quantity should be changed.
     * @param newNumber - new quantity of the product.
     * @param cookie - cookie string with product id separated by '_'.
     * @param response - response that will be returned to user (string in cookie).
     * @return cart page with items.
     **/
    public String changeNumberProduct(Long id, Long newNumber, String cookie, HttpServletResponse response) {
        changing(id, newNumber, cookie, response);
        return "redirect:/cart";
    }

    private void changing(Long id, long newNumber, String cookie, HttpServletResponse response) {
        List<String> stringIDs = getListOfIDs(cookie);
        String strID = Long.toString(id);
        StringBuilder stringBuilder = new StringBuilder();
        for(String str: stringIDs) {
            if(!str.equals(strID))
                stringBuilder.append(str).append("_");
        }
        for(int i = 0; i < newNumber; i++)
            stringBuilder.append(id).append("_");

        Cookie newCookie = new Cookie(cartName, stringBuilder.toString());
        newCookie.setPath("/");
        newCookie.setMaxAge(2147483647);
        response.addCookie(newCookie);
    }

    /**
     * A method that clears the shopping cart.
     * @param response - the response that will be returned to the user (a string in the cookie).
     **/
    public void clear(HttpServletResponse response){
        Cookie cookie = new Cookie(cartName, "");
        cookie.setPath("/");
        cookie.setMaxAge(2147483647);
        response.addCookie(cookie);
    }
}
