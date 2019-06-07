package com.zeyilinxin.npcchat.Command;

import com.pixelmonmod.pixelmon.api.dialogue.Dialogue;
import com.zeyilinxin.npcchat.NpcChat;
import com.zeyilinxin.npcchat.Utils.FileUtils;
import com.zeyilinxin.npcchat.Utils.StringUtils;
import me.lucko.luckperms.api.LuckPermsApi;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.ProviderRegistration;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;
import java.util.*;

public class NpcChatCommand implements CommandExecutor {

    private NpcChat npcChat;

    public NpcChatCommand(NpcChat npcChat) {
        this.npcChat = npcChat;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player){
            Player mePlayer = (Player) src;
            String open = args.<String>getOne("open").get();
            Optional<Player> player = args.<Player>getOne("player");
            StringUtils stringUtils = new StringUtils(npcChat);
            if (player.isPresent()){
                if (mePlayer.hasPermission("npcchat.open.other." + open)){
                    Player pl = player.get();
                    Dialogue.setPlayerDialogueData((EntityPlayerMP)pl,stringUtils.getDialog(open , pl), true);
              /*  Optional<Player> optionalPlayer = Sponge.getServer().getPlayer(player.get().getName());
                  if (!optionalPlayer.isPresent()){
                       mePlayer.sendMessage(Text.of("§cPlayers are not online."));
                       return CommandResult.empty();
                  }*/
                    return CommandResult.success();
                } else {
                    mePlayer.sendMessage(Text.of("§cYou don't have enough authority."));
                    return CommandResult.empty();
                }
            } else {
                if (mePlayer.hasPermission("npcchat.open.self." + open)){
                    Dialogue.setPlayerDialogueData((EntityPlayerMP)mePlayer,stringUtils.getDialog(open , mePlayer), true);
                    return CommandResult.success();
                } else {
                    mePlayer.sendMessage(Text.of("§cYou don't have enough authority."));
                    return CommandResult.empty();
                }
            }
        }
        throw  new CommandException(Text.of("Only players can perform"));

    }

    public class Help implements CommandExecutor{
        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            if (src instanceof Player){
                src.sendMessage(Text.of("§a/npcchat <open> <name>"));
                src.sendMessage(Text.of("§a/npcchat create <name>"));
                src.sendMessage(Text.of("§6Right-click on NPC after entering command or block binding dialog"));
                src.sendMessage(Text.of("§a/npcchat remove"));
                src.sendMessage(Text.of("§cRight-click on NPC after entering command to delete the binding"));
                src.sendMessage(Text.of("§d/npcchat out <name>"));
                src.sendMessage(Text.of("§dAfter input, the corresponding dialog box will pop up"));
                src.sendMessage(Text.of("§e/npcchat reload"));
                src.sendMessage(Text.of("§e/Reload the configuration file"));
                return CommandResult.success();
            }
            throw  new CommandException(Text.of("Only players can perform"));
        }
    }

    public class Add implements CommandExecutor{

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            String name = args.<String>getOne(Text.of("name")).get();
            String permissionString = args.<String>getOne(Text.of("permission")).get();
            boolean isHas = args.<Boolean>getOne(Text.of("boolean")).get();
            Optional<ProviderRegistration<LuckPermsApi>> provider = Sponge.getServiceManager().getRegistration(LuckPermsApi.class);
            if (provider.isPresent()) {
                LuckPermsApi api = provider.get().getProvider();
                api.getUser(name).setPermission(api.getNodeFactory().newBuilder(permissionString).setValue(isHas).build());
                api.getStorage().saveUser(api.getUser(name));
                Sponge.getCommandManager().process(npcChat.getServerConsole() , "lp user " + api.getUser(name).getUuid().toString() +
                        " permission info");
                src.sendMessage(Text.of("§a====================="));
                src.sendMessage(Text.of("§dUUID:§e" + api.getUser(name).getUuid().toString()));
                src.sendMessage(Text.of("§d名字:§e" + name));
                src.sendMessage(Text.of("§d权限:§e" + permissionString));
                src.sendMessage(Text.of("§d状态:§e" + getBoolean(isHas)));
                src.sendMessage(Text.of("§a====================="));
                return CommandResult.success();
            } else {
                src.sendMessage(Text.of("§c没有找到luckperms"));
                return CommandResult.empty();
            }
        }

        private String getBoolean(boolean is){
            if (is){
                return "拥有";
            } else {
                return "未拥有";
            }
        }
    }

    public class Cretae implements CommandExecutor{

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            if (src instanceof  Player){

                Player player = (Player) src;
                String name = args.<String>getOne("name").get();
                npcChat.getAddcmd().put(player.getUniqueId() , name);
                player.sendMessage(Text.of("§aPlease right bind to the box or NPC!"));
                return CommandResult.success();
            }
            throw new CommandException(Text.of("Console cannot use commands"));
        }
    }

    public class Remove implements CommandExecutor{
        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            String name = args.<String>getOne("name").get();
            FileUtils utils = new FileUtils(npcChat);
            utils.Remove(name);
            src.sendMessage(Text.of("§aRemoved successfully"));
            return CommandResult.success();
        }
    }


    public class Reload implements CommandExecutor{

        @Override
        public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
            try {
                npcChat.setReload(npcChat.getConfigurationLoader().load());
                src.sendMessage(Text.of(TextColors.GREEN ,"Reload the configuration file successfully!"));
                return CommandResult.success();
            } catch (IOException e) {
                return CommandResult.empty();
            }
        }

    }


}
