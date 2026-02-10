package com.example.demo.controllers;

import com.example.demo.dto.PetRequest;
import com.example.demo.dto.PetResponse;
import com.example.demo.Entities.ResponsePetPost;
import com.example.demo.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping("/pet/{id}")
    public ResponseEntity<PetResponse> getPetById(@PathVariable String id) {
        PetResponse pet = petService.getPetById(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping("/pet")
    public ResponseEntity<ResponsePetPost> createPet(@Valid @RequestBody PetRequest request) {
        ResponsePetPost response = petService.createPet(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
