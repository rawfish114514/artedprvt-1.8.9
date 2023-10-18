package rawfish.artedprvt.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface FormatHandler {
    String handleFormat(String source);

    Pattern format= Pattern.compile("[0-9a-fkm-or]*");
    static String toFormatCode(String value){
        Matcher matcher=format.matcher(value);
        if(matcher.matches()) {
            char[] chars = value.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char c : chars) {
                sb.append('§');
                sb.append(c);
            }
            return sb.toString();
        }
        return "";
    }

    static String substring(String s, int begin, int end){
        String formats="123456789abcdeflmnor";
        int index=0;
        char[] chars=s.toCharArray();
        StringBuilder sb=new StringBuilder();
        int format=0;
        int lastFormat=0;
        for(int i=0;i<chars.length;i++){
            if(i+1<chars.length&&chars[i]=='§'&&formats.indexOf(chars[i+1])>=0){
                //format
                tf:{
                    char f=chars[i+1];
                    if(f>48&&f<58){
                        format=f-48;
                        break tf;
                    }
                    if(f>96&&f<103){
                        format=f-87;
                        break tf;
                    }
                    if(f=='l'){
                        format|=0b10000;
                        break tf;
                    }
                    if(f=='m'){
                        format|=0b100000;
                        break tf;
                    }
                    if(f=='n'){
                        format|=0b1000000;
                        break tf;
                    }
                    if(f=='o'){
                        format|=0b10000000;
                        break tf;
                    }
                    if(f=='r'){
                        format=0;
                    }
                }

                i++;
            }else{
                if(index>=begin&&index<end) {
                    if(format!=lastFormat){
                        lastFormat=format;
                        int a;
                        if((a=format&0b1111)>0){
                            if(a<10){
                                sb.append('§');
                                sb.append((char)(a+48));
                            }else {
                                sb.append('§');
                                sb.append((char)(a+87));
                            }
                        }
                        if((format>>4&1)==1){
                            sb.append('§');
                            sb.append('l');
                        }
                        if((format>>5&1)==1){
                            sb.append('§');
                            sb.append('m');
                        }
                        if((format>>6&1)==1){
                            sb.append('§');
                            sb.append('n');
                        }
                        if((format>>7&1)==1){
                            sb.append('§');
                            sb.append('o');
                        }
                        if(format==0){
                            sb.append('§');
                            sb.append('r');
                        }
                    }
                    sb.append(chars[i]);
                }
                index++;
            }
        }
        return sb.toString();
    }

    static String substring(String s, int begin){
        return substring(s,begin, length(s));
    }

    static int length(String s){
        return unformat(s).length();
    }

    static String unformat(String s){
        return s.replaceAll("§[1-9a-flmnor]","");
    }
}
