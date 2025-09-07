package com.javaPlayground.baseUserPanel.webApi.controllers;

import com.javaPlayground.baseUserPanel.business.concretes.LlmModelManager;
import com.javaPlayground.baseUserPanel.entities.concretes.LlmModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LlmModelController {

    private final LlmModelManager llmModelManager;

    @GetMapping
    public ResponseEntity<List<LlmModel>> getAllModels() {
        List<LlmModel> models = llmModelManager.getAllModels();
        return ResponseEntity.ok(models);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LlmModel> getModelById(@PathVariable Long id) {
        return llmModelManager.getModelById(id)
                .map(model -> ResponseEntity.ok(model))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<LlmModel>> getActiveModels() {
        List<LlmModel> models = llmModelManager.getActiveModels();
        return ResponseEntity.ok(models);
    }

    @GetMapping("/provider/{provider}")
    public ResponseEntity<List<LlmModel>> getModelsByProvider(@PathVariable String provider) {
        List<LlmModel> models = llmModelManager.getModelsByProvider(provider);
        return ResponseEntity.ok(models);
    }

    @GetMapping("/search")
    public ResponseEntity<List<LlmModel>> searchByModelName(@RequestParam String modelName) {
        List<LlmModel> models = llmModelManager.searchByModelName(modelName);
        return ResponseEntity.ok(models);
    }

    @GetMapping("/filter/ram")
    public ResponseEntity<List<LlmModel>> getModelsByMaxRequiredRam(@RequestParam Integer maxRamGb) {
        List<LlmModel> models = llmModelManager.getModelsByMaxRequiredRam(maxRamGb);
        return ResponseEntity.ok(models);
    }

    @GetMapping("/name-version")
    public ResponseEntity<List<LlmModel>> getModelsByNameAndVersion(
            @RequestParam String modelName,
            @RequestParam String version) {
        List<LlmModel> models = llmModelManager.getModelsByNameAndVersion(modelName, version);
        return ResponseEntity.ok(models);
    }

    @PostMapping
    public ResponseEntity<LlmModel> createModel(@Valid @RequestBody LlmModel model) {
        LlmModel createdModel = llmModelManager.createModel(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LlmModel> updateModel(@PathVariable Long id, @Valid @RequestBody LlmModel modelDetails) {
        try {
            LlmModel updatedModel = llmModelManager.updateModel(id, modelDetails);
            return ResponseEntity.ok(updatedModel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        try {
            llmModelManager.deleteModel(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
