package com.zeyilinxin.npcchat.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import com.google.common.reflect.TypeToken;
import com.pixelmonmod.pixelmon.api.dialogue.Choice;
import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.zeyilinxin.npcchat.NpcChat;
import com.zeyilinxin.npcchat.api.Requirements.RequirementsUtlis;
import com.zeyilinxin.npcchat.api.button.utils.ButtonUtils;
import com.zeyilinxin.npcchat.api.permission.DialogPermission;

import net.minecraftforge.fml.common.FMLCommonHandler;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

/**
 * 该类主要用于处理NpcChat的按钮加载
 * @Auth 择忆霖心
 */
public class StringUtils {

    private NpcChat chat;
    private boolean hasRequirements = false;
    private ArrayList<Dialogue> dialogueArrayList = new ArrayList<>();

    public StringUtils(NpcChat chat){
        this.chat = chat;
    }

    public ArrayList<Dialogue> getDialog(String name , Player player) {

        for(Object data : chat.getConfigurationNode().getNode("Data").getChildrenMap().keySet()){
            if (data.equals(name)){
                Dialogue dialogue = this.getDialogue(name , player , 0);
                if (dialogue != null){
                    dialogueArrayList.add(dialogue);
                }
            }
        }
        return dialogueArrayList == null ? null : dialogueArrayList;
    }

    public synchronized Dialogue getDialogue(String name , Player player , int d ) {
        Dialogue.DialogueBuilder dialogueBuilder = Dialogue.builder();
        String open = this.getOpen(name);
        if (!open.isEmpty()){
            this.getDialog(open, player);
        }
        if (!this.getRequirements(name).isEmpty()){
            RequirementsUtlis requirements = new RequirementsUtlis(player , this.getRequirements(name));
            if (!requirements.isVia()){
                return null;
            }
        }
        dialogueBuilder.setName(MePleaceholder.parse(this.getDialogueName(name), player , player));
        dialogueBuilder.setText(MePleaceholder.parse(this.getDialogueText(name), player , player));
        List<String> list = ButtonUtils.formList(name);
        if (list.size() != 0){
            for(String l : list){
                ButtonUtils buttonUtils = new ButtonUtils(l.replace("&" , "§"));
                buttonUtils.setRequirements((requirements , bu , v)->{
                    if (v){
                        RequirementsUtlis requirementsUtlis = new RequirementsUtlis(player , requirements);
                        if (requirementsUtlis.isVia()){
                            this.dialogRun(name , bu , dialogueBuilder , player);
                            return;
                        } else {
                            return;
                        }
                    } else {
                        this.dialogRun(name , bu , dialogueBuilder , player);
                        return;
                    }
                });
                buttonUtils.setPermission((permission , button , isV)->{
                    if (isV){
                        DialogPermission dialogPermission = new DialogPermission(player , permission);
                        if (dialogPermission.getPermission()){
                            buttonUtils.setRunreuirements(this.getButtonRequirements(name , l));
                            return;
                        } else {
                            return;
                        }
                    } else {
                        buttonUtils.setRunreuirements(this.getButtonRequirements(name , l));
                        return;
                    }
                });
                buttonUtils.setRun(this.getButtonPermission(name , l));

            }
            return dialogueBuilder.build();
        } else {
            return dialogueBuilder.build();
        }
    }

    private String getCmd(String npcName , String name , String playerName){
        String cmd = chat.getConfigurationNode().getNode(new Object[]{"Data", npcName, "Button", name.replace("§" , "&"), "Cmd"}).getString("").replace("<player>", playerName);
        if (cmd.isEmpty()){
            return this.chat.getConfigurationNode().getNode(new Object[]{"Data" , npcName , "button" , name.replace("§" , "&") , "cmd"}).getString("")
                    .replace("<player>" , playerName);
        } else {
            return cmd;
        }
    }

    private synchronized String getOpen(String npcName , String name){
        String open = chat.getConfigurationNode().getNode(new Object[]{"Data", npcName, "Button", name.replace("§" , "&"), "Open"}).getString("");
        if (open.isEmpty()){
            return this.chat.getConfigurationNode().getNode(new Object[]{"Data" , npcName , "button" , name.replace("§" , "&"), "open"}).getString("");
        } else {
            return open;
        }
    }



    public synchronized void dialogRun(String npcName , String name , Dialogue.DialogueBuilder dialogueBuilder , Player player){
        String cmd = this.getCmd(npcName , name , player.getName());
        String Outopen = this.getOpen(npcName,name);
        if (cmd != null && !cmd.isEmpty()){
            if (!Outopen.isEmpty() && Outopen != null){
                this.cmdOpenrun(dialogueBuilder , name , player , cmd , this.getButtonCmd(npcName , name) , Outopen);
                return;
            } else {
                this.cmdRun(dialogueBuilder , name , player , cmd , this.getButtonCmd(npcName , name));
                return;
            }
        } else {
            if (!Outopen.isEmpty() && Outopen != null){
                this.openRun(dialogueBuilder , name , player , Outopen);
                return;
            } else {
                this.dialogButtonFill(dialogueBuilder , name , player);
                return;
            }

        }
    }

