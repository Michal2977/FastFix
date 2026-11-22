package com.fastfix.mapper;

import com.fastfix.dto.MaterialsDto;
import com.fastfix.entity.Materials;

import java.util.Base64;

public class MaterialMapper {
    public static MaterialsDto materialsDto(Materials materials){
        String base64Picture = null;
        if (materials.getPicture() != null){
            base64Picture = Base64.getEncoder().encodeToString(materials.getPicture());
        }
        return  new MaterialsDto(materials.getId(),materials.getName(),materials.getDescription(),
               materials.getAmount(),materials.getMaterialUnit(),materials.getPackageSize(),
                materials.getPackageUnit(),materials.getPrice(),base64Picture,
                UserMapper.userDto(materials.getUser()),materials.getCurrency());
    }
}
