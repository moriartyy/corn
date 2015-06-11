package com.hichao.analysis.corn;

public class CharacterUtil {

    public static int normalize(int input) {
        if (input == 12288) {
            input = 32;
            
        }else if (input > 65280 && input < 65375) { // To half-angle.
            input = input - 65248;
            
        }else if (input >= 'A' && input <= 'Z') { // To lower case.
            input += 32;
        }
        return input;
    }

    public static byte getCharType(int codepoint) {
        if(codepoint >= '0' && codepoint <= '9'){
            return CharacterType.ARABIC_NUMBER;
            
        }else if((codepoint >= 'a' && codepoint <= 'z')
                || (codepoint >= 'A' && codepoint <= 'Z')){
            return CharacterType.ENGLISH_LETTER;
            
        }else {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(codepoint);
            
            if(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A){
                return CharacterType.CHINESE_CHARACTER;
                
            }
        }
        //其他的不做处理的字符
        return CharacterType.USELESS;
    }
}
