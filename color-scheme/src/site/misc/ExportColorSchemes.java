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
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.dishevelled.color.scheme.ColorScheme;

import static org.dishevelled.color.scheme.impl.ColorSchemes.*;

public final class ExportColorSchemes
{
    // sequential, diverging, qualitative

    public static void main(final String args[])
    {
        /*
        export(getDiscreteColorScheme("bl-rd-bu-wh", 4, Interpolations.LINEAR), "discrete-bl-rd-bu-wh-4");
        export(getDiscreteColorScheme("accent", 3, Interpolations.LINEAR), "discrete-accent-3");
        export(getDiscreteColorScheme("accent", 4, Interpolations.LINEAR), "discrete-accent-4");
        export(getDiscreteColorScheme("accent", 5, Interpolations.LINEAR), "discrete-accent-5");
        export(getDiscreteColorScheme("accent", 6, Interpolations.LINEAR), "discrete-accent-6");
        export(getDiscreteColorScheme("accent", 7, Interpolations.LINEAR), "discrete-accent-7");
        export(getDiscreteColorScheme("bl-wh", 2, Interpolations.LINEAR), "discrete-bl-wh-2");
        export(getDiscreteColorScheme("blues", 3, Interpolations.LINEAR), "discrete-blues-3");
        export(getDiscreteColorScheme("blues", 4, Interpolations.LINEAR), "discrete-blues-4");
        export(getDiscreteColorScheme("blues", 5, Interpolations.LINEAR), "discrete-blues-5");
        export(getDiscreteColorScheme("blues", 6, Interpolations.LINEAR), "discrete-blues-6");
        export(getDiscreteColorScheme("blues", 7, Interpolations.LINEAR), "discrete-blues-7");
        export(getDiscreteColorScheme("blues", 8, Interpolations.LINEAR), "discrete-blues-8");
        export(getDiscreteColorScheme("blues", 9, Interpolations.LINEAR), "discrete-blues-9");
        export(getDiscreteColorScheme("br-b-g", 10, Interpolations.LINEAR), "discrete-br-b-g-10");
        export(getDiscreteColorScheme("br-b-g", 11, Interpolations.LINEAR), "discrete-br-b-g-11");
        export(getDiscreteColorScheme("br-b-g", 3, Interpolations.LINEAR), "discrete-br-b-g-3");
        export(getDiscreteColorScheme("br-b-g", 4, Interpolations.LINEAR), "discrete-br-b-g-4");
        export(getDiscreteColorScheme("br-b-g", 5, Interpolations.LINEAR), "discrete-br-b-g-5");

        export(getDiscreteColorScheme("rd-bl-gr", 3, Interpolations.LINEAR), "discrete-rd-bl-gr-3");
        export(getDiscreteColorScheme("bu-bl-yl", 3, Interpolations.LINEAR), "discrete-bu-bl-yl-3");

        ColorScheme oddDivergent = getDiscreteColorScheme("br-b-g", 5, Interpolations.LINEAR);
        System.out.println("Setting zero value 0.25d");
        oddDivergent.setZeroValue(0.25d);
        export(oddDivergent, "zero-left-odd");
        System.out.println("Setting zero value 0.75d");
        oddDivergent.setZeroValue(0.75d);
        export(oddDivergent, "zero-right-odd");
        System.out.println("Setting zero value -0.25d");
        oddDivergent.setZeroValue(-0.25d);
        export(oddDivergent, "zero-off-left-odd");
        System.out.println("Setting zero value 1.25d");
        oddDivergent.setZeroValue(1.25d);
        export(oddDivergent, "zero-off-right-odd");

        export(getDiscreteColorScheme("br-b-g", 6, Interpolations.LINEAR), "discrete-br-b-g-6");

        ColorScheme evenDivergent = getDiscreteColorScheme("br-b-g", 6, Interpolations.LINEAR);
        System.out.println("Setting zero value 0.25d");
        evenDivergent.setZeroValue(0.25d);
        export(evenDivergent, "zero-left-even");
        System.out.println("Setting zero value 0.75d");
        evenDivergent.setZeroValue(0.75d);
        export(evenDivergent, "zero-right-even");
        System.out.println("Setting zero value -0.25d");
        evenDivergent.setZeroValue(-0.25d);
        export(evenDivergent, "zero-off-left-even");
        System.out.println("Setting zero value 1.25d");
        evenDivergent.setZeroValue(1.25d);
        export(evenDivergent, "zero-off-right-even");

        export(getDiscreteColorScheme("br-b-g", 7, Interpolations.LINEAR), "discrete-br-b-g-7");
        export(getDiscreteColorScheme("br-b-g", 8, Interpolations.LINEAR), "discrete-br-b-g-8");
        export(getDiscreteColorScheme("br-b-g", 9, Interpolations.LINEAR), "discrete-br-b-g-9");
        export(getDiscreteColorScheme("bu-gn", 3, Interpolations.LINEAR), "discrete-bu-gn-3");
        export(getDiscreteColorScheme("bu-gn", 4, Interpolations.LINEAR), "discrete-bu-gn-4");
        export(getDiscreteColorScheme("bu-gn", 5, Interpolations.LINEAR), "discrete-bu-gn-5");
        export(getDiscreteColorScheme("bu-gn", 6, Interpolations.LINEAR), "discrete-bu-gn-6");
        export(getDiscreteColorScheme("bu-gn", 7, Interpolations.LINEAR), "discrete-bu-gn-7");
        export(getDiscreteColorScheme("bu-gn", 8, Interpolations.LINEAR), "discrete-bu-gn-8");
        export(getDiscreteColorScheme("bu-gn", 9, Interpolations.LINEAR), "discrete-bu-gn-9");
        export(getDiscreteColorScheme("bu-pu", 3, Interpolations.LINEAR), "discrete-bu-pu-3");
        export(getDiscreteColorScheme("bu-pu", 4, Interpolations.LINEAR), "discrete-bu-pu-4");
        export(getDiscreteColorScheme("bu-pu", 5, Interpolations.LINEAR), "discrete-bu-pu-5");
        export(getDiscreteColorScheme("bu-pu", 6, Interpolations.LINEAR), "discrete-bu-pu-6");
        export(getDiscreteColorScheme("bu-pu", 7, Interpolations.LINEAR), "discrete-bu-pu-7");
        export(getDiscreteColorScheme("bu-pu", 8, Interpolations.LINEAR), "discrete-bu-pu-8");
        export(getDiscreteColorScheme("bu-pu", 9, Interpolations.LINEAR), "discrete-bu-pu-9");
        export(getDiscreteColorScheme("gn-bu", 3, Interpolations.LINEAR), "discrete-gn-bu-3");
        export(getDiscreteColorScheme("gn-bu", 4, Interpolations.LINEAR), "discrete-gn-bu-4");
        export(getDiscreteColorScheme("gn-bu", 5, Interpolations.LINEAR), "discrete-gn-bu-5");
        export(getDiscreteColorScheme("gn-bu", 6, Interpolations.LINEAR), "discrete-gn-bu-6");
        export(getDiscreteColorScheme("gn-bu", 7, Interpolations.LINEAR), "discrete-gn-bu-7");
        export(getDiscreteColorScheme("gn-bu", 8, Interpolations.LINEAR), "discrete-gn-bu-8");
        export(getDiscreteColorScheme("gn-bu", 9, Interpolations.LINEAR), "discrete-gn-bu-9");
        export(getDiscreteColorScheme("greens", 3, Interpolations.LINEAR), "discrete-greens-3");
        export(getDiscreteColorScheme("greens", 4, Interpolations.LINEAR), "discrete-greens-4");
        export(getDiscreteColorScheme("greens", 5, Interpolations.LINEAR), "discrete-greens-5");
        export(getDiscreteColorScheme("greens", 6, Interpolations.LINEAR), "discrete-greens-6");
        export(getDiscreteColorScheme("greens", 7, Interpolations.LINEAR), "discrete-greens-7");
        export(getDiscreteColorScheme("greens", 8, Interpolations.LINEAR), "discrete-greens-8");
        export(getDiscreteColorScheme("greens", 9, Interpolations.LINEAR), "discrete-greens-9");
        export(getDiscreteColorScheme("greys", 3, Interpolations.LINEAR), "discrete-greys-3");
        export(getDiscreteColorScheme("greys", 4, Interpolations.LINEAR), "discrete-greys-4");
        export(getDiscreteColorScheme("greys", 5, Interpolations.LINEAR), "discrete-greys-5");
        export(getDiscreteColorScheme("greys", 6, Interpolations.LINEAR), "discrete-greys-6");
        export(getDiscreteColorScheme("greys", 7, Interpolations.LINEAR), "discrete-greys-7");
        export(getDiscreteColorScheme("greys", 8, Interpolations.LINEAR), "discrete-greys-8");
        export(getDiscreteColorScheme("greys", 9, Interpolations.LINEAR), "discrete-greys-9");
        export(getDiscreteColorScheme("or-rd", 3, Interpolations.LINEAR), "discrete-or-rd-3");
        export(getDiscreteColorScheme("or-rd", 4, Interpolations.LINEAR), "discrete-or-rd-4");
        export(getDiscreteColorScheme("or-rd", 5, Interpolations.LINEAR), "discrete-or-rd-5");
        export(getDiscreteColorScheme("or-rd", 6, Interpolations.LINEAR), "discrete-or-rd-6");
        export(getDiscreteColorScheme("or-rd", 7, Interpolations.LINEAR), "discrete-or-rd-7");
        export(getDiscreteColorScheme("or-rd", 8, Interpolations.LINEAR), "discrete-or-rd-8");
        export(getDiscreteColorScheme("or-rd", 9, Interpolations.LINEAR), "discrete-or-rd-9");
        export(getDiscreteColorScheme("oranges", 3, Interpolations.LINEAR), "discrete-oranges-3");
        export(getDiscreteColorScheme("oranges", 4, Interpolations.LINEAR), "discrete-oranges-4");
        export(getDiscreteColorScheme("oranges", 5, Interpolations.LINEAR), "discrete-oranges-5");
        export(getDiscreteColorScheme("oranges", 6, Interpolations.LINEAR), "discrete-oranges-6");
        export(getDiscreteColorScheme("oranges", 7, Interpolations.LINEAR), "discrete-oranges-7");
        export(getDiscreteColorScheme("oranges", 8, Interpolations.LINEAR), "discrete-oranges-8");
        export(getDiscreteColorScheme("oranges", 9, Interpolations.LINEAR), "discrete-oranges-9");
        export(getDiscreteColorScheme("p-r-gn", 10, Interpolations.LINEAR), "discrete-p-r-gn-10");
        export(getDiscreteColorScheme("p-r-gn", 11, Interpolations.LINEAR), "discrete-p-r-gn-11");
        export(getDiscreteColorScheme("p-r-gn", 3, Interpolations.LINEAR), "discrete-p-r-gn-3");
        export(getDiscreteColorScheme("p-r-gn", 4, Interpolations.LINEAR), "discrete-p-r-gn-4");
        export(getDiscreteColorScheme("p-r-gn", 5, Interpolations.LINEAR), "discrete-p-r-gn-5");
        export(getDiscreteColorScheme("p-r-gn", 6, Interpolations.LINEAR), "discrete-p-r-gn-6");
        export(getDiscreteColorScheme("p-r-gn", 7, Interpolations.LINEAR), "discrete-p-r-gn-7");
        export(getDiscreteColorScheme("p-r-gn", 8, Interpolations.LINEAR), "discrete-p-r-gn-8");
        export(getDiscreteColorScheme("p-r-gn", 9, Interpolations.LINEAR), "discrete-p-r-gn-9");
        export(getDiscreteColorScheme("paired", 10, Interpolations.LINEAR), "discrete-paired-10");
        export(getDiscreteColorScheme("paired", 11, Interpolations.LINEAR), "discrete-paired-11");
        export(getDiscreteColorScheme("paired", 12, Interpolations.LINEAR), "discrete-paired-12");
        export(getDiscreteColorScheme("paired", 3, Interpolations.LINEAR), "discrete-paired-3");
        export(getDiscreteColorScheme("paired", 4, Interpolations.LINEAR), "discrete-paired-4");
        export(getDiscreteColorScheme("paired", 5, Interpolations.LINEAR), "discrete-paired-5");
        export(getDiscreteColorScheme("paired", 6, Interpolations.LINEAR), "discrete-paired-6");
        export(getDiscreteColorScheme("paired", 7, Interpolations.LINEAR), "discrete-paired-7");
        export(getDiscreteColorScheme("paired", 8, Interpolations.LINEAR), "discrete-paired-8");
        export(getDiscreteColorScheme("paired", 9, Interpolations.LINEAR), "discrete-paired-9");
        export(getDiscreteColorScheme("pi-y-g", 10, Interpolations.LINEAR), "discrete-pi-y-g-10");
        export(getDiscreteColorScheme("pi-y-g", 11, Interpolations.LINEAR), "discrete-pi-y-g-11");
        export(getDiscreteColorScheme("pi-y-g", 3, Interpolations.LINEAR), "discrete-pi-y-g-3");
        export(getDiscreteColorScheme("pi-y-g", 4, Interpolations.LINEAR), "discrete-pi-y-g-4");
        export(getDiscreteColorScheme("pi-y-g", 5, Interpolations.LINEAR), "discrete-pi-y-g-5");
        export(getDiscreteColorScheme("pi-y-g", 6, Interpolations.LINEAR), "discrete-pi-y-g-6");
        export(getDiscreteColorScheme("pi-y-g", 7, Interpolations.LINEAR), "discrete-pi-y-g-7");
        export(getDiscreteColorScheme("pi-y-g", 8, Interpolations.LINEAR), "discrete-pi-y-g-8");
        export(getDiscreteColorScheme("pi-y-g", 9, Interpolations.LINEAR), "discrete-pi-y-g-9");
        export(getDiscreteColorScheme("pu-bu", 3, Interpolations.LINEAR), "discrete-pu-bu-3");
        export(getDiscreteColorScheme("pu-bu", 4, Interpolations.LINEAR), "discrete-pu-bu-4");
        export(getDiscreteColorScheme("pu-bu", 5, Interpolations.LINEAR), "discrete-pu-bu-5");
        export(getDiscreteColorScheme("pu-bu", 6, Interpolations.LINEAR), "discrete-pu-bu-6");
        export(getDiscreteColorScheme("pu-bu", 7, Interpolations.LINEAR), "discrete-pu-bu-7");
        export(getDiscreteColorScheme("pu-bu", 8, Interpolations.LINEAR), "discrete-pu-bu-8");
        export(getDiscreteColorScheme("pu-bu", 9, Interpolations.LINEAR), "discrete-pu-bu-9");
        export(getDiscreteColorScheme("pu-bu-gn", 3, Interpolations.LINEAR), "discrete-pu-bu-gn-3");
        export(getDiscreteColorScheme("pu-bu-gn", 4, Interpolations.LINEAR), "discrete-pu-bu-gn-4");
        export(getDiscreteColorScheme("pu-bu-gn", 5, Interpolations.LINEAR), "discrete-pu-bu-gn-5");
        export(getDiscreteColorScheme("pu-bu-gn", 6, Interpolations.LINEAR), "discrete-pu-bu-gn-6");
        export(getDiscreteColorScheme("pu-bu-gn", 7, Interpolations.LINEAR), "discrete-pu-bu-gn-7");
        export(getDiscreteColorScheme("pu-bu-gn", 8, Interpolations.LINEAR), "discrete-pu-bu-gn-8");
        export(getDiscreteColorScheme("pu-bu-gn", 9, Interpolations.LINEAR), "discrete-pu-bu-gn-9");
        export(getDiscreteColorScheme("pu-or", 10, Interpolations.LINEAR), "discrete-pu-or-10");
        export(getDiscreteColorScheme("pu-or", 11, Interpolations.LINEAR), "discrete-pu-or-11");
        export(getDiscreteColorScheme("pu-or", 3, Interpolations.LINEAR), "discrete-pu-or-3");
        export(getDiscreteColorScheme("pu-or", 4, Interpolations.LINEAR), "discrete-pu-or-4");
        export(getDiscreteColorScheme("pu-or", 5, Interpolations.LINEAR), "discrete-pu-or-5");
        export(getDiscreteColorScheme("pu-or", 6, Interpolations.LINEAR), "discrete-pu-or-6");
        export(getDiscreteColorScheme("pu-or", 7, Interpolations.LINEAR), "discrete-pu-or-7");
        export(getDiscreteColorScheme("pu-or", 8, Interpolations.LINEAR), "discrete-pu-or-8");
        export(getDiscreteColorScheme("pu-or", 9, Interpolations.LINEAR), "discrete-pu-or-9");
        export(getDiscreteColorScheme("pu-rd", 3, Interpolations.LINEAR), "discrete-pu-rd-3");
        export(getDiscreteColorScheme("pu-rd", 4, Interpolations.LINEAR), "discrete-pu-rd-4");
        export(getDiscreteColorScheme("pu-rd", 5, Interpolations.LINEAR), "discrete-pu-rd-5");
        export(getDiscreteColorScheme("pu-rd", 6, Interpolations.LINEAR), "discrete-pu-rd-6");
        export(getDiscreteColorScheme("pu-rd", 7, Interpolations.LINEAR), "discrete-pu-rd-7");
        export(getDiscreteColorScheme("pu-rd", 8, Interpolations.LINEAR), "discrete-pu-rd-8");
        export(getDiscreteColorScheme("pu-rd", 9, Interpolations.LINEAR), "discrete-pu-rd-9");
        export(getDiscreteColorScheme("purples", 3, Interpolations.LINEAR), "discrete-purples-3");
        export(getDiscreteColorScheme("purples", 4, Interpolations.LINEAR), "discrete-purples-4");
        export(getDiscreteColorScheme("purples", 5, Interpolations.LINEAR), "discrete-purples-5");
        export(getDiscreteColorScheme("purples", 6, Interpolations.LINEAR), "discrete-purples-6");
        export(getDiscreteColorScheme("purples", 7, Interpolations.LINEAR), "discrete-purples-7");
        export(getDiscreteColorScheme("purples", 8, Interpolations.LINEAR), "discrete-purples-8");
        export(getDiscreteColorScheme("purples", 9, Interpolations.LINEAR), "discrete-purples-9");
        export(getDiscreteColorScheme("rd-bu", 10, Interpolations.LINEAR), "discrete-rd-bu-10");
        export(getDiscreteColorScheme("rd-bu", 11, Interpolations.LINEAR), "discrete-rd-bu-11");
        export(getDiscreteColorScheme("rd-bu", 3, Interpolations.LINEAR), "discrete-rd-bu-3");
        export(getDiscreteColorScheme("rd-bu", 4, Interpolations.LINEAR), "discrete-rd-bu-4");
        export(getDiscreteColorScheme("rd-bu", 5, Interpolations.LINEAR), "discrete-rd-bu-5");
        export(getDiscreteColorScheme("rd-bu", 6, Interpolations.LINEAR), "discrete-rd-bu-6");
        export(getDiscreteColorScheme("rd-bu", 7, Interpolations.LINEAR), "discrete-rd-bu-7");
        export(getDiscreteColorScheme("rd-bu", 8, Interpolations.LINEAR), "discrete-rd-bu-8");
        export(getDiscreteColorScheme("rd-bu", 9, Interpolations.LINEAR), "discrete-rd-bu-9");
        export(getDiscreteColorScheme("rd-gy", 10, Interpolations.LINEAR), "discrete-rd-gy-10");
        export(getDiscreteColorScheme("rd-gy", 11, Interpolations.LINEAR), "discrete-rd-gy-11");
        export(getDiscreteColorScheme("rd-gy", 3, Interpolations.LINEAR), "discrete-rd-gy-3");
        export(getDiscreteColorScheme("rd-gy", 4, Interpolations.LINEAR), "discrete-rd-gy-4");
        export(getDiscreteColorScheme("rd-gy", 5, Interpolations.LINEAR), "discrete-rd-gy-5");
        export(getDiscreteColorScheme("rd-gy", 6, Interpolations.LINEAR), "discrete-rd-gy-6");
        export(getDiscreteColorScheme("rd-gy", 7, Interpolations.LINEAR), "discrete-rd-gy-7");
        export(getDiscreteColorScheme("rd-gy", 8, Interpolations.LINEAR), "discrete-rd-gy-8");
        export(getDiscreteColorScheme("rd-gy", 9, Interpolations.LINEAR), "discrete-rd-gy-9");
        export(getDiscreteColorScheme("rd-pu", 3, Interpolations.LINEAR), "discrete-rd-pu-3");
        export(getDiscreteColorScheme("rd-pu", 4, Interpolations.LINEAR), "discrete-rd-pu-4");
        export(getDiscreteColorScheme("rd-pu", 5, Interpolations.LINEAR), "discrete-rd-pu-5");
        export(getDiscreteColorScheme("rd-pu", 6, Interpolations.LINEAR), "discrete-rd-pu-6");
        export(getDiscreteColorScheme("rd-pu", 7, Interpolations.LINEAR), "discrete-rd-pu-7");
        export(getDiscreteColorScheme("rd-pu", 8, Interpolations.LINEAR), "discrete-rd-pu-8");
        export(getDiscreteColorScheme("rd-pu", 9, Interpolations.LINEAR), "discrete-rd-pu-9");
        export(getDiscreteColorScheme("rd-yl-bu", 10, Interpolations.LINEAR), "discrete-rd-yl-bu-10");
        export(getDiscreteColorScheme("rd-yl-bu", 11, Interpolations.LINEAR), "discrete-rd-yl-bu-11");
        export(getDiscreteColorScheme("rd-yl-bu", 3, Interpolations.LINEAR), "discrete-rd-yl-bu-3");
        export(getDiscreteColorScheme("rd-yl-bu", 4, Interpolations.LINEAR), "discrete-rd-yl-bu-4");
        export(getDiscreteColorScheme("rd-yl-bu", 5, Interpolations.LINEAR), "discrete-rd-yl-bu-5");
        export(getDiscreteColorScheme("rd-yl-bu", 6, Interpolations.LINEAR), "discrete-rd-yl-bu-6");
        export(getDiscreteColorScheme("rd-yl-bu", 7, Interpolations.LINEAR), "discrete-rd-yl-bu-7");
        export(getDiscreteColorScheme("rd-yl-bu", 8, Interpolations.LINEAR), "discrete-rd-yl-bu-8");
        export(getDiscreteColorScheme("rd-yl-bu", 9, Interpolations.LINEAR), "discrete-rd-yl-bu-9");
        export(getDiscreteColorScheme("rd-yl-gn", 10, Interpolations.LINEAR), "discrete-rd-yl-gn-10");
        export(getDiscreteColorScheme("rd-yl-gn", 11, Interpolations.LINEAR), "discrete-rd-yl-gn-11");
        export(getDiscreteColorScheme("rd-yl-gn", 3, Interpolations.LINEAR), "discrete-rd-yl-gn-3");
        export(getDiscreteColorScheme("rd-yl-gn", 4, Interpolations.LINEAR), "discrete-rd-yl-gn-4");
        export(getDiscreteColorScheme("rd-yl-gn", 5, Interpolations.LINEAR), "discrete-rd-yl-gn-5");
        export(getDiscreteColorScheme("rd-yl-gn", 6, Interpolations.LINEAR), "discrete-rd-yl-gn-6");
        export(getDiscreteColorScheme("rd-yl-gn", 7, Interpolations.LINEAR), "discrete-rd-yl-gn-7");
        export(getDiscreteColorScheme("rd-yl-gn", 8, Interpolations.LINEAR), "discrete-rd-yl-gn-8");
        export(getDiscreteColorScheme("rd-yl-gn", 9, Interpolations.LINEAR), "discrete-rd-yl-gn-9");
        export(getDiscreteColorScheme("reds", 3, Interpolations.LINEAR), "discrete-reds-3");
        export(getDiscreteColorScheme("reds", 4, Interpolations.LINEAR), "discrete-reds-4");
        export(getDiscreteColorScheme("reds", 5, Interpolations.LINEAR), "discrete-reds-5");
        export(getDiscreteColorScheme("reds", 6, Interpolations.LINEAR), "discrete-reds-6");
        export(getDiscreteColorScheme("reds", 7, Interpolations.LINEAR), "discrete-reds-7");
        export(getDiscreteColorScheme("reds", 8, Interpolations.LINEAR), "discrete-reds-8");
        export(getDiscreteColorScheme("reds", 9, Interpolations.LINEAR), "discrete-reds-9");
        export(getDiscreteColorScheme("spectral", 10, Interpolations.LINEAR), "discrete-spectral-10");
        export(getDiscreteColorScheme("spectral", 11, Interpolations.LINEAR), "discrete-spectral-11");
        export(getDiscreteColorScheme("spectral", 3, Interpolations.LINEAR), "discrete-spectral-3");
        export(getDiscreteColorScheme("spectral", 4, Interpolations.LINEAR), "discrete-spectral-4");
        export(getDiscreteColorScheme("spectral", 5, Interpolations.LINEAR), "discrete-spectral-5");
        export(getDiscreteColorScheme("spectral", 6, Interpolations.LINEAR), "discrete-spectral-6");
        export(getDiscreteColorScheme("spectral", 7, Interpolations.LINEAR), "discrete-spectral-7");
        export(getDiscreteColorScheme("spectral", 8, Interpolations.LINEAR), "discrete-spectral-8");
        export(getDiscreteColorScheme("spectral", 9, Interpolations.LINEAR), "discrete-spectral-9");
        export(getDiscreteColorScheme("wh-bl", 2, Interpolations.LINEAR), "discrete-wh-bl-2");
        export(getDiscreteColorScheme("yl-gn", 3, Interpolations.LINEAR), "discrete-yl-gn-3");
        export(getDiscreteColorScheme("yl-gn", 4, Interpolations.LINEAR), "discrete-yl-gn-4");
        export(getDiscreteColorScheme("yl-gn", 5, Interpolations.LINEAR), "discrete-yl-gn-5");
        export(getDiscreteColorScheme("yl-gn", 6, Interpolations.LINEAR), "discrete-yl-gn-6");
        export(getDiscreteColorScheme("yl-gn", 7, Interpolations.LINEAR), "discrete-yl-gn-7");
        export(getDiscreteColorScheme("yl-gn", 8, Interpolations.LINEAR), "discrete-yl-gn-8");
        export(getDiscreteColorScheme("yl-gn", 9, Interpolations.LINEAR), "discrete-yl-gn-9");
        export(getDiscreteColorScheme("yl-gn-bu", 3, Interpolations.LINEAR), "discrete-yl-gn-bu-3");
        export(getDiscreteColorScheme("yl-gn-bu", 4, Interpolations.LINEAR), "discrete-yl-gn-bu-4");
        export(getDiscreteColorScheme("yl-gn-bu", 5, Interpolations.LINEAR), "discrete-yl-gn-bu-5");
        export(getDiscreteColorScheme("yl-gn-bu", 6, Interpolations.LINEAR), "discrete-yl-gn-bu-6");
        export(getDiscreteColorScheme("yl-gn-bu", 7, Interpolations.LINEAR), "discrete-yl-gn-bu-7");
        export(getDiscreteColorScheme("yl-gn-bu", 8, Interpolations.LINEAR), "discrete-yl-gn-bu-8");
        export(getDiscreteColorScheme("yl-gn-bu", 9, Interpolations.LINEAR), "discrete-yl-gn-bu-9");
        export(getDiscreteColorScheme("yl-or-br", 3, Interpolations.LINEAR), "discrete-yl-or-br-3");
        export(getDiscreteColorScheme("yl-or-br", 4, Interpolations.LINEAR), "discrete-yl-or-br-4");
        export(getDiscreteColorScheme("yl-or-br", 5, Interpolations.LINEAR), "discrete-yl-or-br-5");
        export(getDiscreteColorScheme("yl-or-br", 6, Interpolations.LINEAR), "discrete-yl-or-br-6");
        export(getDiscreteColorScheme("yl-or-br", 7, Interpolations.LINEAR), "discrete-yl-or-br-7");
        export(getDiscreteColorScheme("yl-or-br", 8, Interpolations.LINEAR), "discrete-yl-or-br-8");
        export(getDiscreteColorScheme("yl-or-br", 9, Interpolations.LINEAR), "discrete-yl-or-br-9");
        export(getDiscreteColorScheme("yl-or-rd", 3, Interpolations.LINEAR), "discrete-yl-or-rd-3");
        export(getDiscreteColorScheme("yl-or-rd", 4, Interpolations.LINEAR), "discrete-yl-or-rd-4");
        export(getDiscreteColorScheme("yl-or-rd", 5, Interpolations.LINEAR), "discrete-yl-or-rd-5");
        export(getDiscreteColorScheme("yl-or-rd", 6, Interpolations.LINEAR), "discrete-yl-or-rd-6");
        export(getDiscreteColorScheme("yl-or-rd", 7, Interpolations.LINEAR), "discrete-yl-or-rd-7");
        export(getDiscreteColorScheme("yl-or-rd", 8, Interpolations.LINEAR), "discrete-yl-or-rd-8");
        export(getDiscreteColorScheme("yl-or-rd", 9, Interpolations.LINEAR), "discrete-yl-or-rd-9");

        export(getDiscreteColorScheme("set-1", 3, Interpolations.LINEAR), "discrete-set-1-3");
        export(getDiscreteColorScheme("set-1", 4, Interpolations.LINEAR), "discrete-set-1-4");
        export(getDiscreteColorScheme("set-1", 5, Interpolations.LINEAR), "discrete-set-1-5");
        export(getDiscreteColorScheme("set-1", 6, Interpolations.LINEAR), "discrete-set-1-6");
        export(getDiscreteColorScheme("set-1", 7, Interpolations.LINEAR), "discrete-set-1-7");
        export(getDiscreteColorScheme("set-1", 8, Interpolations.LINEAR), "discrete-set-1-8");
        export(getDiscreteColorScheme("set-1", 9, Interpolations.LINEAR), "discrete-set-1-9");

        export(getDiscreteColorScheme("set-2", 3, Interpolations.LINEAR), "discrete-set-2-3");
        export(getDiscreteColorScheme("set-2", 4, Interpolations.LINEAR), "discrete-set-2-4");
        export(getDiscreteColorScheme("set-2", 5, Interpolations.LINEAR), "discrete-set-2-5");
        export(getDiscreteColorScheme("set-2", 6, Interpolations.LINEAR), "discrete-set-2-6");
        export(getDiscreteColorScheme("set-2", 7, Interpolations.LINEAR), "discrete-set-2-7");
        export(getDiscreteColorScheme("set-2", 8, Interpolations.LINEAR), "discrete-set-2-8");

        export(getDiscreteColorScheme("set-3", 3, Interpolations.LINEAR), "discrete-set-3-3");
        export(getDiscreteColorScheme("set-3", 4, Interpolations.LINEAR), "discrete-set-3-4");
        export(getDiscreteColorScheme("set-3", 5, Interpolations.LINEAR), "discrete-set-3-5");
        export(getDiscreteColorScheme("set-3", 6, Interpolations.LINEAR), "discrete-set-3-6");
        export(getDiscreteColorScheme("set-3", 7, Interpolations.LINEAR), "discrete-set-3-7");
        export(getDiscreteColorScheme("set-3", 8, Interpolations.LINEAR), "discrete-set-3-8");
        export(getDiscreteColorScheme("set-3", 9, Interpolations.LINEAR), "discrete-set-3-9");
        export(getDiscreteColorScheme("set-3", 10, Interpolations.LINEAR), "discrete-set-3-10");
        export(getDiscreteColorScheme("set-3", 11, Interpolations.LINEAR), "discrete-set-3-11");
        export(getDiscreteColorScheme("set-3", 12, Interpolations.LINEAR), "discrete-set-3-12");

        export(getDiscreteColorScheme("bu-wh-rd", 3, Interpolations.LINEAR), "discrete-bu-wh-rd-3");
        export(getDiscreteColorScheme("bu-wh-rd", 7, Interpolations.LINEAR), "discrete-bu-wh-rd-7");

        export(getDiscreteColorScheme("dark-2", 3, Interpolations.LINEAR), "discrete-dark-2-3");
        export(getDiscreteColorScheme("dark-2", 4, Interpolations.LINEAR), "discrete-dark-2-4");
        export(getDiscreteColorScheme("dark-2", 5, Interpolations.LINEAR), "discrete-dark-2-5");
        export(getDiscreteColorScheme("dark-2", 6, Interpolations.LINEAR), "discrete-dark-2-6");
        export(getDiscreteColorScheme("dark-2", 7, Interpolations.LINEAR), "discrete-dark-2-7");
        export(getDiscreteColorScheme("dark-2", 8, Interpolations.LINEAR), "discrete-dark-2-8");

        export(getDiscreteColorScheme("pastel-1", 3, Interpolations.LINEAR), "discrete-pastel-1-3");
        export(getDiscreteColorScheme("pastel-1", 4, Interpolations.LINEAR), "discrete-pastel-1-4");
        export(getDiscreteColorScheme("pastel-1", 5, Interpolations.LINEAR), "discrete-pastel-1-5");
        export(getDiscreteColorScheme("pastel-1", 6, Interpolations.LINEAR), "discrete-pastel-1-6");
        export(getDiscreteColorScheme("pastel-1", 7, Interpolations.LINEAR), "discrete-pastel-1-7");
        export(getDiscreteColorScheme("pastel-1", 8, Interpolations.LINEAR), "discrete-pastel-1-8");
        export(getDiscreteColorScheme("pastel-1", 9, Interpolations.LINEAR), "discrete-pastel-1-9");

        export(getDiscreteColorScheme("pastel-2", 3, Interpolations.LINEAR), "discrete-pastel-2-3");
        export(getDiscreteColorScheme("pastel-2", 4, Interpolations.LINEAR), "discrete-pastel-2-4");
        export(getDiscreteColorScheme("pastel-2", 5, Interpolations.LINEAR), "discrete-pastel-2-5");
        export(getDiscreteColorScheme("pastel-2", 6, Interpolations.LINEAR), "discrete-pastel-2-6");
        export(getDiscreteColorScheme("pastel-2", 7, Interpolations.LINEAR), "discrete-pastel-2-7");
        export(getDiscreteColorScheme("pastel-2", 8, Interpolations.LINEAR), "discrete-pastel-2-8");

        export(getContinuousColorScheme("bl-rd-bu-wh", 4, Interpolations.LINEAR), "continuous-bl-rd-bu-wh-4");
        export(getContinuousColorScheme("rd-bl-gr", 3, Interpolations.LINEAR), "continuous-rd-bl-gr-3");
        export(getContinuousColorScheme("bu-bl-yl", 3, Interpolations.LINEAR), "continuous-bu-bl-yl-3");

        export(getContinuousColorScheme("accent", 3, Interpolations.LINEAR), "continuous-accent-3");
        export(getContinuousColorScheme("accent", 4, Interpolations.LINEAR), "continuous-accent-4");
        export(getContinuousColorScheme("accent", 5, Interpolations.LINEAR), "continuous-accent-5");
        export(getContinuousColorScheme("accent", 6, Interpolations.LINEAR), "continuous-accent-6");
        export(getContinuousColorScheme("accent", 7, Interpolations.LINEAR), "continuous-accent-7");
        export(getContinuousColorScheme("bl-wh", 2, Interpolations.LINEAR), "continuous-bl-wh-2");
        export(getContinuousColorScheme("blues", 3, Interpolations.LINEAR), "continuous-blues-3");
        export(getContinuousColorScheme("blues", 4, Interpolations.LINEAR), "continuous-blues-4");
        export(getContinuousColorScheme("blues", 5, Interpolations.LINEAR), "continuous-blues-5");
        export(getContinuousColorScheme("blues", 6, Interpolations.LINEAR), "continuous-blues-6");
        export(getContinuousColorScheme("blues", 7, Interpolations.LINEAR), "continuous-blues-7");
        export(getContinuousColorScheme("blues", 8, Interpolations.LINEAR), "continuous-blues-8");
        export(getContinuousColorScheme("blues", 9, Interpolations.LINEAR), "continuous-blues-9");
        export(getContinuousColorScheme("br-b-g", 10, Interpolations.LINEAR), "continuous-br-b-g-10");
        export(getContinuousColorScheme("br-b-g", 11, Interpolations.LINEAR), "continuous-br-b-g-11");
        export(getContinuousColorScheme("br-b-g", 3, Interpolations.LINEAR), "continuous-br-b-g-3");
        export(getContinuousColorScheme("br-b-g", 4, Interpolations.LINEAR), "continuous-br-b-g-4");
        export(getContinuousColorScheme("br-b-g", 5, Interpolations.LINEAR), "continuous-br-b-g-5");
        export(getContinuousColorScheme("br-b-g", 6, Interpolations.LINEAR), "continuous-br-b-g-6");
        export(getContinuousColorScheme("br-b-g", 7, Interpolations.LINEAR), "continuous-br-b-g-7");
        export(getContinuousColorScheme("br-b-g", 8, Interpolations.LINEAR), "continuous-br-b-g-8");
        export(getContinuousColorScheme("br-b-g", 9, Interpolations.LINEAR), "continuous-br-b-g-9");
        export(getContinuousColorScheme("bu-gn", 3, Interpolations.LINEAR), "continuous-bu-gn-3");
        export(getContinuousColorScheme("bu-gn", 4, Interpolations.LINEAR), "continuous-bu-gn-4");
        export(getContinuousColorScheme("bu-gn", 5, Interpolations.LINEAR), "continuous-bu-gn-5");
        export(getContinuousColorScheme("bu-gn", 6, Interpolations.LINEAR), "continuous-bu-gn-6");
        export(getContinuousColorScheme("bu-gn", 7, Interpolations.LINEAR), "continuous-bu-gn-7");
        export(getContinuousColorScheme("bu-gn", 8, Interpolations.LINEAR), "continuous-bu-gn-8");
        export(getContinuousColorScheme("bu-gn", 9, Interpolations.LINEAR), "continuous-bu-gn-9");
        export(getContinuousColorScheme("bu-pu", 3, Interpolations.LINEAR), "continuous-bu-pu-3");
        export(getContinuousColorScheme("bu-pu", 4, Interpolations.LINEAR), "continuous-bu-pu-4");
        export(getContinuousColorScheme("bu-pu", 5, Interpolations.LINEAR), "continuous-bu-pu-5");
        export(getContinuousColorScheme("bu-pu", 6, Interpolations.LINEAR), "continuous-bu-pu-6");
        export(getContinuousColorScheme("bu-pu", 7, Interpolations.LINEAR), "continuous-bu-pu-7");
        export(getContinuousColorScheme("bu-pu", 8, Interpolations.LINEAR), "continuous-bu-pu-8");
        export(getContinuousColorScheme("bu-pu", 9, Interpolations.LINEAR), "continuous-bu-pu-9");
        export(getContinuousColorScheme("gn-bu", 3, Interpolations.LINEAR), "continuous-gn-bu-3");
        export(getContinuousColorScheme("gn-bu", 4, Interpolations.LINEAR), "continuous-gn-bu-4");
        export(getContinuousColorScheme("gn-bu", 5, Interpolations.LINEAR), "continuous-gn-bu-5");
        export(getContinuousColorScheme("gn-bu", 6, Interpolations.LINEAR), "continuous-gn-bu-6");
        export(getContinuousColorScheme("gn-bu", 7, Interpolations.LINEAR), "continuous-gn-bu-7");
        export(getContinuousColorScheme("gn-bu", 8, Interpolations.LINEAR), "continuous-gn-bu-8");
        export(getContinuousColorScheme("gn-bu", 9, Interpolations.LINEAR), "continuous-gn-bu-9");
        export(getContinuousColorScheme("greens", 3, Interpolations.LINEAR), "continuous-greens-3");
        export(getContinuousColorScheme("greens", 4, Interpolations.LINEAR), "continuous-greens-4");
        export(getContinuousColorScheme("greens", 5, Interpolations.LINEAR), "continuous-greens-5");
        export(getContinuousColorScheme("greens", 6, Interpolations.LINEAR), "continuous-greens-6");
        export(getContinuousColorScheme("greens", 7, Interpolations.LINEAR), "continuous-greens-7");
        export(getContinuousColorScheme("greens", 8, Interpolations.LINEAR), "continuous-greens-8");
        export(getContinuousColorScheme("greens", 9, Interpolations.LINEAR), "continuous-greens-9");
        export(getContinuousColorScheme("greys", 3, Interpolations.LINEAR), "continuous-greys-3");
        export(getContinuousColorScheme("greys", 4, Interpolations.LINEAR), "continuous-greys-4");
        export(getContinuousColorScheme("greys", 5, Interpolations.LINEAR), "continuous-greys-5");
        export(getContinuousColorScheme("greys", 6, Interpolations.LINEAR), "continuous-greys-6");
        export(getContinuousColorScheme("greys", 7, Interpolations.LINEAR), "continuous-greys-7");
        export(getContinuousColorScheme("greys", 8, Interpolations.LINEAR), "continuous-greys-8");
        export(getContinuousColorScheme("greys", 9, Interpolations.LINEAR), "continuous-greys-9");
        export(getContinuousColorScheme("or-rd", 3, Interpolations.LINEAR), "continuous-or-rd-3");
        export(getContinuousColorScheme("or-rd", 4, Interpolations.LINEAR), "continuous-or-rd-4");
        export(getContinuousColorScheme("or-rd", 5, Interpolations.LINEAR), "continuous-or-rd-5");
        export(getContinuousColorScheme("or-rd", 6, Interpolations.LINEAR), "continuous-or-rd-6");
        export(getContinuousColorScheme("or-rd", 7, Interpolations.LINEAR), "continuous-or-rd-7");
        export(getContinuousColorScheme("or-rd", 8, Interpolations.LINEAR), "continuous-or-rd-8");
        export(getContinuousColorScheme("or-rd", 9, Interpolations.LINEAR), "continuous-or-rd-9");
        export(getContinuousColorScheme("oranges", 3, Interpolations.LINEAR), "continuous-oranges-3");
        export(getContinuousColorScheme("oranges", 4, Interpolations.LINEAR), "continuous-oranges-4");
        export(getContinuousColorScheme("oranges", 5, Interpolations.LINEAR), "continuous-oranges-5");
        export(getContinuousColorScheme("oranges", 6, Interpolations.LINEAR), "continuous-oranges-6");
        export(getContinuousColorScheme("oranges", 7, Interpolations.LINEAR), "continuous-oranges-7");
        export(getContinuousColorScheme("oranges", 8, Interpolations.LINEAR), "continuous-oranges-8");
        export(getContinuousColorScheme("oranges", 9, Interpolations.LINEAR), "continuous-oranges-9");
        export(getContinuousColorScheme("p-r-gn", 10, Interpolations.LINEAR), "continuous-p-r-gn-10");
        export(getContinuousColorScheme("p-r-gn", 11, Interpolations.LINEAR), "continuous-p-r-gn-11");
        export(getContinuousColorScheme("p-r-gn", 3, Interpolations.LINEAR), "continuous-p-r-gn-3");
        export(getContinuousColorScheme("p-r-gn", 4, Interpolations.LINEAR), "continuous-p-r-gn-4");
        export(getContinuousColorScheme("p-r-gn", 5, Interpolations.LINEAR), "continuous-p-r-gn-5");
        export(getContinuousColorScheme("p-r-gn", 6, Interpolations.LINEAR), "continuous-p-r-gn-6");
        export(getContinuousColorScheme("p-r-gn", 7, Interpolations.LINEAR), "continuous-p-r-gn-7");
        export(getContinuousColorScheme("p-r-gn", 8, Interpolations.LINEAR), "continuous-p-r-gn-8");
        export(getContinuousColorScheme("p-r-gn", 9, Interpolations.LINEAR), "continuous-p-r-gn-9");
        export(getContinuousColorScheme("paired", 10, Interpolations.LINEAR), "continuous-paired-10");
        export(getContinuousColorScheme("paired", 11, Interpolations.LINEAR), "continuous-paired-11");
        export(getContinuousColorScheme("paired", 12, Interpolations.LINEAR), "continuous-paired-12");
        export(getContinuousColorScheme("paired", 3, Interpolations.LINEAR), "continuous-paired-3");
        export(getContinuousColorScheme("paired", 4, Interpolations.LINEAR), "continuous-paired-4");
        export(getContinuousColorScheme("paired", 5, Interpolations.LINEAR), "continuous-paired-5");
        export(getContinuousColorScheme("paired", 6, Interpolations.LINEAR), "continuous-paired-6");
        export(getContinuousColorScheme("paired", 7, Interpolations.LINEAR), "continuous-paired-7");
        export(getContinuousColorScheme("paired", 8, Interpolations.LINEAR), "continuous-paired-8");
        export(getContinuousColorScheme("paired", 9, Interpolations.LINEAR), "continuous-paired-9");
        export(getContinuousColorScheme("pi-y-g", 10, Interpolations.LINEAR), "continuous-pi-y-g-10");
        export(getContinuousColorScheme("pi-y-g", 11, Interpolations.LINEAR), "continuous-pi-y-g-11");
        export(getContinuousColorScheme("pi-y-g", 3, Interpolations.LINEAR), "continuous-pi-y-g-3");
        export(getContinuousColorScheme("pi-y-g", 4, Interpolations.LINEAR), "continuous-pi-y-g-4");
        export(getContinuousColorScheme("pi-y-g", 5, Interpolations.LINEAR), "continuous-pi-y-g-5");
        export(getContinuousColorScheme("pi-y-g", 6, Interpolations.LINEAR), "continuous-pi-y-g-6");
        export(getContinuousColorScheme("pi-y-g", 7, Interpolations.LINEAR), "continuous-pi-y-g-7");
        export(getContinuousColorScheme("pi-y-g", 8, Interpolations.LINEAR), "continuous-pi-y-g-8");
        export(getContinuousColorScheme("pi-y-g", 9, Interpolations.LINEAR), "continuous-pi-y-g-9");
        export(getContinuousColorScheme("pu-bu", 3, Interpolations.LINEAR), "continuous-pu-bu-3");
        export(getContinuousColorScheme("pu-bu", 4, Interpolations.LINEAR), "continuous-pu-bu-4");
        export(getContinuousColorScheme("pu-bu", 5, Interpolations.LINEAR), "continuous-pu-bu-5");
        export(getContinuousColorScheme("pu-bu", 6, Interpolations.LINEAR), "continuous-pu-bu-6");
        export(getContinuousColorScheme("pu-bu", 7, Interpolations.LINEAR), "continuous-pu-bu-7");
        export(getContinuousColorScheme("pu-bu", 8, Interpolations.LINEAR), "continuous-pu-bu-8");
        export(getContinuousColorScheme("pu-bu", 9, Interpolations.LINEAR), "continuous-pu-bu-9");
        export(getContinuousColorScheme("pu-bu-gn", 3, Interpolations.LINEAR), "continuous-pu-bu-gn-3");
        export(getContinuousColorScheme("pu-bu-gn", 4, Interpolations.LINEAR), "continuous-pu-bu-gn-4");
        export(getContinuousColorScheme("pu-bu-gn", 5, Interpolations.LINEAR), "continuous-pu-bu-gn-5");
        export(getContinuousColorScheme("pu-bu-gn", 6, Interpolations.LINEAR), "continuous-pu-bu-gn-6");
        export(getContinuousColorScheme("pu-bu-gn", 7, Interpolations.LINEAR), "continuous-pu-bu-gn-7");
        export(getContinuousColorScheme("pu-bu-gn", 8, Interpolations.LINEAR), "continuous-pu-bu-gn-8");
        export(getContinuousColorScheme("pu-bu-gn", 9, Interpolations.LINEAR), "continuous-pu-bu-gn-9");
        export(getContinuousColorScheme("pu-or", 10, Interpolations.LINEAR), "continuous-pu-or-10");
        export(getContinuousColorScheme("pu-or", 11, Interpolations.LINEAR), "continuous-pu-or-11");
        export(getContinuousColorScheme("pu-or", 3, Interpolations.LINEAR), "continuous-pu-or-3");
        export(getContinuousColorScheme("pu-or", 4, Interpolations.LINEAR), "continuous-pu-or-4");
        export(getContinuousColorScheme("pu-or", 5, Interpolations.LINEAR), "continuous-pu-or-5");
        export(getContinuousColorScheme("pu-or", 6, Interpolations.LINEAR), "continuous-pu-or-6");
        export(getContinuousColorScheme("pu-or", 7, Interpolations.LINEAR), "continuous-pu-or-7");
        export(getContinuousColorScheme("pu-or", 8, Interpolations.LINEAR), "continuous-pu-or-8");
        export(getContinuousColorScheme("pu-or", 9, Interpolations.LINEAR), "continuous-pu-or-9");
        export(getContinuousColorScheme("pu-rd", 3, Interpolations.LINEAR), "continuous-pu-rd-3");
        export(getContinuousColorScheme("pu-rd", 4, Interpolations.LINEAR), "continuous-pu-rd-4");
        export(getContinuousColorScheme("pu-rd", 5, Interpolations.LINEAR), "continuous-pu-rd-5");
        export(getContinuousColorScheme("pu-rd", 6, Interpolations.LINEAR), "continuous-pu-rd-6");
        export(getContinuousColorScheme("pu-rd", 7, Interpolations.LINEAR), "continuous-pu-rd-7");
        export(getContinuousColorScheme("pu-rd", 8, Interpolations.LINEAR), "continuous-pu-rd-8");
        export(getContinuousColorScheme("pu-rd", 9, Interpolations.LINEAR), "continuous-pu-rd-9");
        export(getContinuousColorScheme("purples", 3, Interpolations.LINEAR), "continuous-purples-3");
        export(getContinuousColorScheme("purples", 4, Interpolations.LINEAR), "continuous-purples-4");
        export(getContinuousColorScheme("purples", 5, Interpolations.LINEAR), "continuous-purples-5");
        export(getContinuousColorScheme("purples", 6, Interpolations.LINEAR), "continuous-purples-6");
        export(getContinuousColorScheme("purples", 7, Interpolations.LINEAR), "continuous-purples-7");
        export(getContinuousColorScheme("purples", 8, Interpolations.LINEAR), "continuous-purples-8");
        export(getContinuousColorScheme("purples", 9, Interpolations.LINEAR), "continuous-purples-9");
        export(getContinuousColorScheme("rd-bu", 10, Interpolations.LINEAR), "continuous-rd-bu-10");
        export(getContinuousColorScheme("rd-bu", 11, Interpolations.LINEAR), "continuous-rd-bu-11");
        export(getContinuousColorScheme("rd-bu", 3, Interpolations.LINEAR), "continuous-rd-bu-3");
        export(getContinuousColorScheme("rd-bu", 4, Interpolations.LINEAR), "continuous-rd-bu-4");
        export(getContinuousColorScheme("rd-bu", 5, Interpolations.LINEAR), "continuous-rd-bu-5");
        export(getContinuousColorScheme("rd-bu", 6, Interpolations.LINEAR), "continuous-rd-bu-6");
        export(getContinuousColorScheme("rd-bu", 7, Interpolations.LINEAR), "continuous-rd-bu-7");
        export(getContinuousColorScheme("rd-bu", 8, Interpolations.LINEAR), "continuous-rd-bu-8");
        export(getContinuousColorScheme("rd-bu", 9, Interpolations.LINEAR), "continuous-rd-bu-9");
        export(getContinuousColorScheme("rd-gy", 10, Interpolations.LINEAR), "continuous-rd-gy-10");
        export(getContinuousColorScheme("rd-gy", 11, Interpolations.LINEAR), "continuous-rd-gy-11");
        export(getContinuousColorScheme("rd-gy", 3, Interpolations.LINEAR), "continuous-rd-gy-3");
        export(getContinuousColorScheme("rd-gy", 4, Interpolations.LINEAR), "continuous-rd-gy-4");
        export(getContinuousColorScheme("rd-gy", 5, Interpolations.LINEAR), "continuous-rd-gy-5");
        export(getContinuousColorScheme("rd-gy", 6, Interpolations.LINEAR), "continuous-rd-gy-6");
        export(getContinuousColorScheme("rd-gy", 7, Interpolations.LINEAR), "continuous-rd-gy-7");
        export(getContinuousColorScheme("rd-gy", 8, Interpolations.LINEAR), "continuous-rd-gy-8");
        export(getContinuousColorScheme("rd-gy", 9, Interpolations.LINEAR), "continuous-rd-gy-9");
        export(getContinuousColorScheme("rd-pu", 3, Interpolations.LINEAR), "continuous-rd-pu-3");
        export(getContinuousColorScheme("rd-pu", 4, Interpolations.LINEAR), "continuous-rd-pu-4");
        export(getContinuousColorScheme("rd-pu", 5, Interpolations.LINEAR), "continuous-rd-pu-5");
        export(getContinuousColorScheme("rd-pu", 6, Interpolations.LINEAR), "continuous-rd-pu-6");
        export(getContinuousColorScheme("rd-pu", 7, Interpolations.LINEAR), "continuous-rd-pu-7");
        export(getContinuousColorScheme("rd-pu", 8, Interpolations.LINEAR), "continuous-rd-pu-8");
        export(getContinuousColorScheme("rd-pu", 9, Interpolations.LINEAR), "continuous-rd-pu-9");
        export(getContinuousColorScheme("rd-yl-bu", 10, Interpolations.LINEAR), "continuous-rd-yl-bu-10");
        export(getContinuousColorScheme("rd-yl-bu", 11, Interpolations.LINEAR), "continuous-rd-yl-bu-11");
        export(getContinuousColorScheme("rd-yl-bu", 3, Interpolations.LINEAR), "continuous-rd-yl-bu-3");
        export(getContinuousColorScheme("rd-yl-bu", 4, Interpolations.LINEAR), "continuous-rd-yl-bu-4");
        export(getContinuousColorScheme("rd-yl-bu", 5, Interpolations.LINEAR), "continuous-rd-yl-bu-5");
        export(getContinuousColorScheme("rd-yl-bu", 6, Interpolations.LINEAR), "continuous-rd-yl-bu-6");
        export(getContinuousColorScheme("rd-yl-bu", 7, Interpolations.LINEAR), "continuous-rd-yl-bu-7");
        export(getContinuousColorScheme("rd-yl-bu", 8, Interpolations.LINEAR), "continuous-rd-yl-bu-8");
        export(getContinuousColorScheme("rd-yl-bu", 9, Interpolations.LINEAR), "continuous-rd-yl-bu-9");
        export(getContinuousColorScheme("rd-yl-gn", 10, Interpolations.LINEAR), "continuous-rd-yl-gn-10");
        export(getContinuousColorScheme("rd-yl-gn", 11, Interpolations.LINEAR), "continuous-rd-yl-gn-11");
        export(getContinuousColorScheme("rd-yl-gn", 3, Interpolations.LINEAR), "continuous-rd-yl-gn-3");
        export(getContinuousColorScheme("rd-yl-gn", 4, Interpolations.LINEAR), "continuous-rd-yl-gn-4");
        export(getContinuousColorScheme("rd-yl-gn", 5, Interpolations.LINEAR), "continuous-rd-yl-gn-5");
        export(getContinuousColorScheme("rd-yl-gn", 6, Interpolations.LINEAR), "continuous-rd-yl-gn-6");
        export(getContinuousColorScheme("rd-yl-gn", 7, Interpolations.LINEAR), "continuous-rd-yl-gn-7");
        export(getContinuousColorScheme("rd-yl-gn", 8, Interpolations.LINEAR), "continuous-rd-yl-gn-8");
        export(getContinuousColorScheme("rd-yl-gn", 9, Interpolations.LINEAR), "continuous-rd-yl-gn-9");
        export(getContinuousColorScheme("reds", 3, Interpolations.LINEAR), "continuous-reds-3");
        export(getContinuousColorScheme("reds", 4, Interpolations.LINEAR), "continuous-reds-4");
        export(getContinuousColorScheme("reds", 5, Interpolations.LINEAR), "continuous-reds-5");
        export(getContinuousColorScheme("reds", 6, Interpolations.LINEAR), "continuous-reds-6");
        export(getContinuousColorScheme("reds", 7, Interpolations.LINEAR), "continuous-reds-7");
        export(getContinuousColorScheme("reds", 8, Interpolations.LINEAR), "continuous-reds-8");
        export(getContinuousColorScheme("reds", 9, Interpolations.LINEAR), "continuous-reds-9");
        export(getContinuousColorScheme("spectral", 10, Interpolations.LINEAR), "continuous-spectral-10");
        export(getContinuousColorScheme("spectral", 11, Interpolations.LINEAR), "continuous-spectral-11");
        export(getContinuousColorScheme("spectral", 3, Interpolations.LINEAR), "continuous-spectral-3");
        export(getContinuousColorScheme("spectral", 4, Interpolations.LINEAR), "continuous-spectral-4");
        export(getContinuousColorScheme("spectral", 5, Interpolations.LINEAR), "continuous-spectral-5");
        export(getContinuousColorScheme("spectral", 6, Interpolations.LINEAR), "continuous-spectral-6");
        export(getContinuousColorScheme("spectral", 7, Interpolations.LINEAR), "continuous-spectral-7");
        export(getContinuousColorScheme("spectral", 8, Interpolations.LINEAR), "continuous-spectral-8");
        export(getContinuousColorScheme("spectral", 9, Interpolations.LINEAR), "continuous-spectral-9");
        export(getContinuousColorScheme("wh-bl", 2, Interpolations.LINEAR), "continuous-wh-bl-2");
        export(getContinuousColorScheme("yl-gn", 3, Interpolations.LINEAR), "continuous-yl-gn-3");
        export(getContinuousColorScheme("yl-gn", 4, Interpolations.LINEAR), "continuous-yl-gn-4");
        export(getContinuousColorScheme("yl-gn", 5, Interpolations.LINEAR), "continuous-yl-gn-5");
        export(getContinuousColorScheme("yl-gn", 6, Interpolations.LINEAR), "continuous-yl-gn-6");
        export(getContinuousColorScheme("yl-gn", 7, Interpolations.LINEAR), "continuous-yl-gn-7");
        export(getContinuousColorScheme("yl-gn", 8, Interpolations.LINEAR), "continuous-yl-gn-8");
        export(getContinuousColorScheme("yl-gn", 9, Interpolations.LINEAR), "continuous-yl-gn-9");
        export(getContinuousColorScheme("yl-gn-bu", 3, Interpolations.LINEAR), "continuous-yl-gn-bu-3");
        export(getContinuousColorScheme("yl-gn-bu", 4, Interpolations.LINEAR), "continuous-yl-gn-bu-4");
        export(getContinuousColorScheme("yl-gn-bu", 5, Interpolations.LINEAR), "continuous-yl-gn-bu-5");
        export(getContinuousColorScheme("yl-gn-bu", 6, Interpolations.LINEAR), "continuous-yl-gn-bu-6");
        export(getContinuousColorScheme("yl-gn-bu", 7, Interpolations.LINEAR), "continuous-yl-gn-bu-7");
        export(getContinuousColorScheme("yl-gn-bu", 8, Interpolations.LINEAR), "continuous-yl-gn-bu-8");
        export(getContinuousColorScheme("yl-gn-bu", 9, Interpolations.LINEAR), "continuous-yl-gn-bu-9");
        export(getContinuousColorScheme("yl-or-br", 3, Interpolations.LINEAR), "continuous-yl-or-br-3");
        export(getContinuousColorScheme("yl-or-br", 4, Interpolations.LINEAR), "continuous-yl-or-br-4");
        export(getContinuousColorScheme("yl-or-br", 5, Interpolations.LINEAR), "continuous-yl-or-br-5");
        export(getContinuousColorScheme("yl-or-br", 6, Interpolations.LINEAR), "continuous-yl-or-br-6");
        export(getContinuousColorScheme("yl-or-br", 7, Interpolations.LINEAR), "continuous-yl-or-br-7");
        export(getContinuousColorScheme("yl-or-br", 8, Interpolations.LINEAR), "continuous-yl-or-br-8");
        export(getContinuousColorScheme("yl-or-br", 9, Interpolations.LINEAR), "continuous-yl-or-br-9");
        export(getContinuousColorScheme("yl-or-rd", 3, Interpolations.LINEAR), "continuous-yl-or-rd-3");
        export(getContinuousColorScheme("yl-or-rd", 4, Interpolations.LINEAR), "continuous-yl-or-rd-4");
        export(getContinuousColorScheme("yl-or-rd", 5, Interpolations.LINEAR), "continuous-yl-or-rd-5");
        export(getContinuousColorScheme("yl-or-rd", 6, Interpolations.LINEAR), "continuous-yl-or-rd-6");
        export(getContinuousColorScheme("yl-or-rd", 7, Interpolations.LINEAR), "continuous-yl-or-rd-7");
        export(getContinuousColorScheme("yl-or-rd", 8, Interpolations.LINEAR), "continuous-yl-or-rd-8");
        export(getContinuousColorScheme("yl-or-rd", 9, Interpolations.LINEAR), "continuous-yl-or-rd-9");

        export(getContinuousColorScheme("set-1", 3, Interpolations.LINEAR), "continuous-set-1-3");
        export(getContinuousColorScheme("set-1", 4, Interpolations.LINEAR), "continuous-set-1-4");
        export(getContinuousColorScheme("set-1", 5, Interpolations.LINEAR), "continuous-set-1-5");
        export(getContinuousColorScheme("set-1", 6, Interpolations.LINEAR), "continuous-set-1-6");
        export(getContinuousColorScheme("set-1", 7, Interpolations.LINEAR), "continuous-set-1-7");
        export(getContinuousColorScheme("set-1", 8, Interpolations.LINEAR), "continuous-set-1-8");
        export(getContinuousColorScheme("set-1", 9, Interpolations.LINEAR), "continuous-set-1-9");

        export(getContinuousColorScheme("set-2", 3, Interpolations.LINEAR), "continuous-set-2-3");
        export(getContinuousColorScheme("set-2", 4, Interpolations.LINEAR), "continuous-set-2-4");
        export(getContinuousColorScheme("set-2", 5, Interpolations.LINEAR), "continuous-set-2-5");
        export(getContinuousColorScheme("set-2", 6, Interpolations.LINEAR), "continuous-set-2-6");
        export(getContinuousColorScheme("set-2", 7, Interpolations.LINEAR), "continuous-set-2-7");
        export(getContinuousColorScheme("set-2", 8, Interpolations.LINEAR), "continuous-set-2-8");

        export(getContinuousColorScheme("set-3", 3, Interpolations.LINEAR), "continuous-set-3-3");
        export(getContinuousColorScheme("set-3", 4, Interpolations.LINEAR), "continuous-set-3-4");
        export(getContinuousColorScheme("set-3", 5, Interpolations.LINEAR), "continuous-set-3-5");
        export(getContinuousColorScheme("set-3", 6, Interpolations.LINEAR), "continuous-set-3-6");
        export(getContinuousColorScheme("set-3", 7, Interpolations.LINEAR), "continuous-set-3-7");
        export(getContinuousColorScheme("set-3", 8, Interpolations.LINEAR), "continuous-set-3-8");
        export(getContinuousColorScheme("set-3", 9, Interpolations.LINEAR), "continuous-set-3-9");
        export(getContinuousColorScheme("set-3", 10, Interpolations.LINEAR), "continuous-set-3-10");
        export(getContinuousColorScheme("set-3", 11, Interpolations.LINEAR), "continuous-set-3-11");
        export(getContinuousColorScheme("set-3", 12, Interpolations.LINEAR), "continuous-set-3-12");

        export(getContinuousColorScheme("bu-wh-rd", 3, Interpolations.LINEAR), "continuous-bu-wh-rd-3");
        export(getContinuousColorScheme("bu-wh-rd", 7, Interpolations.LINEAR), "continuous-bu-wh-rd-7");

        export(getContinuousColorScheme("dark-2", 3, Interpolations.LINEAR), "continuous-dark-2-3");
        export(getContinuousColorScheme("dark-2", 4, Interpolations.LINEAR), "continuous-dark-2-4");
        export(getContinuousColorScheme("dark-2", 5, Interpolations.LINEAR), "continuous-dark-2-5");
        export(getContinuousColorScheme("dark-2", 6, Interpolations.LINEAR), "continuous-dark-2-6");
        export(getContinuousColorScheme("dark-2", 7, Interpolations.LINEAR), "continuous-dark-2-7");
        export(getContinuousColorScheme("dark-2", 8, Interpolations.LINEAR), "continuous-dark-2-8");

        export(getContinuousColorScheme("pastel-1", 3, Interpolations.LINEAR), "continuous-pastel-1-3");
        export(getContinuousColorScheme("pastel-1", 4, Interpolations.LINEAR), "continuous-pastel-1-4");
        export(getContinuousColorScheme("pastel-1", 5, Interpolations.LINEAR), "continuous-pastel-1-5");
        export(getContinuousColorScheme("pastel-1", 6, Interpolations.LINEAR), "continuous-pastel-1-6");
        export(getContinuousColorScheme("pastel-1", 7, Interpolations.LINEAR), "continuous-pastel-1-7");
        export(getContinuousColorScheme("pastel-1", 8, Interpolations.LINEAR), "continuous-pastel-1-8");
        export(getContinuousColorScheme("pastel-1", 9, Interpolations.LINEAR), "continuous-pastel-1-9");

        export(getContinuousColorScheme("pastel-2", 3, Interpolations.LINEAR), "continuous-pastel-2-3");
        export(getContinuousColorScheme("pastel-2", 4, Interpolations.LINEAR), "continuous-pastel-2-4");
        export(getContinuousColorScheme("pastel-2", 5, Interpolations.LINEAR), "continuous-pastel-2-5");
        export(getContinuousColorScheme("pastel-2", 6, Interpolations.LINEAR), "continuous-pastel-2-6");
        export(getContinuousColorScheme("pastel-2", 7, Interpolations.LINEAR), "continuous-pastel-2-7");
        export(getContinuousColorScheme("pastel-2", 8, Interpolations.LINEAR), "continuous-pastel-2-8");

        export(getContinuousColorScheme("blues", 4, Interpolations.LINEAR), "continuous-blues-4-linear");
        export(getContinuousColorScheme("blues", 4, Interpolations.LOG_2), "continuous-blues-4-log-2");
        export(getContinuousColorScheme("blues", 4, Interpolations.LOG_10), "continuous-blues-4-log-10");

        export(getDiscreteColorScheme("pi-gy", 2, Interpolations.LINEAR), "discrete-pi-gy-2");
        export(getDiscreteColorScheme("gn-gy", 2, Interpolations.LINEAR), "discrete-gn-gy-2");
        export(getDiscreteColorScheme("pu-bu-gy", 3, Interpolations.LINEAR), "discrete-pu-bu-gy-3");

        export(getDiscreteColorScheme("syn", 3, Interpolations.LINEAR), "discrete-syn-3");
        export(getDiscreteColorScheme("syn", 4, Interpolations.LINEAR), "discrete-syn-4");
        export(getDiscreteColorScheme("syn", 5, Interpolations.LINEAR), "discrete-syn-5");
        export(getDiscreteColorScheme("syn", 6, Interpolations.LINEAR), "discrete-syn-6");

        export(getContinuousColorScheme("syn", 3, Interpolations.LINEAR), "continuous-syn-3");
        export(getContinuousColorScheme("syn", 4, Interpolations.LINEAR), "continuous-syn-4");
        export(getContinuousColorScheme("syn", 5, Interpolations.LINEAR), "continuous-syn-5");
        export(getContinuousColorScheme("syn", 6, Interpolations.LINEAR), "continuous-syn-6");

        export(getContinuousColorScheme("pi-gy", 2, Interpolations.LINEAR), "continuous-pi-gy-2");
        export(getContinuousColorScheme("gn-gy", 2, Interpolations.LINEAR), "continuous-gn-gy-2");
        export(getContinuousColorScheme("pu-bu-gy", 3, Interpolations.LINEAR), "continuous-pu-bu-gy-3");

        export(getContinuousColorScheme("bu-rd", 2, Interpolations.LINEAR), "continuous-bu-rd-2");
        export(getContinuousColorScheme("steel", 3, Interpolations.LINEAR), "continuous-steel-3");
        export(getDiscreteColorScheme("bu-rd", 2, Interpolations.LINEAR), "discrete-bu-rd-2");
        export(getDiscreteColorScheme("steel", 3, Interpolations.LINEAR), "discrete-steel-3");

        export(getContinuousColorScheme("rubus", 9, Interpolations.LINEAR), "continuous-rubus-9");
        export(getDiscreteColorScheme("rubus", 9, Interpolations.LINEAR), "discrete-rubus-9");

        export(getContinuousColorScheme("gw", 2, Interpolations.LINEAR), "continuous-gw-2");
        export(getDiscreteColorScheme("gw", 2, Interpolations.LINEAR), "discrete-gw-2");
        export(getContinuousColorScheme("gw", 3, Interpolations.LINEAR), "continuous-gw-3");
        export(getDiscreteColorScheme("gw", 3, Interpolations.LINEAR), "discrete-gw-3");
        export(getContinuousColorScheme("gw", 4, Interpolations.LINEAR), "continuous-gw-4");
        export(getDiscreteColorScheme("gw", 4, Interpolations.LINEAR), "discrete-gw-4");

        export(getContinuousColorScheme("gut", 3, Interpolations.LINEAR), "continuous-gut-3");
        export(getDiscreteColorScheme("gut", 3, Interpolations.LINEAR), "discrete-gut-3");
        export(getContinuousColorScheme("gut", 4, Interpolations.LINEAR), "continuous-gut-4");
        export(getDiscreteColorScheme("gut", 4, Interpolations.LINEAR), "discrete-gut-4");
        export(getContinuousColorScheme("gut", 5, Interpolations.LINEAR), "continuous-gut-5");
        export(getDiscreteColorScheme("gut", 5, Interpolations.LINEAR), "discrete-gut-5");
        export(getContinuousColorScheme("gut", 6, Interpolations.LINEAR), "continuous-gut-6");
        export(getDiscreteColorScheme("gut", 6, Interpolations.LINEAR), "discrete-gut-6");
        export(getContinuousColorScheme("gut", 7, Interpolations.LINEAR), "continuous-gut-7");
        export(getDiscreteColorScheme("gut", 7, Interpolations.LINEAR), "discrete-gut-7");
        export(getContinuousColorScheme("gut", 8, Interpolations.LINEAR), "continuous-gut-8");
        export(getDiscreteColorScheme("gut", 8, Interpolations.LINEAR), "discrete-gut-8");
        export(getContinuousColorScheme("gut", 9, Interpolations.LINEAR), "continuous-gut-9");
        export(getDiscreteColorScheme("gut", 9, Interpolations.LINEAR), "discrete-gut-9");
        export(getContinuousColorScheme("gut", 10, Interpolations.LINEAR), "continuous-gut-10");
        export(getDiscreteColorScheme("gut", 10, Interpolations.LINEAR), "discrete-gut-10");

        export(getContinuousColorScheme("enceladus", 3, Interpolations.LINEAR), "continuous-enceladus-3");
        export(getDiscreteColorScheme("enceladus", 3, Interpolations.LINEAR), "discrete-enceladus-3");
        export(getContinuousColorScheme("europa", 3, Interpolations.LINEAR), "continuous-europa-3");
        export(getDiscreteColorScheme("europa", 3, Interpolations.LINEAR), "discrete-europa-3");

        export(getContinuousColorScheme("earth", 2), "continuous-earth-2");
        export(getDiscreteColorScheme("earth", 2), "discrete-earth-2");
        export(getContinuousColorScheme("earth", 3), "continuous-earth-3");
        export(getDiscreteColorScheme("earth", 3), "discrete-earth-3");
        export(getContinuousColorScheme("earth", 4), "continuous-earth-4");
        export(getDiscreteColorScheme("earth", 4), "discrete-earth-4");

        export(getDiscreteColorScheme("unambiguous", 4), "discrete-unambiguous-4");
        export(getDiscreteColorScheme("unambiguous", 5), "discrete-unambiguous-5");
        export(getDiscreteColorScheme("unambiguous", 6), "discrete-unambiguous-6");
        export(getDiscreteColorScheme("unambiguous", 7), "discrete-unambiguous-7");
        export(getDiscreteColorScheme("unambiguous", 8), "discrete-unambiguous-8");

        export(getDiscreteColorScheme("tableau", 3), "discrete-tableau-3");
        export(getDiscreteColorScheme("tableau", 4), "discrete-tableau-4");
        export(getDiscreteColorScheme("tableau", 5), "discrete-tableau-5");
        export(getDiscreteColorScheme("tableau", 6), "discrete-tableau-6");
        export(getDiscreteColorScheme("tableau", 7), "discrete-tableau-7");
        export(getDiscreteColorScheme("tableau", 8), "discrete-tableau-8");
        export(getDiscreteColorScheme("tableau", 9), "discrete-tableau-9");
        export(getDiscreteColorScheme("tableau", 10), "discrete-tableau-10");

        export(getDiscreteColorScheme("category", 3), "discrete-category-3");
        export(getDiscreteColorScheme("category", 4), "discrete-category-4");
        export(getDiscreteColorScheme("category", 5), "discrete-category-5");
        export(getDiscreteColorScheme("category", 6), "discrete-category-6");
        export(getDiscreteColorScheme("category", 7), "discrete-category-7");
        export(getDiscreteColorScheme("category", 8), "discrete-category-8");
        export(getDiscreteColorScheme("category", 9), "discrete-category-9");
        export(getDiscreteColorScheme("category", 10), "discrete-category-10");

        export(getDiscreteColorScheme("aaas", 3), "discrete-aaas-3");
        export(getDiscreteColorScheme("aaas", 4), "discrete-aaas-4");
        export(getDiscreteColorScheme("aaas", 5), "discrete-aaas-5");
        export(getDiscreteColorScheme("aaas", 6), "discrete-aaas-6");
        export(getDiscreteColorScheme("aaas", 7), "discrete-aaas-7");
        export(getDiscreteColorScheme("aaas", 8), "discrete-aaas-8");
        export(getDiscreteColorScheme("aaas", 9), "discrete-aaas-9");
        export(getDiscreteColorScheme("aaas", 10), "discrete-aaas-10");

        export(getDiscreteColorScheme("npg", 3), "discrete-npg-3");
        export(getDiscreteColorScheme("npg", 4), "discrete-npg-4");
        export(getDiscreteColorScheme("npg", 5), "discrete-npg-5");
        export(getDiscreteColorScheme("npg", 6), "discrete-npg-6");
        export(getDiscreteColorScheme("npg", 7), "discrete-npg-7");
        export(getDiscreteColorScheme("npg", 8), "discrete-npg-8");
        export(getDiscreteColorScheme("npg", 9), "discrete-npg-9");
        export(getDiscreteColorScheme("npg", 10), "discrete-npg-10");

        export(getDiscreteColorScheme("nejm", 3), "discrete-nejm-3");
        export(getDiscreteColorScheme("nejm", 4), "discrete-nejm-4");
        export(getDiscreteColorScheme("nejm", 5), "discrete-nejm-5");
        export(getDiscreteColorScheme("nejm", 6), "discrete-nejm-6");
        export(getDiscreteColorScheme("nejm", 7), "discrete-nejm-7");
        export(getDiscreteColorScheme("nejm", 8), "discrete-nejm-8");

        export(getDiscreteColorScheme("lancet", 3), "discrete-lancet-3");
        export(getDiscreteColorScheme("lancet", 4), "discrete-lancet-4");
        export(getDiscreteColorScheme("lancet", 5), "discrete-lancet-5");
        export(getDiscreteColorScheme("lancet", 6), "discrete-lancet-6");
        export(getDiscreteColorScheme("lancet", 7), "discrete-lancet-7");
        export(getDiscreteColorScheme("lancet", 8), "discrete-lancet-8");
        export(getDiscreteColorScheme("lancet", 9), "discrete-lancet-9");

        export(getDiscreteColorScheme("jama", 3), "discrete-jama-3");
        export(getDiscreteColorScheme("jama", 4), "discrete-jama-4");
        export(getDiscreteColorScheme("jama", 5), "discrete-jama-5");
        export(getDiscreteColorScheme("jama", 6), "discrete-jama-6");
        export(getDiscreteColorScheme("jama", 7), "discrete-jama-7");

        export(getDiscreteColorScheme("jco", 3), "discrete-jco-3");
        export(getDiscreteColorScheme("jco", 4), "discrete-jco-4");
        export(getDiscreteColorScheme("jco", 5), "discrete-jco-5");
        export(getDiscreteColorScheme("jco", 6), "discrete-jco-6");
        export(getDiscreteColorScheme("jco", 7), "discrete-jco-7");
        export(getDiscreteColorScheme("jco", 8), "discrete-jco-8");
        export(getDiscreteColorScheme("jco", 9), "discrete-jco-9");
        export(getDiscreteColorScheme("jco", 10), "discrete-jco-10");

        export(getContinuousColorScheme("inferno", 256), "continuous-inferno-256");
        export(getContinuousColorScheme("magma", 256), "continuous-magma-256");
        export(getContinuousColorScheme("plasma", 256), "continuous-plasma-256");
        export(getContinuousColorScheme("viridis", 256), "continuous-viridis-256");

        export(getDiscreteColorScheme("inferno", 256), "discrete-inferno-256");
        export(getDiscreteColorScheme("magma", 256), "discrete-magma-256");
        export(getDiscreteColorScheme("plasma", 256), "discrete-plasma-256");
        export(getDiscreteColorScheme("viridis", 256), "discrete-viridis-256");
        */

        export(getDiscreteColorScheme("aaas", 2), "discrete-aaas-2");
        export(getDiscreteColorScheme("accent", 2), "discrete-accent-2");
        export(getDiscreteColorScheme("category", 2), "discrete-category-2");
        export(getDiscreteColorScheme("dark-2", 2), "discrete-dark-2-2");
        export(getDiscreteColorScheme("gut", 2), "discrete-gut-2");
        export(getDiscreteColorScheme("jama", 2), "discrete-jama-2");
        export(getDiscreteColorScheme("jco", 2), "discrete-jco-2");
        export(getDiscreteColorScheme("lancet", 2), "discrete-lancet-2");
        export(getDiscreteColorScheme("npg", 2), "discrete-npg-2");
        export(getDiscreteColorScheme("nejm", 2), "discrete-nejm-2");
        export(getDiscreteColorScheme("pastel-1", 2), "discrete-pastel-1-2");
        export(getDiscreteColorScheme("pastel-2", 2), "discrete-pastel-2-2");
        export(getDiscreteColorScheme("set-1", 2), "discrete-set-1-2");
        export(getDiscreteColorScheme("set-2", 2), "discrete-set-2-2");
        export(getDiscreteColorScheme("set-3", 2), "discrete-set-3-2");
        export(getDiscreteColorScheme("spectral", 2), "discrete-spectral-2");
        export(getDiscreteColorScheme("syn", 2), "discrete-syn-2");
        export(getDiscreteColorScheme("tableau", 2), "discrete-tableau-2");
        export(getDiscreteColorScheme("unambiguous", 2), "discrete-unambiguous-2");
        export(getDiscreteColorScheme("unambiguous", 3), "discrete-unambiguous-3");
    }

