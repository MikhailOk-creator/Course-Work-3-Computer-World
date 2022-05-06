package ru.rtu_mirea.course_work_spring.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    /** ID order in the Database **/
    @Id
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(generator = "orders_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    /** Class of user who placed the order **/
    @ManyToOne
    private User user;

    /** List of products in the order **/
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<Product> products;

    /** Order status
     * @see Order **/
    @ElementCollection(targetClass = OrderStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "order_status_pon", joinColumns = @JoinColumn(name = "order_id"))
    @Enumerated(EnumType.STRING)
    private Set<OrderStatus> status;


    /** The next parameters are responsible for basic user data **/
    private String user_name = "";

    private String user_surname = "";

    private String user_email = "";

    private String user_address = "";

    private String phone = "";
}
