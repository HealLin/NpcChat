package com.zeyilinxin.npcchat.Utils;

import com.zeyilinxin.npcchat.NpcChat;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;


/**
 * 封装类：主要用于手中物品判断
 * @author 择忆霖心
 */
public class PlayerItemUtils {

    private boolean isSuccess = false;
    private NpcChat chat;
    private ItemStack main;
    private ItemStack off;

    public PlayerItemUtils(NpcChat chat, ItemStack main, ItemStack off) {
        this.chat = chat;
        this.main = main;
        this.off = off;
    }

    public boolean contrast(String stack , boolean m ){
        if (m){
            if (this.main.getType().getName().equalsIgnoreCase(stack)){
                this.isSuccess = true;
            } else {
                this.isSuccess = false;
            }
        } else {
            if (this.off.getType().getName().equalsIgnoreCase(stack)){
                this.isSuccess = true;
            } else {
                this.isSuccess = false;
            }
        }
        return this.isSuccess;
    }

    public boolean contrast(ItemStack stack , boolean m){
        if (m){
            if (this.main.equalTo(stack)){
                this.isSuccess = true;
            } else {
                this.isSuccess = false;
            }
        } else {
            if (this.off.equalTo(stack)){
                this.isSuccess = true;
            } else {
                this.isSuccess = false;
            }
        }
        return isSuccess;
    }

    public boolean contrast(ItemStack stack , ItemStack stack1 , boolean m){
        if (m){
            if (this.main.equalTo(stack) || this.main.equalTo(stack1) || this.off.equalTo(stack) || this.off.equalTo(stack1)){
                this.isSuccess = true;
            } else {
                this.isSuccess = false;
            }
        } else {
            if (this.main.equalTo(stack) && this.off.equalTo(stack1)){
                this.isSuccess = true;
            } else {
                this.isSuccess = false;
            }
        }
        return isSuccess;
    }

    public static class PlayerItemUtilsBuilder{

        private NpcChat chat;
        private Player player;
        private ItemStack playerMainItemStack;
        private ItemStack playerOffItemStack;


        public PlayerItemUtilsBuilder(){
        }

        public PlayerItemUtilsBuilder(NpcChat chat){
            this.chat = chat;
        }

        public PlayerItemUtilsBuilder(NpcChat chat , ItemStack stack){
            this.chat = chat;
            this.playerMainItemStack = stack;
        }

        public PlayerItemUtilsBuilder(NpcChat chat , ItemStack mainStack , ItemStack stack){
            this.chat = chat;
            this.playerMainItemStack = mainStack;
            this.playerOffItemStack = stack;
        }

        public PlayerItemUtilsBuilder(NpcChat chat , Player player){
            this.chat = chat;
            this.player = player;
            if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                this.playerMainItemStack = player.getItemInHand(HandTypes.MAIN_HAND).get();
            }
            if (player.getItemInHand(HandTypes.OFF_HAND).isPresent()){
                this.playerOffItemStack = player.getItemInHand(HandTypes.MAIN_HAND).get();
            }
        }

        public PlayerItemUtilsBuilder setNpcChat(NpcChat chat){
            this.chat = chat;
            return this;
        }

        public PlayerItemUtilsBuilder setPlayer(Player player){
            this.player = player;
            if (player.getItemInHand(HandTypes.MAIN_HAND).isPresent()){
                this.playerMainItemStack = player.getItemInHand(HandTypes.MAIN_HAND).get();
            }
            if (player.getItemInHand(HandTypes.OFF_HAND).isPresent()){
                this.playerOffItemStack = player.getItemInHand(HandTypes.MAIN_HAND).get();
            }
            return this;
        }

        public PlayerItemUtilsBuilder setPlayerMainItemStack(ItemStack stack){
            this.playerMainItemStack = stack;
            return this;
        }

        public PlayerItemUtilsBuilder setPlayerOffItemStack(ItemStack stack){
            this.playerOffItemStack = stack;
            return this;
        }

        public PlayerItemUtils build(){
            return new PlayerItemUtils(this.chat , this.playerMainItemStack , this.playerOffItemStack);
        }

    }



}
