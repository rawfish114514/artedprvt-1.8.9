package rawfish.artedprvt.script.js;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 类成员
 */
public class ClassMember {
    public Map<String,String> nameTable;

    public ClassMember(){
        nameTable=new HashMap<>();
    }

    /**
     * 数据加载期间调用方法
     * @param mcpName MCP名
     * @param srgName Srg名
     */
    public void up(String mcpName,String srgName){
        String oldValue=nameTable.get(mcpName);
        if(oldValue==null){
            nameTable.put(mcpName,srgName);
        }else{
            //串联
            Set<String> srgs=new HashSet<>();
            String[] srgList=oldValue.split(ClassLevel.link);
            for(int i=0;i<srgList.length;i++){
                srgs.add(srgList[i]);
            }
            String[] srgList2=srgName.split(ClassLevel.link);
            for(int i=0;i<srgList2.length;i++){
                srgs.add(srgList2[i]);
            }
            nameTable.put(mcpName,String.join(ClassLevel.link,srgs));
        }
    }

    /**
     * 添加所有成员到this
     * @param member
     */
    public void up(ClassMember member){
        for(String mcpName:member.nameTable.keySet()){
            up(mcpName,member.get(mcpName));
        }
    }

    /**
     * 获取MCP名对应的Srg名 如果没有则返回"0"
     * @param mcpName MCP名
     * @return Srg名|"0"
     */
    public String get(String mcpName){
        String srgName=nameTable.get(mcpName);
        return srgName==null?ClassLevel.memberNull:srgName;
    }
}
