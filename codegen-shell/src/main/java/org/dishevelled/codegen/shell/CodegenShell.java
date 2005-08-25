/*

    dsh-codegen-shell  Runnable shell for the codegen library.
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
package org.dishevelled.codegen.shell;

import java.io.Reader;
import java.io.InputStreamReader;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Runnable shell for the codegen library.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class CodegenShell
    implements Runnable
{

    /** @see Runnable */
    public void run()
    {
        Reader in = new InputStreamReader(System.in);
        Interpreter bsh = new Interpreter(in, System.out, System.err, true);

        bsh.setExitOnEOF(true);

        try
        {
            bsh.eval("import org.dishevelled.codegen.*;");
            bsh.eval("static import org.dishevelled.codegen.Codegen.*;");
        }
        catch (EvalError e)
        {
            // ignore
        }

        System.out.println("Shell interface to org.dishevelled.codegen.Codegen");
        bsh.run();
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        if (args.length > 0)
        {
            if (("-h".equals(args[0])) || ("--help".equals(args[0])))
            {
                System.out.println("Shell interface to org.biojava.codegen.Codegen");
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

        CodegenShell cs = new CodegenShell();
        cs.run();
    }
}
