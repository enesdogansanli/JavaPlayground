package com.javaPlayground.baseUserPanel.dataAccess.abstracts;

import com.javaPlayground.baseUserPanel.entities.concretes.DeviceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {

    List<DeviceModel> findByDeviceId(Long deviceId);

    List<DeviceModel> findByLlmModelId(Long modelId);

    List<DeviceModel> findByStatus(String status);

    @Query("SELECT dm FROM DeviceModel dm WHERE dm.device.id = ?1 AND dm.llmModel.id = ?2")
    List<DeviceModel> findByDeviceIdAndModelId(Long deviceId, Long modelId);

    @Query("SELECT SUM(dm.instanceCount) FROM DeviceModel dm WHERE dm.device.id = ?1")
    Integer getTotalInstancesByDeviceId(Long deviceId);

    @Query("SELECT SUM(dm.instanceCount) FROM DeviceModel dm WHERE dm.llmModel.id = ?1")
    Integer getTotalInstancesByModelId(Long modelId);
}