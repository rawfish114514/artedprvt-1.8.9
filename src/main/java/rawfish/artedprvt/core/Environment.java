package rawfish.artedprvt.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 环境
 */
public class Environment {
    /**
     * 模组id
     */
    public static final String MODID = "artedprvt";

    /**
     * 模组名称
     */
    public static final String MODNAME = "Artedprvt Frame";

    /**
     * 模组版本
     */
    public static final String MODVERSION = "1.3";

    /**
     * mc版本
     */
    public static final String MCVERSION = "1.8.9";

    /**
     * 存在mc客户端
     */
    public static final boolean MCCLIENT;

    /**
     * 模组日志记录器
     */
    public static final Logger MODLOGGER = LogManager.getLogger(MODID);


    static {
        boolean x = false;
        try {
            Class.forName("net.minecraft.client.Minecraft");
            x = true;
        } catch (ClassNotFoundException ignore) {
        }
        MCCLIENT = x;
    }
}
