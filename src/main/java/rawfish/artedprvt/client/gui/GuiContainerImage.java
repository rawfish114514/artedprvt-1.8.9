package rawfish.artedprvt.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rawfish.artedprvt.Artedprvt;
import rawfish.artedprvt.inventory.ContainerImage;
import rawfish.artedprvt.item.ItemLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiContainerImage extends GuiContainer
{
    private static final String TEXTURE_PATH = Artedprvt.MODID + ":" + "textures/gui/container/gui_image.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_PATH);


    public String data=null;
    public GuiContainerImage(ContainerImage inventorySlotsIn)
    {
        super(inventorySlotsIn);
        this.xSize = 256;
        this.ySize = 176;

        data=inventorySlotsIn.data;

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        byte[] rawSource = hexStringToBytes(data);
        BufferedImage bufImage = null;
        if(rawSource==null){
            System.out.println("对象null");
            return;
        }
        try {
            bufImage = ImageIO.read(new ByteArrayInputStream(rawSource));
        } catch (IOException e) {
            System.out.println("读取文件发生错误");
            return;
        }
        int imx=bufImage.getWidth();
        int imy=bufImage.getHeight();
        for(int i=0;i<imx;i++){
            for(int j=0;j<imy;j++){
                drawPixel(i,j,bufImage.getRGB(i,j));
            }
        }


    }
    public void drawPixel(int x,int y,int color){
        int sp=6;
        int x1=37+x*sp;
        int y1=14+y*sp;
        int x2=x1+sp;
        int y2=y1+sp;
        this.drawGradientRect(x1,y1,x2,y2,color,color);
    }
    //hex串转为byte
    public byte[] hexStringToBytes(String hexStr) {
        if (null == hexStr || hexStr.length() < 1) return null;

        int byteLen = hexStr.length() / 2;
        byte[] result = new byte[byteLen];
        char[] hexChar = hexStr.toCharArray();
        for(int i=0 ;i<byteLen;i++){
            result[i] = (byte)(Character.digit(hexChar[i*2],16)<<4 | Character.digit(hexChar[i*2+1],16));
        }
        return result;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {

    }
}