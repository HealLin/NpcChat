package com.zeyilinxin.npcchat.api.button.utils;

import com.zeyilinxin.npcchat.NpcChat;
import com.zeyilinxin.npcchat.interfaceclass.PermissionInterface;
import com.zeyilinxin.npcchat.interfaceclass.RequirementsInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ButtonUtils {

    private final static NpcChat chat = NpcChat.getNpcchat();
    private PermissionInterface permissionInterface;
    private RequirementsInterface requirementsInterface;
    private String button;

    public ButtonUtils(){}

    public ButtonUtils(String button){
        this.button = button;
    }

    public void setPermission(PermissionInterface permission){
        this.permissionInterface = permission;
    }

    public void setRequirements(RequirementsInterface requirementsInterface){
        this.requirementsInterface = requirementsInterface;
    }

    public void setRun(List<String> run){
        if (run.size() == 0){
            this.permissionInterface.run(run ,this.button , false);
        } else {
            this.permissionInterface.run(run , this.button , true);
        }
    }

    public void setRunreuirements(List<String> list){
        if (list.size() == 0){
            this.requirementsInterface.run(list , this.button , false);
        } else {
            this.requirementsInterface.run(list , this.button , true);
        }
    }

    public static List<String> formList(String name){
        ArrayList<String> arrayList = new ArrayList<>();
        Set<Object> list = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Button"}).getChildrenMap().keySet();
        int size = list.size();
        if (size > 0){
            for(Object b : list){
                arrayList.add((String) b);
            }
            return arrayList;
        } else {
            Set<Object> set = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "button"}).getChildrenMap().keySet();
            if (set.size() > 0){
                for(Object b : set){
                    arrayList.add((String) b);
                }
                return arrayList;
            } else {
                return arrayList;
            }
        }
    }



}
