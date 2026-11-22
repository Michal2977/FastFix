package com.fastfix.entity;

import com.fastfix.enums.Currency;
import com.fastfix.enums.MaterialUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "materials")
public class Materials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    @Size(min = 2 ,max = 50)
    private String name;

    @Column(name = "description",columnDefinition = "TEXT")
    @Size(max = 1000)
    private String description;

    @Column(name = "create_at",nullable = false)
    private LocalDateTime createAt;

    @Column(name = "amount",nullable = false)
    @DecimalMin("1")
    private BigDecimal amount;

    @Column(name = "material_unit",nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialUnit materialUnit;

    @Column(name = "package_size",nullable = false)
    @DecimalMin("0.01")
    private BigDecimal packageSize;

    @Column(name = "package_unit",nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialUnit packageUnit;

    @Column(name = "price",nullable = false)
    @DecimalMin("0.01")
    private BigDecimal price;

    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] picture;

    @JoinColumn(name = "user_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "currency",nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

//  edit produktu i zazcznij strukure przyjeciea zlecenia ;
    public Materials() {
    }

    public Materials(String name, String description, BigDecimal amount, MaterialUnit materialUnit,
                     BigDecimal packageSize, MaterialUnit packageUnit, BigDecimal price, byte[] picture, User user
    ,Currency currency) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.materialUnit = materialUnit;
        this.packageSize = packageSize;
        this.packageUnit = packageUnit;
        this.price = price;
        this.picture = picture;
        this.user = user;
        this.currency = currency;
    }
}
