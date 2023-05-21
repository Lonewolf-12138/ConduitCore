package me.lonewolf.conduitcore;


import dlovin.inventoryhud.config.InGameConfigScreen;
import dlovin.inventoryhud.config.gui.ArmorConfigScreen;
import me.lonewolf.conduitcore.keybinds.KeyBind;
import me.lonewolf.conduitcore.render.gui.hud.buff.BuffManager;
import me.lonewolf.conduitcore.render.gui.hud.skillcooldown.SkillDownManger;
import me.lonewolf.conduitcore.util.config.ConfigUtil;
import net.fabricmc.api.ClientModInitializer;
import me.lonewolf.conduitcore.network.MessageCore;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/16 18:46
 * @description TODO
 */

public class ConduitCoreClient implements ClientModInitializer {

    private static ConduitCoreClient instance;

    private MessageCore messageCore;

    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        instance = this;
        this.messageCore = new MessageCore();
        ConfigUtil.init();
        KeyBind.init();
        BuffManager.init();
        SkillDownManger.init();
    }

    public MessageCore getMessageCore() {
        return messageCore;
    }

}
