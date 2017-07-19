package com.sky.detector;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

public class DoStuff {

int escape() throws RESyntaxException
{
// "Shouldn't" happen
if (pattern.charAt(idx) != '\\')
    {
        internalError();
    }

// Escape shouldn't occur as last character in string!
if (idx + 1 == len)
    {
        syntaxError("Escape terminates string");
    }

// Switch on character after backslash
idx += 2;
char escapeChar = pattern.charAt(idx - 1);
switch (escapeChar)
{
   case RE.E_BOUND:
       case RE.E_NBOUND:
           return ESC_COMPLEX;

   case RE.E_ALNUM:
       case RE.E_NALNUM:
       case RE.E_SPACE:
       case RE.E_NSPACE:
       case RE.E_DIGIT:
       case RE.E_NDIGIT:
           return ESC_CLASS;

   case 'u':
       case 'x':
           {
               // Exact required hex digits for escape type
               int hexDigits = (escapeChar == 'u' ? 4 : 2);

               // Parse up to hexDigits characters from input
               int val = 0;
               for ( ; idx < len && hexDigits-- > 0; idx++)
                   {
                       // Get char
                       char c = pattern.charAt(idx);

                       // If it's a hexadecimal digit (0-9)
                       if (c >= '0' && c <= '9')
                           {
                               // Compute new value
                               val = (val << 4) + c - '0';
                           }
                       else
                       {
                               // If it's a hexadecimal letter (a-f)
                               c = Character.toLowerCase(c);
                               if (c >= 'a' && c <= 'f')
                               {
                                   // Compute new value
                                   val = (val << 4) + (c - 'a') + 10;
                               }
                           else
                           {
                                   // If it's not a valid digit or hex letter, the escape must be invalid
                                   // because hexDigits of input have not been absorbed yet.
                                   syntaxError("Expected " + hexDigits + " hexadecimal digits after \\" + escapeChar);
                               }
                           }
                   }
               return val;
           }

   case 't':
           return '\t';

   case 'n':
           return '\n';

   case 'r':
           return '\r';

   case 'f':
           return '\f';

   case '0':
       case '1':
       case '2':
       case '3':
       case '4':
       case '5':
       case '6':
       case '7':
       case '8':
       case '9':

           // An octal escape starts with a 0 or has two digits in a row
           if ((idx < len && Character.isDigit(pattern.charAt(idx))) || escapeChar == '0')
               {
               // Handle \nnn octal escapes
               int val = escapeChar - '0';
               if (idx < len && Character.isDigit(pattern.charAt(idx)))
                   {
                       val = ((val << 3) + (pattern.charAt(idx++) - '0'));
                       if (idx < len && Character.isDigit(pattern.charAt(idx)))
                           {
                               val = ((val << 3) + (pattern.charAt(idx++) - '0'));
                           }
                   }
               return val;
           }

       // It's actually a backreference (\[1-9]), not an escape
       return ESC_BACKREF;

   default:

           // Simple quoting of a character
           return escapeChar;
    }
}