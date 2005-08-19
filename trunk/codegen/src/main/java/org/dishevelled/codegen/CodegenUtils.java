/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2005 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 2.1 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.gnu.org/copyleft/lesser.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.codegen;

/**
 * Static utility methods for the codegen package.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class CodegenUtils
{

    /**
     * CodegenUtils should not normally be instantiated.
     */
    public CodegenUtils()
    {
        // empty
    }


    /**
     * Make a lowercase name from the specified name.
     *
     * @param name name
     * @return a lowercase name
     */
    public static String makeLowercase(final String name)
    {
        return makeDescription(name);
    }

    /**
     * Make an Uppercase name from the specified name.
     *
     * @param name name
     * @return an Uppercase name
     */
    public static String makeUppercase(final String name)
    {
        return name;
    }

    /**
     * Make a mixedCase name from the specified name.
     *
     * @param name name
     * @return a mixedCase name
     */
    public static String makeMixedCase(final String name)
    {
        StringBuffer sb = new StringBuffer();
        char ch = name.charAt(0);
        sb.append(Character.toLowerCase(ch));
        sb.append(name.substring(1));
        return sb.toString();
    }

    /**
     * Make a description (all lowercase, words split by spaces) from
     * the specified name.
     *
     * @param name name
     * @return a description
     */
    public static String makeDescription(final String name)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, size = name.length(); i < size; i++)
        {
            char ch = name.charAt(i);
            if (Character.isTitleCase(ch) || Character.isUpperCase(ch))
            {
                if (i != 0)
                {
                    sb.append(" ");
                }
                sb.append(Character.toLowerCase(ch));
            }
            else
            {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
