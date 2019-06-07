package com.zeyilinxin.npcchat.Utils;

import com.zeyilinxin.npcchat.NpcChat;
import me.rojo8399.placeholderapi.Placeholder;
import me.rojo8399.placeholderapi.PlaceholderService;
import me.rojo8399.placeholderapi.Source;
import me.rojo8399.placeholderapi.Token;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

public class MePleaceholder {

    private NpcChat main;
    private PlaceholderService placeholderService;

    public MePleaceholder(final NpcChat main) {
        this.main = main;
        placeholderService = (PlaceholderService)Sponge.getServiceManager().provideUnchecked((Class) PlaceholderService.class);

    }

    public static String parse(String arg , Object src , Object server){
        PlaceholderService service = NpcChat.getNpcchat().getPleaceholder().getPlaceholderService();
        return service.replacePlaceholders(arg.replace("&" , "§") , src , server).toPlain();
    }

    public PlaceholderService getPlaceholderService(){
        return placeholderService;
    }
}
