package ru.rtu_mirea.course_work_spring.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="products")
@Setter
@Getter
public class Product {
    /** ID product in the Database **/
    @Id
    @SequenceGenerator(name = "products_seq", sequenceName = "products_sequence", allocationSize = 1)
    @GeneratedValue(generator = "products_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    /** The type of product
     * @see ProductType **/
    @ElementCollection(targetClass = ProductType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "product_type", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    private Set<ProductType> type;

    /** The name of product **/
    private String name;

    /** Quantity of products in the "warehouse" **/
    private Long number;

    /** Price of product **/
    private Long price;

    /** The description of product **/
    private String description;

    /** URL where the product image is stored **/
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    /** The order to which the product is attached **/
    private Order order;

    /** Class constructors **/
    public Product() {
    }
    public Product(Set<ProductType> type, String name, Long number, Long price, String description) {
        this.type = type;
        this.name = name;
        this.number = number;
        this.price = price;
        this.description = description;
    }
}
