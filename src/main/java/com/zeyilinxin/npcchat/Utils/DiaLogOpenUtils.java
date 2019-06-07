package com.zeyilinxin.npcchat.Utils;

public class DiaLogOpenUtils {

                    /*boolean ishasper = false;
                boolean ishasRequirements = true;
                boolean isquan = false;
                String button = "";
                for(Object b : chat.getConfigurationNode().getNode(new Object[]{"Data" , name , npc}).getChildrenMap().keySet()){
                    if (this.getHasPermission(name , b)){

                    }
                    Debug.dubug("Button" +b);
                    button = MePleaceholder.parse((String) b, player , player);
                    Set<Object> buttonlist = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , npc , b}).getChildrenMap().keySet();
                    for (Object Permission : buttonlist){
                        String per = (String) Permission;
                        Debug.dubug("Permission:" + per);
                        if (per.equalsIgnoreCase("Permission")){
                            ishasper = true;

                        }
                    }
                    Debug.dubug("isPermission" + !isquan);
                    if (ishasper){
                        if (!isquan){
                            boolean isreq = false;
                            for (Object re : buttonlist){
                                Debug.dubug("TwoButton" + re);
                                String requirement = (String) re;
                                if (requirement.equalsIgnoreCase("Requirements")){
                                    isreq = true;


                                }
                            }
                            if (isreq){
                                if (!ishasRequirements){
                                    for(Object op : buttonlist){
                                        String butt = (String) op;
                                        if (butt.equalsIgnoreCase("cmd") || butt.equalsIgnoreCase("open")) {
                                            dialogueBuilder.addChoice(Choice.builder()
                                                    .setText(button)
                                                    .setHandle(e -> {
                                                        Timer timer = new Timer();
                                                        TimerTask task = new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                                                                    String cmd = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Cmd"}).getString("").replace("<player>", player.getName());
                                                                    if (!cmd.isEmpty() && cmd != null) {
                                                                        if (cmd.startsWith("[")){
                                                                            try {
                                                                                List<String> perlist = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Cmd"}).getList(TypeToken.of(String.class));
                                                                                String useper = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , npc , b , "usePermission"}).getString("");
                                                                                for(String c : perlist){
                                                                                    if (c.indexOf("cmd") != -1) {
                                                                                        c = c.substring(c.indexOf(":") + 1).trim();
                                                                                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), c.replace("<player>", player.getName()));
                                                                                    } else {
                                                                                        if (!useper.isEmpty()){
                                                                                            if (player.hasPermission(useper)){
                                                                                                Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                            } else {
                                                                                                StringUtils.usePermission(player , useper);
                                                                                                Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                                StringUtils.removePermission(player , useper);
                                                                                            }
                                                                                        } else {
                                                                                            Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                        }
                                                                                    }
                                                                                }
                                                                            } catch (ObjectMappingException e1) {
                                                                            }
                                                                        } else {
                                                                            if (cmd.indexOf("cmd") != -1) {
                                                                                cmd = cmd.substring(cmd.indexOf(":") + 1).trim();
                                                                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), cmd);
                                                                            } else {
                                                                                Sponge.getCommandManager().process(player, cmd);
                                                                            }
                                                                        }


                                                                    }
                                                                });
                                                                String Outopen = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Open"}).getString("");
                                                                if (!Outopen.isEmpty() && Outopen != null) {
                                                                    //  ArrayList<Dialogue> dialogueArrayList = new ArrayList<>();;
                                                                    StringUtils stringUtils = new StringUtils(chat);
                                                                    Dialogue.setPlayerDialogueData((EntityPlayerMP) player, stringUtils.getDialog(Outopen, player), true);


                                                                }
                                                            }
                                                        };
                                                        timer.schedule(task , 1 * 100);
                                                    })
                                                    .build());
                                        }
                                    }

                                }
                            } else {
                                for(Object op : buttonlist){
                                    String butt = (String) op;
                                    if (butt.equalsIgnoreCase("cmd") || butt.equalsIgnoreCase("open")) {
                                        dialogueBuilder.addChoice(Choice.builder()
                                                .setText(button)
                                                .setHandle(e -> {
                                                    Timer timer = new Timer();
                                                    TimerTask task = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                                                                String cmd = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Cmd"}).getString("").replace("<player>", player.getName());
                                                                if (!cmd.isEmpty() && cmd != null) {
                                                                    if (cmd.startsWith("[")){
                                                                        try {
                                                                            List<String> perlist = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Cmd"}).getList(TypeToken.of(String.class));
                                                                            String useper = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , npc , b , "usePermission"}).getString("");
                                                                            for(String c : perlist){
                                                                                if (c.indexOf("cmd") != -1) {
                                                                                    c = c.substring(c.indexOf(":") + 1).trim();
                                                                                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), c.replace("<player>", player.getName()));
                                                                                } else {
                                                                                    if (!useper.isEmpty()){
                                                                                        if (player.hasPermission(useper)){
                                                                                            Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                        } else {
                                                                                            StringUtils.usePermission(player , useper);
                                                                                            Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                            StringUtils.removePermission(player , useper);
                                                                                        }
                                                                                    } else {
                                                                                        Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (ObjectMappingException e1) {
                                                                        }
                                                                    } else {
                                                                        if (cmd.indexOf("cmd") != -1) {
                                                                            cmd = cmd.substring(cmd.indexOf(":") + 1).trim();
                                                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), cmd);
                                                                        } else {
                                                                            Sponge.getCommandManager().process(player, cmd);
                                                                        }
                                                                    }


                                                                }
                                                            });
                                                            String Outopen = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Open"}).getString("");
                                                            if (!Outopen.isEmpty() && Outopen != null) {
                                                                //   ArrayList<Dialogue> dialogueArrayList = new ArrayList<>();;
                                                                StringUtils stringUtils = new StringUtils(chat);
                                                                Dialogue.setPlayerDialogueData((EntityPlayerMP) player, stringUtils.getDialog(Outopen, player), true);

                                                            }
                                                        }
                                                    };
                                                    timer.schedule(task , 1 * 100);
                                                })
                                                .build());
                                    }
                                }

                            }
                        }
                    } else {
                        boolean isreq = false;
                        for (Object re : buttonlist){
                            String requirement = (String) re;
                            if (requirement.equalsIgnoreCase("Requirements")){
                                isreq = true;
                                try {
                                    List<String> relist = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , npc , b , requirement}).getList(TypeToken.of(String.class));
                                    for(String req :relist ){
                                        Requirements requirements = new Requirements(player , req);
                                        if (!requirements.getSu()){
                                            ishasRequirements = false;
                                        }
                                    }

                                } catch (ObjectMappingException e) {
                                }

                            }
                        }
                        if (isreq){
                            if (!ishasRequirements){
                                for(Object op : buttonlist){
                                    String butt = (String) op;
                                    if (butt.equalsIgnoreCase("cmd") || butt.equalsIgnoreCase("open")) {
                                        dialogueBuilder.addChoice(Choice.builder()
                                                .setText(button)
                                                .setHandle(e -> {
                                                    Timer timer = new Timer();
                                                    TimerTask task = new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                                                                String cmd = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Cmd"}).getString("").replace("<player>", player.getName());
                                                                if (!cmd.isEmpty() && cmd != null) {
                                                                    if (cmd.startsWith("[")){
                                                                        try {
                                                                            List<String> perlist = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Cmd"}).getList(TypeToken.of(String.class));
                                                                            String useper = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , npc , b , "usePermission"}).getString("");
                                                                            for(String c : perlist){
                                                                                if (c.indexOf("cmd") != -1) {
                                                                                    c = c.substring(c.indexOf(":") + 1).trim();
                                                                                    Sponge.getCommandManager().process(Sponge.getServer().getConsole(), c.replace("<player>", player.getName()));
                                                                                } else {
                                                                                    if (!useper.isEmpty()){
                                                                                        if (player.hasPermission(useper)){
                                                                                            Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                        } else {
                                                                                            StringUtils.usePermission(player , useper);
                                                                                            Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                            StringUtils.removePermission(player , useper);
                                                                                        }
                                                                                    } else {
                                                                                        Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                    }
                                                                                }
                                                                            }
                                                                        } catch (ObjectMappingException e1) {
                                                                        }
                                                                    } else {
                                                                        if (cmd.indexOf("cmd") != -1) {
                                                                            cmd = cmd.substring(cmd.indexOf(":") + 1).trim();
                                                                            Sponge.getCommandManager().process(Sponge.getServer().getConsole(), cmd);
                                                                        } else {
                                                                            Sponge.getCommandManager().process(player, cmd);
                                                                        }
                                                                    }


                                                                }
                                                            });
                                                            String Outopen = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Open"}).getString("");
                                                            if (!Outopen.isEmpty() && Outopen != null) {
                                                                // ArrayList<Dialogue> dialogueArrayList = new ArrayList<>();;
                                                                StringUtils stringUtils = new StringUtils(chat);
                                                                Dialogue.setPlayerDialogueData((EntityPlayerMP) player, stringUtils.getDialog(Outopen, player), true);


                                                            }
                                                        }
                                                    };
                                                    timer.schedule(task , 1 * 100);
                                                })
                                                .build());
                                    }
                                }

                            }
                        } else {
                            for(Object op : buttonlist){
                                String butt = (String) op;
                                if (butt.equalsIgnoreCase("cmd") || butt.equalsIgnoreCase("open")) {
                                    dialogueBuilder.addChoice(Choice.builder()
                                            .setText(button)
                                            .setHandle(e -> {
                                                Timer timer = new Timer();
                                                TimerTask task = new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        FMLCommonHandler.instance().getMinecraftServerInstance().func_152344_a(() -> {
                                                            String cmd = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Cmd"}).getString("").replace("<player>", player.getName());
                                                            String useper = chat.getConfigurationNode().getNode(new Object[]{"Data" , name , npc , b , "usePermission"}).getString("");
                                                            if (!cmd.isEmpty() && cmd != null) {
                                                                if (cmd.startsWith("[")){
                                                                    try {
                                                                        List<String> perlist = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Cmd"}).getList(TypeToken.of(String.class));
                                                                        for(String c : perlist){
                                                                            if (c.indexOf("cmd") != -1) {
                                                                                c = c.substring(c.indexOf(":") + 1).trim();
                                                                                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), c.replace("<player>", player.getName()));
                                                                            } else {
                                                                                if (!useper.isEmpty()){
                                                                                    if (player.hasPermission(useper)){
                                                                                        Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                    } else {
                                                                                        StringUtils.usePermission(player , useper);
                                                                                        Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                        StringUtils.removePermission(player , useper);
                                                                                    }
                                                                                } else {
                                                                                    Sponge.getCommandManager().process(player, c.replace("<player>", player.getName()));
                                                                                }
                                                                            }
                                                                        }
                                                                    } catch (ObjectMappingException e1) {
                                                                    }
                                                                } else {
                                                                    if (cmd.indexOf("cmd") != -1) {
                                                                        cmd = cmd.substring(cmd.indexOf(":") + 1).trim();
                                                                        Sponge.getCommandManager().process(Sponge.getServer().getConsole(), cmd);
                                                                    } else {
                                                                        Sponge.getCommandManager().process(player, cmd);
                                                                    }
                                                                }


                                                            }
                                                        });
                                                        String Outopen = chat.getConfigurationNode().getNode(new Object[]{"Data", name, npc, b, "Open"}).getString("");
                                                        if (!Outopen.isEmpty() && Outopen != null) {
                                                            // ArrayList<Dialogue> dialogueArrayList = new ArrayList<>();;
                                                            //  dialogueArrayList.add(getDialogue(Outopen, player));
                                                            StringUtils stringUtils = new StringUtils(chat);
                                                            Dialogue.setPlayerDialogueData((EntityPlayerMP) player, stringUtils.getDialog(Outopen, player), true);
                                                        }
                                                    }
                                                };
                                                timer.schedule(task , 1 * 100);

                                            })
                                            .build());
                                }
                            }

                        }
                    }


                }*/
}
