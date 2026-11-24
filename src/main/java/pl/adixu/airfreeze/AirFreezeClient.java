package pl.adixu.airfreeze;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

/**
 * Best-effort client-side movement freeze.
 * It deliberately does NOT pin the player to saved coordinates: doing so would
 * pull the player back after an Ender Pearl teleport.
 */
public final class AirFreezeClient implements ClientModInitializer {
    private static KeyBinding toggleKey;
    private static boolean frozen;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.airfreeze.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                KeyBinding.Category.MOVEMENT
        ));

        ClientTickEvents.END_CLIENT_TICK.register(AirFreezeClient::tick);
    }

    private static void tick(MinecraftClient client) {
        if (client.player == null || client.world == null) {
            frozen = false;
            return;
        }

        while (toggleKey.wasPressed()) {
            frozen = !frozen;
            client.player.sendMessage(Text.literal(frozen
                    ? "§aAir Freeze: ON"
                    : "§cAir Freeze: OFF"), true);
        }

        if (!frozen) {
            return;
        }

        // Do not call setPosition() and do not save/restore an anchor.
        // A server-approved Ender Pearl teleport must remain at its new position.
        client.player.setVelocity(Vec3d.ZERO);
        client.player.setOnGround(true);
        client.player.fallDistance = 0.0f;
    }
}
