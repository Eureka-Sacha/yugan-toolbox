package one.yugan.mission;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
public class ScheduMissionBucket implements Bucket, DisposableBean {
    private final ScheduledThreadPoolExecutor scheduledPool;
    private final Map<String, ScheduledMission> missionMap;
    private final Map<String, ScheduledMission> trashMap;
    private final Properties properties;

    public ScheduMissionBucket(Properties properties) {
        this.properties = properties;
        scheduledPool = new ScheduledThreadPoolExecutor(
            properties.getCore() >> 1,
            FactoryConstruct.getSchedulerMissionThreadFactory(),
            properties.getRejectedHandler()
        );
        missionMap = new HashMap<>();
        trashMap = new HashMap<>();
    }

    private String createId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String createMission(Mission mission) {
        ScheduledMission m = (ScheduledMission)mission;
        String id = createId();
        scheduledPool.schedule(m, m.delay(), properties.getUnit());
        missionMap.put(id, m);
        return id;
    }

    @Override
    public void stopMission(String id) {
        ScheduledMission mission = missionMap.get(id);
        mission.interupt();
        scheduledPool.remove(mission);
        trashMap.put(id, mission);

    }

    @Override
    public void resumeMission(String id) {
        ScheduledMission mission = trashMap.get(id);
        scheduledPool.submit(mission);
    }

    @Override
    public void destroy() throws Exception {
        scheduledPool.shutdown();

    }
}