    private synchronized void dialogButtonFill(Dialogue.DialogueBuilder dialogueBuilder , String text  , Player player){
        dialogueBuilder.addChoice(Choice.builder()
                .setText(MePleaceholder.parse(text, player , player))
                .build()
        );
    }

    private void cmdOpenrun(Dialogue.DialogueBuilder dialogueBuilder , String text , Player player , final String cmd , final List<String> cmdList , String open){
        dialogueBuilder.addChoice(Choice.builder()
                .setText(MePleaceholder.parse(text, player , player))
                .setHandle(e -> {
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
                                if (!cmd.isEmpty() && cmd != null) {
                                    if (cmd.startsWith("[")){
                                        for(String c : cmdList){
                                            char[] chars = c.toCharArray();
                                            if (chars[0] == 'c' && chars[1] == 'm' && chars[2] == 'd' && chars[3] == ':') {
                                                c = c.substring(c.indexOf(":") + 1).trim();
                                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), c.replace("<player>", player.getName()));
                                            } else {
                                                Sponge.getCommandManager().process(player, c.replace("<player>" , player.getName()));
                                            }
                                        }

                                    } else {
                                        char[] chars = cmd.toCharArray();
                                        if (chars[0] == 'c' && chars[1] == 'm' && chars[2] == 'd' && chars[3] == ':') {
                                            String buttonCmd = cmd.substring(cmd.indexOf(":") + 1).trim();
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), buttonCmd.replace("<player>" , player.getName()));
                                        } else {
                                            Sponge.getCommandManager().process(player, cmd.replace("<player>" , player.getName()));
                                        }
                                    }
                                }

                            });
                        }
                    };
                    timer.schedule(task , 1 * 100);
                    StringUtils stringUtils = new StringUtils(chat);
                    ArrayList<Dialogue> arrayList = stringUtils.getDialog(open, player);
                    Dialogue[] dialogues = new Dialogue[arrayList.size()];
                    for(int i = 0 ; i < arrayList.size(); i++){
                        dialogues[i] = arrayList.get(i);
                    }
                    e.reply(dialogues);
                })
                .build());
    }


    private synchronized void cmdRun(Dialogue.DialogueBuilder dialogueBuilder , String text , Player player , final String cmd , final List<String> cmdList){
        dialogueBuilder.addChoice(Choice.builder()
                .setText(MePleaceholder.parse(text, player , player))
                .setHandle(e -> {
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
                                if (!cmd.isEmpty() && cmd != null) {
                                    if (cmd.startsWith("[")){
                                        for(String c : cmdList){
                                            char[] chars = c.toCharArray();
                                            if (chars[0] == 'c' && chars[1] == 'm' && chars[2] == 'd' && chars[3] == ':') {
                                                c = c.substring(c.indexOf(":") + 1).trim();
                                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), c.replace("<player>", player.getName()));
                                            } else {
                                                Sponge.getCommandManager().process(player, c.replace("<player>" , player.getName()));
                                                    /*if (!useper.isEmpty()){
                                                        if (player.hasPermission(useper)){
                                                            Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                        } else {
                                                            StringUtils.usePermission(player , useper , true);
                                                            Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                            StringUtils.removePermission(player , useper , false);
                                                        }
                                                    } else {
                                                        Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                    }*/
                                            }
                                        }
                                    } else {
                                        char[] chars = cmd.toCharArray();
                                        if (chars[0] == 'c' && chars[1] == 'm' && chars[2] == 'd' && chars[3] == ':') {
                                            String buttonCmd = cmd.substring(cmd.indexOf(":") + 1).trim();
                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), buttonCmd.replace("<player>" , player.getName()));
                                        } else {
                                            Sponge.getCommandManager().process(player, cmd.replace("<player>" , player.getName()));
                                        }
                                    }
                                } else {
                                    return;
                                }
                            });
                        }
                    };
                    timer.schedule(task , 1 * 100);
                })
                .build());
    }

    private synchronized void openRun(Dialogue.DialogueBuilder dialogueBuilder , String text , Player player , String open){
        dialogueBuilder.addChoice(Choice.builder()
                .setText(MePleaceholder.parse(text, player , player))
                .setHandle(e -> {
                    StringUtils stringUtils = new StringUtils(chat);
                    ArrayList<Dialogue> arrayList = stringUtils.getDialog(open, player);
                    Dialogue[] dialogues = new Dialogue[arrayList.size()];
                    for(int i = 0 ; i < arrayList.size(); i++){
                        dialogues[i] = arrayList.get(i);
                    }
                    e.reply(dialogues);
                }).build()
        );


    }




    /**
     * 获取对应的对话框名称
     * @param name 传入NPC名称
     * @return 返回对应的NPC名称
     */
    public synchronized String getOpen(String name){
        String arg = this.chat.getConfigurationNode().getNode(new Object[]{"Data", name, "Open"}).getString("");
        if (arg.isEmpty()){
             return this.chat.getConfigurationNode().getNode(new Object[]{"Data", name, "open"}).getString("");
        } else {
            return arg;
        }
    }

    /**
     * 获取是否存在
     * @param name 传入对应的NPC名称
     * @return 返回是否存在
     */
    public List<String> getRequirements(String name){
        List<String> arg = new ArrayList<>();
        try {
            arg = this.chat.getConfigurationNode().getNode(new Object[]{"Data", name, "Requirements"}).getList(TypeToken.of(String.class) , new ArrayList<>());
        } catch (ObjectMappingException e) {
        }
        if (arg.isEmpty()){
            try {
                return this.chat.getConfigurationNode().getNode(new Object[]{"Data", name, "requirements"}).getList(TypeToken.of(String.class) , new ArrayList<>());
            } catch (ObjectMappingException e) {
                return new ArrayList<String>();
            }
        } else {
            return arg;
        }
    }

    public List<String> getButtonCmd(String name , String button){
        List<String> arg = new ArrayList<>();
        try {
            arg = this.chat.getConfigurationNode().getNode(new Object[]{"Data", name, "Button", button.replace("§" , "&"), "Cmd"}).getList(TypeToken.of(String.class) , new ArrayList<>());
        } catch (ObjectMappingException e) {
        }
        if (arg.isEmpty()){
            try {
                return this.chat.getConfigurationNode().getNode(new Object[]{"Data", name, "button", button.replace("§" , "&"), "cmd"}).getList(TypeToken.of(String.class) , new ArrayList<>());
            } catch (ObjectMappingException e) {
                return new ArrayList<String>();
            }
        } else {
            return arg;
        }
    }

    public List<String> getButtonPermission(String npc ,String name){
        List<String> arg = new ArrayList<>();
        try {
            arg = this.chat.getConfigurationNode().getNode(new Object[]{"Data", npc, "Button" , name , "Permission"}).getList(TypeToken.of(String.class) , new ArrayList<>());
        } catch (ObjectMappingException e) {
        }
        if (arg.isEmpty()){
            try {
                return this.chat.getConfigurationNode().getNode(new Object[]{"Data", npc, "button" , name , "permission"}).getList(TypeToken.of(String.class) , new ArrayList<>());
            } catch (ObjectMappingException e) {
                return new ArrayList<String>();
            }
        } else {
            return arg;
        }
    }

    public List<String> getButtonRequirements(String npc , String name){
        List<String> arg = new ArrayList<>();
        try {
            arg = this.chat.getConfigurationNode().getNode(new Object[]{"Data", npc, "Button" , name , "Requirements"}).getList(TypeToken.of(String.class) , new ArrayList<>());
        } catch (ObjectMappingException e) {
        }
        if (arg.isEmpty()){
            try {
                return this.chat.getConfigurationNode().getNode(new Object[]{"Data", npc, "button" , name , "requirements"}).getList(TypeToken.of(String.class) , new ArrayList<>());
            } catch (ObjectMappingException e) {
                return new ArrayList<String>();
            }
        } else {
            return arg;
        }
    }

    /**
     * 获取Dialog的标题
     * @param name 传入对应的Npc名称
     * @return 返回标题
     */
    public String getDialogueName(String name){
        String arg = this.chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Name"}).getString("");
        if (arg.isEmpty()){
            return this.chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "name"}).getString("");
        } else {
            return arg;
        }
    }

    /**
     * 获取Dialog的信息
     * @param name 传入对应的Npc名称
     * @return 返回信息
     */
    public String getDialogueText(String name){
        String arg = this.chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Text"}).getString("").replace("&" , "§");
        if (arg.isEmpty()){
            return this.chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "text"}).getString("").replace("&" , "§");
        } else {
            return arg;
        }
    }


