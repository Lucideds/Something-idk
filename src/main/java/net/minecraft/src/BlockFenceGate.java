package net.minecraft.src;

public class BlockFenceGate extends BlockDirectional {
    public BlockFenceGate(int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture.
     * Args: side, metadata
     */
    public Icon getIcon(int par1, int par2) {
        return Block.planks.getBlockTextureFromSide(par1);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates.
     * Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        return par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid() && super.canPlaceBlockAt(par1World, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box
     * can change after the pool has been cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        return isFenceGateOpen(var5) ? null
                : (var5 != 2 && var5 != 0 ? AxisAlignedBB.getAABBPool().getAABB((float) par2 + 0.375F, par3, par4, (float) par2 + 0.625F, (float) par3 + 1.5F, par4 + 1)
                : AxisAlignedBB.getAABBPool().getAABB(par2, par3, (float) par4 + 0.375F, par2 + 1, (float) par3 + 1.5F, (float) par4 + 0.625F));
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        int var5 = getDirection(par1IBlockAccess.getBlockMetadata(par2, par3, par4));

        if (var5 != 2 && var5 != 0) {
            this.setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
        } else {
            this.setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or
     * not to render the shared face of two adjacent blocks and also whether the
     * player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False
     * (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return isFenceGateOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType() {
        return 21;
    }

    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack) {
        int var7 = (MathHelper.floor_double((double) (par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
        int var10 = par1World.getBlockMetadata(par2, par3, par4);

        if (isFenceGateOpen(var10)) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var10 & -5, 2);
        } else {
            int var11 = (MathHelper.floor_double((double) (par5EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
            int var12 = getDirection(var10);

            if (var12 == (var11 + 2) % 4) {
                var10 = var11;
            }

            par1World.setBlockMetadataWithNotify(par2, par3, par4, var10 | 4, 2);
        }

        par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
        return true;
    }

    /**
     * Returns if the fence gate is open according to its metadata.
     */
    public static boolean isFenceGateOpen(int par0) {
        return (par0 & 4) != 0;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the
     * adjacent block is at the given coordinates. Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        return true;
    }

    /**
     * When this method is called, your block should register all the icons it needs
     * with the given IconRegister. This is the only chance you get to register
     * icons.
     */
    public void registerIcons(IconRegister par1IconRegister) {
    }
}
