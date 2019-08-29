package com.zeyilinxin.npcchat.api.Requirements;

import com.zeyilinxin.npcchat.NpcChat;
import me.rojo8399.placeholderapi.PlaceholderService;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class RequirementsUtlis {

    private NpcChat chat = NpcChat.getNpcchat();
    private boolean[] booleans;
    private Player player;
    private List<String> list;

    public RequirementsUtlis(Player player , List<String> list){
        this.player = player;
        this.booleans = new boolean[list.size()];
        this.list = list;
        for(int i = 0 ; i < this.list.size() ; i++){
            this.booleans[i] = this.getRequirements(this.player , this.list.get(i));
        }
    }

    public boolean isVia(){
        for(boolean b : this.booleans){
            if (b == false){
                return false;
            }
        }
        return true;
    }

    private boolean getRequirements (Player player, String re){
        PlaceholderService service = chat.getPleaceholder().getPlaceholderService();
        String[] tmp = re.split(",");
        if (re.indexOf("=") == 0){
            if (service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player) != null || !service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).isEmpty()
                    ||service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).equals("{" + tmp[0].substring(re.indexOf("%")) +  "}")){
                String term = service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).toPlain();
                String term2 = tmp[1];
                if (term.equals(term2)){
                    return true;
                }
                return false;
            } else {
                return  false;
            }

        } else if (re.indexOf("<=") == 0){
            if (service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player) != null || !service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).isEmpty()
                    ||service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).equals("{" + tmp[0].substring(re.indexOf("%")) +  "}")){
                double term = 0.0;
                double term2 = 0.0;
                try {
                    term = Double.valueOf(service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).toPlain());
                    term2 = Double.valueOf(tmp[1]);
                } catch (Exception e){
                    return false;
                }
                if (term <= term2){
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } else if (re.indexOf("<") == 0){
            if (service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player) != null || !service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).isEmpty()
                    ||service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).equals("{" + tmp[0].substring(re.indexOf("%")) +  "}")){
                double term = 0.0;
                double term2 = 0.0;
                try {
                    term = Double.valueOf(service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).toPlain());
                    term2 = Double.valueOf(tmp[1]);
                } catch (Exception e){
                    return false;
                }
                if (term < term2){
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } else if (re.indexOf(">=") == 0){
            if (service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player) != null || !service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).isEmpty()
                    ||service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).equals("{" + tmp[0].substring(re.indexOf("%")) +  "}")){
                double term = 0.0;
                double term2 = 0.0;
                try {
                    term = Double.valueOf(service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).toPlain());
                    term2 = Double.valueOf(tmp[1]);
                } catch (Exception e){
                    return false;
                }
                if (term >= term2){
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } else if (re.indexOf(">") == 0){
            if (service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player) != null || !service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).isEmpty()
                    ||service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).equals("{" + tmp[0].substring(re.indexOf("%")) +  "}")){
                double term = 0.0;
                double term2 = 0.0;
                try {
                    term = Double.valueOf(service.replacePlaceholders(tmp[0].substring(re.indexOf("%")), (Player)player , player).toPlain());
                    term2 = Double.valueOf(tmp[1]);
                } catch (Exception e){
                    return false;
                }
                if (term > term2){
                    return true;
                }
                return false;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }
}
