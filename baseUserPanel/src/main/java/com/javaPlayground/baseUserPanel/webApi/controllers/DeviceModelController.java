package com.javaPlayground.baseUserPanel.webApi.controllers;

import com.javaPlayground.baseUserPanel.business.concretes.DeviceModelManager;
import com.javaPlayground.baseUserPanel.entities.concretes.DeviceModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device-models")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DeviceModelController {

    private final DeviceModelManager deviceModelManager;

    @GetMapping
    public ResponseEntity<List<DeviceModel>> getAllDeviceModels() {
        List<DeviceModel> deviceModels = deviceModelManager.getAllDeviceModels();
        return ResponseEntity.ok(deviceModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceModel> getDeviceModelById(@PathVariable Long id) {
        return deviceModelManager.getDeviceModelById(id)
                .map(deviceModel -> ResponseEntity.ok(deviceModel))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/device/{deviceId}")
    public ResponseEntity<List<DeviceModel>> getDeviceModelsByDeviceId(@PathVariable Long deviceId) {
        List<DeviceModel> deviceModels = deviceModelManager.getDeviceModelsByDeviceId(deviceId);
        return ResponseEntity.ok(deviceModels);
    }

    @GetMapping("/model/{modelId}")
    public ResponseEntity<List<DeviceModel>> getDeviceModelsByModelId(@PathVariable Long modelId) {
        List<DeviceModel> deviceModels = deviceModelManager.getDeviceModelsByModelId(modelId);
        return ResponseEntity.ok(deviceModels);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DeviceModel>> getDeviceModelsByStatus(@PathVariable String status) {
        List<DeviceModel> deviceModels = deviceModelManager.getDeviceModelsByStatus(status);
        return ResponseEntity.ok(deviceModels);
    }

    @GetMapping("/device/{deviceId}/model/{modelId}")
    public ResponseEntity<List<DeviceModel>> getDeviceModelsByDeviceAndModel(
            @PathVariable Long deviceId,
            @PathVariable Long modelId) {
        List<DeviceModel> deviceModels = deviceModelManager.getDeviceModelsByDeviceAndModel(deviceId, modelId);
        return ResponseEntity.ok(deviceModels);
    }

    @GetMapping("/device/{deviceId}/total-instances")
    public ResponseEntity<Integer> getTotalInstancesByDevice(@PathVariable Long deviceId) {
        Integer totalInstances = deviceModelManager.getTotalInstancesByDevice(deviceId);
        return ResponseEntity.ok(totalInstances != null ? totalInstances : 0);
    }

    @GetMapping("/model/{modelId}/total-instances")
    public ResponseEntity<Integer> getTotalInstancesByModel(@PathVariable Long modelId) {
        Integer totalInstances = deviceModelManager.getTotalInstancesByModel(modelId);
        return ResponseEntity.ok(totalInstances != null ? totalInstances : 0);
    }

    @PostMapping
    public ResponseEntity<DeviceModel> createDeviceModel(@Valid @RequestBody DeviceModel deviceModel) {
        try {
            DeviceModel createdDeviceModel = deviceModelManager.createDeviceModel(deviceModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDeviceModel);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceModel> updateDeviceModel(@PathVariable Long id, @Valid @RequestBody DeviceModel deviceModelDetails) {
        try {
            DeviceModel updatedDeviceModel = deviceModelManager.updateDeviceModel(id, deviceModelDetails);
            return ResponseEntity.ok(updatedDeviceModel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeviceModel(@PathVariable Long id) {
        try {
            deviceModelManager.deleteDeviceModel(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
