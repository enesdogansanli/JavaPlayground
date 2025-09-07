package com.javaPlayground.baseUserPanel.webApi.controllers;

import com.javaPlayground.baseUserPanel.business.concretes.DeviceManager;
import com.javaPlayground.baseUserPanel.entities.concretes.Device;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DeviceController {

    private final DeviceManager deviceManager;

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceManager.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {
        return deviceManager.getDeviceById(id)
                .map(device -> ResponseEntity.ok(device))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Device>> getDevicesByStatus(@PathVariable String status) {
        List<Device> devices = deviceManager.getDevicesByStatus(status);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/type/{deviceType}")
    public ResponseEntity<List<Device>> getDevicesByType(@PathVariable String deviceType) {
        List<Device> devices = deviceManager.getDevicesByType(deviceType);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Device>> searchByDeviceName(@RequestParam String deviceName) {
        List<Device> devices = deviceManager.searchByDeviceName(deviceName);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/filter/ram")
    public ResponseEntity<List<Device>> getDevicesByMinRam(@RequestParam Integer minRamGb) {
        List<Device> devices = deviceManager.getDevicesByMinRam(minRamGb);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/filter/cpu")
    public ResponseEntity<List<Device>> getDevicesByMinCpuCores(@RequestParam Integer minCpuCores) {
        List<Device> devices = deviceManager.getDevicesByMinCpuCores(minCpuCores);
        return ResponseEntity.ok(devices);
    }

    @PostMapping
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        Device createdDevice = deviceManager.createDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDevice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @Valid @RequestBody Device deviceDetails) {
        try {
            Device updatedDevice = deviceManager.updateDevice(id, deviceDetails);
            return ResponseEntity.ok(updatedDevice);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        try {
            deviceManager.deleteDevice(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
