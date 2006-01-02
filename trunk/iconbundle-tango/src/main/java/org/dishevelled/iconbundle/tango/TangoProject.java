/*

    dsh-iconbundle-tango  Icon bundles for the Tango Project icon theme.
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
package org.dishevelled.iconbundle.tango;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.dishevelled.iconbundle.IconSize;
import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.impl.CachingIconBundle;

/**
 * Icon bundles and icon sizes for the Tango Project icon theme.
 *
 * @author  Michael Heuer
 * @author  $Revision$ $Date$
 */
public final class TangoProject
{

    //
    // Tango Project icon sizes

    /** Custom icon size, tango project "extra small". */
    public static final IconSize EXTRA_SMALL = new IconSize(16, 16) { };

    /** Custom icon size, tango project "small". */
    public static final IconSize SMALL = new IconSize(22, 22) { };

    /** Custom icon size, tango project "large". */
    public static final IconSize LARGE = new IconSize(48, 48) { };


    /** Private array of tango custom icon sizes. */
    private static IconSize[] sizes = new IconSize[] { EXTRA_SMALL, SMALL, LARGE };

    /** Public unmodifiable collection of tango project custom icon sizes. */
    public static final Collection SIZES = Collections.unmodifiableList(Arrays.asList(sizes));


    //
    // Tango Project base icon bundles

