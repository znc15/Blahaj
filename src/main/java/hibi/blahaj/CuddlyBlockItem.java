package hibi.blahaj;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

public class CuddlyBlockItem extends BlockItem {
    public CuddlyBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState state = this.getBlock().getStateForPlacement(context);
        Direction direction = context.getHorizontalDirection();
        
        // 设置方块朝向
        if (state != null) {
            return state.setValue(CuddlyBlock.FACING, direction);
        }
        
        return state;
    }
} 