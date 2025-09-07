package com.javaPlayground.baseUserPanel.business.concretes;

import com.javaPlayground.baseUserPanel.dataAccess.abstracts.DeviceRepository;
import com.javaPlayground.baseUserPanel.entities.concretes.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceManager {

    private final DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDeviceById(Long id) {
        return deviceRepository.findById(id);
    }

    public List<Device> getDevicesByStatus(String status) {
        return deviceRepository.findByStatus(status);
    }

    public List<Device> getDevicesByType(String deviceType) {
        return deviceRepository.findByDeviceType(deviceType);
    }

    public List<Device> searchByDeviceName(String deviceName) {
        return deviceRepository.findByDeviceNameContaining(deviceName);
    }

    public List<Device> getDevicesByMinRam(Integer minRamGb) {
        return deviceRepository.findByMinRamGb(minRamGb);
    }

    public List<Device> getDevicesByMinCpuCores(Integer minCpuCores) {
        return deviceRepository.findByMinCpuCores(minCpuCores);
    }

    public Device createDevice(Device device) {
        return deviceRepository.save(device);
    }

    public Device updateDevice(Long id, Device deviceDetails) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setDeviceName(deviceDetails.getDeviceName());
                    device.setDeviceType(deviceDetails.getDeviceType());
                    device.setIpAddress(deviceDetails.getIpAddress());
                    device.setStatus(deviceDetails.getStatus());
                    device.setCpuCores(deviceDetails.getCpuCores());
                    device.setRamGb(deviceDetails.getRamGb());
                    device.setDescription(deviceDetails.getDescription());
                    return deviceRepository.save(device);
                })
                .orElseThrow(() -> new RuntimeException("Device not found with id: " + id));
    }

    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new RuntimeException("Device not found with id: " + id);
        }
        deviceRepository.deleteById(id);
    }
}
