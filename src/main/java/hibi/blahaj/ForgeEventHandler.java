package hibi.blahaj;

import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Common.MODID)
public class ForgeEventHandler {
    
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof Allay && 
            event.getItemStack().getItem() instanceof CuddlyItem) {
            event.setCanceled(true);
        }
    }
} 