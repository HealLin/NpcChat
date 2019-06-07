package com.zeyilinxin.npcchat.api.permission;

import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class DialogPermission {

    private boolean[] hasPermission ;
    private boolean Success = true;

    /**
     * @Auth 择忆霖心
     * @param p  玩家实例
     * @param perlist  权限列表
     */
    public DialogPermission(Player p , List<String> perlist){
        this.hasPermission = new boolean[perlist.size()];
        for(int i = 0 ; i < perlist.size(); i ++ ){
            String pe = perlist.get(i);
            if (pe.startsWith("u")){
                String temp = pe.substring(3);
                if (!p.hasPermission(temp)){
                    this.hasPermission[i] = true;
                } else {
                    this.hasPermission[i] = false;
                }
            } else {
                if (p.hasPermission(pe)){
                    this.hasPermission[i] = true;
                } else {
                    this.hasPermission[i] = false;
                }
            }
        }
        for (boolean perm : this.hasPermission){
            if (perm == false){
                this.Success = false;
                break;
            }
        }
    }

    public boolean getPermission(){
        return this.Success;
    }
}
