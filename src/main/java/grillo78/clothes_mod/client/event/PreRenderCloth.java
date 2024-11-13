package grillo78.clothes_mod.client.event;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class PreRenderCloth extends Event {

    private PlayerModel model;
    private Player player;
    private PlayerModel bipedModel;

    public PreRenderCloth(PlayerModel model, Player player, PlayerModel bipedModel) {
        this.model = model;
        this.player = player;
        this.bipedModel = bipedModel;
    }

    public PlayerModel getModel() {
        return model;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerModel getBipedModel() {
        return bipedModel;
    }
}
