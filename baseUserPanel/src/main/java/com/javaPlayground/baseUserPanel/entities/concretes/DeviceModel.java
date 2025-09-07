package com.javaPlayground.baseUserPanel.entities.concretes;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_models")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false)
    private LlmModel llmModel;

    @Min(value = 1, message = "Instance count must be at least 1")
    @Column(name = "instance_count", nullable = false)
    private Integer instanceCount = 1;

    @Column(name = "status", length = 20)
    private String status = "RUNNING";

    @Column(name = "memory_usage_gb")
    private Double memoryUsageGb;

    @Column(name = "cpu_usage_percent")
    private Double cpuUsagePercent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}