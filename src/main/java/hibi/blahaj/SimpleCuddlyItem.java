package hibi.blahaj;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;

public class SimpleCuddlyItem extends CuddlyItem {
    
    public SimpleCuddlyItem(Properties settings, String subtitle) {
        super(settings, subtitle);
    }

    @Override
    public boolean inventoryTick(ItemStack stack, Slot slot, ClickType type, Player player) {
        return false;
    }

    @Override
    public InteractionResult useOn(ItemStack stack, Player player, Slot slot, ClickType type) {
        return InteractionResult.PASS;
    }
} 