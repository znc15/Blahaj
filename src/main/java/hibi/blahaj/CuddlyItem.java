package hibi.blahaj;

import java.util.List;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

public abstract class CuddlyItem extends Item {

	public static final String OWNER_KEY = "Owner";

	private final String subtitle;

	public CuddlyItem(Properties settings, String subtitle) {
		super(settings);
		this.subtitle = subtitle;
	}

	public abstract boolean inventoryTick(ItemStack stack, Slot slot, ClickType type, Player player);

	public abstract InteractionResult useOn(ItemStack stack, Player player, Slot slot, ClickType type);

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		if (this.subtitle != null) {
			tooltip.add(Component.translatable(this.subtitle));
		}
		if(stack.hasTag()) {
			CompoundTag nbt = stack.getTag();
			String owner = nbt.getString(OWNER_KEY);
			if(owner == "") {
				return;
			}
			if(stack.hasCustomHoverName()) {
				tooltip.add(Component.translatable("tooltip.blahaj.owner.rename", getName(stack), Component.literal(owner)).withStyle(ChatFormatting.GRAY));
			}
			else {
				tooltip.add(Component.translatable("tooltip.blahaj.owner.craft", Component.literal(owner)).withStyle(ChatFormatting.GRAY));
			}
		}
		super.appendHoverText(stack, world, tooltip, context);
	}

	@Override
	public void onCraftedBy(ItemStack stack, Level world, Player player) {
		if(player != null) { // compensate for auto-crafter mods
			stack.addTagElement(OWNER_KEY, StringTag.valueOf(player.getName().getString()));
		}
		super.onCraftedBy(stack, world, player);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return 0.25f;
	}
}
