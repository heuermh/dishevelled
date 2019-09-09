/*

    dsh-color-scheme  Color schemes.
    Copyright (c) 2009-2019 held jointly by the individual authors.

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
import java.awt.Color;

import java.io.File;

import java.nio.charset.StandardCharsets;

import com.google.common.io.Files;

import com.jsoniter.JsonIterator;

import org.beryx.awt.color.ColorFactory;

public final class ConvertJsonHex
{
    public static void main(final String args[]) throws Exception
    {
        if (args.length != 1)
        {
            System.out.println("usage:\njava ConvertJsonHex fileName.json");
            System.exit(1);
        }
        String[] hexColors = JsonIterator.deserialize(Files.toString(new File(args[0]), StandardCharsets.UTF_8), String[].class);
        for (String hex : hexColors)
        {
            Color c = ColorFactory.valueOf(hex);
            System.out.println("    <color red=\"" + c.getRed() + "\" green=\"" + c.getGreen() + "\" blue=\"" + c.getBlue() + "\"/>");
        }
    }
}
