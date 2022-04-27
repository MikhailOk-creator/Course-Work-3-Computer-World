package ru.rtu_mirea.course_work_spring.DTO;

import lombok.Data;
import ru.rtu_mirea.course_work_spring.Model.ProductType;

import java.util.Set;

@Data
public class CartProduct {
    private Long id;
    private Set<ProductType> type;
    private String name;
    private String description;
    private Long price;
    private Long numberInCart;
    private Long numberInBD;
    private String imageUrl;
}
