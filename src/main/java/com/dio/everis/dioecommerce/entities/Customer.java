package com.dio.everis.dioecommerce.entities;

import com.dio.everis.dioecommerce.enums.CustomerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(length = 25)
    private String cpfOrCnpj;

    private Integer customerType;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @ElementCollection
    @CollectionTable(name = "PHONE")
    private Set<String> phones;

    public Customer(Long id, String name, String email, String cpfOrCnpj, CustomerType customerType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpfOrCnpj = cpfOrCnpj;
        this.customerType = (customerType==null) ? null : customerType.getCode();
    }

    public CustomerType getCustomerType() {
        return CustomerType.toEnum(customerType);
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType.getCode();
    }
}
