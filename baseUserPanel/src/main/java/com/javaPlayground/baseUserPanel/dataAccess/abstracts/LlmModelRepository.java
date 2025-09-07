package com.javaPlayground.baseUserPanel.dataAccess.abstracts;

import com.javaPlayground.baseUserPanel.entities.concretes.LlmModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LlmModelRepository extends JpaRepository<LlmModel, Long> {

    List<LlmModel> findByIsActiveTrue();

    List<LlmModel> findByProvider(String provider);

    @Query("SELECT m FROM LlmModel m WHERE m.modelName LIKE %?1%")
    List<LlmModel> findByModelNameContaining(String modelName);

    @Query("SELECT m FROM LlmModel m WHERE m.requiredRamGb <= ?1")
    List<LlmModel> findByMaxRequiredRamGb(Integer maxRamGb);

    List<LlmModel> findByModelNameAndVersion(String modelName, String version);
}
