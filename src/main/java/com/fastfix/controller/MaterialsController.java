package com.fastfix.controller;

import com.fastfix.exception.InvalidFileException;
import com.fastfix.request.CreateMaterialRequest;
import com.fastfix.response.ApiResponse;
import com.fastfix.service.Impl.MaterialsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class MaterialsController {

    private final MaterialsServiceImpl materialsService;

    public MaterialsController(MaterialsServiceImpl materialsService) {
        this.materialsService = materialsService;
    }

    @PostMapping("/add-material")
    public ResponseEntity<?> addNewMaterial(Authentication authentication, @Valid @RequestPart("request")CreateMaterialRequest request
    , @RequestPart(value = "file" ,required = false)MultipartFile file){
        try {
           materialsService.addNewMaterial(authentication.getName(),request,file);
           return ResponseEntity.ok().body(new ApiResponse("Material has been successfully added"));
        }catch (InvalidFileException e){
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage()));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponse("Something Went Wrong Try Again Later"));
        }
    }

    @GetMapping("/all-materials")
    public ResponseEntity<?> findAllMaterials(){
        return ResponseEntity.ok().body(materialsService.findAllMaterials());
    }

    @GetMapping("/count-materials")
    public ResponseEntity<Long> countAllMaterials(){
        return ResponseEntity.ok().body(materialsService.countAllMaterials());
    }
}
