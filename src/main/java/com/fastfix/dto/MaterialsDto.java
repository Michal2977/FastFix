package com.fastfix.dto;

import com.fastfix.enums.Currency;
import com.fastfix.enums.MaterialUnit;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MaterialsDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal amount;
    private MaterialUnit materialUnit;
    private BigDecimal packageSize;
    private MaterialUnit packageUnit;
    private BigDecimal price;
    private String picture;
    private UserDto userDto;
    private Currency currency;

    public MaterialsDto() {
    }

    public MaterialsDto(Long id, String name, String description, BigDecimal amount, MaterialUnit materialUnit,
                        BigDecimal packageSize, MaterialUnit packageUnit, BigDecimal price, String picture, UserDto userDto
    ,Currency currency) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.materialUnit = materialUnit;
        this.packageSize = packageSize;
        this.packageUnit = packageUnit;
        this.price = price;
        this.picture = picture;
        this.userDto = userDto;
        this.currency = currency;
    }
}
