package me.lonewolf.conduitcore.keybinds;

import me.lonewolf.conduitcore.render.gui.hudeditorscreen.BuffHudEditorScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/20 22:08
 * @description TODO
 */

public class KeyBind {

    private static KeyBinding hudEditor;

    public static void init(){
        hudEditor = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.conduitcore.hudeditor", InputUtil.Type.KEYSYM, InputUtil.GLFW_KEY_K, "key.conduitcore.category"));
        registerKeyBinds();
    }

    private static void registerKeyBinds() {
        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            if (hudEditor.wasPressed()) {
//                client.setScreen(new HudEditorScreen());
                client.setScreen(new BuffHudEditorScreen());
            }
        });
    }

}
