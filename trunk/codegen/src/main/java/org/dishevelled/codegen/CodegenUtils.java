/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2010 held jointly by the individual authors.

    This library is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation; either version 3 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; with out even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
    License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this library;  if not, write to the Free Software Foundation,
    Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA.

    > http://www.fsf.org/licensing/licenses/lgpl.html
    > http://www.opensource.org/licenses/lgpl-license.php

*/
package org.dishevelled.codegen;

/**
 * Static utility methods for the codegen package.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CodegenUtils
{

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
        // names should already be in uppercase form
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

    /**
     * Make a sentence-case description (first word uppercase, rest lowercase, words split by spaces) from
     * the specified name.
     *
     * @param name name
     * @return a sentence-case description
     */
    public static String makeSentenceCaseDescription(final String name)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, size = name.length(); i < size; i++)
        {
            char ch = name.charAt(i);
            if (Character.isTitleCase(ch) || Character.isUpperCase(ch))
            {
                if (i == 0)
                {
                    sb.append(ch);
                }
                else
                {
                    sb.append(" ");
                    sb.append(Character.toLowerCase(ch));
                }
            }
            else
            {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * Make a lowercase-with-dashes name from the specified name.
     *
     * @param name name
     * @return a lowercase-with-dashes name
     */
    public static String makeLowercaseWithDashes(final String name)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, size = name.length(); i < size; i++)
        {
            char ch = name.charAt(i);
            if (Character.isTitleCase(ch) || Character.isUpperCase(ch))
            {
                if (i != 0)
                {
                    sb.append("-");
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

    /**
     * Make an UPPERCASE_WITH_UNDERSCORES name from the specified name.
     *
     * @param name name
     * @return an UPPERCASE_WITH_UNDERSCORES name
     */
    public static String makeUppercaseWithUnderscores(final String name)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0, size = name.length(); i < size; i++)
        {
            char ch = name.charAt(i);
            if (Character.isTitleCase(ch) || Character.isUpperCase(ch))
            {
                if (i != 0)
                {
                    sb.append("_");
                }
                sb.append(ch);
            }
            else
            {
                sb.append(Character.toUpperCase(ch));
            }
        }
        return sb.toString();
    }
}
