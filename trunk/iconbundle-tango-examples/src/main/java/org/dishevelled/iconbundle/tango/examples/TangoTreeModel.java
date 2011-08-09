/*

    dsh-iconbundle-tango-examples  Examples using the iconbundle-tango library.
    Copyright (c) 2005-2011 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.tango.examples;

import java.util.List;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.SortedSet;

import javax.swing.event.TreeModelListener;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeModel;

import com.google.common.collect.Multimap;

import com.google.common.collect.ArrayListMultimap;

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

    /** Multimap of contexts to list of icons. */
    private final Multimap multimap;


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

        multimap = ArrayListMultimap.create();
        initializeMultimap();

        contexts = new ArrayList(multimap.size());
        initializeContexts();
    }

    /**
     * Initialize the multi map.
     */
    private void initializeMultimap()
    {
        multimap.put("actions", new Icon("address-book-new", TangoProject.ADDRESS_BOOK_NEW));
        //multimap.put("actions", new Icon("application-exit", TangoProject.APPLICATION_EXIT));
        multimap.put("actions", new Icon("appointment-new", TangoProject.APPOINTMENT_NEW));
        multimap.put("actions", new Icon("bookmark-new", TangoProject.BOOKMARK_NEW));
        multimap.put("actions", new Icon("contact-new", TangoProject.CONTACT_NEW));
        //multimap.put("actions", new Icon("dialog-cancel", TangoProject.DIALOG_CANCEL));
        //multimap.put("actions", new Icon("dialog-close", TangoProject.DIALOG_CLOSE));
        //multimap.put("actions", new Icon("dialog-ok", TangoProject.DIALOG_OK));
        multimap.put("actions", new Icon("document-new", TangoProject.DOCUMENT_NEW));
        multimap.put("actions", new Icon("document-open", TangoProject.DOCUMENT_OPEN));
        //multimap.put("actions", new Icon("document-open-recent", TangoProject.DOCUMENT_OPEN_RECENT));
        //multimap.put("actions", new Icon("document-page-setup", TangoProject.DOCUMENT_PAGE_SETUP));
        multimap.put("actions", new Icon("document-print", TangoProject.DOCUMENT_PRINT));
        multimap.put("actions", new Icon("document-print-preview", TangoProject.DOCUMENT_PRINT_PREVIEW));
        multimap.put("actions", new Icon("document-properties", TangoProject.DOCUMENT_PROPERTIES));
        //multimap.put("actions", new Icon("document-revert", TangoProject.DOCUMENT_REVERT));
        multimap.put("actions", new Icon("document-save", TangoProject.DOCUMENT_SAVE));
        multimap.put("actions", new Icon("document-save-as", TangoProject.DOCUMENT_SAVE_AS));
        multimap.put("actions", new Icon("edit-clear", TangoProject.EDIT_CLEAR));
        multimap.put("actions", new Icon("edit-copy", TangoProject.EDIT_COPY));
        multimap.put("actions", new Icon("edit-cut", TangoProject.EDIT_CUT));
        multimap.put("actions", new Icon("edit-delete", TangoProject.EDIT_DELETE));
        multimap.put("actions", new Icon("edit-find", TangoProject.EDIT_FIND));
        multimap.put("actions", new Icon("edit-find-replace", TangoProject.EDIT_FIND_REPLACE));
        multimap.put("actions", new Icon("edit-paste", TangoProject.EDIT_PASTE));
        multimap.put("actions", new Icon("edit-redo", TangoProject.EDIT_REDO));
        multimap.put("actions", new Icon("edit-select-all", TangoProject.EDIT_SELECT_ALL));
        multimap.put("actions", new Icon("edit-undo", TangoProject.EDIT_UNDO));
        multimap.put("actions", new Icon("folder-new", TangoProject.FOLDER_NEW));
        multimap.put("actions", new Icon("format-indent-less", TangoProject.FORMAT_INDENT_LESS));
        multimap.put("actions", new Icon("format-indent-more", TangoProject.FORMAT_INDENT_MORE));
        multimap.put("actions", new Icon("format-justify-center", TangoProject.FORMAT_JUSTIFY_CENTER));
        multimap.put("actions", new Icon("format-justify-fill", TangoProject.FORMAT_JUSTIFY_FILL));
        multimap.put("actions", new Icon("format-justify-left", TangoProject.FORMAT_JUSTIFY_LEFT));
        multimap.put("actions", new Icon("format-justify-right", TangoProject.FORMAT_JUSTIFY_RIGHT));
        multimap.put("actions", new Icon("format-text-bold", TangoProject.FORMAT_TEXT_BOLD));
        //multimap.put("actions", new Icon("format-text-direction-ltr", TangoProject.FORMAT_TEXT_DIRECTION_LTR));
        //multimap.put("actions", new Icon("format-text-direction-rtl", TangoProject.FORMAT_TEXT_DIRECTION_RTL));
        multimap.put("actions", new Icon("format-text-italic", TangoProject.FORMAT_TEXT_ITALIC));
        multimap.put("actions", new Icon("format-text-strikethrough", TangoProject.FORMAT_TEXT_STRIKETHROUGH));
        multimap.put("actions", new Icon("format-text-underline", TangoProject.FORMAT_TEXT_UNDERLINE));
        multimap.put("actions", new Icon("go-bottom", TangoProject.GO_BOTTOM));
        multimap.put("actions", new Icon("go-down", TangoProject.GO_DOWN));
        multimap.put("actions", new Icon("go-first", TangoProject.GO_FIRST));
        //multimap.put("actions", new Icon("go-home", TangoProject.GO_HOME));
        multimap.put("actions", new Icon("go-jump", TangoProject.GO_JUMP));
        multimap.put("actions", new Icon("go-last", TangoProject.GO_LAST));
        multimap.put("actions", new Icon("go-next", TangoProject.GO_NEXT));
        multimap.put("actions", new Icon("go-previous", TangoProject.GO_PREVIOUS));
        multimap.put("actions", new Icon("go-top", TangoProject.GO_TOP));
        multimap.put("actions", new Icon("go-up", TangoProject.GO_UP));
        //multimap.put("actions", new Icon("help-about", TangoProject.HELP_ABOUT));
        //multimap.put("actions", new Icon("help-contents", TangoProject.HELP_CONTENTS));
        //multimap.put("actions", new Icon("help-faq", TangoProject.HELP_FAQ));
        //multimap.put("actions", new Icon("insert-image", TangoProject.INSERT_IMAGE));
        //multimap.put("actions", new Icon("insert-link", TangoProject.INSERT_LINK));
        //multimap.put("actions", new Icon("insert-object", TangoProject.INSERT_OBJECT));
        //multimap.put("actions", new Icon("insert-text", TangoProject.INSERT_TEXT));
        multimap.put("actions", new Icon("list-add", TangoProject.LIST_ADD));
        multimap.put("actions", new Icon("list-remove", TangoProject.LIST_REMOVE));
        multimap.put("actions", new Icon("mail-forward", TangoProject.MAIL_FORWARD));
        //multimap.put("actions", new Icon("mail-mark-important", TangoProject.MAIL_MARK_IMPORTANT));
        multimap.put("actions", new Icon("mail-mark-junk", TangoProject.MAIL_MARK_JUNK));
        //multimap.put("actions", new Icon("mail-mark-notjunk", TangoProject.MAIL_MARK_NOTJUNK));
        //multimap.put("actions", new Icon("mail-mark-read", TangoProject.MAIL_MARK_READ));
        //multimap.put("actions", new Icon("mail-mark-unread", TangoProject.MAIL_MARK_UNREAD));
        multimap.put("actions", new Icon("mail-message-new", TangoProject.MAIL_MESSAGE_NEW));
        multimap.put("actions", new Icon("mail-reply-all", TangoProject.MAIL_REPLY_ALL));
        multimap.put("actions", new Icon("mail-reply-sender", TangoProject.MAIL_REPLY_SENDER));
        //multimap.put("actions", new Icon("mail-send", TangoProject.MAIL_SEND));
        multimap.put("actions", new Icon("mail-send-receive", TangoProject.MAIL_SEND_RECEIVE));
        multimap.put("actions", new Icon("media-eject", TangoProject.MEDIA_EJECT));
        multimap.put("actions", new Icon("media-playback-pause", TangoProject.MEDIA_PLAYBACK_PAUSE));
        multimap.put("actions", new Icon("media-playback-start", TangoProject.MEDIA_PLAYBACK_START));
        multimap.put("actions", new Icon("media-playback-stop", TangoProject.MEDIA_PLAYBACK_STOP));
        multimap.put("actions", new Icon("media-record", TangoProject.MEDIA_RECORD));
        multimap.put("actions", new Icon("media-seek-backward", TangoProject.MEDIA_SEEK_BACKWARD));
        multimap.put("actions", new Icon("media-seek-forward", TangoProject.MEDIA_SEEK_FORWARD));
        multimap.put("actions", new Icon("media-skip-backward", TangoProject.MEDIA_SKIP_BACKWARD));
        multimap.put("actions", new Icon("media-skip-forward", TangoProject.MEDIA_SKIP_FORWARD));
        //multimap.put("actions", new Icon("object-flip-horizontal", TangoProject.OBJECT_FLIP_HORIZONTAL));
        //multimap.put("actions", new Icon("object-flip-vertical", TangoProject.OBJECT_FLIP_VERTICAL));
        //multimap.put("actions", new Icon("object-rotate-left", TangoProject.OBJECT_ROTATE_LEFT));
        //multimap.put("actions", new Icon("object-rotate-right", TangoProject.OBJECT_ROTATE_RIGHT));
        multimap.put("actions", new Icon("process-stop", TangoProject.PROCESS_STOP));
        multimap.put("actions", new Icon("system-lock-screen", TangoProject.SYSTEM_LOCK_SCREEN));
        multimap.put("actions", new Icon("system-log-out", TangoProject.SYSTEM_LOG_OUT));
        //multimap.put("actions", new Icon("system-run", TangoProject.SYSTEM_RUN));
        multimap.put("actions", new Icon("system-search", TangoProject.SYSTEM_SEARCH));
        multimap.put("actions", new Icon("system-shutdown", TangoProject.SYSTEM_SHUTDOWN));
        multimap.put("actions", new Icon("tab-new", TangoProject.TAB_NEW));
        //multimap.put("actions", new Icon("tools-check-spelling", TangoProject.TOOLS_CHECK_SPELLING));
        multimap.put("actions", new Icon("view-fullscreen", TangoProject.VIEW_FULLSCREEN));
        multimap.put("actions", new Icon("view-refresh", TangoProject.VIEW_REFRESH));
        multimap.put("actions", new Icon("view-sort-ascending", TangoProject.VIEW_SORT_ASCENDING));
        multimap.put("actions", new Icon("view-sort-descending", TangoProject.VIEW_SORT_DESCENDING));
        //multimap.put("actions", new Icon("window-close", TangoProject.WINDOW_CLOSE));
        multimap.put("actions", new Icon("window-new", TangoProject.WINDOW_NEW));
        //multimap.put("actions", new Icon("zoom-best-fit", TangoProject.ZOOM_BEST_FIT));
        //multimap.put("actions", new Icon("zoom-in", TangoProject.ZOOM_IN));
        //multimap.put("actions", new Icon("zoom-original", TangoProject.ZOOM_ORIGINAL));
        //multimap.put("actions", new Icon("zoom-out", TangoProject.ZOOM_OUT));

        //multimap.put("animations", new Icon("process-working", TangoProject.PROCESS_WORKING));

        multimap.put("apps", new Icon("accessories-calculator", TangoProject.ACCESSORIES_CALCULATOR));
        multimap.put("apps", new Icon("accessories-character-map", TangoProject.ACCESSORIES_CHARACTER_MAP));
        //multimap.put("apps", new Icon("accessories-dictionary", TangoProject.ACCESSORIES_DICTIONARY));
        multimap.put("apps", new Icon("accessories-text-editor", TangoProject.ACCESSORIES_TEXT_EDITOR));
        multimap.put("apps", new Icon("help-browser", TangoProject.HELP_BROWSER));
        multimap.put("apps", new Icon("internet-group-chat", TangoProject.INTERNET_GROUP_CHAT));
        multimap.put("apps", new Icon("internet-mail", TangoProject.INTERNET_MAIL));
        multimap.put("apps", new Icon("internet-web-browser", TangoProject.INTERNET_WEB_BROWSER));
        multimap.put("apps", new Icon("office-calendar", TangoProject.OFFICE_CALENDAR));
        //multimap.put("apps", new Icon("multimedia-volume-control", TangoProject.MULTIMEDIA_VOLUME_CONTROL));
        multimap.put("apps", new Icon("preferences-desktop-accessibility", TangoProject.PREFERENCES_DESKTOP_ACCESSIBILITY));
        multimap.put("apps", new Icon("preferences-desktop-assistive-technology", TangoProject.PREFERENCES_DESKTOP_ASSISTIVE_TECHNOLOGY));
        multimap.put("apps", new Icon("preferences-desktop-font", TangoProject.PREFERENCES_DESKTOP_FONT));
        //multimap.put("apps", new Icon("preferences-desktop-keyboard", TangoProject.PREFERENCES_DESKTOP_KEYBOARD));
        multimap.put("apps", new Icon("preferences-desktop-keyboard-shortcuts", TangoProject.PREFERENCES_DESKTOP_KEYBOARD_SHORTCUTS));
        multimap.put("apps", new Icon("preferences-desktop-locale", TangoProject.PREFERENCES_DESKTOP_LOCALE));
        multimap.put("apps", new Icon("preferences-desktop-multimedia", TangoProject.PREFERENCES_DESKTOP_MULTIMEDIA));
        multimap.put("apps", new Icon("preferences-desktop-remote-desktop", TangoProject.PREFERENCES_DESKTOP_REMOTE_DESKTOP));
        multimap.put("apps", new Icon("preferences-desktop-screensaver", TangoProject.PREFERENCES_DESKTOP_SCREENSAVER));
        multimap.put("apps", new Icon("preferences-desktop-theme", TangoProject.PREFERENCES_DESKTOP_THEME));
        multimap.put("apps", new Icon("preferences-desktop-wallpaper", TangoProject.PREFERENCES_DESKTOP_WALLPAPER));
        multimap.put("apps", new Icon("preferences-system-network-proxy", TangoProject.PREFERENCES_SYSTEM_NETWORK_PROXY));
        multimap.put("apps", new Icon("preferences-system-sesson", TangoProject.PREFERENCES_SYSTEM_SESSION));
        multimap.put("apps", new Icon("preferences-system-windows", TangoProject.PREFERENCES_SYSTEM_WINDOWS));
        multimap.put("apps", new Icon("system-file-manager", TangoProject.SYSTEM_FILE_MANAGER));
        multimap.put("apps", new Icon("system-users", TangoProject.SYSTEM_USERS));
        //multimap.put("apps", new Icon("system-software-update", TangoProject.SYSTEM_SOFTWARE_UPDATE));
        multimap.put("apps", new Icon("utilities-system-monitor", TangoProject.UTILITIES_SYSTEM_MONITOR));
        multimap.put("apps", new Icon("utilities-terminal", TangoProject.UTILITIES_TERMINAL));

        multimap.put("categories", new Icon("applications-accessories", TangoProject.APPLICATIONS_ACCESSORIES));
        //multimap.put("categories", new Icon("applications-development", TangoProject.APPLICATIONS_DEVELOPMENT));
        //multimap.put("categories", new Icon("applications-engineering", TangoProject.APPLICATIONS_ENGINEERING));
        multimap.put("categories", new Icon("applications-games", TangoProject.APPLICATIONS_GAMES));
        multimap.put("categories", new Icon("applications-graphics", TangoProject.APPLICATIONS_GRAPHICS));
        multimap.put("categories", new Icon("applications-internet", TangoProject.APPLICATIONS_INTERNET));
        multimap.put("categories", new Icon("applications-multimedia", TangoProject.APPLICATIONS_MULTIMEDIA));
        multimap.put("categories", new Icon("applications-office", TangoProject.APPLICATIONS_OFFICE));
        multimap.put("categories", new Icon("applications-other", TangoProject.APPLICATIONS_OTHER));
        //multimap.put("categories", new Icon("applications-science", TangoProject.APPLICATIONS_SCIENCE));
        multimap.put("categories", new Icon("applications-system", TangoProject.APPLICATIONS_SYSTEM));
        //multimap.put("categories", new Icon("applications-utilities", TangoProject.APPLICATIONS_UTILITIES));
        multimap.put("categories", new Icon("preferences-desktop", TangoProject.PREFERENCES_DESKTOP));
        multimap.put("categories", new Icon("preferences-desktop-peripherals", TangoProject.PREFERENCES_DESKTOP_PERIPHERALS));
        //multimap.put("categories", new Icon("preferences-desktop-personal", TangoProject.PREFERENCES_DESKTOP_PERSONAL));
        //multimap.put("categories", new Icon("preferences-other", TangoProject.PREFERENCES_OTHER));
        multimap.put("categories", new Icon("preferences-system", TangoProject.PREFERENCES_SYSTEM));
        //multimap.put("categories", new Icon("preferences-system-network", TangoProject.PREFERENCES_SYSTEM_NETWORK));
        //multimap.put("categories", new Icon("system-help", TangoProject.SYSTEM_HELP));

        multimap.put("devices", new Icon("audio-card", TangoProject.AUDIO_CARD));
        multimap.put("devices", new Icon("audio-input-microphone", TangoProject.AUDIO_INPUT_MICROPHONE));
        multimap.put("devices", new Icon("battery", TangoProject.BATTERY));
        multimap.put("devices", new Icon("camera-photo", TangoProject.CAMERA_PHOTO));
        multimap.put("devices", new Icon("camera-video", TangoProject.CAMERA_VIDEO));
        multimap.put("devices", new Icon("computer", TangoProject.COMPUTER));
        multimap.put("devices", new Icon("drive-harddisk", TangoProject.DRIVE_HARDDISK));
        multimap.put("devices", new Icon("drive-optical", TangoProject.DRIVE_OPTICAL));
        multimap.put("devices", new Icon("drive-removable-media", TangoProject.DRIVE_REMOVABLE_MEDIA));
        multimap.put("devices", new Icon("input-gaming", TangoProject.INPUT_GAMING));
        multimap.put("devices", new Icon("input-keyboard", TangoProject.INPUT_KEYBOARD));
        multimap.put("devices", new Icon("input-mouse", TangoProject.INPUT_MOUSE));
        multimap.put("devices", new Icon("media-flash", TangoProject.MEDIA_FLASH));
        multimap.put("devices", new Icon("media-floppy", TangoProject.MEDIA_FLOPPY));
        multimap.put("devices", new Icon("media-optical", TangoProject.MEDIA_OPTICAL));
        //multimap.put("devices", new Icon("media-tape", TangoProject.MEDIA_TAPE));
        //multimap.put("devices", new Icon("modem", TangoProject.MODEM));
        multimap.put("devices", new Icon("multimedia-player", TangoProject.MULTIMEDIA_PLAYER));
        multimap.put("devices", new Icon("network-wired", TangoProject.NETWORK_WIRED));
        multimap.put("devices", new Icon("network-wireless", TangoProject.NETWORK_WIRELESS));
        multimap.put("devices", new Icon("printer", TangoProject.PRINTER));
        multimap.put("devices", new Icon("video-display", TangoProject.VIDEO_DISPLAY));

        //multimap.put("emblems", new Icon("emblem-default", TangoProject.EMBLEM_DEFAULT));
        //multimap.put("emblems", new Icon("emblem-documents", TangoProject.EMBLEM_DOCUMENTS));
        //multimap.put("emblems", new Icon("emblem-downloads", TangoProject.EMBLEM_DOWNLOADS));
        multimap.put("emblems", new Icon("emblem-favorite", TangoProject.EMBLEM_FAVORITE));
        multimap.put("emblems", new Icon("emblem-important", TangoProject.EMBLEM_IMPORTANT));
        //multimap.put("emblems", new Icon("emblem-mail", TangoProject.EMBLEM_MAIL));
        multimap.put("emblems", new Icon("emblem-photos", TangoProject.EMBLEM_PHOTOS));
        multimap.put("emblems", new Icon("emblem-readonly", TangoProject.EMBLEM_READONLY));
        //multimap.put("emblems", new Icon("emblem-shared", TangoProject.EMBLEM_SHARED));
        multimap.put("emblems", new Icon("emblem-symbolic-link", TangoProject.EMBLEM_SYMBOLIC_LINK));
        //multimap.put("emblems", new Icon("emblem-synchronized", TangoProject.EMBLEM_SYNCHRONIZED));
        multimap.put("emblems", new Icon("emblem-system", TangoProject.EMBLEM_SYSTEM));
        multimap.put("emblems", new Icon("emblem-unreadable", TangoProject.EMBLEM_UNREADABLE));

        multimap.put("emotes", new Icon("face-angel", TangoProject.FACE_ANGEL));
        multimap.put("emotes", new Icon("face-crying", TangoProject.FACE_CRYING));
        multimap.put("emotes", new Icon("face-devilish", TangoProject.FACE_DEVILISH));
        multimap.put("emotes", new Icon("face-glasses", TangoProject.FACE_GLASSES));
        multimap.put("emotes", new Icon("face-grin", TangoProject.FACE_GRIN));
        multimap.put("emotes", new Icon("face-kiss", TangoProject.FACE_KISS));
        multimap.put("emotes", new Icon("face-monkey", TangoProject.FACE_MONKEY));
        multimap.put("emotes", new Icon("face-plain", TangoProject.FACE_PLAIN));
        multimap.put("emotes", new Icon("face-sad", TangoProject.FACE_SAD));
        multimap.put("emotes", new Icon("face-smile", TangoProject.FACE_SMILE));
        multimap.put("emotes", new Icon("face-smile-big", TangoProject.FACE_SMILE_BIG));
        //multimap.put("emotes", new Icon("face-smirk", TangoProject.FACE_SMIRK));
        multimap.put("emotes", new Icon("face-surprise", TangoProject.FACE_SURPRISE));
        multimap.put("emotes", new Icon("face-wink", TangoProject.FACE_WINK));

        //multimap.put("intl", new Icon("flag-..", TangoProject.FLAG_..));

        multimap.put("mimetypes", new Icon("application-x-executable", TangoProject.APPLICATION_X_EXECUTABLE));
        multimap.put("mimetypes", new Icon("application-certificate", TangoProject.APPLICATION_CERTIFICATE));
        multimap.put("mimetypes", new Icon("audio-x-generic", TangoProject.AUDIO_X_GENERIC));
        multimap.put("mimetypes", new Icon("font-x-generic", TangoProject.FONT_X_GENERIC));
        multimap.put("mimetypes", new Icon("image-x-generic", TangoProject.IMAGE_X_GENERIC));
        multimap.put("mimetypes", new Icon("package-x-generic", TangoProject.PACKAGE_X_GENERIC));
        multimap.put("mimetypes", new Icon("text-html", TangoProject.TEXT_HTML));
        multimap.put("mimetypes", new Icon("text-x-generic", TangoProject.TEXT_X_GENERIC));
        multimap.put("mimetypes", new Icon("text-x-generic-template", TangoProject.TEXT_X_GENERIC_TEMPLATE));
        multimap.put("mimetypes", new Icon("text-x-script", TangoProject.TEXT_X_SCRIPT));
        multimap.put("mimetypes", new Icon("video-x-generic", TangoProject.VIDEO_X_GENERIC));
        multimap.put("mimetypes", new Icon("x-office-address-book", TangoProject.X_OFFICE_ADDRESS_BOOK));
        multimap.put("mimetypes", new Icon("x-office-calendar", TangoProject.X_OFFICE_CALENDAR));
        multimap.put("mimetypes", new Icon("x-office-document", TangoProject.X_OFFICE_DOCUMENT));
        multimap.put("mimetypes", new Icon("x-office-document-template", TangoProject.X_OFFICE_DOCUMENT_TEMPLATE));
        multimap.put("mimetypes", new Icon("x-office-drawing", TangoProject.X_OFFICE_DRAWING));
        multimap.put("mimetypes", new Icon("x-office-drawing-template", TangoProject.X_OFFICE_DRAWING_TEMPLATE));
        multimap.put("mimetypes", new Icon("x-office-presentation", TangoProject.X_OFFICE_PRESENTATION));
        multimap.put("mimetypes", new Icon("x-office-presentation-template", TangoProject.X_OFFICE_PRESENTATION_TEMPLATE));
        multimap.put("mimetypes", new Icon("x-office-spreadsheet", TangoProject.X_OFFICE_SPREADSHEET));
        multimap.put("mimetypes", new Icon("x-office-spreadsheet-template", TangoProject.X_OFFICE_SPREADSHEET_TEMPLATE));

        multimap.put("places", new Icon("folder", TangoProject.FOLDER));
        multimap.put("places", new Icon("folder-remote", TangoProject.FOLDER_REMOTE));
        multimap.put("places", new Icon("folder-saved-search", TangoProject.FOLDER_SAVED_SEARCH));
        multimap.put("places", new Icon("network-server", TangoProject.NETWORK_SERVER));
        multimap.put("places", new Icon("network-workgroup", TangoProject.NETWORK_WORKGROUP));
        multimap.put("places", new Icon("start-here", TangoProject.START_HERE));
        multimap.put("places", new Icon("user-desktop", TangoProject.USER_DESKTOP));
        multimap.put("places", new Icon("user-home", TangoProject.USER_HOME));
        multimap.put("places", new Icon("user-trash", TangoProject.USER_TRASH));

        //multimap.put("status", new Icon("appointment-missed", TangoProject.APPOINTMENT_MISSED));
        //multimap.put("status", new Icon("appointment-soon", TangoProject.APPOINTMENT_SOON));
        multimap.put("status", new Icon("audio-volume-high", TangoProject.AUDIO_VOLUME_HIGH));
        multimap.put("status", new Icon("audio-volume-low", TangoProject.AUDIO_VOLUME_LOW));
        multimap.put("status", new Icon("audio-volume-medium", TangoProject.AUDIO_VOLUME_MEDIUM));
        multimap.put("status", new Icon("audio-volume-muted", TangoProject.AUDIO_VOLUME_MUTED));
        multimap.put("status", new Icon("battery-caution", TangoProject.BATTERY_CAUTION));
        //multimap.put("status", new Icon("battery-low", TangoProject.BATTERY_LOW));
        multimap.put("status", new Icon("dialog-error", TangoProject.DIALOG_ERROR));
        multimap.put("status", new Icon("dialog-information", TangoProject.DIALOG_INFORMATION));
        //multimap.put("status", new Icon("dialog-password", TangoProject.DIALOG_PASSWORD));
        //multimap.put("status", new Icon("dialog-question", TangoProject.DIALOG_QUESTION));
        multimap.put("status", new Icon("dialog-warning", TangoProject.DIALOG_WARNING));
        multimap.put("status", new Icon("folder-drag-accept", TangoProject.FOLDER_DRAG_ACCEPT));
        multimap.put("status", new Icon("folder-open", TangoProject.FOLDER_OPEN));
        multimap.put("status", new Icon("folder-visiting", TangoProject.FOLDER_VISITING));
        multimap.put("status", new Icon("image-loading", TangoProject.IMAGE_LOADING));
        multimap.put("status", new Icon("image-missing", TangoProject.IMAGE_MISSING));
        multimap.put("status", new Icon("mail-attachment", TangoProject.MAIL_ATTACHMENT));
        //multimap.put("status", new Icon("mail-unread", TangoProject.MAIL_UNREAD));
        //multimap.put("status", new Icon("mail-read", TangoProject.MAIL_READ));
        //multimap.put("status", new Icon("mail-replied", TangoProject.MAIL_REPLIED));
        //multimap.put("status", new Icon("mail-signed", TangoProject.MAIL_SIGNED));
        //multimap.put("status", new Icon("mail-signed-verified", TangoProject.MAIL_SIGNED_VERIFIED));
        //multimap.put("status", new Icon("media-playlist-repeat", TangoProject.MEDIA_PLAYLIST_REPEAT));
        //multimap.put("status", new Icon("media-playlist-shuffle", TangoProject.MEDIA_PLAYLIST_SHUFFLE));
        multimap.put("status", new Icon("network-error", TangoProject.NETWORK_ERROR));
        multimap.put("status", new Icon("network-idle", TangoProject.NETWORK_IDLE));
        multimap.put("status", new Icon("network-offline", TangoProject.NETWORK_OFFLINE));
        multimap.put("status", new Icon("network-receive", TangoProject.NETWORK_RECEIVE));
        multimap.put("status", new Icon("network-transmit", TangoProject.NETWORK_TRANSMIT));
        multimap.put("status", new Icon("network-transmit-receive", TangoProject.NETWORK_TRANSMIT_RECEIVE));
        multimap.put("status", new Icon("network-wireless-encrypted", TangoProject.NETWORK_WIRELESS_ENCRYPTED));
        multimap.put("status", new Icon("printer-error", TangoProject.PRINTER_ERROR));
        //multimap.put("status", new Icon("printer-printing", TangoProject.PRINTER_PRINTING));
        //multimap.put("status", new Icon("security-high", TangoProject.SECURITY_HIGH));
        //multimap.put("status", new Icon("security-medium", TangoProject.SECURITY_MEDIUM));
        //multimap.put("status", new Icon("security-low", TangoProject.SECURITY_LOW));
        //multimap.put("status", new Icon("software-update-available", TangoProject.SOFTWARE_UPDATE_AVAILABLE));
        //multimap.put("status", new Icon("software-update-urgent", TangoProject.SOFTWARE_UPDATE_URGENT));
        //multimap.put("status", new Icon("sync-error", TangoProject.SYNC_ERROR));
        //multimap.put("status", new Icon("sync-synchronizing", TangoProject.SYNC_SYNCHRONIZING));
        //multimap.put("status", new Icon("task-due", TangoProject.TASK_DUE));
        //multimap.put("status", new Icon("task-passed-due", TangoProject.TASK_PASSED_DUE));
        //multimap.put("status", new Icon("user-away", TangoProject.USER_AWAY));
        //multimap.put("status", new Icon("user-idle", TangoProject.USER_IDLE));
        //multimap.put("status", new Icon("user-offline", TangoProject.USER_OFFLINE));
        //multimap.put("status", new Icon("user-online", TangoProject.USER_ONLINE));
        multimap.put("status", new Icon("user-trash-full", TangoProject.USER_TRASH_FULL));
        multimap.put("status", new Icon("weather-clear", TangoProject.WEATHER_CLEAR));
        multimap.put("status", new Icon("weather-clear-night", TangoProject.WEATHER_CLEAR_NIGHT));
        multimap.put("status", new Icon("weather-few-clouds", TangoProject.WEATHER_FEW_CLOUDS));
        multimap.put("status", new Icon("weather-few-clouds-night", TangoProject.WEATHER_FEW_CLOUDS_NIGHT));
        //multimap.put("status", new Icon("weather-fog", TangoProject.WEATHER_FOG));
        multimap.put("status", new Icon("weather-overcast", TangoProject.WEATHER_OVERCAST));
        multimap.put("status", new Icon("weather-severe-alert", TangoProject.WEATHER_SEVERE_ALERT));
        multimap.put("status", new Icon("weather-showers", TangoProject.WEATHER_SHOWERS));
        multimap.put("status", new Icon("weather-showers-scattered", TangoProject.WEATHER_SHOWERS_SCATTERED));
        multimap.put("status", new Icon("weather-snow", TangoProject.WEATHER_SNOW));
        multimap.put("status", new Icon("weather-storm", TangoProject.WEATHER_STORM));
    }

    /**
     * Intialize the list of contexts.
     */
    private void initializeContexts()
    {
        SortedSet keys = new TreeSet();
        keys.addAll(multimap.keySet());

        for (Iterator i = keys.iterator(); i.hasNext(); )
        {
            contexts.add(new Context((String) i.next()));
        }

        keys = null;
    }

    /** {@inheritDoc} */
    public void addTreeModelListener(final TreeModelListener l)
    {
        // ignore
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    public Object getRoot()
    {
        return root;
    }

    /** {@inheritDoc} */
    public boolean isLeaf(final Object node)
    {
        return (getChildCount(node) == 0);
    }

    /** {@inheritDoc} */
    public void removeTreeModelListener(final TreeModelListener l)
    {
        // ignore
    }

    /** {@inheritDoc} */
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


        /** {@inheritDoc} */
        public String getName()
        {
            return name;
        }

        /** {@inheritDoc} */
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
            List icons = (List) multimap.get(name);
            return icons.get(index);
        }

        /**
         * Return the number of icons in this context.
         *
         * @return the number of icons in this context
         */
        public int size()
        {
            List icons = (List) multimap.get(name);
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
            List icons = (List) multimap.get(name);
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


        /** {@inheritDoc} */
        public String getName()
        {
            return name;
        }

        /** {@inheritDoc} */
        public IconBundle getIconBundle()
        {
            return iconBundle;
        }
    }
}
