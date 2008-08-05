/*

    dsh-codegen-shell  Runnable shell for the codegen library.
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
package org.dishevelled.codegen.shell;

import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

import bsh.EvalError;
import bsh.Interpreter;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.FileArgument;

/**
 * Runnable shell for the codegen library.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CodegenShell
    implements Runnable
{
    /** Input file to source. */
    private final File inputFile;


    /**
     * Create a new codegen shell.
     */
    public CodegenShell()
    {
        inputFile = null;
    }

    /**
     * Create a new codegen shell with the specified input file to source.
     *
     * @param inputFile input file to source, must not be null
     */
    public CodegenShell(final File inputFile)
    {
        if (inputFile == null)
        {
            throw new IllegalArgumentException("inputFile must not be null");
        }
        if (!inputFile.canRead())
        {
            throw new IllegalArgumentException("inputFile must exist and be readable");
        }
        this.inputFile = inputFile;
    }


    /** {@inheritDoc} */
    public void run()
    {
        Reader in = new InputStreamReader(System.in);
        Interpreter bsh = new Interpreter(in, System.out, System.err, (inputFile == null));

        bsh.setExitOnEOF(true);

        try
        {
            bsh.eval("import org.dishevelled.codegen.*;");
            bsh.eval("static import org.dishevelled.codegen.Codegen.*;");
            if (inputFile != null)
            {
                bsh.source(inputFile.getAbsolutePath());
            }
        }
        catch (EvalError e)
        {
            // ignore
        }
        catch (IOException e)
        {
            // ignore
        }

        if (inputFile == null)
        {
            System.out.println("Shell interface to org.dishevelled.codegen.Codegen");
            System.out.println("Copyright (c) 2004-2008 held jointly by the individual authors.");
            bsh.run();
        }
    }


    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        ArgumentList arguments = null;
        CodegenShell shell = null;
        CommandLine commandLine = null;
        try
        {
            FileArgument input = new FileArgument("i", "input-file", "input file to source", false);
            Switch help = new Switch("h", "help", "display help message");
            arguments = new ArgumentList(input, help);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                help();
            }
            else
            {
                if (input.wasFound())
                {
                    shell = new CodegenShell(input.getValue());
                }
                else
                {
                    shell = new CodegenShell();
                }
                shell.run();
            }
        }
        catch (CommandLineParseException e)
        {
            Usage.usage("java org.dishevelled.codegen.shell.CodegenShell [args]",
                        e, commandLine, arguments, System.err);
        }
        catch (IllegalArgumentException e)
        {
            Usage.usage("java org.dishevelled.codegen.shell.CodegenShell [args]",
                        e, commandLine, arguments, System.err);
        }
    }

    /**
     * Display help message.
     */
    private static void help()
    {
        System.out.println("Shell interface to org.dishevelled.codegen.Codegen");
        System.out.println("Copyright (c) 2004-2008 held jointly by the individual authors.");
        System.out.println("  example:");
        System.out.println("");
        System.out.println("bsh % ClassDescription foo = new ClassDescription(\"example\", \"Foo\");");
        System.out.println("bsh % ClassDescription bar = new ClassDescription(\"example\", \"Bar\");");
        System.out.println("");
        System.out.println("bsh % foo.attribute(\"String\", \"Name\", Cardinality.ZeroToOne);");
        System.out.println("bsh % bar.attribute(\"Integer\", \"Value\", Cardinality.StrictlyOne);");
        System.out.println("");
        System.out.println("bsh % foo.associate(bar, Cardinality.ZeroToOne);");
        System.out.println("bsh % bar.associate(foo, Cardinality.ZeroToMany, false, true, false, false);");
        System.out.println("");
        System.out.println("bsh % generateSource(foo, Style.Immutable);");
        System.out.println("bsh % generateSource(bar, Style.RichlyMutable);");
        System.out.println("");
    }
}
