package com.fastfix.request;

import com.fastfix.dto.UserDto;
import com.fastfix.enums.Currency;
import com.fastfix.enums.MaterialUnit;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CreateMaterialRequest {



    private String name;
    private String description;
    private BigDecimal amount;
    private BigDecimal packageSize;
    private MaterialUnit packageUnit;
    private BigDecimal price;
    private String picture;
    private Currency currency;

    public CreateMaterialRequest() {
    }
}
