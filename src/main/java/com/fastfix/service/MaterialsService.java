package com.fastfix.service;

import com.fastfix.dto.MaterialsDto;
import com.fastfix.request.CreateMaterialRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MaterialsService {
    MaterialsDto addNewMaterial(String email, CreateMaterialRequest request, MultipartFile file) throws IOException;
    List<MaterialsDto> findAllMaterials();
    long countAllMaterials();
}
