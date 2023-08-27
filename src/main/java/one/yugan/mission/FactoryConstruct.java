package one.yugan.mission;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
public class FactoryConstruct {

    public static ThreadFactory getMissionThreadFactory() {
        return new MissionThreadFactory();
    }

    public static ThreadFactory getSchedulerMissionThreadFactory() {
        return new MissionThreadFactory();
    }

    public static class MissionThreadFactory implements ThreadFactory {
        private final AtomicInteger increase = new AtomicInteger(1);
        private static final String Thread_Prefix = "mission-";

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, Thread_Prefix + increase.getAndIncrement());
        }
    }

    public static class SchedulerMissionThreadFactory implements ThreadFactory {
        private final AtomicInteger increase = new AtomicInteger(1);
        private static final String Thread_Prefix = "sch-mission-";

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, Thread_Prefix + increase.getAndIncrement());
        }
    }
}
