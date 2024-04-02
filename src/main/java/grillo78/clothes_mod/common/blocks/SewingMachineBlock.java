package grillo78.clothes_mod.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class SewingMachineBlock extends HorizontalBlock {
    private static final VoxelShape NORTH_SHAPE = Stream.of(
            Block.box(3, 6, 5, 14, 10, 11),
            Block.box(10, 2, 6, 14, 6, 10),
            Block.box(2, 0, 5, 14, 2, 11),
            Block.box(11.499999999999998, 7.5, 4.5, 12.499999999999998, 8.5, 5),
            Block.box(11, 3, 5.5, 13, 5, 6),
            Block.box(4, 5, 7, 6, 6, 9),
            Block.box(4.5, 4, 7.500000000000002, 5.5, 5, 8.500000000000002)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape SOUTH_SHAPE = Stream.of(
            Block.box(10.5, 4, 7.499999999999998, 11.5, 5, 8.499999999999998),
            Block.box(2, 6, 5, 13, 10, 11),
            Block.box(2, 2, 6, 6, 6, 10),
            Block.box(2, 0, 5, 14, 2, 11),
            Block.box(3.5000000000000018, 7.5, 11, 4.500000000000002, 8.5, 11.5),
            Block.box(3, 3, 10, 5, 5, 10.5),
            Block.box(10, 5, 7, 12, 6, 9)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape EAST_SHAPE = Stream.of(
            Block.box(7.499999999999998, 4, 4.5, 8.499999999999998, 5, 5.5),
            Block.box(5, 6, 3, 11, 10, 14),
            Block.box(6, 2, 10, 10, 6, 14),
            Block.box(5, 0, 2, 11, 2, 14),
            Block.box(11, 7.5, 11.499999999999998, 11.5, 8.5, 12.499999999999998),
            Block.box(10, 3, 11, 10.5, 5, 13),
            Block.box(7, 5, 4, 9, 6, 6)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    private static final VoxelShape WEST_SHAPE = Stream.of(
            Block.box(7.500000000000002, 4, 10.5, 8.500000000000002, 5, 11.5),
            Block.box(5, 6, 2, 11, 10, 13),
            Block.box(6, 2, 2, 10, 6, 6),
            Block.box(5, 0, 2, 11, 2, 14),
            Block.box(4.5, 7.5, 3.5000000000000018, 5, 8.5, 4.500000000000002),
            Block.box(5.5, 3, 3, 6, 5, 5),
            Block.box(7, 5, 10, 9, 6, 12)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public SewingMachineBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        VoxelShape shape = VoxelShapes.block();

        switch (pState.getValue(FACING)){
            case NORTH:
                shape = NORTH_SHAPE;
                break;
            case SOUTH:
                shape = SOUTH_SHAPE;
                break;
            case EAST:
                shape = EAST_SHAPE;
                break;
            case WEST:
                shape = WEST_SHAPE;
                break;
        }

        return shape;
    }
}
