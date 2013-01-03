/*

    dsh-text-rendering-benchmark  Text rendering benchmarks.
    Copyright (c) 2009-2013 held jointly by the individual authors.

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
package org.dishevelled.text.runner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;

import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import javax.swing.border.EmptyBorder;

import org.dishevelled.layout.ButtonPanel;
import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.text.TextRenderingBenchmark;

import org.dishevelled.text.java2d.*;
import org.dishevelled.text.phtmlview.*;
import org.dishevelled.text.ptext.*;
import org.dishevelled.text.pswing.*;
import org.dishevelled.text.swing.*;

/**
 * Text rendering benchmarks.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class TextRenderingBenchmarks
    extends JPanel
    implements Runnable
{
    /** Exit action. */
    private final AbstractAction exitAction = new AbstractAction("Exit")
        {
            /** {@inheritDoc} */
            public void actionPerformed(final ActionEvent event)
            {
                exit();
            }
        };


    /**
     * Create a new text rendering benchmarks.
     */
    public TextRenderingBenchmarks()
    {
        super();
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(12, 12, 12, 12));
        setOpaque(true);
        setBackground(Color.WHITE);
        add("Center", createMainPanel());
        //add("South", createButtonPanel());
    }

    /**
     * Create and return the main panel.
     *
     * @return the main panel
     */
    private JPanel createMainPanel()
    {
        LabelFieldPanel panel = new LabelFieldPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        Dimension bounds = new Dimension(600, 22);

        List<Class<?>> classes = Arrays.asList(new Class<?>[]
                                               {
                                                   JLabelAntialiasingOff.class,
                                                   JLabelAntialiasingGasp.class,
                                                   JLabelAntialiasingLcdHrgb.class,
                                                   JLabelAntialiasingLcdHbgr.class,
                                                   JLabelAntialiasingLcdVrgb.class,
                                                   JLabelAntialiasingLcdVbgr.class,
                                                   PTextAntialiasingOff.class,
                                                   PTextAntialiasingGasp.class,
                                                   PTextAntialiasingLcdHrgb.class,
                                                   PTextAntialiasingLcdHbgr.class,
                                                   PTextAntialiasingLcdVrgb.class,
                                                   PTextAntialiasingLcdVbgr.class,
                                                   PSwingAntialiasingOff.class,
                                                   PSwingAntialiasingGasp.class,
                                                   PSwingAntialiasingLcdHrgb.class,
                                                   PSwingAntialiasingLcdHbgr.class,
                                                   PSwingAntialiasingLcdVrgb.class,
                                                   PSwingAntialiasingLcdVbgr.class,
                                                   JTextComponentAntialiasingOff.class,
                                                   JTextComponentAntialiasingGasp.class,
                                                   JTextComponentAntialiasingLcdHrgb.class,
                                                   JTextComponentAntialiasingLcdHbgr.class,
                                                   JTextComponentAntialiasingLcdVrgb.class,
                                                   JTextComponentAntialiasingLcdVbgr.class,
                                                   Java2DAntialiasingOff.class,
                                                   Java2DAntialiasingGasp.class,
                                                   Java2DAntialiasingLcdHrgb.class,
                                                   Java2DAntialiasingLcdHbgr.class,
                                                   Java2DAntialiasingLcdVrgb.class,
                                                   Java2DAntialiasingLcdVbgr.class,
                                                   PHtmlViewAntialiasingOff.class,
                                                   PHtmlViewAntialiasingGasp.class,
                                                   PHtmlViewAntialiasingLcdHrgb.class,
                                                   PHtmlViewAntialiasingLcdHbgr.class,
                                                   PHtmlViewAntialiasingLcdVrgb.class,
                                                   PHtmlViewAntialiasingLcdVbgr.class,
                                               });

        int i = 0;
        for (Class<?> c : classes)
        {
            TextRenderingBenchmark benchmark = null;
            try
            {
                benchmark = (TextRenderingBenchmark) c.newInstance();
            }
            catch (Exception e)
            {
                // ignore
            }
            benchmark.setFont(font);
            benchmark.setBounds(bounds);
            benchmark.setText(i + ". Lorem ipsum dolor sit amet, consectetur adipiscing elit posuere.");
            panel.addLabel(benchmark.getName() + ":");
            panel.addField(benchmark.getComponent());
            panel.addSpacing(8);
            i++;
        }
        panel.addFinalSpacing();
        return panel;
    }

    /**
     * Create and return the button panel.
     *
     * @return the button panel
     */
    private JPanel createButtonPanel()
    {
        ButtonPanel panel = new ButtonPanel();
        panel.add(new JButton(exitAction));
        return panel;
    }

    /** {@inheritDoc} */
    public void run()
    {
        JFrame frame = new JFrame("Text rendering benchmarks");
        frame.setContentPane(new JScrollPane(this));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 700, 500);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(exitAction);
        menuBar.add(fileMenu);
        //frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    /**
     * Exit.
     */
    private void exit()
    {
        System.exit(0);
    }


    /**
     * Main.
     *
     * @param args command line arguments, ignored
     */
    public static void main(final String[] args)
    {
        SwingUtilities.invokeLater(new TextRenderingBenchmarks());
    }
}