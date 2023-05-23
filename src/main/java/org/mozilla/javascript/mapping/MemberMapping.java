package org.mozilla.javascript.mapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MemberMapping {
    public Map<String,String> nameTable;

    public MemberMapping(){
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
            String[] srgList=oldValue.split("/");
            for(int i=0;i<srgList.length;i++){
                srgs.add(srgList[i]);
            }
            String[] srgList2=srgName.split("/");
            for(int i=0;i<srgList2.length;i++){
                srgs.add(srgList2[i]);
            }
            nameTable.put(mcpName,String.join("/",srgs));
        }
    }

    /**
     * 添加所有成员到this
     * @param member
     */
    public void up(MemberMapping member){
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
        return srgName==null?"0":srgName;
    }
}
