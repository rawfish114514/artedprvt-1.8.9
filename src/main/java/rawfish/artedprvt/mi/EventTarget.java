package rawfish.artedprvt.mi;


import net.minecraftforge.fml.common.eventhandler.Event;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EventTarget {
    Class<? extends Event> value();
}
