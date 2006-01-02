/*

    dsh-iconbundle-tango-examples  Examples using the iconbundle-tango library.
    Copyright (c) 2005-2006 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.tango.example;

import java.util.List;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.SortedSet;

import javax.swing.event.TreeModelListener;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeModel;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.MultiHashMap;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.tango.TangoProject;

import org.dishevelled.identify.Identifiable;

/**
 * Tango tree model.
 *
 * <pre>
 * root
 *   |
 *   +-- context-1
 *   |     |
 *   |     +-- icon-name-1
 *   |     |
 *   |     +-- icon-name-2
 *   |
 *   +-- context-2
 *   |
 *   ...
 * </pre>
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
final class TangoTreeModel
    implements TreeModel
{
    /** Root object. */
    private final Identifiable root;

    /** Contexts. */
    private final List contexts;

    /** MultiMap of contexts to list of icons. */
    private final MultiMap multiMap;


    /**
     * Create a new tango tree model.
     */
    public TangoTreeModel()
    {
        root = new Identifiable()
            {
                /** @see Identifiable */
                public String getName()
                {
                    return "Tango Project";
                }
                public IconBundle getIconBundle()
                {
                    return TangoProject.FOLDER;
                }
            };

        multiMap = new MultiHashMap();
        initializeMultiMap();

        contexts = new ArrayList(multiMap.size());
        initializeContexts();
    }

    /**
     * Initialize the multi map.
     */
    private void initializeMultiMap()
    {
        multiMap.put("actions", new Icon("address-book-new", TangoProject.ADDRESS_BOOK_NEW));
        //multiMap.put("actions", new Icon("application-exit", TangoProject.APPLICATION_EXIT));
        multiMap.put("actions", new Icon("appointment-new", TangoProject.APPOINTMENT_NEW));
        multiMap.put("actions", new Icon("bookmark-new", TangoProject.BOOKMARK_NEW));
        multiMap.put("actions", new Icon("contact-new", TangoProject.CONTACT_NEW));
        //multiMap.put("actions", new Icon("dialog-cancel", TangoProject.DIALOG_CANCEL));
        multiMap.put("actions", new Icon("document-new", TangoProject.DOCUMENT_NEW));
        multiMap.put("actions", new Icon("document-open", TangoProject.DOCUMENT_OPEN));
        multiMap.put("actions", new Icon("document-print", TangoProject.DOCUMENT_PRINT));
        multiMap.put("actions", new Icon("document-print-preview", TangoProject.DOCUMENT_PRINT_PREVIEW));
        multiMap.put("actions", new Icon("document-properties", TangoProject.DOCUMENT_PROPERTIES));
        multiMap.put("actions", new Icon("document-save", TangoProject.DOCUMENT_SAVE));
        multiMap.put("actions", new Icon("document-save-as", TangoProject.DOCUMENT_SAVE_AS));
        multiMap.put("actions", new Icon("edit-clear", TangoProject.EDIT_CLEAR));
        multiMap.put("actions", new Icon("edit-copy", TangoProject.EDIT_COPY));
        multiMap.put("actions", new Icon("edit-cut", TangoProject.EDIT_CUT));
        multiMap.put("actions", new Icon("edit-find", TangoProject.EDIT_FIND));
        multiMap.put("actions", new Icon("edit-paste", TangoProject.EDIT_PASTE));
        multiMap.put("actions", new Icon("edit-redo", TangoProject.EDIT_REDO));
        multiMap.put("actions", new Icon("edit-undo", TangoProject.EDIT_UNDO));
        multiMap.put("actions", new Icon("folder-new", TangoProject.FOLDER_NEW));
        multiMap.put("actions", new Icon("format-indent-less", TangoProject.FORMAT_INDENT_LESS));
        multiMap.put("actions", new Icon("format-indent-more", TangoProject.FORMAT_INDENT_MORE));
        multiMap.put("actions", new Icon("format-justify-center", TangoProject.FORMAT_JUSTIFY_CENTER));
        multiMap.put("actions", new Icon("format-justify-fill", TangoProject.FORMAT_JUSTIFY_FILL));
        multiMap.put("actions", new Icon("format-justify-left", TangoProject.FORMAT_JUSTIFY_LEFT));
        multiMap.put("actions", new Icon("format-justify-right", TangoProject.FORMAT_JUSTIFY_RIGHT));
        multiMap.put("actions", new Icon("format-text-bold", TangoProject.FORMAT_TEXT_BOLD));
        multiMap.put("actions", new Icon("format-text-italic", TangoProject.FORMAT_TEXT_ITALIC));
        multiMap.put("actions", new Icon("format-text-strikethrough", TangoProject.FORMAT_TEXT_STRIKETHROUGH));
        multiMap.put("actions", new Icon("format-text-underline", TangoProject.FORMAT_TEXT_UNDERLINE));
        multiMap.put("actions", new Icon("go-bottom", TangoProject.GO_BOTTOM));
        multiMap.put("actions", new Icon("go-down", TangoProject.GO_DOWN));
        multiMap.put("actions", new Icon("go-first", TangoProject.GO_FIRST));
        //multiMap.put("actions", new Icon("go-home", TangoProject.GO_HOME));
        multiMap.put("actions", new Icon("go-jump", TangoProject.GO_JUMP));
        multiMap.put("actions", new Icon("go-last", TangoProject.GO_LAST));
        multiMap.put("actions", new Icon("go-next", TangoProject.GO_NEXT));
        multiMap.put("actions", new Icon("go-previous", TangoProject.GO_PREVIOUS));
        multiMap.put("actions", new Icon("go-top", TangoProject.GO_TOP));
        multiMap.put("actions", new Icon("go-up", TangoProject.GO_UP));
        multiMap.put("actions", new Icon("mail-message-new", TangoProject.MAIL_MESSAGE_NEW));
        multiMap.put("actions", new Icon("media-playback-pause", TangoProject.MEDIA_PLAYBACK_PAUSE));
        multiMap.put("actions", new Icon("media-playback-start", TangoProject.MEDIA_PLAYBACK_START));
        multiMap.put("actions", new Icon("media-playback-stop", TangoProject.MEDIA_PLAYBACK_STOP));
        multiMap.put("actions", new Icon("media-record", TangoProject.MEDIA_RECORD));
        multiMap.put("actions", new Icon("media-seek-backward", TangoProject.MEDIA_SEEK_BACKWARD));
        multiMap.put("actions", new Icon("media-seek-forward", TangoProject.MEDIA_SEEK_FORWARD));
        multiMap.put("actions", new Icon("media-skip-backward", TangoProject.MEDIA_SKIP_BACKWARD));
        multiMap.put("actions", new Icon("media-skip-forward", TangoProject.MEDIA_SKIP_FORWARD));
        multiMap.put("actions", new Icon("process-stop", TangoProject.PROCESS_STOP));
        multiMap.put("actions", new Icon("system-lock-screen", TangoProject.SYSTEM_LOCK_SCREEN));
        multiMap.put("actions", new Icon("system-log-out", TangoProject.SYSTEM_LOG_OUT));
        multiMap.put("actions", new Icon("system-search", TangoProject.SYSTEM_SEARCH));
        multiMap.put("actions", new Icon("tab-new", TangoProject.TAB_NEW));
        multiMap.put("actions", new Icon("view-refresh", TangoProject.VIEW_REFRESH));
        //multiMap.put("actions", new Icon("view-sort-ascending", TangoProject.VIEW_SORT_ASCENDING));
        //multiMap.put("actions", new Icon("view-sort-descending", TangoProject.VIEW_SORT_DESCENDING));
        multiMap.put("actions", new Icon("window-new", TangoProject.WINDOW_NEW));
        multiMap.put("apps", new Icon("accessories-calculator", TangoProject.ACCESSORIES_CALCULATOR));
        multiMap.put("apps", new Icon("accessories-character-map", TangoProject.ACCESSORIES_CHARACTER_MAP));
        multiMap.put("apps", new Icon("accessories-text-editor", TangoProject.ACCESSORIES_TEXT_EDITOR));
        multiMap.put("apps", new Icon("help-browser", TangoProject.HELP_BROWSER));
        multiMap.put("apps", new Icon("internet-mail", TangoProject.INTERNET_MAIL));
        multiMap.put("apps", new Icon("internet-web-browser", TangoProject.INTERNET_WEB_BROWSER));
        multiMap.put("apps", new Icon("multimedia-volume-control", TangoProject.MULTIMEDIA_VOLUME_CONTROL));
        multiMap.put("apps", new Icon("office-calendar", TangoProject.OFFICE_CALENDAR));
        multiMap.put("apps", new Icon("preferences-desktop-accessibility", TangoProject.PREFERENCES_DESKTOP_ACCESSIBILITY));
        multiMap.put("apps", new Icon("preferences-desktop-assistive-technology", TangoProject.PREFERENCES_DESKTOP_ASSISTIVE_TECHNOLOGY));
        multiMap.put("apps", new Icon("preferences-desktop-font", TangoProject.PREFERENCES_DESKTOP_FONT));
        multiMap.put("apps", new Icon("preferences-desktop-keyboard-shortcuts", TangoProject.PREFERENCES_DESKTOP_KEYBOARD_SHORTCUTS));
        multiMap.put("apps", new Icon("preferences-desktop-remote-desktop", TangoProject.PREFERENCES_DESKTOP_REMOTE_DESKTOP));
        multiMap.put("apps", new Icon("preferences-desktop-screensaver", TangoProject.PREFERENCES_DESKTOP_SCREENSAVER));
        multiMap.put("apps", new Icon("preferences-desktop-sound", TangoProject.PREFERENCES_DESKTOP_SOUND));
        multiMap.put("apps", new Icon("preferences-desktop-theme", TangoProject.PREFERENCES_DESKTOP_THEME));
        multiMap.put("apps", new Icon("preferences-desktop-wallpaper", TangoProject.PREFERENCES_DESKTOP_WALLPAPER));
        multiMap.put("apps", new Icon("preferences-system-network-proxy", TangoProject.PREFERENCES_SYSTEM_NETWORK_PROXY));
        multiMap.put("apps", new Icon("preferences-system-session", TangoProject.PREFERENCES_SYSTEM_SESSION));
        multiMap.put("apps", new Icon("preferences-system-windows", TangoProject.PREFERENCES_SYSTEM_WINDOWS));
        multiMap.put("apps", new Icon("system-file-manager", TangoProject.SYSTEM_FILE_MANAGER));
        multiMap.put("apps", new Icon("system-users", TangoProject.SYSTEM_USERS));
        multiMap.put("apps", new Icon("utilities-system-monitor", TangoProject.UTILITIES_SYSTEM_MONITOR));
        multiMap.put("apps", new Icon("utilities-terminal", TangoProject.UTILITIES_TERMINAL));
        multiMap.put("categories", new Icon("applications-accessories", TangoProject.APPLICATIONS_ACCESSORIES));
        //multiMap.put("categories", new Icon("applications-development", TangoProject.APPLICATIONS_DEVELOPMENT));
        multiMap.put("categories", new Icon("applications-games", TangoProject.APPLICATIONS_GAMES));
        multiMap.put("categories", new Icon("applications-graphics", TangoProject.APPLICATIONS_GRAPHICS));
        //multiMap.put("categories", new Icon("applications-multimedia", TangoProject.APPLICATIONS_MULTIMEDIA));
        //multiMap.put("categories", new Icon("applications-office", TangoProject.APPLICATIONS_OFFICE));
        //multiMap.put("categories", new Icon("applications-system", TangoProject.APPLICATIONS_SYSTEM));
        multiMap.put("categories", new Icon("preferences-desktop", TangoProject.PREFERENCES_DESKTOP));
        multiMap.put("devices", new Icon("audio-card", TangoProject.AUDIO_CARD));
        multiMap.put("devices", new Icon("audio-input-microphone", TangoProject.AUDIO_INPUT_MICROPHONE));
        multiMap.put("devices", new Icon("battery", TangoProject.BATTERY));
        multiMap.put("devices", new Icon("camera-photo", TangoProject.CAMERA_PHOTO));
        multiMap.put("devices", new Icon("computer", TangoProject.COMPUTER));
        multiMap.put("devices", new Icon("drive-cdrom", TangoProject.DRIVE_CDROM));
        multiMap.put("devices", new Icon("drive-harddisk", TangoProject.DRIVE_HARDDISK));
        multiMap.put("devices", new Icon("drive-removable-media", TangoProject.DRIVE_REMOVABLE_MEDIA));
        multiMap.put("devices", new Icon("input-gaming", TangoProject.INPUT_GAMING));
        multiMap.put("devices", new Icon("input-keyboard", TangoProject.INPUT_KEYBOARD));
        multiMap.put("devices", new Icon("input-mouse", TangoProject.INPUT_MOUSE));
        multiMap.put("devices", new Icon("media-cdrom", TangoProject.MEDIA_CDROM));
        multiMap.put("devices", new Icon("media-floppy", TangoProject.MEDIA_FLOPPY));
        multiMap.put("devices", new Icon("multimedia-player", TangoProject.MULTIMEDIA_PLAYER));
        multiMap.put("devices", new Icon("printer", TangoProject.PRINTER));
        multiMap.put("devices", new Icon("video-display", TangoProject.VIDEO_DISPLAY));
        multiMap.put("emblems", new Icon("emblem-important", TangoProject.EMBLEM_IMPORTANT));
        multiMap.put("mimetypes", new Icon("application-x-executable", TangoProject.APPLICATION_X_EXECUTABLE));
        multiMap.put("mimetypes", new Icon("audio-x-generic", TangoProject.AUDIO_X_GENERIC));
        multiMap.put("mimetypes", new Icon("image-x-generic", TangoProject.IMAGE_X_GENERIC));
        multiMap.put("mimetypes", new Icon("package-x-generic", TangoProject.PACKAGE_X_GENERIC));
        multiMap.put("mimetypes", new Icon("text-html", TangoProject.TEXT_HTML));
        multiMap.put("mimetypes", new Icon("text-x-generic", TangoProject.TEXT_X_GENERIC));
        multiMap.put("mimetypes", new Icon("text-x-generic-template", TangoProject.TEXT_X_GENERIC_TEMPLATE));
        multiMap.put("mimetypes", new Icon("text-x-script", TangoProject.TEXT_X_SCRIPT));
        multiMap.put("mimetypes", new Icon("video-x-generic", TangoProject.VIDEO_X_GENERIC));
        multiMap.put("mimetypes", new Icon("x-office-address-book", TangoProject.X_OFFICE_ADDRESS_BOOK));
        multiMap.put("mimetypes", new Icon("x-office-document", TangoProject.X_OFFICE_DOCUMENT));
        multiMap.put("mimetypes", new Icon("x-office-spreadsheet", TangoProject.X_OFFICE_SPREADSHEET));
        multiMap.put("places", new Icon("folder", TangoProject.FOLDER));
        multiMap.put("places", new Icon("folder-remote", TangoProject.FOLDER_REMOTE));
        multiMap.put("places", new Icon("network-server", TangoProject.NETWORK_SERVER));
        multiMap.put("places", new Icon("network-workgroup", TangoProject.NETWORK_WORKGROUP));
        //multiMap.put("places", new Icon("start-here", TangoProject.START_HERE));
        multiMap.put("places", new Icon("user-desktop", TangoProject.USER_DESKTOP));
        multiMap.put("places", new Icon("user-home", TangoProject.USER_HOME));
        multiMap.put("places", new Icon("user-trash", TangoProject.USER_TRASH));
        multiMap.put("status", new Icon("dialog-error", TangoProject.DIALOG_ERROR));
        multiMap.put("status", new Icon("dialog-information", TangoProject.DIALOG_INFORMATION));
        //multiMap.put("status", new Icon("dialog-password", TangoProject.DIALOG_PASSWORD));
        //multiMap.put("status", new Icon("dialog-question", TangoProject.DIALOG_QUESTION));
        multiMap.put("status", new Icon("dialog-warning", TangoProject.DIALOG_WARNING));
        //multiMap.put("status", new Icon("folder-drag-accept", TangoProject.FOLDER_DRAG_ACCEPT));
        //multiMap.put("status", new Icon("folder-open", TangoProject.FOLDER_OPEN));
        //multiMap.put("status", new Icon("folder-visiting", TangoProject.FOLDER_VISITING));
        multiMap.put("status", new Icon("image-loading", TangoProject.IMAGE_LOADING));
        multiMap.put("status", new Icon("image-missing", TangoProject.IMAGE_MISSING));
        //multiMap.put("status", new Icon("user-trash-full", TangoProject.USER_TRASH_FULL));
    }

    /**
     * Intialize the list of contexts.
     */
    private void initializeContexts()
    {
        SortedSet keys = new TreeSet();
        keys.addAll(multiMap.keySet());

        for (Iterator i = keys.iterator(); i.hasNext(); )
        {
            contexts.add(new Context((String) i.next()));
        }

        keys = null;
    }

    /** @see TreeModel */
    public void addTreeModelListener(final TreeModelListener l)
    {
        // ignore
    }

    /** @see TreeModel */
    public Object getChild(final Object parent, final int index)
    {
        if (root == parent)
        {
            return contexts.get(index);
        }
        else if (parent instanceof Context)
        {
            Context context = (Context) parent;
            return context.get(index);
        }
        else
        {
            return null;
        }
    }

    /** @see TreeModel */
    public int getChildCount(final Object parent)
    {
        if (root == parent)
        {
            return contexts.size();
        }
        else if (parent instanceof Context)
        {
            Context context = (Context) parent;
            return context.size();
        }
        else
        {
            return 0;
        }
    }

    /** @see TreeModel */
    public int getIndexOfChild(final Object parent, final Object child)
    {
        if (root == parent)
        {
            return contexts.indexOf(child);
        }
        else if (parent instanceof Context)
        {
            Context context = (Context) parent;
            return context.indexOf(child);
        }
        else
        {
            return -1;
        }
    }

    /** @see TreeModel */
    public Object getRoot()
    {
        return root;
    }

    /** @see TreeModel */
    public boolean isLeaf(final Object node)
    {
        return (getChildCount(node) == 0);
    }

    /** @see TreeModel */
    public void removeTreeModelListener(final TreeModelListener l)
    {
        // ignore
    }

    /** @see TreeModel */
    public void valueForPathChanged(final TreePath path, final Object value)
    {
        // ignore
    }


    /**
     * Context.
     */
    private class Context
        implements Identifiable
    {
        /** Name. */
        private final String name;


        /**
         * Create a new context with the specified name.
         *
         * @param name name
         */
        public Context(final String name)
        {
            this.name = name;
        }


        /** @see Identifiable */
        public String getName()
        {
            return name;
        }

        /** @see Identifiable */
        public IconBundle getIconBundle()
        {
            return TangoProject.FOLDER;
        }

        /**
         * Return the icon at the specified index.
         *
         * @param index index
         * @return the icon at the specified index
         */
        public Object get(final int index)
        {
            List icons = (List) multiMap.get(name);
            return icons.get(index);
        }

        /**
         * Return the number of icons in this context.
         *
         * @return the number of icons in this context
         */
        public int size()
        {
            List icons = (List) multiMap.get(name);
            return icons.size();
        }

        /**
         * Return the index of the specified object.
         *
         * @param object object
         * @return the index of the specified object
         */
        public int indexOf(final Object object)
        {
            List icons = (List) multiMap.get(name);
            return icons.indexOf(object);
        }
    }

    /**
     * Icon.
     */
    private class Icon
        implements Identifiable
    {
        /** Name. */
        private final String name;

        /** Icon bundle. */
        private final IconBundle iconBundle;


        /**
         * Create a new icon with the specified name and icon bundle.
         *
         * @param name name
         * @param iconBundle icon bundle
         */
        public Icon(final String name, final IconBundle iconBundle)
        {
            this.name = name;
            this.iconBundle = iconBundle;
        }


        /** @see Identifiable */
        public String getName()
        {
            return name;
        }

        /** @see Identifiable */
        public IconBundle getIconBundle()
        {
            return iconBundle;
        }
    }
}