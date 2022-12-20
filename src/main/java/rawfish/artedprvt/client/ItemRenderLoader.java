package rawfish.artedprvt.client;

import rawfish.artedprvt.block.BlockLoader;
import rawfish.artedprvt.item.ItemLoader;

public class ItemRenderLoader
{
    public ItemRenderLoader()
    {
        ItemLoader.registerRenders();
        BlockLoader.registerRenders();
    }
}