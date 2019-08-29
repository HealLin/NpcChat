package com.zeyilinxin.npcchat.Event;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.api.dialogue.Choice;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.zeyilinxin.npcchat.NpcChat;
import com.zeyilinxin.npcchat.Utils.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.item.ItemMergeItemEvent;
import org.spongepowered.api.event.entity.item.TargetItemEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent;
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NPCEvent {

    private NpcChat chat;


    public NPCEvent(NpcChat chat) {
        this.chat = chat;
    }

    	// You can't do this. This is a Forge event, which does not use @Listener or @First.
//    @Listener
//    public void onserverquit(FMLNetworkEvent.ServerConnectionFromClientEvent event , @First Player player){
//        if (chat.getAddcmd().containsKey(player.getUniqueId())){
//            chat.getAddcmd().remove(player.getUniqueId());
//        }
//    }
    @Listener
    public void OnQuilt(ClientConnectionEvent.Disconnect event, @First Player player){
        if (chat.getAddcmd().containsKey(player.getUniqueId())){
            chat.getAddcmd().remove(player.getUniqueId());
        }
    }


    @Listener
    public void onBlock(InteractBlockEvent.Secondary.MainHand event , @First Player player){
        Optional<Vector3d> optionalVector3d = event.getInteractionPoint();
        if (!optionalVector3d.isPresent()){
            return;
        }
        String blockData = event.getInteractionPoint().get().toString();
        if (blockData != null && !blockData.isEmpty()){
            FileUtils utils = new FileUtils(chat);
            if (chat.getAddcmd().containsKey(player.getUniqueId())) {
                utils.setBlock(blockData + player.getWorld().getName() , chat.getAddcmd().get(player.getUniqueId()));
                FileUtils.setNpcChatData(chat.getAddcmd().get(player.getUniqueId()));
                player.sendMessages(Text.of("§eSuccessfully bound!"));
                chat.getAddcmd().remove(player.getUniqueId());
            }
            else{
                FileUtils fileUtils = new FileUtils(chat);
                String name = fileUtils.getBlockData(blockData + player.getWorld().getName());
                if (name.isEmpty() | name.equals("")){
                    return;
                }
                StringUtils stringUtils = new StringUtils(chat);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Dialogue.setPlayerDialogueData((EntityPlayerMP)player,stringUtils.getDialog(name , player), true);
                    }
                }).start();
            }
        }
    }

    private List<Dialogue> getDialog() {
        ArrayList<Dialogue> dialoglist = new ArrayList<>();
        dialoglist.add(null);
        return dialoglist;
    }


    @Listener
    public synchronized void onNPCClick(final InteractEntityEvent.Secondary.MainHand e, @First final Player player) {
        StringUtils stringUtils = new StringUtils(chat);
        String EntityUUID = e.getTargetEntity().getUniqueId().toString();
        if (EntityUUID != null && !EntityUUID.isEmpty()){
            final EntityType et =  Sponge.getRegistry().getType(EntityType.class, "pixelmon:chattingnpc").get();
            FileUtils utils = new FileUtils(chat);
            if (chat.getAddcmd().containsKey(player.getUniqueId())) {
                e.setCancelled(true);
                utils.setNpcData(e.getTargetEntity().getUniqueId().toString() , chat.getAddcmd().get(player.getUniqueId()));
                FileUtils.setNpcChatData(chat.getAddcmd().get(player.getUniqueId()));
                player.sendMessages(Text.of("§eSuccessfully bound!"));
                chat.getAddcmd().remove(player.getUniqueId());
            }
            else{
                FileUtils fileUtils = new FileUtils(chat);
                String name = fileUtils.getNpcData(e.getTargetEntity().getUniqueId().toString());
                if (name.isEmpty() | name.equals("")){
                    return;
                }
                Dialogue.setPlayerDialogueData((EntityPlayerMP)player,stringUtils.getDialog(name , player), true);
                e.setCancelled(true);
            }
        }

    }

}
