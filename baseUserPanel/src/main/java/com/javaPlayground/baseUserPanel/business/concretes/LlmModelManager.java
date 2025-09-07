package com.javaPlayground.baseUserPanel.business.concretes;

import com.javaPlayground.baseUserPanel.dataAccess.abstracts.LlmModelRepository;
import com.javaPlayground.baseUserPanel.entities.concretes.LlmModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LlmModelManager {

    private final LlmModelRepository llmModelRepository;

    public List<LlmModel> getAllModels() {
        return llmModelRepository.findAll();
    }

    public Optional<LlmModel> getModelById(Long id) {
        return llmModelRepository.findById(id);
    }

    public List<LlmModel> getActiveModels() {
        return llmModelRepository.findByIsActiveTrue();
    }

    public List<LlmModel> getModelsByProvider(String provider) {
        return llmModelRepository.findByProvider(provider);
    }

    public List<LlmModel> searchByModelName(String modelName) {
        return llmModelRepository.findByModelNameContaining(modelName);
    }

    public List<LlmModel> getModelsByMaxRequiredRam(Integer maxRamGb) {
        return llmModelRepository.findByMaxRequiredRamGb(maxRamGb);
    }

    public List<LlmModel> getModelsByNameAndVersion(String modelName, String version) {
        return llmModelRepository.findByModelNameAndVersion(modelName, version);
    }

    public LlmModel createModel(LlmModel model) {
        return llmModelRepository.save(model);
    }

    public LlmModel updateModel(Long id, LlmModel modelDetails) {
        return llmModelRepository.findById(id)
                .map(model -> {
                    model.setModelName(modelDetails.getModelName());
                    model.setVersion(modelDetails.getVersion());
                    model.setProvider(modelDetails.getProvider());
                    model.setModelSizeGb(modelDetails.getModelSizeGb());
                    model.setRequiredRamGb(modelDetails.getRequiredRamGb());
                    model.setDescription(modelDetails.getDescription());
                    model.setIsActive(modelDetails.getIsActive());
                    return llmModelRepository.save(model);
                })
                .orElseThrow(() -> new RuntimeException("Model not found with id: " + id));
    }

    public void deleteModel(Long id) {
        if (!llmModelRepository.existsById(id)) {
            throw new RuntimeException("Model not found with id: " + id);
        }
        llmModelRepository.deleteById(id);
    }
}
