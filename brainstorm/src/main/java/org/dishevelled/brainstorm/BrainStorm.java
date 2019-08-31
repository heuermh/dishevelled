/*

    dsh-brainstorm  Brain storm, a fit of mental confusion or excitement.
    Copyright (c) 2008-2019 held jointly by the individual authors.

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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

import static java.awt.RenderingHints.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import java.awt.image.BufferedImage;

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
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;

import org.apache.commons.io.FileUtils;

import org.beryx.awt.color.ColorFactory;

import org.dishevelled.commandline.ArgumentList;
import org.dishevelled.commandline.CommandLine;
import org.dishevelled.commandline.CommandLineParser;
import org.dishevelled.commandline.CommandLineParseException;
import org.dishevelled.commandline.Switch;
import org.dishevelled.commandline.Usage;

import org.dishevelled.commandline.argument.IntegerArgument;
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

    /** Font name. */
    private final String fontName;

    /** Font size. */
    private final int fontSize;

    /** Number of rows to display. */
    private final int rows;

    /** Background color. */
    private final Color backgroundColor;

    /** Text color. */
    private final Color textColor;

    /** Default font name, <code>null</code>. */
    private static final String DEFAULT_FONT_NAME = null;

    /** Default font size, <code>28</code>. */
    private static final int DEFAULT_FONT_SIZE = 28;

    /** Default number of rows to display, <code>2</code>. */
    private static final int DEFAULT_ROWS = 2;

    /** Default background color, <code>black</code>. */
    private static final String DEFAULT_BACKGROUND_COLOR = "black";

    /** Default text color, <code>rgb(200, 200, 200)</code>. */
    private static final String DEFAULT_TEXT_COLOR = "rgb(200, 200, 200)";

    /** Text area. */
    private JTextArea textArea;

    /** Hidden cursor. */
    private Cursor hiddenCursor;

    /** Placeholder. */
    private Component placeholder;


    /**
     * Create a new brain storm with the specified file name.
     *
     * @param fileName file name
     */
    public BrainStorm(final String fileName)
    {
        this(fileName,
             DEFAULT_FONT_NAME,
             DEFAULT_FONT_SIZE,
             DEFAULT_ROWS,
             DEFAULT_BACKGROUND_COLOR,
             DEFAULT_TEXT_COLOR);
    }

    /**
     * Create a new brain storm with the specified file name.
     *
     * @param fileName file name
     */
    public BrainStorm(final String fileName,
                      final String fontName,
                      final int fontSize,
                      final int rows,
                      final String backgroundColor,
                      final String textColor)
    {
        super();
        this.fileName = fileName;
        this.fontName = fontName;
        this.fontSize = fontSize;
        this.rows = rows;
        this.backgroundColor = ColorFactory.valueOf(backgroundColor);
        this.textColor = ColorFactory.valueOf(textColor);
        setOpaque(true);
        initComponents();
        layoutComponents();
    }


    /**
     * Initialize components.
     */
    private void initComponents()
    {
        Font font = new Font(chooseFontName(), Font.PLAIN, fontSize);
        hiddenCursor = createHiddenCursor();
        textArea = new JTextArea()
            {
                /** {@inheritDoc} */
                protected void paintComponent(final Graphics graphics)
                {
                    Graphics2D g2 = (Graphics2D) graphics;
                    g2.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
                    g2.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                    super.paintComponent(g2);
                }
            };
        textArea.setFont(font);
        textArea.setOpaque(true);
        textArea.setCursor(hiddenCursor);
        textArea.setBackground(backgroundColor);
        textArea.setForeground(textColor);
        textArea.setRows(rows);
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
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "delete-previous");
        int keyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, keyMask), "delete-previous-word");

        // add new input mappings
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, keyMask), "increase-font-size");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, keyMask | InputEvent.SHIFT_MASK), "increase-font-size");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, keyMask), "increase-font-size");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, keyMask | InputEvent.SHIFT_MASK), "increase-font-size");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, keyMask), "decrease-font-size");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, keyMask | InputEvent.SHIFT_MASK), "decrease-font-size");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, keyMask), "save");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "quit");
        textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, keyMask), "quit");

        Action increaseFontSizeAction = new IncreaseFontSizeAction();
        Action decreaseFontSizeAction = new DecreaseFontSizeAction();
        Action saveAction = new SaveAction();
        Action quitAction = new QuitAction();

        textArea.getActionMap().put("increase-font-size", increaseFontSizeAction);
        textArea.getActionMap().put("decrease-font-size", decreaseFontSizeAction);
        textArea.getActionMap().put("save", saveAction);
        textArea.getActionMap().put("quit", quitAction);

        placeholder = Box.createGlue();
    }

    /**
     * Choose an installed font name from a list of preferred font names.
     */
    private String chooseFontName()
    {
        List<String> preferredNames = Arrays.asList(new String[] { "Luxi Sans", "Calibri", "Corbel", "Lucida Sans Unicode", "Lucida Grande" });
        List<String> actualNames = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        for (String actual : actualNames)
        {
            if ((fontName != null) && (fontName.equalsIgnoreCase(actual)))
            {
                System.out.println("found font " + actual);
                return actual;
            }
        }
        for (String actual : actualNames)
        {
            for (String preferred : preferredNames)
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
        scrollPane.setBackground(backgroundColor);
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

    /**
     * Create and return a new hidden cursor, that is a fully transparent one pixel custom cursor.
     *
     * @return a new hidden cursor
     */
    private Cursor createHiddenCursor()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension size = toolkit.getBestCursorSize(1, 1);
        BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
        graphics.clearRect(0, 0, size.width, size.height);
        graphics.dispose();
        return toolkit.createCustomCursor(image, new Point(0, 0), "hiddenCursor");
    }

    /** {@inheritDoc} */
    public void paintComponent(final Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics;
        Paint oldPaint = g.getPaint();
        g.setPaint(backgroundColor);
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
        // hide cursor on linux and windows platforms
        f.setCursor(hiddenCursor);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(f);
        f.validate();

        SwingUtilities.invokeLater(new Runnable()
            {
                /** {@inheritDoc} */
                public void run()
                {
                    calculatePlaceholderSize();
                    calculateTextAreaSize();
                    validate();
                    textArea.scrollRectToVisible(new Rectangle(0, textArea.getHeight() - 1, textArea.getWidth(), textArea.getHeight()));
                }
            });

        // save every five minutes
        Timer t = new Timer(5 * 60 * 1000, new ActionListener()
            {
                /** {@inheritDoc} */
                public void actionPerformed(final ActionEvent event)
                {
                    save();
                }
            });
        t.setRepeats(true);
        t.start();
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
        // hide cursor on mac platform
        System.setProperty("apple.awt.fullscreenhidecursor","true");

        CommandLine commandLine = null;
        ArgumentList arguments = null;
        try
        {
            Switch help = new Switch("h", "help", "display help and usage text");
            StringArgument fileName = new StringArgument("f", "file-name", "file name", false);
            StringArgument fontName = new StringArgument("n", "font-name", "font name, default " + DEFAULT_FONT_NAME, false);
            IntegerArgument fontSize = new IntegerArgument("z", "font-size", "font size, default " + DEFAULT_FONT_SIZE, false);
            IntegerArgument rows = new IntegerArgument("r", "rows", "number of rows to display, default " + DEFAULT_ROWS, false);
            StringArgument backgroundColor = new StringArgument("b", "background-color", "background color, default " + DEFAULT_BACKGROUND_COLOR, false);
            StringArgument textColor = new StringArgument("t", "text-color", "text color, default " + DEFAULT_TEXT_COLOR, false);
            arguments = new ArgumentList(help, fileName, fontName, fontSize, rows, backgroundColor, textColor);
            commandLine = new CommandLine(args);
            CommandLineParser.parse(commandLine, arguments);

            if (help.wasFound())
            {
                System.out.println("Brain storm, a fit of mental confusion or excitement.\n\nCopyright (c) 2008-2019 held jointly by the individual authors.\nLicensed under the GNU Lesser General Public License (LGPL). \n");
                Usage.usage("brain-storm [args]", null, commandLine, arguments, System.out);
            }
            else
            {
                SwingUtilities.invokeLater(new BrainStorm(fileName.getValue(),
                                                          fontName.getValue(DEFAULT_FONT_NAME),
                                                          fontSize.getValue(DEFAULT_FONT_SIZE),
                                                          rows.getValue(DEFAULT_ROWS),
                                                          backgroundColor.getValue(DEFAULT_BACKGROUND_COLOR),
                                                          textColor.getValue(DEFAULT_TEXT_COLOR)));
            }
        }
        catch (CommandLineParseException e)
        {
            Usage.usage("java BrainStorm [args]", e, commandLine, arguments, System.err);
        }
    }
}
