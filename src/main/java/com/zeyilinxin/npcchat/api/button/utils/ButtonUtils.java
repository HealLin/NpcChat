package com.zeyilinxin.npcchat.api.button.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.zeyilinxin.npcchat.NpcChat;
import com.zeyilinxin.npcchat.interfaceclass.PermissionInterface;
import com.zeyilinxin.npcchat.interfaceclass.RequirementsInterface;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

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
		
		Map<Object, ? extends CommentedConfigurationNode> map = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Button"}).getChildrenMap();
		if (map == null || map.isEmpty())
			map = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "button"}).getChildrenMap();
		
		ArrayList<String> arrayList = new ArrayList<>();
		for (int i = 0 ; i < map.size() ; i++)
			arrayList.add(null);
		
		Function<Void, Integer> getFirstNonNull = Void -> 
		{
			for (int i = 0 ; i < arrayList.size() ; i++)
				if (arrayList.get(i) == null)
					return i;
			arrayList.add(null);
			return arrayList.size() - 1;
		};
		
		for (Entry<Object, ? extends CommentedConfigurationNode> entry : map.entrySet())
		{
			String key = entry.getKey().toString();
			CommentedConfigurationNode node = entry.getValue();
			
			if (node == null)
			{
				arrayList.set(getFirstNonNull.apply(null), key);
				continue;
			}
			
			int slot = node.getNode("Order").getInt(getFirstNonNull.apply(null));
			
			while (slot >= arrayList.size())
				arrayList.add(null);
			
			arrayList.set(slot, key);
		}
		
		arrayList.removeIf(s -> s == null);
		
		return arrayList;
		
//		Set<Object> list = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Button"}).getChildrenMap().keySet();
//		int size = list.size();
//		if (size > 0){
//			for(Object b : list){
//				arrayList.add((String) b);
//			}
//			return arrayList;
//		} else {
//			Set<Object> set = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "button"}).getChildrenMap().keySet();
//			if (set.size() > 0){
//				for(Object b : set){
//					arrayList.add((String) b);
//				}
//				return arrayList;
//			} else {
//				return arrayList;
//			}
//		}
	}
	
	
	
}