//    private boolean getHasPermission(String name, Object b) {
//        boolean hasPermission = false;
//        for (Object p : chat.getConfigurationNode().getNode(new Object[]{"Data" , name , "Button" , b}).getChildrenMap().keySet()){
//            if (p.equals("Permission") || p.equals("permission")){
//                hasPermission = true;
//            }
//            if (p.equals("Requirements") || p.equals("requirements")){
//                setRequirements(true);
//            }
//        }
//        return hasPermission;
//    }
//
//    private void setRequirements(boolean r){
//        this.hasRequirements = r;
//    }
//
//    private boolean getHasRequirements(){
//        return this.hasRequirements;
//    }




    /**
     * 抽奖几率类
     */
    class Dice{

        public boolean getDice(int i){
            if (i ==100){
                return true;
            }
            Random random = new Random();
            int a = random.nextInt(100) + 1;
            if (a <= i) {
                return true;
            }
            return false;
        }
    }


//    private boolean getMainItem(Object name , Object b , ItemStack stack) throws ObjectMappingException {
//        String item = chat.getConfigurationNode().getNode(new Object[]{"Data", name, "Button", b, "Item"}).getString("");
//        if (item != null && !item.isEmpty()){
//            PlayerItemUtils playerItemUtils = new PlayerItemUtils(chat , stack , stack);
//            return playerItemUtils.contrast(item , false);
//        }
//        return true;
//    }

}