    /** Icon bundle for <code>actions/address-book-new</code>. */
    public static final IconBundle ADDRESS_BOOK_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "address-book-new"));

    /** Icon bundle for <code>actions/application-exit</code>. */
    //public static final IconBundle APPLICATION_EXIT = new CachingIconBundle(new TangoProjectIconBundle("actions", "application-exit"));

    /** Icon bundle for <code>actions/appointment-new</code>. */
    public static final IconBundle APPOINTMENT_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "appointment-new"));

    /** Icon bundle for <code>actions/bookmark-new</code>. */
    public static final IconBundle BOOKMARK_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "bookmark-new"));

    /** Icon bundle for <code>actions/contact-new</code>. */
    public static final IconBundle CONTACT_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "contact-new"));

    /** Icon bundle for <code>actions/dialog-cancel</code>. */
    //public static final IconBundle DIALOG_CANCEL = new CachingIconBundle(new TangoProjectIconBundle("actions", "dialog-cancel"));

    /** Icon bundle for <code>actions/document-new</code>. */
    public static final IconBundle DOCUMENT_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-new"));

    /** Icon bundle for <code>actions/document-open</code>. */
    public static final IconBundle DOCUMENT_OPEN = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-open"));

    /** Icon bundle for <code>actions/document-print</code>. */
    public static final IconBundle DOCUMENT_PRINT = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-print"));

    /** Icon bundle for <code>actions/document-print-preview</code>. */
    public static final IconBundle DOCUMENT_PRINT_PREVIEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-print-preview"));

    /** Icon bundle for <code>actions/document-properties</code>. */
    public static final IconBundle DOCUMENT_PROPERTIES = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-properties"));

    /** Icon bundle for <code>actions/document-save</code>. */
    public static final IconBundle DOCUMENT_SAVE = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-save"));

    /** Icon bundle for <code>actions/document-save-as</code>. */
    public static final IconBundle DOCUMENT_SAVE_AS = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-save-as"));

    /** Icon bundle for <code>actions/edit-clear</code>. */
    public static final IconBundle EDIT_CLEAR = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-clear"));

    /** Icon bundle for <code>actions/edit-copy</code>. */
    public static final IconBundle EDIT_COPY = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-copy"));

    /** Icon bundle for <code>actions/edit-cut</code>. */
    public static final IconBundle EDIT_CUT = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-cut"));

    /** Icon bundle for <code>actions/edit-find</code>. */
    public static final IconBundle EDIT_FIND = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-find"));

    /** Icon bundle for <code>actions/edit-paste</code>. */
    public static final IconBundle EDIT_PASTE = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-paste"));

    /** Icon bundle for <code>actions/edit-redo</code>. */
    public static final IconBundle EDIT_REDO = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-redo"));

    /** Icon bundle for <code>actions/edit-undo</code>. */
    public static final IconBundle EDIT_UNDO = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-undo"));

    /** Icon bundle for <code>actions/folder-new</code>. */
    public static final IconBundle FOLDER_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "folder-new"));

    /** Icon bundle for <code>actions/format-indent-less</code>. */
    public static final IconBundle FORMAT_INDENT_LESS = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-indent-less"));

    /** Icon bundle for <code>actions/format-indent-more</code>. */
    public static final IconBundle FORMAT_INDENT_MORE = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-indent-more"));

    /** Icon bundle for <code>actions/format-justify-center</code>. */
    public static final IconBundle FORMAT_JUSTIFY_CENTER = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-justify-center"));

    /** Icon bundle for <code>actions/format-justify-fill</code>. */
    public static final IconBundle FORMAT_JUSTIFY_FILL = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-justify-fill"));

    /** Icon bundle for <code>actions/format-justify-left</code>. */
    public static final IconBundle FORMAT_JUSTIFY_LEFT = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-justify-left"));

    /** Icon bundle for <code>actions/format-justify-right</code>. */
    public static final IconBundle FORMAT_JUSTIFY_RIGHT = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-justify-right"));

    /** Icon bundle for <code>actions/format-text-bold</code>. */
    public static final IconBundle FORMAT_TEXT_BOLD = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-text-bold"));

    /** Icon bundle for <code>actions/format-text-italic</code>. */
    public static final IconBundle FORMAT_TEXT_ITALIC = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-text-italic"));

    /** Icon bundle for <code>actions/format-text-strikethrough</code>. */
    public static final IconBundle FORMAT_TEXT_STRIKETHROUGH = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-text-strikethrough"));

    /** Icon bundle for <code>actions/format-text-underline</code>. */
    public static final IconBundle FORMAT_TEXT_UNDERLINE = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-text-underline"));

    /** Icon bundle for <code>actions/go-bottom</code>. */
    public static final IconBundle GO_BOTTOM = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-bottom"));

    /** Icon bundle for <code>actions/go-down</code>. */
    public static final IconBundle GO_DOWN = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-down"));

    /** Icon bundle for <code>actions/go-first</code>. */
    public static final IconBundle GO_FIRST = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-first"));

    /** Icon bundle for <code>actions/go-home</code>. */
    //public static final IconBundle GO_HOME = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-home"));

    /** Icon bundle for <code>actions/go-jump</code>. */
    public static final IconBundle GO_JUMP = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-jump"));

    /** Icon bundle for <code>actions/go-last</code>. */
    public static final IconBundle GO_LAST = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-last"));

    /** Icon bundle for <code>actions/go-next</code>. */
    public static final IconBundle GO_NEXT = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-next"));

    /** Icon bundle for <code>actions/go-previous</code>. */
    public static final IconBundle GO_PREVIOUS = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-previous"));

    /** Icon bundle for <code>actions/go-top</code>. */
    public static final IconBundle GO_TOP = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-top"));

    /** Icon bundle for <code>actions/go-up</code>. */
    public static final IconBundle GO_UP = new CachingIconBundle(new TangoProjectIconBundle("actions", "go-up"));

    /** Icon bundle for <code>actions/mail-message-new</code>. */
    public static final IconBundle MAIL_MESSAGE_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-message-new"));

    /** Icon bundle for <code>actions/media-playback-pause</code>. */
    public static final IconBundle MEDIA_PLAYBACK_PAUSE = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-playback-pause"));

    /** Icon bundle for <code>actions/media-playback-start</code>. */
    public static final IconBundle MEDIA_PLAYBACK_START = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-playback-start"));

    /** Icon bundle for <code>actions/media-playback-stop</code>. */
    public static final IconBundle MEDIA_PLAYBACK_STOP = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-playback-stop"));

    /** Icon bundle for <code>actions/media-record</code>. */
    public static final IconBundle MEDIA_RECORD = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-record"));

    /** Icon bundle for <code>actions/media-seek-backward</code>. */
    public static final IconBundle MEDIA_SEEK_BACKWARD = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-seek-backward"));

    /** Icon bundle for <code>actions/media-seek-forward</code>. */
    public static final IconBundle MEDIA_SEEK_FORWARD = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-seek-forward"));

    /** Icon bundle for <code>actions/media-skip-backward</code>. */
    public static final IconBundle MEDIA_SKIP_BACKWARD = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-skip-backward"));

    /** Icon bundle for <code>actions/media-skip-forward</code>. */
    public static final IconBundle MEDIA_SKIP_FORWARD = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-skip-forward"));

    /** Icon bundle for <code>actions/process-stop</code>. */
    public static final IconBundle PROCESS_STOP = new CachingIconBundle(new TangoProjectIconBundle("actions", "process-stop"));

    /** Icon bundle for <code>actions/system-lock-screen</code>. */
    public static final IconBundle SYSTEM_LOCK_SCREEN = new CachingIconBundle(new TangoProjectIconBundle("actions", "system-lock-screen"));

    /** Icon bundle for <code>actions/system-log-out</code>. */
    public static final IconBundle SYSTEM_LOG_OUT = new CachingIconBundle(new TangoProjectIconBundle("actions", "system-log-out"));

    /** Icon bundle for <code>actions/system-search</code>. */
    public static final IconBundle SYSTEM_SEARCH = new CachingIconBundle(new TangoProjectIconBundle("actions", "system-search"));

    /** Icon bundle for <code>actions/tab-new</code>. */
    public static final IconBundle TAB_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "tab-new"));

    /** Icon bundle for <code>actions/view-refresh</code>. */
    public static final IconBundle VIEW_REFRESH = new CachingIconBundle(new TangoProjectIconBundle("actions", "view-refresh"));

    /** Icon bundle for <code>actions/view-sort-ascending</code>. */
    //public static final IconBundle VIEW_SORT_ASCENDING = new CachingIconBundle(new TangoProjectIconBundle("actions", "view-sort-ascending"));

    /** Icon bundle for <code>actions/view-sort-descending</code>. */
    //public static final IconBundle VIEW_SORT_DESCENDING = new CachingIconBundle(new TangoProjectIconBundle("actions", "view-sort-descending"));

    /** Icon bundle for <code>actions/window-new</code>. */
    public static final IconBundle WINDOW_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "window-new"));

    /** Icon bundle for <code>apps/accessories-calculator</code>. */
    public static final IconBundle ACCESSORIES_CALCULATOR = new CachingIconBundle(new TangoProjectIconBundle("apps", "accessories-calculator"));

    /** Icon bundle for <code>apps/accessories-character-map</code>. */
    public static final IconBundle ACCESSORIES_CHARACTER_MAP = new CachingIconBundle(new TangoProjectIconBundle("apps", "accessories-character-map"));

    /** Icon bundle for <code>apps/accessories-text-editor</code>. */
    public static final IconBundle ACCESSORIES_TEXT_EDITOR = new CachingIconBundle(new TangoProjectIconBundle("apps", "accessories-text-editor"));

    /** Icon bundle for <code>apps/help-browser</code>. */
    public static final IconBundle HELP_BROWSER = new CachingIconBundle(new TangoProjectIconBundle("apps", "help-browser"));

    /** Icon bundle for <code>apps/internet-mail</code>. */
    public static final IconBundle INTERNET_MAIL = new CachingIconBundle(new TangoProjectIconBundle("apps", "internet-mail"));

    /** Icon bundle for <code>apps/internet-web-browser</code>. */
    public static final IconBundle INTERNET_WEB_BROWSER = new CachingIconBundle(new TangoProjectIconBundle("apps", "internet-web-browser"));

    /** Icon bundle for <code>apps/multimedia-volume-control</code>. */
    public static final IconBundle MULTIMEDIA_VOLUME_CONTROL = new CachingIconBundle(new TangoProjectIconBundle("apps", "multimedia-volume-control"));

    /** Icon bundle for <code>apps/office-calendar</code>. */
    public static final IconBundle OFFICE_CALENDAR = new CachingIconBundle(new TangoProjectIconBundle("apps", "office-calendar"));

    /** Icon bundle for <code>apps/preferences-desktop-accessibility</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_ACCESSIBILITY = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-accessibility"));

    /** Icon bundle for <code>apps/preferences-desktop-assistive-technology</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_ASSISTIVE_TECHNOLOGY = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-assistive-technology"));

    /** Icon bundle for <code>apps/preferences-desktop-font</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_FONT = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-font"));

    /** Icon bundle for <code>apps/preferences-desktop-keyboard-shortcuts</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_KEYBOARD_SHORTCUTS = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-keyboard-shortcuts"));

    /** Icon bundle for <code>apps/preferences-desktop-remote-desktop</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_REMOTE_DESKTOP = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-remote-desktop"));

    /** Icon bundle for <code>apps/preferences-desktop-screensaver</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_SCREENSAVER = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-screensaver"));

    /** Icon bundle for <code>apps/preferences-desktop-sound</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_SOUND = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-sound"));

    /** Icon bundle for <code>apps/preferences-desktop-theme</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_THEME = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-theme"));

    /** Icon bundle for <code>apps/preferences-desktop-wallpaper</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_WALLPAPER = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-wallpaper"));

    /** Icon bundle for <code>apps/preferences-system-network-proxy</code>. */
    public static final IconBundle PREFERENCES_SYSTEM_NETWORK_PROXY = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-system-network-proxy"));

    /** Icon bundle for <code>apps/preferences-system-session</code>. */
    public static final IconBundle PREFERENCES_SYSTEM_SESSION = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-system-session"));

    /** Icon bundle for <code>apps/preferences-system-windows</code>. */
    public static final IconBundle PREFERENCES_SYSTEM_WINDOWS = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-system-windows"));

    /** Icon bundle for <code>apps/system-file-manager</code>. */
    public static final IconBundle SYSTEM_FILE_MANAGER = new CachingIconBundle(new TangoProjectIconBundle("apps", "system-file-manager"));

    /** Icon bundle for <code>apps/system-users</code>. */
    public static final IconBundle SYSTEM_USERS = new CachingIconBundle(new TangoProjectIconBundle("apps", "system-users"));

    /** Icon bundle for <code>apps/utilities-system-monitor</code>. */
    public static final IconBundle UTILITIES_SYSTEM_MONITOR = new CachingIconBundle(new TangoProjectIconBundle("apps", "utilities-system-monitor"));

    /** Icon bundle for <code>apps/utilities-terminal</code>. */
    public static final IconBundle UTILITIES_TERMINAL = new CachingIconBundle(new TangoProjectIconBundle("apps", "utilities-terminal"));

    /** Icon bundle for <code>categories/applications-accessories</code>. */
    public static final IconBundle APPLICATIONS_ACCESSORIES = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-accessories"));

    /** Icon bundle for <code>categories/applications-development</code>. */
    //public static final IconBundle APPLICATIONS_DEVELOPMENT = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-development"));

    /** Icon bundle for <code>categories/applications-games</code>. */
    public static final IconBundle APPLICATIONS_GAMES = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-games"));

    /** Icon bundle for <code>categories/applications-graphics</code>. */
    public static final IconBundle APPLICATIONS_GRAPHICS = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-graphics"));

    /** Icon bundle for <code>categories/applications-multimedia</code>. */
    //public static final IconBundle APPLICATIONS_MULTIMEDIA = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-multimedia"));

    /** Icon bundle for <code>categories/applications-office</code>. */
    //public static final IconBundle APPLICATIONS_OFFICE = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-office"));

    /** Icon bundle for <code>categories/applications-system</code>. */
    //public static final IconBundle APPLICATIONS_SYSTEM = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-system"));

    /** Icon bundle for <code>categories/preferences-desktop</code>. */
    public static final IconBundle PREFERENCES_DESKTOP = new CachingIconBundle(new TangoProjectIconBundle("categories", "preferences-desktop"));

    /** Icon bundle for <code>devices/audio-card</code>. */
    public static final IconBundle AUDIO_CARD = new CachingIconBundle(new TangoProjectIconBundle("devices", "audio-card"));

    /** Icon bundle for <code>devices/audio-input-microphone</code>. */
    public static final IconBundle AUDIO_INPUT_MICROPHONE = new CachingIconBundle(new TangoProjectIconBundle("devices", "audio-input-microphone"));

    /** Icon bundle for <code>devices/battery</code>. */
    public static final IconBundle BATTERY = new CachingIconBundle(new TangoProjectIconBundle("devices", "battery"));

    /** Icon bundle for <code>devices/camera-photo</code>. */
    public static final IconBundle CAMERA_PHOTO = new CachingIconBundle(new TangoProjectIconBundle("devices", "camera-photo"));

    /** Icon bundle for <code>devices/computer</code>. */
    public static final IconBundle COMPUTER = new CachingIconBundle(new TangoProjectIconBundle("devices", "computer"));

    /** Icon bundle for <code>devices/drive-cdrom</code>. */
    public static final IconBundle DRIVE_CDROM = new CachingIconBundle(new TangoProjectIconBundle("devices", "drive-cdrom"));

    /** Icon bundle for <code>devices/drive-harddisk</code>. */
    public static final IconBundle DRIVE_HARDDISK = new CachingIconBundle(new TangoProjectIconBundle("devices", "drive-harddisk"));

    /** Icon bundle for <code>devices/drive-removable-media</code>. */
    public static final IconBundle DRIVE_REMOVABLE_MEDIA = new CachingIconBundle(new TangoProjectIconBundle("devices", "drive-removable-media"));

    /** Icon bundle for <code>devices/input-gaming</code>. */
    public static final IconBundle INPUT_GAMING = new CachingIconBundle(new TangoProjectIconBundle("devices", "input-gaming"));

    /** Icon bundle for <code>devices/input-keyboard</code>. */
    public static final IconBundle INPUT_KEYBOARD = new CachingIconBundle(new TangoProjectIconBundle("devices", "input-keyboard"));

    /** Icon bundle for <code>devices/input-mouse</code>. */
    public static final IconBundle INPUT_MOUSE = new CachingIconBundle(new TangoProjectIconBundle("devices", "input-mouse"));

    /** Icon bundle for <code>devices/media-cdrom</code>. */
    public static final IconBundle MEDIA_CDROM = new CachingIconBundle(new TangoProjectIconBundle("devices", "media-cdrom"));

    /** Icon bundle for <code>devices/media-floppy</code>. */
    public static final IconBundle MEDIA_FLOPPY = new CachingIconBundle(new TangoProjectIconBundle("devices", "media-floppy"));

    /** Icon bundle for <code>devices/multimedia-player</code>. */
    public static final IconBundle MULTIMEDIA_PLAYER = new CachingIconBundle(new TangoProjectIconBundle("devices", "multimedia-player"));

    /** Icon bundle for <code>devices/printer</code>. */
    public static final IconBundle PRINTER = new CachingIconBundle(new TangoProjectIconBundle("devices", "printer"));

    /** Icon bundle for <code>devices/video-display</code>. */
    public static final IconBundle VIDEO_DISPLAY = new CachingIconBundle(new TangoProjectIconBundle("devices", "video-display"));

    /** Icon bundle for <code>emblems/emblem-important</code>. */
    public static final IconBundle EMBLEM_IMPORTANT = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-important"));

    /** Icon bundle for <code>mimetypes/application-x-executable</code>. */
    public static final IconBundle APPLICATION_X_EXECUTABLE = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "application-x-executable"));

    /** Icon bundle for <code>mimetypes/audio-x-generic</code>. */
    public static final IconBundle AUDIO_X_GENERIC = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "audio-x-generic"));

    /** Icon bundle for <code>mimetypes/image-x-generic</code>. */
    public static final IconBundle IMAGE_X_GENERIC = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "image-x-generic"));

    /** Icon bundle for <code>mimetypes/package-x-generic</code>. */
    public static final IconBundle PACKAGE_X_GENERIC = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "package-x-generic"));

    /** Icon bundle for <code>mimetypes/text-html</code>. */
    public static final IconBundle TEXT_HTML = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "text-html"));

    /** Icon bundle for <code>mimetypes/text-x-generic</code>. */
    public static final IconBundle TEXT_X_GENERIC = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "text-x-generic"));

    /** Icon bundle for <code>mimetypes/text-x-generic-template</code>. */
    public static final IconBundle TEXT_X_GENERIC_TEMPLATE = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "text-x-generic-template"));

    /** Icon bundle for <code>mimetypes/text-x-script</code>. */
    public static final IconBundle TEXT_X_SCRIPT = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "text-x-script"));

    /** Icon bundle for <code>mimetypes/video-x-generic</code>. */
    public static final IconBundle VIDEO_X_GENERIC = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "video-x-generic"));

    /** Icon bundle for <code>mimetypes/x-office-address-book</code>. */
    public static final IconBundle X_OFFICE_ADDRESS_BOOK = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-address-book"));

    /** Icon bundle for <code>mimetypes/x-office-document</code>. */
    public static final IconBundle X_OFFICE_DOCUMENT = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-document"));

    /** Icon bundle for <code>mimetypes/x-office-spreadsheet</code>. */
    public static final IconBundle X_OFFICE_SPREADSHEET = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-spreadsheet"));

    /** Icon bundle for <code>places/folder</code>. */
    public static final IconBundle FOLDER = new CachingIconBundle(new TangoProjectIconBundle("places", "folder"));

    /** Icon bundle for <code>places/folder-remote</code>. */
    public static final IconBundle FOLDER_REMOTE = new CachingIconBundle(new TangoProjectIconBundle("places", "folder-remote"));

    /** Icon bundle for <code>places/network-server</code>. */
    public static final IconBundle NETWORK_SERVER = new CachingIconBundle(new TangoProjectIconBundle("places", "network-server"));

    /** Icon bundle for <code>places/network-workgroup</code>. */
    public static final IconBundle NETWORK_WORKGROUP = new CachingIconBundle(new TangoProjectIconBundle("places", "network-workgroup"));

    /** Icon bundle for <code>places/start-here</code>. */
    //public static final IconBundle START_HERE = new CachingIconBundle(new TangoProjectIconBundle("places", "start-here"));

    /** Icon bundle for <code>places/user-desktop</code>. */
    public static final IconBundle USER_DESKTOP = new CachingIconBundle(new TangoProjectIconBundle("places", "user-desktop"));

    /** Icon bundle for <code>places/user-home</code>. */
    public static final IconBundle USER_HOME = new CachingIconBundle(new TangoProjectIconBundle("places", "user-home"));

    /** Icon bundle for <code>places/user-trash</code>. */
    public static final IconBundle USER_TRASH = new CachingIconBundle(new TangoProjectIconBundle("places", "user-trash"));

    /** Icon bundle for <code>status/dialog-error</code>. */
    public static final IconBundle DIALOG_ERROR = new CachingIconBundle(new TangoProjectIconBundle("status", "dialog-error"));

    /** Icon bundle for <code>status/dialog-information</code>. */
    public static final IconBundle DIALOG_INFORMATION = new CachingIconBundle(new TangoProjectIconBundle("status", "dialog-information"));

    /** Icon bundle for <code>status/dialog-password</code>. */
    //public static final IconBundle DIALOG_PASSWORD = new CachingIconBundle(new TangoProjectIconBundle("status", "dialog-password"));

    /** Icon bundle for <code>status/dialog-question</code>. */
    //public static final IconBundle DIALOG_QUESTION = new CachingIconBundle(new TangoProjectIconBundle("status", "dialog-question"));

    /** Icon bundle for <code>status/dialog-warning</code>. */
    public static final IconBundle DIALOG_WARNING = new CachingIconBundle(new TangoProjectIconBundle("status", "dialog-warning"));

    /** Icon bundle for <code>status/folder-drag-accept</code>. */
    //public static final IconBundle FOLDER_DRAG_ACCEPT = new CachingIconBundle(new TangoProjectIconBundle("status", "folder-drag-accept"));

    /** Icon bundle for <code>status/folder-open</code>. */
    //public static final IconBundle FOLDER_OPEN = new CachingIconBundle(new TangoProjectIconBundle("status", "folder-open"));

    /** Icon bundle for <code>status/folder-visiting</code>. */
    //public static final IconBundle FOLDER_VISITING = new CachingIconBundle(new TangoProjectIconBundle("status", "folder-visiting"));

    /** Icon bundle for <code>status/image-loading</code>. */
    public static final IconBundle IMAGE_LOADING = new CachingIconBundle(new TangoProjectIconBundle("status", "image-loading"));

    /** Icon bundle for <code>status/image-missing</code>. */
    public static final IconBundle IMAGE_MISSING = new CachingIconBundle(new TangoProjectIconBundle("status", "image-missing"));

    /** Icon bundle for <code>status/user-trash-full</code>. */
    //public static final IconBundle USER_TRASH_FULL = new CachingIconBundle(new TangoProjectIconBundle("status", "user-trash-full"));
}