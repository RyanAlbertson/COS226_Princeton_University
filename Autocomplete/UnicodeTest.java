/* *****************************************************************************
 *  Compilation:  javac-algs4 UnicodeTest.java
 *  Execution:    java-algs4  UnicodeTest
 *  Dependencies: none
 *
 *  This programs prints out all of the Unicode characters in the basic
 *  multilingual plane (U+0000 to U+FFFF) in a table. It skips the
 *  following types of characters:
 *    -  undefined
 *    -  control characters
 *    -  modifier symbols
 *    -  non-spacing marks
 *    -  Unicode formatting commands
 *    -  reserved for surrogate pairs
 *    -  reserved for private use
 *
 *
 *  % java-algs4 UnicodeTest
 *  U+0020      !  "  #  $  %  &  '  (  )  *  +  ,  -  .  /  
 *  U+0030   0  1  2  3  4  5  6  7  8  9  :  ;  <  =  >  ?  
 *  U+0040   @  A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  
 *  U+0050   P  Q  R  S  T  U  V  W  X  Y  Z  [  \  ]     _  
 *  U+0060      a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  
 *  U+0070   p  q  r  s  t  u  v  w  x  y  z  {  |  }  ~     
 *  U+00A0      ¡  ¢  £  ¤  ¥  ¦  §     ©  ª  «  ¬     ®     
 *  U+00B0   °  ±  ²  ³     µ  ¶  ·     ¹  º  »  ¼  ½  ¾  ¿  
 *  U+00C0   À  Á  Â  Ã  Ä  Å  Æ  Ç  È  É  Ê  Ë  Ì  Í  Î  Ï  
 *  U+00D0   Ð  Ñ  Ò  Ó  Ô  Õ  Ö  ×  Ø  Ù  Ú  Û  Ü  Ý  Þ  ß  
 *  U+00E0   à  á  â  ã  ä  å  æ  ç  è  é  ê  ë  ì  í  î  ï  
 *  U+00F0   ð  ñ  ò  ó  ô  õ  ö  ÷  ø  ù  ú  û  ü  ý  þ  ÿ 
 *  U+0100   Ā  ā  Ă  ă  Ą  ą  Ć  ć  Ĉ  ĉ  Ċ  ċ  Č  č  Ď  ď
 *  ...
 *
 *  Depending on your system setup and font, not all of the Unicode
 *  characters may display properly.
 *
 *  Quirks: when printing certain Hebrew or Arabic characters, the
 *  table may print right-to-left instead of left-to-right.
 *
 *  For a description of Unicode terminology, see:
 *  http://docs.oracle.com/javase/tutorial/i18n/text/terminology.html
 *
 *  For the Character API, see:
 *  https://docs.oracle.com/javase/8/docs/api/java/lang/Character.html
 *
 *  To see what each Unicode character should look like, see:
 *  http://www.fileformat.info/info/unicode/index.htm
 *  http://www.fileformat.info/info/unicode/char/05D0/index.htm
 *
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class UnicodeTest {
    // number of Unicode characters to display per line
    private static final int CHARS_PER_LINE = 16;

    // do not instantiate
    private UnicodeTest() { }

    // Returns a string representation of the given codePoint, or a single
    // space if the codePoint should be suppressed when printing.
    private static String toString(int codePoint) {
        if (!Character.isDefined(codePoint))         return " ";
        if (Character.isISOControl(codePoint))       return " ";
        if (Character.isWhitespace(codePoint))       return " ";
        if (Character.isSurrogate((char) codePoint)) return " ";

        switch(Character.getType(codePoint)) {
            case Character.MODIFIER_SYMBOL:          return " ";
            case Character.CONTROL:                  return " ";
            case Character.MODIFIER_LETTER:          return " ";
            case Character.NON_SPACING_MARK:         return " ";
            case Character.FORMAT:                   return " ";
            case Character.PRIVATE_USE:              return " ";
            default: return new String(Character.toChars(codePoint));
        }
    }

   /**
     * Prints Unicode characters to standard output.
     */
    public static void main(String[] args) {
        for (int line = 0; line < 2*Character.MAX_VALUE / CHARS_PER_LINE; line++) {
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < CHARS_PER_LINE; i++) {
                int codePoint = CHARS_PER_LINE*line + i;
                buffer.append(toString(codePoint) + "  ");
            }
            String output = buffer.toString();
            if (!output.trim().equals("")) {
                // U+202D is the Unicode override to force left-to-right direction
                // but doesn't seem to work with Unix more
                StdOut.printf("U+%04X   %s\n", 16*line, output);
            }
        }
    }
}
