package rawfish.artedprvt.client;

import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import rawfish.artedprvt.command.util.CommandInputHandler;
import rawfish.artedprvt.command.util.HandleResult;
import rawfish.artedprvt.command.util.Literals;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CommandLiteralGuiCommandBlock extends GuiCommandBlock {
    public String text="";
    public String oldText=text;
    public int pos=0;
    public int oldPos=0;
    public int sp0=0;
    public String handledText="";
    public String infoText="";
    public GuiTextField formatField;
    public GuiTextField infoField;

    public static Field localCommandBlockField=null;
    public static Field commandTextFieldField=null;
    public static Field previousOutputTextFieldField=null;
    public CommandBlockLogic localCommandBlock0;
    public GuiTextField inputField;
    public GuiTextField previousField;
    static{
        localCommandBlockField= ReflectionHelper.findField(GuiCommandBlock.class,"localCommandBlock","field_146489_h");
        commandTextFieldField=ReflectionHelper.findField(GuiCommandBlock.class,"commandTextField","field_146485_f");
        previousOutputTextFieldField=ReflectionHelper.findField(GuiCommandBlock.class,"previousOutputTextField","field_146486_g");
    }
    public CommandLiteralGuiCommandBlock(GuiCommandBlock guiCommandBlock) {
        super(null);
        try{
            localCommandBlock0=(CommandBlockLogic) localCommandBlockField.get(guiCommandBlock);
            localCommandBlockField.set(this,localCommandBlock0);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void initGui(){
        super.initGui();
        try{
            inputField=(GuiTextField) commandTextFieldField.get(this);
            previousField=(GuiTextField) previousOutputTextFieldField.get(this);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }

        formatField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 150, 50, 300, 20);
        formatField.setEnableBackgroundDrawing(true);
        formatField.setFocused(true);
        formatField.setMaxStringLength(114514);
        formatField.setText(this.localCommandBlock0.getCommand());

        infoField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 8, 12);
        infoField.setEnableBackgroundDrawing(false);
        infoField.setFocused(false);
        infoField.setMaxStringLength(114514);
        infoField.setText("");

        text=inputField.getText();
        pos=inputField.getCursorPosition();
        oldText="";
        oldPos=0;
    }

    @Override
    public void updateScreen(){
        try{
            inputField=(GuiTextField) commandTextFieldField.get(this);
            previousField=(GuiTextField) previousOutputTextFieldField.get(this);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        super.updateScreen();
        formatField.updateCursorCounter();
        text = inputField.getText();
        pos = inputField.getCursorPosition();
        if(!text.equals(oldText)){
            oldText=text;
            HandleResult result=CommandInputHandler.handleFormat(text,pos);
            if(result.isHandle()) {
                handledText=result.getResult();
                formatField.setText(handledText);
            }else{
                formatField.setText(text);
            }
        }
        if(pos!=oldPos) {
            oldPos = pos;
            HandleResult result=CommandInputHandler.handleInfo(text,pos);
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
        try{
            inputField=(GuiTextField) commandTextFieldField.get(this);
            previousField=(GuiTextField) previousOutputTextFieldField.get(this);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }

        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("advMode.setCommand", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("advMode.command", new Object[0]), this.width / 2 - 150, 37, 10526880);

        int i = 75;
        int j = 0;/*
        this.drawString(this.fontRendererObj, I18n.format("advMode.nearestPlayer", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.randomPlayer", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.allPlayers", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("advMode.allEntities", new Object[0]), this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRendererObj, "", this.width / 2 - 150, i + j++ * this.fontRendererObj.FONT_HEIGHT, 10526880);
*/
        j+=5;
        if (this.previousField.getText().length() > 0)
        {
            i = i + j * this.fontRendererObj.FONT_HEIGHT + 16;
            this.drawString(this.fontRendererObj, I18n.format("advMode.previousOutput", new Object[0]), this.width / 2 - 150, i, 10526880);
            this.previousField.drawTextBox();
        }
        try {
            drawTextBox(inputField,-256,true);
            drawTextBox(formatField,0,false);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        for (i = 0; i < this.buttonList.size(); ++i)
        {
            ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY);
        }

        for (j = 0; j < this.labelList.size(); ++j)
        {
            ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
        }

        if(!infoText.isEmpty()){
            int tfw= 0;
            try {
                if((int)lineScrollOffsetField.get(inputField)<sp0){
                    tfw = fontRendererObj.getStringWidth(text.substring((int)lineScrollOffsetField.get(inputField),sp0));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace(System.err);
            }
            int ifw=fontRendererObj.getStringWidth(infoText);
            infoField.xPosition=this.width / 2 - 150+tfw;
            infoField.yPosition=73;
            drawRect(infoField.xPosition-2, infoField.yPosition-2, infoField.xPosition+2+ifw, infoField.yPosition+10, 0xFF000000);

            infoField.drawTextBox();

        }

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
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
            if (v)
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
            String s = fontRendererObj.trimStringToWidth(Literals.Formats.substring(guiTextField.getText(),lineScrollOffset), guiTextField.getWidth());

            boolean flag = j >= 0 && j <= Literals.Formats.length(s);
            boolean flag1 = guiTextField.isFocused() && cursorCounter / 6 % 2 == 0 && flag;
            int l = enableBackgroundDrawing ? guiTextField.xPosition + 4 : guiTextField.xPosition;
            int i1 = enableBackgroundDrawing ? guiTextField.yPosition + (guiTextField.height - 8) / 2 : guiTextField.yPosition;
            int j1 = l;

            if (k > Literals.Formats.length(s))
            {
                k = Literals.Formats.length(s);
            }

            if (Literals.Formats.length(s) > 0)
            {
                String s1 = flag ? Literals.Formats.substring(s,0, j) : s;
                j1 = fontRendererObj.drawStringWithShadow(s1, (float)l, i1+offset, i);
            }

            boolean flag2 = guiTextField.getCursorPosition() < Literals.Formats.length(guiTextField.getText()) || Literals.Formats.length(guiTextField.getText()) >= guiTextField.getMaxStringLength();
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

            if (Literals.Formats.length(s) > 0 && flag && j < Literals.Formats.length(s))
            {
                j1 = fontRendererObj.drawStringWithShadow(Literals.Formats.substring(s,j), (float)j1, i1+offset, i);
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
                int l1 = l + fontRendererObj.getStringWidth(Literals.Formats.substring(s,0, k));

                /*reflect*/
                if(v) {
                    drawCursorVerticalMethod.invoke(guiTextField,
                            k1, i1 - 1, l1 - 1, i1 + 1 + fontRendererObj.FONT_HEIGHT);
                }
            }
        }
    }

}
