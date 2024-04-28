package grillo78.clothes_mod.common.blocks;

import grillo78.clothes_mod.common.block_entities.ModBlockEntities;
import grillo78.clothes_mod.common.block_entities.SewingMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class SewingMachineBlock extends HorizontalDirectionalBlock implements EntityBlock {
    private static final VoxelShape NORTH_SHAPE = Stream.of(
            Block.box(3, 6, 5, 14, 10, 11),
            Block.box(10, 2, 6, 14, 6, 10),
            Block.box(2, 0, 5, 14, 2, 11),
            Block.box(11.499999999999998, 7.5, 4.5, 12.499999999999998, 8.5, 5),
            Block.box(11, 3, 5.5, 13, 5, 6),
            Block.box(4, 5, 7, 6, 6, 9),
            Block.box(4.5, 4, 7.500000000000002, 5.5, 5, 8.500000000000002)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SOUTH_SHAPE = Stream.of(
            Block.box(10.5, 4, 7.499999999999998, 11.5, 5, 8.499999999999998),
            Block.box(2, 6, 5, 13, 10, 11),
            Block.box(2, 2, 6, 6, 6, 10),
            Block.box(2, 0, 5, 14, 2, 11),
            Block.box(3.5000000000000018, 7.5, 11, 4.500000000000002, 8.5, 11.5),
            Block.box(3, 3, 10, 5, 5, 10.5),
            Block.box(10, 5, 7, 12, 6, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape EAST_SHAPE = Stream.of(
            Block.box(7.499999999999998, 4, 4.5, 8.499999999999998, 5, 5.5),
            Block.box(5, 6, 3, 11, 10, 14),
            Block.box(6, 2, 10, 10, 6, 14),
            Block.box(5, 0, 2, 11, 2, 14),
            Block.box(11, 7.5, 11.499999999999998, 11.5, 8.5, 12.499999999999998),
            Block.box(10, 3, 11, 10.5, 5, 13),
            Block.box(7, 5, 4, 9, 6, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape WEST_SHAPE = Stream.of(
            Block.box(7.500000000000002, 4, 10.5, 8.500000000000002, 5, 11.5),
            Block.box(5, 6, 2, 11, 10, 13),
            Block.box(6, 2, 2, 10, 6, 6),
            Block.box(5, 0, 2, 11, 2, 14),
            Block.box(4.5, 7.5, 3.5000000000000018, 5, 8.5, 4.500000000000002),
            Block.box(5.5, 3, 3, 6, 5, 5),
            Block.box(7, 5, 10, 9, 6, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public SewingMachineBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide)
            if (pLevel.getBlockEntity(pPos) instanceof SewingMachineBlockEntity blockEntity)
                NetworkHooks.openScreen((ServerPlayer) pPlayer, blockEntity, pPos);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.SEWING_MACHINE.get() ? SewingMachineBlockEntity::tick : null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SewingMachineBlockEntity(pPos, pState);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        Direction direction = pContext.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape shape = Shapes.block();

        switch (pState.getValue(FACING)) {
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

//    @Override
//    public boolean hasTileEntity(BlockState state) {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
//        return new SewingMachineBlockEntity();
//    }
}
