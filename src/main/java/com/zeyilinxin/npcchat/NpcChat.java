package com.zeyilinxin.npcchat;


import com.google.inject.Inject;
import com.zeyilinxin.npcchat.Command.NpcChatCommand;
import com.zeyilinxin.npcchat.Event.NPCEvent;
import com.zeyilinxin.npcchat.Utils.MePleaceholder;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import java.io.File;
import java.io.IOException;
import java.util.*;
/*
* Name:Npcchat
* #The code is written in a mess, if you have suggestions, please ask
* #I am also the first open source project and the first public project.
* Authors:择忆霖心
* */
@Plugin(id = "npcchat", name = "NpcChat", authors = { "择忆霖心" },version = "1.5.7", description = "口袋妖怪排位")
public class NpcChat {

    private final Map<UUID, String> addcmd = new HashMap<>();
    public static boolean dubug = false;
    private MePleaceholder pleaceholder;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private File defaultConfig;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private CommentedConfigurationNode configurationNode;
    private static NpcChat npcChat;

    @Listener
    public void onGameInitlization(final GameInitializationEvent event) {
        try {
            if (!this.defaultConfig.exists()) {
                this.defaultConfig.createNewFile();
                this.configurationNode = ((CommentedConfigurationNode) this.configurationLoader.load());
                this.configurationLoader.save(this.configurationNode);
            }
            this.configurationNode = ((CommentedConfigurationNode) this.configurationLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        npcChat = this;
        if (Sponge.getPluginManager().isLoaded("placeholderapi")) {
            pleaceholder = new MePleaceholder(this);
            this.getServerConsole().sendMessage(Text.of("检测到有placeholderapi已经开启变量"));
        }

        Sponge.getEventManager().registerListeners(this, new NPCEvent(this));
        NpcChatCommand jlCommand = new NpcChatCommand(this);
        CommandSpec createcmd = CommandSpec.builder()
                .permission("npcchat.create.use")
                .arguments(GenericArguments.string(Text.of("name")))
                .description(Text.of("Create chat dialog"))
                .executor(jlCommand.new Cretae())
                .build();
        CommandSpec removecmd = CommandSpec.builder()
                .permission("npcchat.remove.use")
                .arguments(GenericArguments.string(Text.of("name")))
                .description(Text.of("Delete chat dialog"))
                .executor(jlCommand.new Remove())
                .build();
        CommandSpec recmd = CommandSpec.builder()
                .permission("npcchat.reload.use")
                .description(Text.of("Reload the configuration file"))
                .executor(jlCommand.new Reload())
                .build();
        CommandSpec addPer = CommandSpec.builder()
                .permission("npcchat.add.use")
                .arguments(GenericArguments.string(Text.of("name")) , GenericArguments.string(Text.of("permission")),
                        GenericArguments.bool(Text.of("boolean")))
                .description(Text.of(""))
                .executor(jlCommand.new Add())
                .build();
        CommandSpec myCommandSpec = CommandSpec.builder()
                .description(Text.of("Chat dialog main command"))
                .arguments(GenericArguments.string(Text.of("open")) ,GenericArguments.optional(GenericArguments.player(Text.of("player"))))
                .permission("npcchat.use")
                .child(createcmd, "create")
                .child(removecmd, "remove")
                .child(recmd, "reload")
                .child(addPer , "add")
                .executor(jlCommand)
                .build();
        Sponge.getCommandManager().register((Object) this, myCommandSpec, new String[]{"npcchat"});
    }

    public CommentedConfigurationNode getConfigurationNode() {
        return this.configurationNode;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getConfigurationLoader() {
        return this.configurationLoader;
    }

    public ConsoleSource getServerConsole() {
        return Sponge.getServer().getConsole();
    }

    public Map<UUID, String> getAddcmd() {
        return this.addcmd;
    }

    public void setReload(CommentedConfigurationNode node) {
        this.configurationNode = node;
    }

    public MePleaceholder getPleaceholder(){
        return this.pleaceholder;
    }

    public static NpcChat getNpcchat(){
        return npcChat;
    }
}
