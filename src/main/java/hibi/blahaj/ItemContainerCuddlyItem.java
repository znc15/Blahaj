package hibi.blahaj;

import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;

import org.jetbrains.annotations.Nullable;

public class ItemContainerCuddlyItem extends CuddlyItem {

	public static final String STORED_ITEM_KEY = "Item";

	public ItemContainerCuddlyItem(Item.Properties settings, String subtitle) {
		super(settings, subtitle);
	}

	@Override
	public boolean inventoryTick(ItemStack stack, Slot slot, ClickType type, Player player) {
		if (type != ClickType.PICKUP) {
			return false;
		}
		ItemStack otherStack = slot.getItem();
		CompoundTag storedItemNbt = stack.getTagElement(STORED_ITEM_KEY);
		if (storedItemNbt != null) {
			if (!otherStack.isEmpty()) {
				return false;
			}
			ItemStack storedStack = ItemStack.of(storedItemNbt);
			if (!slot.mayPlace(storedStack)) {
				return false;
			}
			slot.set(storedStack);
			ItemContainerCuddlyItem.storeItemStack(stack, null);
			return true;
		} else {
			if (otherStack.isEmpty()) {
				return false;
			}
			if (!ItemContainerCuddlyItem.canHold(otherStack)) {
				return false;
			}
			ItemContainerCuddlyItem.storeItemStack(stack, otherStack);
			return true;
		}
	}

	@Override
	public InteractionResult useOn(ItemStack stack, Player player, Slot slot, ClickType type) {
		if (type != ClickType.PICKUP || stack.isEmpty()) {
			return InteractionResult.PASS;
		}
		CompoundTag storedItemNbt = stack.getTagElement(STORED_ITEM_KEY);
		if (storedItemNbt != null) {
			return InteractionResult.PASS;
		} else {
			ItemStack otherStack = slot.getItem();
			if (!ItemContainerCuddlyItem.canHold(otherStack)) {
				return InteractionResult.PASS;
			}
			ItemContainerCuddlyItem.storeItemStack(stack, otherStack);
			return InteractionResult.SUCCESS;
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
		super.appendHoverText(stack, world, tooltip, context);
		CompoundTag itemsNbt = stack.getTagElement(STORED_ITEM_KEY);
		if (itemsNbt == null) {
			return;
		}
		ItemStack storedStack = ItemStack.of(itemsNbt);
		Component text = storedStack.getHoverName().copy();
		tooltip.add(Component.literal(" x" + storedStack.getCount()).withStyle(storedStack.getHoverName().getStyle()));
	}

	protected static boolean canHold(ItemStack otherStack) {
		if (!otherStack.getItem().canFitInsideContainerItems()
			|| otherStack.getItem() instanceof ItemContainerCuddlyItem) {
			return false;
		}
		return true;
	}

	protected static void storeItemStack(ItemStack thisStack, @Nullable ItemStack otherStack) {
		CompoundTag nbt = thisStack.getOrCreateTag();
		if (otherStack == null || otherStack.isEmpty()) {
			nbt.remove(STORED_ITEM_KEY);
		} else {
			CompoundTag itemNbt = new CompoundTag();
			otherStack.save(itemNbt);
			thisStack.addTagElement(STORED_ITEM_KEY, itemNbt);
			otherStack.setCount(0);
		}
	}
}
