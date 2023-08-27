package one.yugan.mission;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
public interface Bucket {

    String createMission(Mission mission);

    void stopMission(String id);

    void resumeMission(String id);

    Status status(String id);


}
