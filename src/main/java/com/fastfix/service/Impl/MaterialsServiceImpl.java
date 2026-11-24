package com.fastfix.service.Impl;

import com.fastfix.dto.MaterialsDto;
import com.fastfix.entity.Materials;
import com.fastfix.entity.User;
import com.fastfix.enums.MaterialUnit;
import com.fastfix.exception.InvalidFileException;
import com.fastfix.mapper.MaterialMapper;
import com.fastfix.repository.MaterialsRepository;
import com.fastfix.request.CreateMaterialRequest;
import com.fastfix.service.MaterialsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class MaterialsServiceImpl implements MaterialsService {

    private final MaterialsRepository materialsRepository;
    private final AuthServiceImpl authService;

    public MaterialsServiceImpl(MaterialsRepository materialsRepository, AuthServiceImpl authService) {
        this.materialsRepository = materialsRepository;
        this.authService = authService;
    }


    @Override
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public MaterialsDto addNewMaterial(String email, CreateMaterialRequest request, MultipartFile file) throws IOException {
        User user = authService.findByEmail(email);

        Materials materials = new Materials();
        materials.setName(request.getName());
        materials.setDescription(request.getDescription());
        materials.setAmount(request.getAmount());
        materials.setMaterialUnit(MaterialUnit.PIECE);
        materials.setPackageSize(request.getPackageSize());
        materials.setPackageUnit(request.getPackageUnit());
        materials.setPrice(request.getPrice());
        materials.setCurrency(request.getCurrency());
        materials.setCreateAt(LocalDateTime.now());
        materials.setUser(user);

        if (file != null && !file.isEmpty()){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            if (fileName.contains("..")){
                throw new InvalidFileException("Invalid file name");
            }
            String contentType = file.getContentType();

            if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png")
            || contentType.equals("image/webp"))){
                throw new InvalidFileException("Only image files are allowed");
            }
            if (file.getSize() > 8 * 1024 * 1024){
                throw new InvalidFileException("Maximum file size is 8 MB");
            }
            materials.setPicture(file.getBytes());
        }

        materialsRepository.save(materials);
        return MaterialMapper.materialsDto(materials);
    }


    @Override
    public  long countAllMaterials(){
        return materialsRepository.countAllMaterials();
    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER' ,'TECHNICIAN','RECEPTIONIST')")
    @Override
    public List<MaterialsDto> findAllMaterials(){
        List<Materials> materials = materialsRepository.findAll();
        return materials.stream().map(MaterialMapper :: materialsDto).toList();
    }

}
