package rawfish.artedprvt.event;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class InputStringEvent extends Event {
    public String input;
    public InputStringEvent(String input){
        this.input=input;
    }
}
