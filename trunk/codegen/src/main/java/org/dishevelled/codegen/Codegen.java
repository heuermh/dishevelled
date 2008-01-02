/*

    dsh-codegen  Source code generation suite.
    Copyright (c) 2004-2008 held jointly by the individual authors.

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

import java.io.FileWriter;

import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Static source code generation methods.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class Codegen
{

    /**
     * Private default constructor.
     */
    private Codegen()
    {
        // empty
    }


    /**
     * Generate a java source file for the specified interface
     * description.
     *
     * @param id interface description, must not be null
     */
    public static void generateSource(final InterfaceDescription id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }

        FileWriter fw = null;
        try
        {
            Properties p = new Properties();
            p.setProperty("resource.loader", "class");
            p.setProperty("class.resource.loader.class",
                          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            Velocity.init(p);

            fw = new FileWriter(id.getUpper() + ".java");

            VelocityContext context = new VelocityContext();
            context.put("id", id);

            Template template = Velocity.getTemplate("org/dishevelled/codegen/Interface.wm");
            template.merge(context, fw);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (Exception e)
            {
                // empty
            }
        }
    }

    /**
     * Generate an abstract unit test java source file for the specified interface
     * description.
     *
     * @param id interface description, must not be null
     */
    public static void generateAbstractUnitTest(final InterfaceDescription id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("id must not be null");
        }

        FileWriter fw = null;
        try
        {
            Properties p = new Properties();
            p.setProperty("resource.loader", "class");
            p.setProperty("class.resource.loader.class",
                          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            Velocity.init(p);

            fw = new FileWriter("Abstract" + id.getUpper() + "Test.java");

            VelocityContext context = new VelocityContext();
            context.put("id", id);

            Template template = Velocity.getTemplate("org/dishevelled/codegen/AbstractInterfaceTest.wm");
            template.merge(context, fw);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (Exception e)
            {
                // empty
            }
        }
    }

    /**
     * Generate a java source file for the specified class
     * description and style.
     *
     * @param cd class description, must not be null
     * @param style style, must not be null
     */
    public static void generateSource(final ClassDescription cd, final Style style)
    {
        if (cd == null)
        {
            throw new IllegalArgumentException("cd must not be null");
        }
        if (style == null)
        {
            throw new IllegalArgumentException("style must not be null");
        }

        FileWriter fw = null;
        try
        {
            Properties p = new Properties();
            p.setProperty("resource.loader", "class");
            p.setProperty("class.resource.loader.class",
                          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            Velocity.init(p);

            fw = new FileWriter(cd.getUpper() + ".java");

            VelocityContext context = new VelocityContext();
            context.put("cd", cd);

            Template template = Velocity.getTemplate("org/dishevelled/codegen/" + style + ".wm");
            template.merge(context, fw);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (Exception e)
            {
                // empty
            }
        }
    }

    /**
     * Generate a java source file for a fluent builder API for the specified class
     * description and style.
     *
     * @param cd class description, must not be null
     * @param style style, must not be null
     */
    public static void generateBuilderSource(final ClassDescription cd, final Style style)
    {
        // TODO:  perhaps style doesn't matter here, if all the templates are the same
        if (cd == null)
        {
            throw new IllegalArgumentException("cd must not be null");
        }
        if (style == null)
        {
            throw new IllegalArgumentException("style must not be null");
        }

        FileWriter fw = null;
        try
        {
            Properties p = new Properties();
            p.setProperty("resource.loader", "class");
            p.setProperty("class.resource.loader.class",
                          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            Velocity.init(p);

            fw = new FileWriter(cd.getUpper() + "Builder.java");

            VelocityContext context = new VelocityContext();
            context.put("cd", cd);

            Template template = Velocity.getTemplate("org/dishevelled/codegen/" + style + "Builder.wm");
            template.merge(context, fw);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (Exception e)
            {
                // empty
            }
        }
    }

    /**
     * Generate a unit test source file for the specified class
     * description and style.
     *
     * @param cd class description, must not be null
     * @param style style, must not be null
     */
    public static void generateUnitTest(final ClassDescription cd, final Style style)
    {
        if (cd == null)
        {
            throw new IllegalArgumentException("cd must not be null");
        }
        if (style == null)
        {
            throw new IllegalArgumentException("style must not be null");
        }

        FileWriter fw = null;
        try
        {
            Properties p = new Properties();
            p.setProperty("resource.loader", "class");
            p.setProperty("class.resource.loader.class",
                          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            Velocity.init(p);

            fw = new FileWriter(cd.getUpper() + "Test.java");

            VelocityContext context = new VelocityContext();
            context.put("cd", cd);

            Template template = Velocity.getTemplate("org/dishevelled/codegen/" + style + "Test.wm");
            template.merge(context, fw);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        finally
        {
            try
            {
                fw.close();
            }
            catch (Exception e)
            {
                // empty
            }
        }
    }
}
