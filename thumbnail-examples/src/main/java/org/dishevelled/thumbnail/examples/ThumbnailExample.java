/*

    dsh-thumbnail-examples  Examples for the thumbnail library.
    Copyright (c) 2013-2019 held jointly by the individual authors.

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
package org.dishevelled.thumbnail.examples;

import java.awt.BorderLayout;

import java.io.File;

import java.net.URI;

import java.util.Collections;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;

//import org.dishevelled.eventlist.view.CountLabel;
import org.dishevelled.eventlist.view.ElementsList;

import org.dishevelled.thumbnail.ThumbnailManager;
import org.dishevelled.thumbnail.XdgThumbnailManager;

import org.dishevelled.thumbnail.swing.ThumbnailCache;
import org.dishevelled.thumbnail.swing.ThumbnailListCellRenderer;

/**
 * Thumbnail example.
 *
 * @author  Michael Heuer
 */
public final class ThumbnailExample extends JPanel implements Runnable
{
    /** Count of URIs. */
    //private final CountLabel<URI> count;

    /** List of URIs. */
    private final UriList list;

    /** Event list of URIs. */
    private final EventList<URI> uris;

    /** Match common image file extensions. */
    private static final Pattern IMAGE_FILE_EXTENSIONS = Pattern.compile("^.+\\.(?i)(?:jpe?g|png|gif)$");


    /**
     * Create a new thumbnail example.
     */
    public ThumbnailExample()
    {
        super();
        uris = GlazedLists.eventList(Collections.<URI>emptyList());
        //count = new CountLabel<URI>(uris);
        list = new UriList(uris);

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(12, 12, 12, 12));
        //add("North", createCountPanel());
        add("Center", list);
    }


    /**
     * Create a new thumbnail example with the specified directory.
     *
     * @param directory directory, must not be null
     */
    public ThumbnailExample(final File directory)
    {
        this();
        addDirectory(directory);
    }


    @Override
    public void run()
    {
        JFrame frame = new JFrame("Thumbnail Example");
        frame.setContentPane(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 600, 400);
        frame.setVisible(true);
    }

    /**
     * Add the specified directory.
     *
     * @param directory directory to add
     */
    private void addDirectory(final File directory)
    {
        new SwingWorker<Void, URI>()
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                for (File file : directory.listFiles())
                {
                    // sucks
                    // String mimeType = Files.probeContentType(file.toPath());
                    Matcher matcher = IMAGE_FILE_EXTENSIONS.matcher(file.getPath());
                    if (matcher.matches())
                    {
                        publish(file.toURI());
                    }
                }
                return null;
            }

            @Override
            protected void process(final List<URI> toAdd)
            {
                for (URI uri : toAdd)
                {
                    uris.add(uri);
                }
            }
        }.run();
    }

    /**
     * URI list.
     */
    private class UriList extends ElementsList<URI>
    {
        /**
         * Create a new URI list.
         *
         * @param eventList event list
         */
        UriList(final EventList<URI> eventList)
        {
            super(eventList);
            ThumbnailManager thumbnailManager = new XdgThumbnailManager();
            ThumbnailCache thumbnailCache = new ThumbnailCache(thumbnailManager);
            getLabel().setText("Thumbnails");
            getList().setCellRenderer(new ThumbnailListCellRenderer(thumbnailCache));
            thumbnailCache.add(getList());
            getPasteAction().setEnabled(false);
        }

        @Override
        public void add()
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(ThumbnailExample.this);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                addDirectory(fileChooser.getSelectedFile());
            }
        }

        @Override
        public void paste()
        {
            // empty
        }
    }

    /**
     * Print usage string and exit.
     */
    private static void usage()
    {
        System.err.println("usage\njava ThumbnailExample [directory]");
        System.exit(-1);
    }

    /**
     * Main.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args)
    {
        if (args.length > 1)
        {
            usage();
        }
        else if (args.length == 1)
        {
            SwingUtilities.invokeLater(new ThumbnailExample(new File(args[0])));
        }
        else
        {
            SwingUtilities.invokeLater(new ThumbnailExample());
        }
    }
}