    private static final void export(final ColorScheme colorScheme, final String fileName)
    {
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(new FileOutputStream(new File(fileName + ".svg")), true);
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            writer.println("<svg xmlns:svg=\"http://www.w3.org/2000/svg\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.0\" width=\"810\" height=\"80\" id=\"svg2\">");
            writer.println("<defs id=\"defs4\"/><g id=\"layer1\">");
            int x = 5;
            for (double value = 0.0d; value <= 1.0d; value += 0.025d)
            {
                writer.print("<rect width=\"20\" height=\"60\" x=\"");
                writer.print(x);
                writer.print("\" y=\"5\" id=\"rect");
                writer.print(x);
                writer.print("\" style=\"fill:#");
                Color color = colorScheme.getColor(value);
                writer.print(toHex(color));
                writer.print(";fill-opacity:1;opacity:");
                writer.print(((float) color.getAlpha()) / 255.0f);
                writer.println(";stroke:none;stroke-width:1;stroke-linejoin:round;stroke-miterlimit:4;stroke-dasharray:none;stroke-opacity:1\"/>");
                x += 20;
            }
            writer.println("<text xml:space=\"preserve\" style=\"font-family:Calibri;font-size:12px;fill:#000000;stroke:none;fill-opacity:1\" x=\"12\" y=\"74\" id=\"text1\">");
            writer.println("<tspan id=\"tspan1\">" + fileName + "</tspan>");
            writer.println("</text>");
            writer.println("</g></svg>");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (Exception e)
            {
                // ignore
            }
        }
    }

    private static String toHex(final Color color)
    {
        return Integer.toHexString((color.getRGB() & 0xffffff) | 0x1000000).substring(1);
    }
}
