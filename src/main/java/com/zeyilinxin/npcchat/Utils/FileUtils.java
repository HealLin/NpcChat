package com.zeyilinxin.npcchat.Utils;

import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.zeyilinxin.npcchat.NpcChat;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileUtils {

    private NpcChat chat;

    public FileUtils(NpcChat chat){
        this.chat = chat;
    }

    public Set<Object> getDataKey(){
        return chat.getConfigurationNode().getNode(new Object[]{"Data"}).getChildrenMap().keySet();
    }

    public Set<Object> getButton(Object button){
        return chat.getConfigurationNode().getNode(new Object[]{"Data" , button , "Button"}).getChildrenMap().keySet();
    }

    public void Remove(String name){
        chat.getConfigurationNode().getNode(new Object[]{"Data"}).removeChild(name);
    }

    public int getButtonLength(String name){
        try {
            return chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Button"}).getList(TypeToken.of(String.class)).size();
        } catch (ObjectMappingException e) {
            return -1;
        }
    }

    public List<String> getButton(String name){
        try {
            return chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Button"}).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException e) {
            return null;
        }
    }

    public List<String> getCmd(String name , String button){
        try {
            return chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Event" , button , "Cmd"}).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException e) {
            return null;
        }
    }

    public void setNpcData(String uuid , String name){
        chat.getConfigurationNode().getNode(new Object[]{"UUID" , uuid}).setValue(name);
        try {
            chat.getConfigurationLoader().save(chat.getConfigurationNode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNpcData(String uuid){
        String name = chat.getConfigurationNode().getNode(new Object[]{"UUID" , uuid}).getString("");
        return name == null || name.isEmpty() ? "" : name;
    }

    public String getName(Object name){
        return chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Name"}).getString();
    }

    public String getText(Object text){
        return chat.getConfigurationNode().getNode(new Object[]{"Data" , text , "Text"}).getString();
    }

    public String getCondition(Object name , Object con){
        return chat.getConfigurationNode().getNode("Data" , name , "Buuton" , con).getString();
    }

    public void setBlock(String block , String name){
        chat.getConfigurationNode().getNode(new Object[]{"Block" , block}).setValue(name);
        try {
            chat.getConfigurationLoader().save(chat.getConfigurationNode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBlockData(String blockdata){
        String block = chat.getConfigurationNode().getNode(new Object[]{"Block" , blockdata}).getString("");
        return block == null || block.isEmpty() ? "" : block;
    }


    public ArrayList<Dialogue> getDialog(String name){
        return null;
    }

    public static void setNpcChatData(String name){
        if (NpcChat.getNpcchat().getConfigurationNode().getNode(new Object[]{"Data" , name , "Name"}).getString("").isEmpty()){
            NpcChat.getNpcchat().getConfigurationNode().getNode(new Object[]{"Data" , name , "Name"}).setValue("Test");
        }
        if (NpcChat.getNpcchat().getConfigurationNode().getNode(new Object[]{"Data" , name , "Text"}).getString("").isEmpty()){
            NpcChat.getNpcchat().getConfigurationNode().getNode(new Object[]{"Data" , name , "Text"}).setValue("Test");
        }
        try {
            NpcChat.getNpcchat().getConfigurationLoader().save(NpcChat.getNpcchat().getConfigurationNode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
