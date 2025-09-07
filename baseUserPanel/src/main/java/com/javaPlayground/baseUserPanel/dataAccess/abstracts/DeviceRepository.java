package com.javaPlayground.baseUserPanel.dataAccess.abstracts;

import com.javaPlayground.baseUserPanel.entities.concretes.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByStatus(String status);

    List<Device> findByDeviceType(String deviceType);

    @Query("SELECT d FROM Device d WHERE d.deviceName LIKE %?1%")
    List<Device> findByDeviceNameContaining(String deviceName);

    @Query("SELECT d FROM Device d WHERE d.ramGb >= ?1")
    List<Device> findByMinRamGb(Integer minRamGb);

    @Query("SELECT d FROM Device d WHERE d.cpuCores >= ?1")
    List<Device> findByMinCpuCores(Integer minCpuCores);
}
