package rawfish.artedprvt.script;

import rawfish.artedprvt.script.mi.Events;

public class SupplementScript {

    public static String getEventListenerRegisterCode(Events type){
        String k=type.name();
        return String.format("if(typeof %s===\"function\"){;var _EventListener_%s=new EventListener(Events.%s,%s);_EventListener_%s.register();};\n",k,k,k,k,k);
    }
}
