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
     *
     * @param name        名
     * @param mappingName 映射名
     */
    public void up(String name, String mappingName){
        String oldValue=nameTable.get(name);
        if(oldValue==null){
            nameTable.put(name, mappingName);
        }else{
            //串联
            Set<String> mappingSet=new HashSet<>();
            String[] mappings=oldValue.split("/");
            for(int i=0;i<mappings.length;i++){
                mappingSet.add(mappings[i]);
            }
            String[] mappings2= mappingName.split("/");
            for(int i=0;i<mappings2.length;i++){
                mappingSet.add(mappings2[i]);
            }
            nameTable.put(name,String.join("/",mappingSet));
        }
    }

    /**
     * 添加所有成员到this
     * @param member
     */
    public void up(MemberMapping member){
        for(String name:member.nameTable.keySet()){
            up(name,member.get(name));
        }
    }

    /**
     * 获取映射名 如果没有则返回"0"
     *
     * @param name 名
     * @return 映射名|"0"
     */
    public String get(String name){
        String mappingName=nameTable.get(name);
        return mappingName==null?"0":mappingName;
    }
}
