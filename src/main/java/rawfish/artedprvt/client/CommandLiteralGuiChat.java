package rawfish.artedprvt.client;

import net.minecraft.client.gui.*;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Mouse;
import rawfish.artedprvt.command.CommandInputHandler;
import rawfish.artedprvt.command.Formatter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 命令信息聊天界面
 * 在原版聊天界面的基础上
 * 为此模组的命令添加更多图形
 *
 * 上方显示参数信息
 * 给参数添加格式代码
 */
public class CommandLiteralGuiChat extends GuiChat {
    public String text="";
    public String oldText=text;
    public int pos=0;
    public int oldPos=0;
    public int sp0=0;
    public String handledText="";
    public String infoText="";
    public GuiTextField formatField;
    public GuiTextField infoField;

    public static Field defaultInputFieldTextField=null;
    static{
        defaultInputFieldTextField= ReflectionHelper.findField(GuiChat.class,"defaultInputFieldText","field_146409_v");
    }

    public String defaultText="";
    public CommandLiteralGuiChat(GuiChat guiChat){
        try {
            defaultText = (String)defaultInputFieldTextField.get(guiChat);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void initGui(){
        super.initGui();
        inputField.setText(defaultText);
        inputField.setTextColor(0);
        inputField.setDisabledTextColour(0);
        text=defaultText;

        formatField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 8, 12);
        formatField.setEnableBackgroundDrawing(false);
        formatField.setFocused(true);
        formatField.setMaxStringLength(114514);
        formatField.setText(defaultText);

        infoField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 8, 12);
        infoField.setEnableBackgroundDrawing(false);
        infoField.setFocused(false);
        infoField.setMaxStringLength(114514);
        infoField.setText("");

    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        formatField.updateCursorCounter();
        text=inputField.getText();
        pos=inputField.getCursorPosition();
        if(!text.equals(oldText)){
            oldText=text;
            CommandInputHandler.HandleResult result=CommandInputHandler.handleFormat(text,pos);
            if(result.isHandle()) {
                handledText=result.getResult();
                formatField.setText(handledText);
            }else{
                formatField.setText(text);
            }
        }
        if(pos!=oldPos) {
            oldPos = pos;
            CommandInputHandler.HandleResult result=CommandInputHandler.handleInfo(text,pos);
            if (!result.isHandle()) {
                infoText="";
            }else{
                infoText=result.getResult();
                sp0=result.getPos();
            }
            infoField.setText(infoText);
        }

        try {
            lineScrollOffsetField.set(formatField,lineScrollOffsetField.get(inputField));
        } catch (IllegalAccessException e) {
            e.printStackTrace(System.err);
        }

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){

        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        try {
            drawTextBox(inputField,-1000,true);
            drawTextBox(formatField,0,false);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());

        if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null)
        {
            this.handleComponentHover(ichatcomponent, mouseX, mouseY);
        }
        for (int i = 0; i < this.buttonList.size(); ++i)
        {
            ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }

        for (int j = 0; j < this.labelList.size(); ++j)
        {
            ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
        }


