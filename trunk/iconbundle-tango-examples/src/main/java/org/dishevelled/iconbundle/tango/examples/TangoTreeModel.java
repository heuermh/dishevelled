/*

    dsh-iconbundle-tango-examples  Examples using the iconbundle-tango library.
    Copyright (c) 2005-2007 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.tango.examples;

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
        multiMap.put("actions", new Icon("contact-new", TangoProject.CONTACT_NEW));
        //multiMap.put("actions", new Icon("dialog-cancel", TangoProject.DIALOG_CANCEL));
        //multiMap.put("actions", new Icon("dialog-close", TangoProject.DIALOG_CLOSE));
        //multiMap.put("actions", new Icon("dialog-ok", TangoProject.DIALOG_OK));
        multiMap.put("actions", new Icon("document-new", TangoProject.DOCUMENT_NEW));
        multiMap.put("actions", new Icon("document-open", TangoProject.DOCUMENT_OPEN));
        //multiMap.put("actions", new Icon("document-open-recent", TangoProject.DOCUMENT_OPEN_RECENT));
        //multiMap.put("actions", new Icon("document-page-setup", TangoProject.DOCUMENT_PAGE_SETUP));
        multiMap.put("actions", new Icon("document-print", TangoProject.DOCUMENT_PRINT));
        multiMap.put("actions", new Icon("document-print-preview", TangoProject.DOCUMENT_PRINT_PREVIEW));
        multiMap.put("actions", new Icon("document-properties", TangoProject.DOCUMENT_PROPERTIES));
        //multiMap.put("actions", new Icon("document-revert", TangoProject.DOCUMENT_REVERT));
        multiMap.put("actions", new Icon("document-save", TangoProject.DOCUMENT_SAVE));
        multiMap.put("actions", new Icon("document-save-as", TangoProject.DOCUMENT_SAVE_AS));
        multiMap.put("actions", new Icon("edit-copy", TangoProject.EDIT_COPY));
        multiMap.put("actions", new Icon("edit-cut", TangoProject.EDIT_CUT));
        multiMap.put("actions", new Icon("edit-delete", TangoProject.EDIT_DELETE));
        multiMap.put("actions", new Icon("edit-find", TangoProject.EDIT_FIND));
        multiMap.put("actions", new Icon("edit-find-replace", TangoProject.EDIT_FIND_REPLACE));
        multiMap.put("actions", new Icon("edit-paste", TangoProject.EDIT_PASTE));
        multiMap.put("actions", new Icon("edit-redo", TangoProject.EDIT_REDO));
        multiMap.put("actions", new Icon("edit-select-all", TangoProject.EDIT_SELECT_ALL));
        multiMap.put("actions", new Icon("edit-undo", TangoProject.EDIT_UNDO));
        multiMap.put("actions", new Icon("format-indent-less", TangoProject.FORMAT_INDENT_LESS));
        multiMap.put("actions", new Icon("format-indent-more", TangoProject.FORMAT_INDENT_MORE));
        multiMap.put("actions", new Icon("format-justify-center", TangoProject.FORMAT_JUSTIFY_CENTER));
        multiMap.put("actions", new Icon("format-justify-fill", TangoProject.FORMAT_JUSTIFY_FILL));
        multiMap.put("actions", new Icon("format-justify-left", TangoProject.FORMAT_JUSTIFY_LEFT));
        multiMap.put("actions", new Icon("format-justify-right", TangoProject.FORMAT_JUSTIFY_RIGHT));
        multiMap.put("actions", new Icon("format-text-bold", TangoProject.FORMAT_TEXT_BOLD));
        //multiMap.put("actions", new Icon("format-text-direction-ltr", TangoProject.FORMAT_TEXT_DIRECTION_LTR));
        //multiMap.put("actions", new Icon("format-text-direction-rtl", TangoProject.FORMAT_TEXT_DIRECTION_RTL));
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
        //multiMap.put("actions", new Icon("help-about", TangoProject.HELP_ABOUT));
        //multiMap.put("actions", new Icon("help-contents", TangoProject.HELP_CONTENTS));
        //multiMap.put("actions", new Icon("help-faq", TangoProject.HELP_FAQ));
        //multiMap.put("actions", new Icon("insert-image", TangoProject.INSERT_IMAGE));
        //multiMap.put("actions", new Icon("insert-link", TangoProject.INSERT_LINK));
        //multiMap.put("actions", new Icon("insert-object", TangoProject.INSERT_OBJECT));
        //multiMap.put("actions", new Icon("insert-text", TangoProject.INSERT_TEXT));
        multiMap.put("actions", new Icon("list-add", TangoProject.LIST_ADD));
        multiMap.put("actions", new Icon("list-remove", TangoProject.LIST_REMOVE));
        multiMap.put("actions", new Icon("mail-forward", TangoProject.MAIL_FORWARD));
        //multiMap.put("actions", new Icon("mail-mark-important", TangoProject.MAIL_MARK_IMPORTANT));
        multiMap.put("actions", new Icon("mail-mark-junk", TangoProject.MAIL_MARK_JUNK));
        //multiMap.put("actions", new Icon("mail-mark-notjunk", TangoProject.MAIL_MARK_NOTJUNK));
        //multiMap.put("actions", new Icon("mail-mark-read", TangoProject.MAIL_MARK_READ));
        //multiMap.put("actions", new Icon("mail-mark-unread", TangoProject.MAIL_MARK_UNREAD));
        multiMap.put("actions", new Icon("mail-message-new", TangoProject.MAIL_MESSAGE_NEW));
        multiMap.put("actions", new Icon("mail-reply-all", TangoProject.MAIL_REPLY_ALL));
        multiMap.put("actions", new Icon("mail-reply-sender", TangoProject.MAIL_REPLY_SENDER));
        multiMap.put("actions", new Icon("mail-send-receive", TangoProject.MAIL_SEND_RECEIVE));
        multiMap.put("actions", new Icon("media-eject", TangoProject.MEDIA_EJECT));
        multiMap.put("actions", new Icon("media-playback-pause", TangoProject.MEDIA_PLAYBACK_PAUSE));
        multiMap.put("actions", new Icon("media-playback-start", TangoProject.MEDIA_PLAYBACK_START));
        multiMap.put("actions", new Icon("media-playback-stop", TangoProject.MEDIA_PLAYBACK_STOP));
        multiMap.put("actions", new Icon("media-record", TangoProject.MEDIA_RECORD));
        multiMap.put("actions", new Icon("media-seek-backward", TangoProject.MEDIA_SEEK_BACKWARD));
        multiMap.put("actions", new Icon("media-seek-forward", TangoProject.MEDIA_SEEK_FORWARD));
        multiMap.put("actions", new Icon("media-skip-backward", TangoProject.MEDIA_SKIP_BACKWARD));
        multiMap.put("actions", new Icon("media-skip-forward", TangoProject.MEDIA_SKIP_FORWARD));
        //multiMap.put("actions", new Icon("object-flip-horizontal", TangoProject.OBJECT_FLIP_HORIZONTAL));
        //multiMap.put("actions", new Icon("object-flip-vertical", TangoProject.OBJECT_FLIP_VERTICAL));
        //multiMap.put("actions", new Icon("object-rotate-left", TangoProject.OBJECT_ROTATE_LEFT));
        //multiMap.put("actions", new Icon("object-rotate-right", TangoProject.OBJECT_ROTATE_RIGHT));
        multiMap.put("actions", new Icon("system-lock-screen", TangoProject.SYSTEM_LOCK_SCREEN));
        multiMap.put("actions", new Icon("system-log-out", TangoProject.SYSTEM_LOG_OUT));
        //multiMap.put("actions", new Icon("system-run", TangoProject.SYSTEM_RUN));
        multiMap.put("actions", new Icon("system-search", TangoProject.SYSTEM_SEARCH));
        //multiMap.put("actions", new Icon("tools-check-spelling", TangoProject.TOOLS_CHECK_SPELLING));
        multiMap.put("actions", new Icon("view-fullscreen", TangoProject.VIEW_FULLSCREEN));
        multiMap.put("actions", new Icon("view-refresh", TangoProject.VIEW_REFRESH));
        //multiMap.put("actions", new Icon("view-sort-ascending", TangoProject.VIEW_SORT_ASCENDING));
        //multiMap.put("actions", new Icon("view-sort-descending", TangoProject.VIEW_SORT_DESCENDING));
        //multiMap.put("actions", new Icon("window-close", TangoProject.WINDOW_CLOSE));
        multiMap.put("actions", new Icon("window-new", TangoProject.WINDOW_NEW));
        //multiMap.put("actions", new Icon("zoom-best-fit", TangoProject.ZOOM_BEST_FIT));
        //multiMap.put("actions", new Icon("zoom-in", TangoProject.ZOOM_IN));
        //multiMap.put("actions", new Icon("zoom-original", TangoProject.ZOOM_ORIGINAL));
        //multiMap.put("actions", new Icon("zoom-out", TangoProject.ZOOM_OUT));

        //multiMap.put("animations", new Icon("process-working", TangoProject.PROCESS_WORKING));

        multiMap.put("apps", new Icon("accessories-calculator", TangoProject.ACCESSORIES_CALCULATOR));
        multiMap.put("apps", new Icon("accessories-character-map", TangoProject.ACCESSORIES_CHARACTER_MAP));
        //multiMap.put("apps", new Icon("accessories-dictionary", TangoProject.ACCESSORIES_DICTIONARY));
        multiMap.put("apps", new Icon("accessories-text-editor", TangoProject.ACCESSORIES_TEXT_EDITOR));
        multiMap.put("apps", new Icon("help-browser", TangoProject.HELP_BROWSER));
        multiMap.put("apps", new Icon("preferences-desktop-accessibility", TangoProject.PREFERENCES_DESKTOP_ACCESSIBILITY));
        multiMap.put("apps", new Icon("preferences-desktop-font", TangoProject.PREFERENCES_DESKTOP_FONT));
        //multiMap.put("apps", new Icon("preferences-desktop-keyboard", TangoProject.PREFERENCES_DESKTOP_KEYBOARD));
        multiMap.put("apps", new Icon("preferences-desktop-locale", TangoProject.PREFERENCES_DESKTOP_LOCALE));
        //multiMap.put("apps", new Icon("preferences-desktop-multimedia", TangoProject.PREFERENCES_DESKTOP_MULTIMEDIA));
        multiMap.put("apps", new Icon("preferences-desktop-screensaver", TangoProject.PREFERENCES_DESKTOP_SCREENSAVER));
        multiMap.put("apps", new Icon("preferences-desktop-theme", TangoProject.PREFERENCES_DESKTOP_THEME));
        multiMap.put("apps", new Icon("preferences-desktop-wallpaper", TangoProject.PREFERENCES_DESKTOP_WALLPAPER));
        multiMap.put("apps", new Icon("system-file-manager", TangoProject.SYSTEM_FILE_MANAGER));
        //multiMap.put("apps", new Icon("system-software-update", TangoProject.SYSTEM_SOFTWARE_UPDATE));
        multiMap.put("apps", new Icon("utilities-system-monitor", TangoProject.UTILITIES_SYSTEM_MONITOR));
        multiMap.put("apps", new Icon("utilities-terminal", TangoProject.UTILITIES_TERMINAL));

        multiMap.put("categories", new Icon("applications-accessories", TangoProject.APPLICATIONS_ACCESSORIES));
        //multiMap.put("categories", new Icon("applications-development", TangoProject.APPLICATIONS_DEVELOPMENT));
        //multiMap.put("categories", new Icon("applications-engineering", TangoProject.APPLICATIONS_ENGINEERING));
        multiMap.put("categories", new Icon("applications-games", TangoProject.APPLICATIONS_GAMES));
        multiMap.put("categories", new Icon("applications-graphics", TangoProject.APPLICATIONS_GRAPHICS));
        multiMap.put("categories", new Icon("applications-internet", TangoProject.APPLICATIONS_INTERNET));
        multiMap.put("categories", new Icon("applications-multimedia", TangoProject.APPLICATIONS_MULTIMEDIA));
        multiMap.put("categories", new Icon("applications-office", TangoProject.APPLICATIONS_OFFICE));
        multiMap.put("categories", new Icon("applications-other", TangoProject.APPLICATIONS_OTHER));
        //multiMap.put("categories", new Icon("applications-science", TangoProject.APPLICATIONS_SCIENCE));
        multiMap.put("categories", new Icon("applications-system", TangoProject.APPLICATIONS_SYSTEM));
        //multiMap.put("categories", new Icon("applications-utilities", TangoProject.APPLICATIONS_UTILITIES));
        multiMap.put("categories", new Icon("preferences-desktop", TangoProject.PREFERENCES_DESKTOP));
        multiMap.put("categories", new Icon("preferences-desktop-peripherals", TangoProject.PREFERENCES_DESKTOP_PERIPHERALS));
        //multiMap.put("categories", new Icon("preferences-desktop-personal", TangoProject.PREFERENCES_DESKTOP_PERSONAL));
        //multiMap.put("categories", new Icon("preferences-other", TangoProject.PREFERENCES_OTHER));
        multiMap.put("categories", new Icon("preferences-system", TangoProject.PREFERENCES_SYSTEM));
        //multiMap.put("categories", new Icon("preferences-system-network", TangoProject.PREFERENCES_SYSTEM_NETWORK));
        //multiMap.put("categories", new Icon("system-help", TangoProject.SYSTEM_HELP));

        multiMap.put("devices", new Icon("audio-card", TangoProject.AUDIO_CARD));
        multiMap.put("devices", new Icon("audio-input-microphone", TangoProject.AUDIO_INPUT_MICROPHONE));
        multiMap.put("devices", new Icon("battery", TangoProject.BATTERY));
        multiMap.put("devices", new Icon("camera-photo", TangoProject.CAMERA_PHOTO));
        multiMap.put("devices", new Icon("camera-video", TangoProject.CAMERA_VIDEO));
        multiMap.put("devices", new Icon("computer", TangoProject.COMPUTER));
        multiMap.put("devices", new Icon("drive-harddisk", TangoProject.DRIVE_HARDDISK));
        multiMap.put("devices", new Icon("drive-optical", TangoProject.DRIVE_OPTICAL));
        multiMap.put("devices", new Icon("drive-removable-media", TangoProject.DRIVE_REMOVABLE_MEDIA));
        multiMap.put("devices", new Icon("input-gaming", TangoProject.INPUT_GAMING));
        multiMap.put("devices", new Icon("input-keyboard", TangoProject.INPUT_KEYBOARD));
        multiMap.put("devices", new Icon("input-mouse", TangoProject.INPUT_MOUSE));
        //multiMap.put("devices", new Icon("media-flash", TangoProject.MEDIA_FLASH));
        multiMap.put("devices", new Icon("media-floppy", TangoProject.MEDIA_FLOPPY));
        multiMap.put("devices", new Icon("media-optical", TangoProject.MEDIA_OPTICAL));
        //multiMap.put("devices", new Icon("media-tape", TangoProject.MEDIA_TAPE));
        //multiMap.put("devices", new Icon("modem", TangoProject.MODEM));
        multiMap.put("devices", new Icon("multimedia-player", TangoProject.MULTIMEDIA_PLAYER));
        multiMap.put("devices", new Icon("network-wired", TangoProject.NETWORK_WIRED));
        multiMap.put("devices", new Icon("network-wireless", TangoProject.NETWORK_WIRELESS));
        multiMap.put("devices", new Icon("printer", TangoProject.PRINTER));
        multiMap.put("devices", new Icon("video-display", TangoProject.VIDEO_DISPLAY));

        //multiMap.put("emblems", new Icon("emblem-default", TangoProject.EMBLEM_DEFAULT));
        //multiMap.put("emblems", new Icon("emblem-documents", TangoProject.EMBLEM_DOCUMENTS));
        //multiMap.put("emblems", new Icon("emblem-downloads", TangoProject.EMBLEM_DOWNLOADS));
        multiMap.put("emblems", new Icon("emblem-favorite", TangoProject.EMBLEM_FAVORITE));
        multiMap.put("emblems", new Icon("emblem-important", TangoProject.EMBLEM_IMPORTANT));
        //multiMap.put("emblems", new Icon("emblem-mail", TangoProject.EMBLEM_MAIL));
        multiMap.put("emblems", new Icon("emblem-photos", TangoProject.EMBLEM_PHOTOS));
        multiMap.put("emblems", new Icon("emblem-readonly", TangoProject.EMBLEM_READONLY));
        //multiMap.put("emblems", new Icon("emblem-shared", TangoProject.EMBLEM_SHARED));
        multiMap.put("emblems", new Icon("emblem-symbolic-link", TangoProject.EMBLEM_SYMBOLIC_LINK));
        //multiMap.put("emblems", new Icon("emblem-synchronized", TangoProject.EMBLEM_SYNCHRONIZED));
        multiMap.put("emblems", new Icon("emblem-system", TangoProject.EMBLEM_SYSTEM));
        multiMap.put("emblems", new Icon("emblem-unreadable", TangoProject.EMBLEM_UNREADABLE));

        multiMap.put("emotes", new Icon("face-angel", TangoProject.FACE_ANGEL));
        multiMap.put("emotes", new Icon("face-crying", TangoProject.FACE_CRYING));
        multiMap.put("emotes", new Icon("face-devil-grin", TangoProject.FACE_DEVIL_GRIN));
        //multiMap.put("emotes", new Icon("face-devil-grin", TangoProject.FACE_DEVIL_SAD));
        multiMap.put("emotes", new Icon("face-glasses", TangoProject.FACE_GLASSES));
        multiMap.put("emotes", new Icon("face-kiss", TangoProject.FACE_KISS));
        //multiMap.put("emotes", new Icon("face-monkey", TangoProject.FACE_MONKEY));
        multiMap.put("emotes", new Icon("face-plain", TangoProject.FACE_PLAIN));
        multiMap.put("emotes", new Icon("face-sad", TangoProject.FACE_SAD));
        multiMap.put("emotes", new Icon("face-smile", TangoProject.FACE_SMILE));
        multiMap.put("emotes", new Icon("face-smile-big", TangoProject.FACE_SMILE_BIG));
        //multiMap.put("emotes", new Icon("face-smirk", TangoProject.FACE_SMIRK));
        multiMap.put("emotes", new Icon("face-surprise", TangoProject.FACE_SURPRISE));
        multiMap.put("emotes", new Icon("face-wink", TangoProject.FACE_WINK));

        //multiMap.put("intl", new Icon("flag-..", TangoProject.FLAG_..));

        multiMap.put("mimetypes", new Icon("application-x-executable", TangoProject.APPLICATION_X_EXECUTABLE));
        multiMap.put("mimetypes", new Icon("audio-x-generic", TangoProject.AUDIO_X_GENERIC));
        multiMap.put("mimetypes", new Icon("font-x-generic", TangoProject.FONT_X_GENERIC));
        multiMap.put("mimetypes", new Icon("image-x-generic", TangoProject.IMAGE_X_GENERIC));
        multiMap.put("mimetypes", new Icon("package-x-generic", TangoProject.PACKAGE_X_GENERIC));
        multiMap.put("mimetypes", new Icon("text-html", TangoProject.TEXT_HTML));
        multiMap.put("mimetypes", new Icon("text-x-generic", TangoProject.TEXT_X_GENERIC));
        multiMap.put("mimetypes", new Icon("text-x-generic-template", TangoProject.TEXT_X_GENERIC_TEMPLATE));
        multiMap.put("mimetypes", new Icon("text-x-script", TangoProject.TEXT_X_SCRIPT));
        multiMap.put("mimetypes", new Icon("video-x-generic", TangoProject.VIDEO_X_GENERIC));
        multiMap.put("mimetypes", new Icon("x-office-address-book", TangoProject.X_OFFICE_ADDRESS_BOOK));
        multiMap.put("mimetypes", new Icon("x-office-calendar", TangoProject.X_OFFICE_CALENDAR));
        multiMap.put("mimetypes", new Icon("x-office-document", TangoProject.X_OFFICE_DOCUMENT));
        multiMap.put("mimetypes", new Icon("x-office-presentation", TangoProject.X_OFFICE_PRESENTATION));
        multiMap.put("mimetypes", new Icon("x-office-spreadsheet", TangoProject.X_OFFICE_SPREADSHEET));

        multiMap.put("places", new Icon("folder", TangoProject.FOLDER));
        multiMap.put("places", new Icon("folder-remote", TangoProject.FOLDER_REMOTE));
        multiMap.put("places", new Icon("network-server", TangoProject.NETWORK_SERVER));
        multiMap.put("places", new Icon("network-workgroup", TangoProject.NETWORK_WORKGROUP));
        multiMap.put("places", new Icon("start-here", TangoProject.START_HERE));
        multiMap.put("places", new Icon("user-desktop", TangoProject.USER_DESKTOP));
        multiMap.put("places", new Icon("user-home", TangoProject.USER_HOME));
        multiMap.put("places", new Icon("user-trash", TangoProject.USER_TRASH));

        //multiMap.put("status", new Icon("appointment-missed", TangoProject.APPOINTMENT_MISSED));
        //multiMap.put("status", new Icon("appointment-soon", TangoProject.APPOINTMENT_SOON));
        multiMap.put("status", new Icon("audio-volume-high", TangoProject.AUDIO_VOLUME_HIGH));
        multiMap.put("status", new Icon("audio-volume-low", TangoProject.AUDIO_VOLUME_LOW));
        multiMap.put("status", new Icon("audio-volume-medium", TangoProject.AUDIO_VOLUME_MEDIUM));
        multiMap.put("status", new Icon("audio-volume-muted", TangoProject.AUDIO_VOLUME_MUTED));
        multiMap.put("status", new Icon("battery-caution", TangoProject.BATTERY_CAUTION));
        //multiMap.put("status", new Icon("battery-low", TangoProject.BATTERY_LOW));
        multiMap.put("status", new Icon("dialog-error", TangoProject.DIALOG_ERROR));
        multiMap.put("status", new Icon("dialog-information", TangoProject.DIALOG_INFORMATION));
        //multiMap.put("status", new Icon("dialog-password", TangoProject.DIALOG_PASSWORD));
        //multiMap.put("status", new Icon("dialog-question", TangoProject.DIALOG_QUESTION));
        multiMap.put("status", new Icon("dialog-warning", TangoProject.DIALOG_WARNING));
        multiMap.put("status", new Icon("folder-drag-accept", TangoProject.FOLDER_DRAG_ACCEPT));
        multiMap.put("status", new Icon("folder-open", TangoProject.FOLDER_OPEN));
        multiMap.put("status", new Icon("folder-visiting", TangoProject.FOLDER_VISITING));
        multiMap.put("status", new Icon("image-loading", TangoProject.IMAGE_LOADING));
        multiMap.put("status", new Icon("image-missing", TangoProject.IMAGE_MISSING));
        multiMap.put("status", new Icon("mail-attachment", TangoProject.MAIL_ATTACHMENT));
        //multiMap.put("status", new Icon("mail-unread", TangoProject.MAIL_UNREAD));
        //multiMap.put("status", new Icon("mail-read", TangoProject.MAIL_READ));
        //multiMap.put("status", new Icon("mail-replied", TangoProject.MAIL_REPLIED));
        //multiMap.put("status", new Icon("mail-signed", TangoProject.MAIL_SIGNED));
        //multiMap.put("status", new Icon("mail-signed-verified", TangoProject.MAIL_SIGNED_VERIFIED));
        //multiMap.put("status", new Icon("media-playlist-repeat", TangoProject.MEDIA_PLAYLIST_REPEAT));
        //multiMap.put("status", new Icon("media-playlist-shuffle", TangoProject.MEDIA_PLAYLIST_SHUFFLE));
        multiMap.put("status", new Icon("network-error", TangoProject.NETWORK_ERROR));
        multiMap.put("status", new Icon("network-idle", TangoProject.NETWORK_IDLE));
        multiMap.put("status", new Icon("network-offline", TangoProject.NETWORK_OFFLINE));
        multiMap.put("status", new Icon("network-receive", TangoProject.NETWORK_RECEIVE));
        multiMap.put("status", new Icon("network-transmit", TangoProject.NETWORK_TRANSMIT));
        multiMap.put("status", new Icon("network-transmit-receive", TangoProject.NETWORK_TRANSMIT_RECEIVE));
        multiMap.put("status", new Icon("printer-error", TangoProject.PRINTER_ERROR));
        //multiMap.put("status", new Icon("printer-printing", TangoProject.PRINTER_PRINTING));
        //multiMap.put("status", new Icon("security-high", TangoProject.SECURITY_HIGH));
        //multiMap.put("status", new Icon("security-medium", TangoProject.SECURITY_MEDIUM));
        //multiMap.put("status", new Icon("security-low", TangoProject.SECURITY_LOW));
        //multiMap.put("status", new Icon("software-update-available", TangoProject.SOFTWARE_UPDATE_AVAILABLE));
        //multiMap.put("status", new Icon("software-update-urgent", TangoProject.SOFTWARE_UPDATE_URGENT));
        //multiMap.put("status", new Icon("sync-error", TangoProject.SYNC_ERROR));
        //multiMap.put("status", new Icon("sync-synchronizing", TangoProject.SYNC_SYNCHRONIZING));
        //multiMap.put("status", new Icon("task-due", TangoProject.TASK_DUE));
        //multiMap.put("status", new Icon("task-passed-due", TangoProject.TASK_PASSED_DUE));
        //multiMap.put("status", new Icon("user-away", TangoProject.USER_AWAY));
        //multiMap.put("status", new Icon("user-idle", TangoProject.USER_IDLE));
        //multiMap.put("status", new Icon("user-offline", TangoProject.USER_OFFLINE));
        //multiMap.put("status", new Icon("user-online", TangoProject.USER_ONLINE));
        multiMap.put("status", new Icon("user-trash-full", TangoProject.USER_TRASH_FULL));
        multiMap.put("status", new Icon("weather-clear", TangoProject.WEATHER_CLEAR));
        multiMap.put("status", new Icon("weather-clear-night", TangoProject.WEATHER_CLEAR_NIGHT));
        multiMap.put("status", new Icon("weather-few-clouds", TangoProject.WEATHER_FEW_CLOUDS));
        multiMap.put("status", new Icon("weather-few-clouds-night", TangoProject.WEATHER_FEW_CLOUDS_NIGHT));
        //multiMap.put("status", new Icon("weather-fog", TangoProject.WEATHER_FOG));
        multiMap.put("status", new Icon("weather-overcast", TangoProject.WEATHER_OVERCAST));
        multiMap.put("status", new Icon("weather-severe-alert", TangoProject.WEATHER_SEVERE_ALERT));
        multiMap.put("status", new Icon("weather-showers", TangoProject.WEATHER_SHOWERS));
        multiMap.put("status", new Icon("weather-showers-scattered", TangoProject.WEATHER_SHOWERS_SCATTERED));
        multiMap.put("status", new Icon("weather-snow", TangoProject.WEATHER_SNOW));
        multiMap.put("status", new Icon("weather-storm", TangoProject.WEATHER_STORM));
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