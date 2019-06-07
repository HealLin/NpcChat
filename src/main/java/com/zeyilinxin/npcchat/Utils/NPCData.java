package com.zeyilinxin.npcchat.Utils;

import java.util.UUID;

public class NPCData {

    private UUID uuid;
    private String name;

    public NPCData(UUID uuid , String name ){
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid(){
        return this.uuid;
    }

    public String getName(){
        return this.name;
    }
}
