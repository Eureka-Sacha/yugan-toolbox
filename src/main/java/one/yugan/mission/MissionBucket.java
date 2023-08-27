package one.yugan.mission;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.DisposableBean;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
public class MissionBucket implements Bucket, DisposableBean {

    private final ThreadPoolExecutor threadPool;

    private final Map<String, Mission> missionMap;
    private final Map<String, Mission> trashMap;

    private final Properties properties;

    public MissionBucket(Properties properties) {
        this.properties = properties;
        threadPool = new ThreadPoolExecutor(
            properties.getCore(),
            properties.getMax(),
            properties.getAlive(),
            properties.getUnit(),
            new ArrayBlockingQueue<>(properties.getQueue()),
            FactoryConstruct.getMissionThreadFactory(),
            properties.getRejectedHandler()
        );
        missionMap = new HashMap<>();
        trashMap = new HashMap<>();
    }

    private String createId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public synchronized String createMission(Mission mission) {
        String id = createId();
        threadPool.submit(mission);
        missionMap.put(id, mission);
        return id;
    }

    @Override
    public synchronized void stopMission(String id) {
        Mission mission = missionMap.get(id);
        mission.interupt();
        threadPool.remove(mission);
        trashMap.put(id, mission);
    }

    @Override
    public synchronized void resumeMission(String id) {
        Mission mission = trashMap.get(id);
        threadPool.submit(mission);
    }

    @Override
    public void destroy() throws Exception {
        threadPool.shutdown();
    }
}
