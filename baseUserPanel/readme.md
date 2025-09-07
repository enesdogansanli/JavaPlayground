# Base User Panel

# Content
* [Base User Panel](#base-user-panel)
* [Content](#content)

# Introduction


# Examples

```java
@Repository
public interface DeviceModelRepository extends JpaRepository<DeviceModel, Long> {
    
    @Query("SELECT new com.javaPlayground.baseUserPanel.dtos.DeviceModelDTO(" +
           "dm.id, d.name, lm.name, dm.instanceCount, dm.status, " +
           "dm.memoryUsageGb, dm.cpuUsagePercent, dm.createdAt, dm.updatedAt) " +
           "FROM DeviceModel dm " +
           "JOIN dm.device d " +
           "JOIN dm.llmModel lm")
    List<DeviceModelDTO> findAllWithDeviceAndModelNames();
}
```