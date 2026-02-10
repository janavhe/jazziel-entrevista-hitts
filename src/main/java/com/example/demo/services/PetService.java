package com.example.demo.services;

import com.example.demo.Entities.Pet;
import com.example.demo.dto.PetRequest;
import com.example.demo.dto.PetResponse;
import com.example.demo.Entities.ResponsePetPost;
import com.example.demo.exception.PetNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PetService {

    private static final Logger log = LoggerFactory.getLogger(PetService.class);
    
    private final RestTemplate restTemplate;
    private final String apiUrl;

    @Autowired
    public PetService(RestTemplate restTemplate, @Value("${external.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    public PetResponse getPetById(String id) {
        try {
            ResponseEntity<Pet> response = restTemplate.exchange(apiUrl + "/pet/" + id,HttpMethod.GET,null,Pet.class);
            Pet pet = response.getBody();
            if (pet == null) {
                throw new PetNotFoundException(id);
            }
            return new PetResponse(pet.getId().intValue(), pet.getName(), pet.getStatus());
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Pet not found with id: {}", id);
            throw new PetNotFoundException(id);
        }
    }

    public ResponsePetPost createPet(PetRequest request) {
        try {
            Pet pet = new Pet(request.getId().longValue(), request.getName(), request.getStatus());
            ResponseEntity<Pet> response = restTemplate.exchange(apiUrl + "/pet",HttpMethod.POST,new HttpEntity<>(pet),Pet.class);
            Pet createdPet = response.getBody();
            if (createdPet == null) {
                return null;
            }
            ResponsePetPost responsePetPost = new ResponsePetPost();
            responsePetPost.setName(createdPet.getName());
            responsePetPost.setStatus(createdPet.getStatus().equals("available"));
            responsePetPost.setDateCreated(LocalDateTime.now().toString());
            responsePetPost.setTransactionId(UUID.randomUUID().toString());
            return responsePetPost;
        } catch (RestClientException e) {
            return null;
        }
    }
}
