/*

    dsh-brainstorm  Brain storm, a fit of mental confusion or excitement.
    Copyright (c) 2008-2009 held jointly by the individual authors.

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
package org.dishevelled.brainstorm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import java.io.File;

import java.util.Arrays;
import java.util.List;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.io.FileUtils;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.StringArgument;

/**
 * Brain storm.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class BrainStorm
    extends JPanel
    implements Runnable
{
    /** File name. */
    private final String fileName;

    /** Text area. */
    private JTextArea textArea;

    /** Placeholder. */
    private Component placeholder;


    /**
     * Create a new brain storm with the specified file name.
     *
     * @param fileName file name
     */
    public BrainStorm(final String fileName)
    {
        super();
        this.fileName = fileName;
        setOpaque(true);
        initComponents();
        layoutComponents();
    }


    /**
     * Initialize components.
     */
    private void initComponents()
    {
        Font font = new Font(chooseFontName(), Font.PLAIN, 28);
        textArea = new JTextArea();
        textArea.setFont(font);
        textArea.setOpaque(true);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setRows(2);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // clear all input mappings
        InputMap inputMap = textArea.getInputMap();
        while (inputMap != null)
        {
            inputMap.clear();
            inputMap = inputMap.getParent();
        }
        // re-add select default input mappings
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "insert-break");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "insert-tab");
        // add new input mappings
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.CTRL_DOWN_MASK), "increase-font-size");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_DOWN_MASK), "decrease-font-size");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "save");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "quit");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK), "quit");

        Action increaseFontSizeAction = new IncreaseFontSizeAction();
        Action decreaseFontSizeAction = new DecreaseFontSizeAction();
        Action saveAction = new SaveAction();
        Action quitAction = new QuitAction();

        textArea.getActionMap().put("increase-font-size", increaseFontSizeAction);
        textArea.getActionMap().put("decrease-font-size", decreaseFontSizeAction);
        textArea.getActionMap().put("save", saveAction);
        textArea.getActionMap().put("quit", quitAction);

        placeholder = Box.createGlue();

        SwingUtilities.invokeLater(new Runnable()
            {
                /** {@inheritDoc} */
                public void run()
                {
                    calculatePlaceholderSize();
                    calculateTextAreaSize();
                }
            });
    }

    /**
     * Choose an installed font name from a list of preferred font names.
     */
    private String chooseFontName()
    {
        List<String> preferredNames = Arrays.asList(new String[] { "Luxi Sans", "Calibri", "Corbel", "Lucida Sans Unicode", "Lucida Grande" });
        List<String> actualNames = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        for (String preferred : preferredNames)
        {
            for (String actual : actualNames)
            {
                if (preferred.equalsIgnoreCase(actual))
                {
                    return actual;
                }
            }
        }
        return "Dialog";
    }

    /**
     * Calculate the placeholder size based on the current window size.
     */
    private void calculatePlaceholderSize()
    {
        int height = (int) (getHeight() / 3);
        placeholder = Box.createVerticalStrut(height);
    }

    /**
     * Calculate the text area size in rows and columns based on the current
     * window size and text area font size.
     */
    private void calculateTextAreaSize()
    {
        double width = (double) getWidth();
        FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
        TextLayout textLayout = new TextLayout("W", textArea.getFont(), frc);
        Rectangle2D textBounds = textLayout.getBounds();
        int columns = Math.min(45, (int) (width / textBounds.getWidth()) - 4);
        textArea.setColumns(columns);
    }

    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(true);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(placeholder);

        JPanel flow = new JPanel();
        flow.setLayout(new FlowLayout());
        flow.setOpaque(false);
        flow.add(scrollPane);

        add(flow);
        add(Box.createVerticalGlue());
        add(Box.createVerticalGlue());
    }

    /**
     * Save.
     */
    private void save()
    {
        final String baseName = StringUtils.isBlank(fileName) ? getFirstWord() : fileName.substring(0, fileName.indexOf("."));
        final String text = textArea.getText();
        (new SwingWorker<String, Object>()
            {
                /** @{inheritDoc} */
                public String doInBackground()
                {
                    try
                    {
                        File file = new File(baseName + ".txt");
                        File revisions = new File(baseName + "-revisions");
                        revisions.mkdir();
                        File latestRevision = new File(revisions, baseName + "-rev" + System.currentTimeMillis() + ".txt");
                        if (file.exists())
                        {
                            FileUtils.copyFile(file, latestRevision);
                        }
                        FileUtils.writeStringToFile(file, text);
                        return "Saved";
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        return "Not saved";
                    }

                    // todo:  show status?
                }
            }).execute();
    }

    /**
     * Return the first word of text, or <code>untitled</code> if the text is blank.
     *
     * @return the first word of text, or <code>untitled</code> if the text is blank
     */
    private String getFirstWord()
    {
        String firstWord = textArea.getText().split("\\W")[0];
        return (StringUtils.isBlank(firstWord)) ? "untitled" : firstWord;
    }

    /**
     * Quit.
     */
    private void quit()
    {
        save();
        System.exit(0);
    }

    /**
     * Show preferences.
     */
    private void showPreferences()
    {
        // empty
    }

    /** {@inheritDoc} */
    public void paintComponent(final Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        Paint oldPaint = g.getPaint();
        g.setPaint(Color.BLACK);
        g.fill(getBounds());
        g.setPaint(oldPaint);
    }

    /** {@inheritDoc} */
    public void run()
    {
        JFrame f = new JFrame("Brain storm");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add("Center", this);
        f.setResizable(false);
        f.setUndecorated(true);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(f);
        f.validate();
    }

    /**
     * Increase font size action.
     */
    private class IncreaseFontSizeAction
        extends AbstractAction
    {
        /**
         * Create a new increase font size action.
         */
        IncreaseFontSizeAction()
        {
            super("Increase font size");
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            Font oldFont = textArea.getFont();
            Font font = oldFont.deriveFont(oldFont.getSize2D() * 1.1f);
            textArea.setFont(font);
            calculateTextAreaSize();

            textArea.invalidate();
            validate();
            textArea.scrollRectToVisible(new Rectangle(0, textArea.getHeight() - 1, textArea.getWidth(), textArea.getHeight()));
        }
    }

    /**
     * Decrease font size action.
     */
    private class DecreaseFontSizeAction
        extends AbstractAction
    {
        /**
         * Create a new decrease font size action.
         */
        DecreaseFontSizeAction()
        {
            super("Decrease font size");
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            Font oldFont = textArea.getFont();
            Font font = oldFont.deriveFont(oldFont.getSize2D() * 0.9f);
            textArea.setFont(font);
            calculateTextAreaSize();

            textArea.invalidate();
            validate();
            textArea.scrollRectToVisible(new Rectangle(0, textArea.getHeight() - 1, textArea.getWidth(), textArea.getHeight()));
        }
    }

    /**
     * Save action.
     */
    private class SaveAction
        extends AbstractAction
    {
        /**
         * Create a new save action.
         */
        SaveAction()
        {
            super("Save");
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            save();
        }
    }

    /**
     * Quit action.
     */
    private class QuitAction
        extends AbstractAction
    {
        /**
         * Create a new quit action.
         */
        QuitAction()
        {
            super("Quit");
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            quit();
        }
    }

    /**
     * Show preferences action.
     */
    private class ShowPreferencesAction
        extends AbstractAction
    {
        /**
         * Create a new show preferences action.
         */
        ShowPreferencesAction()
        {
            super("Show preferences");
        }


        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent event)
        {
            showPreferences();
        }
    }

    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static final void main(final String[] args)
    {
        CommandLine commandLine = null;
        ArgumentList arguments = null;
        try
        {
            Switch help = new Switch("h", "help", "display help and usage text");
            final StringArgument fileName = new StringArgument("f", "file-name", "file name", false);
            arguments = new ArgumentList(help, fileName);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                System.out.println("Brain storm, a fit of mental confusion or excitement.\n\nCopyright (c) 2008-2009 held jointly by the individual authors.\nLicensed under the GNU Lesser General Public License (LGPL). \n");
                Usage.usage("java BrainStorm [args]", null, commandLine, arguments, System.out);
            }
            else
            {
                SwingUtilities.invokeLater(new Runnable()
                    {
                        /** {@inheritDoc} */
                        public void run()
                        {
                            BrainStorm brainStorm = new BrainStorm(fileName.getValue());
                            brainStorm.run();
                        }
                    });
            }
        }
        catch (CommandLineParseException e)
        {
            Usage.usage("java BrainStorm [args]", e, commandLine, arguments, System.err);
        }
    }
}