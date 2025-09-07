package com.javaPlayground.baseUserPanel.business.concretes;

import com.javaPlayground.baseUserPanel.dataAccess.abstracts.DeviceModelRepository;
import com.javaPlayground.baseUserPanel.dataAccess.abstracts.DeviceRepository;
import com.javaPlayground.baseUserPanel.dataAccess.abstracts.LlmModelRepository;
import com.javaPlayground.baseUserPanel.entities.concretes.DeviceModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceModelManager {

    private final DeviceModelRepository deviceModelRepository;
    private final DeviceRepository deviceRepository;
    private final LlmModelRepository llmModelRepository;

    public List<DeviceModel> getAllDeviceModels() {
        return deviceModelRepository.findAll();
    }

    public Optional<DeviceModel> getDeviceModelById(Long id) {
        return deviceModelRepository.findById(id);
    }

    public List<DeviceModel> getDeviceModelsByDeviceId(Long deviceId) {
        return deviceModelRepository.findByDeviceId(deviceId);
    }

    public List<DeviceModel> getDeviceModelsByModelId(Long modelId) {
        return deviceModelRepository.findByLlmModelId(modelId);
    }

    public List<DeviceModel> getDeviceModelsByStatus(String status) {
        return deviceModelRepository.findByStatus(status);
    }

    public List<DeviceModel> getDeviceModelsByDeviceAndModel(Long deviceId, Long modelId) {
        return deviceModelRepository.findByDeviceIdAndModelId(deviceId, modelId);
    }

    public Integer getTotalInstancesByDevice(Long deviceId) {
        return deviceModelRepository.getTotalInstancesByDeviceId(deviceId);
    }

    public Integer getTotalInstancesByModel(Long modelId) {
        return deviceModelRepository.getTotalInstancesByModelId(modelId);
    }

    public DeviceModel createDeviceModel(DeviceModel deviceModel) {
        // Verify that device and model exist
        if (!deviceRepository.existsById(deviceModel.getDevice().getId())) {
            throw new RuntimeException("Device not found with id: " + deviceModel.getDevice().getId());
        }
        if (!llmModelRepository.existsById(deviceModel.getLlmModel().getId())) {
            throw new RuntimeException("Model not found with id: " + deviceModel.getLlmModel().getId());
        }
        return deviceModelRepository.save(deviceModel);
    }

    public DeviceModel updateDeviceModel(Long id, DeviceModel deviceModelDetails) {
        return deviceModelRepository.findById(id)
                .map(deviceModel -> {
                    deviceModel.setInstanceCount(deviceModelDetails.getInstanceCount());
                    deviceModel.setStatus(deviceModelDetails.getStatus());
                    deviceModel.setMemoryUsageGb(deviceModelDetails.getMemoryUsageGb());
                    deviceModel.setCpuUsagePercent(deviceModelDetails.getCpuUsagePercent());
                    return deviceModelRepository.save(deviceModel);
                })
                .orElseThrow(() -> new RuntimeException("DeviceModel not found with id: " + id));
    }

    public void deleteDeviceModel(Long id) {
        if (!deviceModelRepository.existsById(id)) {
            throw new RuntimeException("DeviceModel not found with id: " + id);
        }
        deviceModelRepository.deleteById(id);
    }
}