        if(!infoText.equals("")){
            int tfw= 0;
            try {
                if((int)lineScrollOffsetField.get(inputField)<sp0){
                    tfw = fontRendererObj.getStringWidth(text.substring((int)lineScrollOffsetField.get(inputField),sp0));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(System.err);
            }
            int ifw=fontRendererObj.getStringWidth(infoText);
            infoField.xPosition=4+tfw;
            if(infoField.xPosition<4){
                infoField.xPosition=4;
            }
            infoField.yPosition=height-25;
            drawRect(infoField.xPosition-2, infoField.yPosition-2, infoField.xPosition+2+ifw, infoField.yPosition+10, Integer.MIN_VALUE);

            infoField.drawTextBox();

        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException{
        super.keyTyped(typedChar,keyCode);
    }

    public static Field lineScrollOffsetField=null;
    public static Field isEnabledField=null;
    public static Field enabledColorField=null;
    public static Field disabledColorField=null;
    public static Field enableBackgroundDrawingField=null;
    public static Field cursorCounterField=null;
    public static Method drawCursorVerticalMethod=null;

    static{
        lineScrollOffsetField=ReflectionHelper.findField(GuiTextField.class,"lineScrollOffset","field_146225_q");
        isEnabledField=ReflectionHelper.findField(GuiTextField.class,"isEnabled","field_146226_p");
        enabledColorField=ReflectionHelper.findField(GuiTextField.class,"enabledColor","field_146222_t");
        disabledColorField=ReflectionHelper.findField(GuiTextField.class,"disabledColor","field_146221_u");
        enableBackgroundDrawingField=ReflectionHelper.findField(GuiTextField.class,"enableBackgroundDrawing","field_146215_m");
        cursorCounterField=ReflectionHelper.findField(GuiTextField.class,"cursorCounter","field_146214_l");
        drawCursorVerticalMethod=ReflectionHelper.findMethod(GuiTextField.class,null,new String[]{"drawCursorVertical","func_146188_c"},int.class,int.class,int.class,int.class);
    }

    public void drawTextBox(GuiTextField guiTextField,int offset,boolean v) throws Exception {
        if (guiTextField.getVisible())
        {
            if (guiTextField.getEnableBackgroundDrawing())
            {
                drawRect(guiTextField.xPosition - 1, guiTextField.yPosition - 1, guiTextField.xPosition + guiTextField.width + 1, guiTextField.yPosition + guiTextField.height + 1, -6250336);
                drawRect(guiTextField.xPosition, guiTextField.yPosition, guiTextField.xPosition + guiTextField.width, guiTextField.yPosition + guiTextField.height, -16777216);
            }

            /*reflect*/
            int lineScrollOffset=(int)lineScrollOffsetField.get(guiTextField);
            boolean isEnabled=(boolean)isEnabledField.get(guiTextField);
            int enabledColor=(int)enabledColorField.get(guiTextField);
            int disabledColor=(int)disabledColorField.get(guiTextField);
            boolean enableBackgroundDrawing=(boolean)enableBackgroundDrawingField.get(guiTextField);
            int cursorCounter=(int)cursorCounterField.get(guiTextField);

            int i = isEnabled ? enabledColor : disabledColor;
            int j = guiTextField.getCursorPosition() - lineScrollOffset;
            int k = guiTextField.getSelectionEnd() - lineScrollOffset;
            String s = fontRendererObj.trimStringToWidth(Formatter.substring(guiTextField.getText(),lineScrollOffset), guiTextField.getWidth());

            boolean flag = j >= 0 && j <= Formatter.length(s);
            boolean flag1 = guiTextField.isFocused() && cursorCounter / 6 % 2 == 0 && flag;
            int l = enableBackgroundDrawing ? guiTextField.xPosition + 4 : guiTextField.xPosition;
            int i1 = enableBackgroundDrawing ? guiTextField.yPosition + (guiTextField.height - 8) / 2 : guiTextField.yPosition;
            int j1 = l;

            if (k > Formatter.length(s))
            {
                k = Formatter.length(s);
            }

            if (Formatter.length(s) > 0)
            {
                String s1 = flag ? Formatter.substring(s,0, j) : s;
                j1 = fontRendererObj.drawStringWithShadow(s1, (float)l, i1+offset, i);
            }

            boolean flag2 = guiTextField.getCursorPosition() < Formatter.length(guiTextField.getText()) || Formatter.length(guiTextField.getText()) >= guiTextField.getMaxStringLength();
            int k1 = j1;

            if (!flag)
            {
                k1 = j > 0 ? l + guiTextField.width : l;
            }
            else if (flag2)
            {
                k1 = j1 - 1;
                --j1;
            }

            if (Formatter.length(s) > 0 && flag && j < Formatter.length(s))
            {
                j1 = fontRendererObj.drawStringWithShadow(Formatter.substring(s,j), (float)j1, i1+offset, i);
            }

            if (flag1)
            {
                if (flag2)
                {
                    Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + fontRendererObj.FONT_HEIGHT, -3092272);
                }
                else
                {
                    fontRendererObj.drawStringWithShadow("_", (float)k1, (float)i1, -3092272);
                }
            }

            if (k != j)
            {
                int l1 = l + fontRendererObj.getStringWidth(Formatter.substring(s,0, k));

                /*reflect*/
                if(v) {
                    drawCursorVerticalMethod.invoke(guiTextField,
                            k1, i1 - 1, l1 - 1, i1 + 1 + fontRendererObj.FONT_HEIGHT);
                }
            }
        }
    }

}
