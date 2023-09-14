package rawfish.artedprvt.client;

import net.minecraft.client.gui.*;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;
import rawfish.artedprvt.command.Command;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 命令信息聊天界面
 * 在原版聊天界面的基础上
 * 为此模组的命令添加更多图形
 *
 * 上方显示参数信息
 * 给参数添加格式代码
 */
public class CommandInfoChatGui extends GuiChat {
    private static Map<String,Command> commandMap=new HashMap<>();

    public static void put(Command command){
        commandMap.put("/"+command.getName(),command);
    }
    public GuiTextField formatInput;
    public GuiTextField commandInfo;

    public static Field defaultInputFieldTextField=null;
    static{
        try{
            defaultInputFieldTextField=GuiChat.class.getDeclaredField("field_146409_v");
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(defaultInputFieldTextField==null){
            try{
                defaultInputFieldTextField=GuiChat.class.getDeclaredField("defaultInputFieldText");
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        if(defaultInputFieldTextField==null){
            throw new RuntimeException();
        }
        defaultInputFieldTextField.setAccessible(true);
    }

    public String defaultText="";
    public CommandInfoChatGui(GuiChat guiChat){
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

        formatInput = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 8, 12);
        formatInput.setEnableBackgroundDrawing(false);
        formatInput.setFocused(true);
        formatInput.setMaxStringLength(114514);

        formatInput.setText(defaultText);


        commandInfo = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 8, 12);
        commandInfo.setEnableBackgroundDrawing(false);
        commandInfo.setFocused(false);
        commandInfo.setMaxStringLength(114514);

        commandInfo.setText("");

    }

    @Override
    public void updateScreen(){
        super.updateScreen();
        formatInput.updateCursorCounter();
        text=inputField.getText();
        if(!text.equals(oldText)){
            oldText=text;
            if(handler()) {
                formatInput.setText(handledText);
            }else{
                formatInput.setText(text);
            }
        }
        pos=inputField.getCursorPosition();
        if(pos!=oldPos) {
            oldPos = pos;
            if (!info()) {
                infoText="";
            }
            commandInfo.setText(infoText);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){

        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        try {
            drawTextBox(inputField);
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

        formatInput.drawTextBox();

        if(!infoText.equals("")){

            int tfw=fontRendererObj.getStringWidth(text.substring(0,sp0));
            int ifw=fontRendererObj.getStringWidth(infoText);
            drawRect(2+tfw, this.height - 27, 6+tfw+ifw, this.height - 15, Integer.MIN_VALUE);

            commandInfo.xPosition=4+tfw;
            commandInfo.yPosition=height-25;
            commandInfo.drawTextBox();

        }
    }

    public String text="";

    public String oldText=text;

    public int pos=0;

    public int oldPos=0;

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException{
        super.keyTyped(typedChar,keyCode);
    }

    public String handledText="";
    public String infoText="";

    public boolean handler(){
        List<String> spaces=new ArrayList<>();
        List<String> items=new ArrayList<>();
        char[] chars=text.toCharArray();
        StringBuilder sb=new StringBuilder();
        boolean isSpace=true;
        for(char c:chars){
            if(isSpace==(c==' ')){
                sb.append(c);
            }else{
                if(isSpace){
                    spaces.add(sb.toString());
                }else{
                    items.add(sb.toString());
                }
                isSpace=!isSpace;
                sb=new StringBuilder();
                sb.append(c);
            }

        }
        if(isSpace){
            spaces.add(sb.toString());
        }else{
            items.add(sb.toString());
        }
        if(items.size()<spaces.size()){
            items.add("");
        }


        Command command=commandMap.get(items.get(0));
        if(command==null){
            return false;
        }
        List<String> args=new ArrayList<>();
        for(int i=1;i<items.size();i++){
            args.add(items.get(i));
        }


        List<String> fargs=new ArrayList<>(args);
        String s0=translateFormat("c")+"/"+translateFormat("b")+command.getName()+translateFormat("r");


        if(args.size()<1){
            handledText=spaces.get(0)+s0;
            return true;
        }

        List<String> formats=command.format(fargs);
        for(int i=0;i<formats.size()&&i<args.size();i++){
            if(isFormat(formats.get(i))) {
                fargs.set(i,translateFormat(formats.get(i)) + args.get(i) + translateFormat("r"));
            }
        }

        fargs.add(0,s0);
        sb=new StringBuilder();
        for(int i=0;i<fargs.size();i++){
            sb.append(spaces.get(i));
            sb.append(fargs.get(i));
        }
        handledText=sb.toString();


        return true;
    }

    int sp0=0;
    public boolean info(){
        List<String> spaces=new ArrayList<>();
        List<String> items=new ArrayList<>();
        String t1=text.substring(0,pos);
        String t2=text.substring(pos);
        int t2si;
        if((t2si=t2.indexOf(" "))!=-1){
            t2=t2.substring(0,t2si);
        }
        char[] chars=(t1+t2.trim()).toCharArray();
        StringBuilder sb=new StringBuilder();
        boolean isSpace=true;
        for(char c:chars){
            if(isSpace==(c==' ')){
                sb.append(c);
            }else{
                if(isSpace){
                    spaces.add(sb.toString());
                }else{
                    items.add(sb.toString());
                }
                isSpace=!isSpace;
                sb=new StringBuilder();
                sb.append(c);
            }

        }
        if(isSpace){
            spaces.add(sb.toString());
        }else{
            items.add(sb.toString());
        }
        if(items.size()<spaces.size()){
            items.add("");
        }

        sp0=0;
        for(int i=0;i<spaces.size();i++){
            sp0+=spaces.get(i).length();
        }
        for(int i=0;i<items.size()-1;i++){
            sp0+=items.get(i).length();
        }


        Command command=commandMap.get(items.get(0));
        if(command==null){
            return false;
        }
        List<String> args=new ArrayList<>();
        for(int i=1;i<items.size();i++){
            args.add(items.get(i));
        }
        if(args.size()<1){
            return false;
        }

        infoText=command.info(args);

        return true;
    }

    public Pattern format= Pattern.compile("[0-9a-fkm-or]*");
    public boolean isFormat(String str){
        Matcher matcher=format.matcher(str);
        return matcher.matches();
    }

    public String translateFormat(String format){
        char[] chars=format.toCharArray();
        StringBuilder sb=new StringBuilder();
        for(char c:chars){
            sb.append('§');
            sb.append(c);
        }
        return sb.toString();
    }


    public static Field lineScrollOffsetField=null;
    static{
        try{
            lineScrollOffsetField=GuiTextField.class.getDeclaredField("field_146225_q");
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(lineScrollOffsetField==null){
            try{
                lineScrollOffsetField=GuiTextField.class.getDeclaredField("lineScrollOffset");
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        if(lineScrollOffsetField==null){
            throw new RuntimeException();
        }
        lineScrollOffsetField.setAccessible(true);
    }

    public static Field isEnabledField=null;
    static{
        try{
            isEnabledField=GuiTextField.class.getDeclaredField("field_146226_p");
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(isEnabledField==null){
            try{
                isEnabledField=GuiTextField.class.getDeclaredField("isEnabled");
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        if(isEnabledField==null){
            throw new RuntimeException();
        }
        isEnabledField.setAccessible(true);
    }

    public static Field enabledColorField=null;
    static{
        try{
            enabledColorField=GuiTextField.class.getDeclaredField("field_146222_t");
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(enabledColorField==null){
            try{
                enabledColorField=GuiTextField.class.getDeclaredField("enabledColor");
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        if(enabledColorField==null){
            throw new RuntimeException();
        }
        enabledColorField.setAccessible(true);
    }

    public static Field disabledColorField=null;
    static{
        try{
            disabledColorField=GuiTextField.class.getDeclaredField("field_146221_u");
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(disabledColorField==null){
            try{
                disabledColorField=GuiTextField.class.getDeclaredField("disabledColor");
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        if(disabledColorField==null){
            throw new RuntimeException();
        }
        disabledColorField.setAccessible(true);
    }

    public static Field enableBackgroundDrawingField=null;
    static{
        try{
            enableBackgroundDrawingField=GuiTextField.class.getDeclaredField("field_146215_m");
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(enableBackgroundDrawingField==null){
            try{
                enableBackgroundDrawingField=GuiTextField.class.getDeclaredField("enableBackgroundDrawing");
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        if(enableBackgroundDrawingField==null){
            throw new RuntimeException();
        }
        enableBackgroundDrawingField.setAccessible(true);
    }

    public static Field cursorCounterField=null;
    static{
        try{
            cursorCounterField=GuiTextField.class.getDeclaredField("field_146214_l");
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(cursorCounterField==null){
            try{
                cursorCounterField=GuiTextField.class.getDeclaredField("cursorCounter");
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        if(cursorCounterField==null){
            throw new RuntimeException();
        }
        cursorCounterField.setAccessible(true);
    }

    public static Method drawCursorVerticalMethod=null;
    static{
        try{
            drawCursorVerticalMethod=GuiTextField.class.getDeclaredMethod("func_146188_c",int.class,int.class,int.class,int.class);
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        if(drawCursorVerticalMethod==null){
            try{
                drawCursorVerticalMethod=GuiTextField.class.getDeclaredMethod("drawCursorVertical",int.class,int.class,int.class,int.class);
            }catch (Exception e){
                e.printStackTrace(System.err);
            }
        }
        if(drawCursorVerticalMethod==null){
            throw new RuntimeException();
        }
        drawCursorVerticalMethod.setAccessible(true);
    }

    public void drawTextBox(GuiTextField guiTextField) throws Exception {
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
            String s = fontRendererObj.trimStringToWidth(guiTextField.getText().substring(lineScrollOffset), guiTextField.getWidth());

            boolean flag = j >= 0 && j <= s.length();
            boolean flag1 = guiTextField.isFocused() && cursorCounter / 6 % 2 == 0 && flag;
            int l = enableBackgroundDrawing ? guiTextField.xPosition + 4 : guiTextField.xPosition;
            int i1 = enableBackgroundDrawing ? guiTextField.yPosition + (guiTextField.height - 8) / 2 : guiTextField.yPosition;
            int j1 = l;

            if (k > s.length())
            {
                k = s.length();
            }

            if (s.length() > 0)
            {
                String s1 = flag ? s.substring(0, j) : s;
                j1 = fontRendererObj.drawStringWithShadow(s1, (float)l, -256/*hidden*/, i);
            }

            boolean flag2 = guiTextField.getCursorPosition() < guiTextField.getText().length() || guiTextField.getText().length() >= guiTextField.getMaxStringLength();
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

            if (s.length() > 0 && flag && j < s.length())
            {
                j1 = fontRendererObj.drawStringWithShadow(s.substring(j), (float)j1, -256/*hidden*/, i);
            }

            if (flag1)
            {
                if (flag2)
                {
                    Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + fontRendererObj.FONT_HEIGHT, -3092272);
                }
                else
                {
                    fontRendererObj.drawStringWithShadow("_", (float)k1, (float)i1, i);
                }
            }

            if (k != j)
            {
                int l1 = l + fontRendererObj.getStringWidth(s.substring(0, k));

                /*reflect*/
                drawCursorVerticalMethod.invoke(guiTextField,
                        k1, i1 - 1, l1 - 1, i1 + 1 + fontRendererObj.FONT_HEIGHT);
            }
        }
    }
}
