package rawfish.artedprvt.script.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaMethod;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;

import java.util.HashMap;
import java.util.Map;

public class NativeJavaMethod2Srg extends NativeJavaMethod {
    public static Map<String,NativeJavaMethod2Srg> n18g=new HashMap<>();
    public static NativeJavaMethod2Srg getNativeJavaMethod2Srg(Class type,String mcp,String srg)throws NoSuchMethodException{
        String key=key(type,srg);
        NativeJavaMethod2Srg nativeJavaMethod2Srg=n18g.get(key);
        if(nativeJavaMethod2Srg==null){
            nativeJavaMethod2Srg=new NativeJavaMethod2Srg(type,mcp,srg);
            n18g.put(key,nativeJavaMethod2Srg);
            System.out.println("put: "+key);
        }
        return nativeJavaMethod2Srg;
    }
    public static String key(Class type,String mcp){
        return type.getName()+';'+mcp;
    }
    public NativeJavaMethod2Srg(Class type, String mcp, String srg) throws NoSuchMethodException {
        super(type.getMethod("toString"),srg);
        this.mcp=mcp;
    }
    public String mcp;

    @Override
    public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
        Object obj=null;
        NativeJavaObject nativeJavaObject=(NativeJavaObject)thisObj;
        String srg=functionName;
        if(!srg.equals(ClassLevel.memberNull)) {
            String[] vs=srg.split(ClassLevel.link);
            for(String v:vs) {
                obj=nativeJavaObject.members.get(
                        nativeJavaObject,
                        v,
                        nativeJavaObject.javaObject,
                        false
                );
                if(obj instanceof NativeJavaMethod) {
                    NativeJavaMethod nativeJavaMethod=(NativeJavaMethod)obj;
                    obj = nativeJavaMethod.call(cx, scope, thisObj, args);
                    if (obj != NO_METHOD) {
                        return obj;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String srg=functionName;
        if(!srg.equals(ClassLevel.memberNull)) {
            String[] vs = srg.split(ClassLevel.link);
            sb.append(mcp);
            for(String v:vs){
                sb.append("\n");
                sb.append(v);
            }
            return sb.toString();
        }
        return "Null Member;";
    }
}
