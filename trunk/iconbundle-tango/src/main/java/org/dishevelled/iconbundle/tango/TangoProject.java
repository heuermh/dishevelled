/*

    dsh-iconbundle-tango  Icon bundles for the Tango Project icon theme.
    Copyright (c) 2005-2010 held jointly by the individual authors.

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
package org.dishevelled.iconbundle.tango;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    public static final IconSize EXTRA_SMALL = new IconSize(16, 16)
        {
            // empty
        };

    /** Custom icon size, tango project "small". */
    public static final IconSize SMALL = new IconSize(22, 22)
        {
            // empty
        };

    /** Custom icon size, tango project "medium". */
    public static final IconSize MEDIUM = new IconSize(32, 32)
        {
            // empty
        };

    /** Custom icon size, tango project "large". */
    public static final IconSize LARGE = new IconSize(48, 48)
        {
            // empty
        };


    /** Private array of tango custom icon sizes. */
    private static IconSize[] sizes = new IconSize[] { EXTRA_SMALL, SMALL, MEDIUM, LARGE };

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

    /** Icon bundle for <code>actions/dialog-close</code>. */
    //public static final IconBundle DIALOG_CLOSE = new CachingIconBundle(new TangoProjectIconBundle("actions", "dialog-close"));

    /** Icon bundle for <code>actions/dialog-ok</code>. */
    //public static final IconBundle DIALOG_OK = new CachingIconBundle(new TangoProjectIconBundle("actions", "dialog-ok"));

    /** Icon bundle for <code>actions/document-new</code>. */
    public static final IconBundle DOCUMENT_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-new"));

    /** Icon bundle for <code>actions/document-open</code>. */
    public static final IconBundle DOCUMENT_OPEN = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-open"));

    /** Icon bundle for <code>actions/document-open-recent</code>. */
    //public static final IconBundle DOCUMENT_OPEN_RECENT = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-open-recent"));

    /** Icon bundle for <code>actions/document-page-setup</code>. */
    //public static final IconBundle DOCUMENT_PAGE_SETUP = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-page-setup"));

    /** Icon bundle for <code>actions/document-print</code>. */
    public static final IconBundle DOCUMENT_PRINT = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-print"));

    /** Icon bundle for <code>actions/document-print-preview</code>. */
    public static final IconBundle DOCUMENT_PRINT_PREVIEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-print-preview"));

    /** Icon bundle for <code>actions/document-properties</code>. */
    public static final IconBundle DOCUMENT_PROPERTIES = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-properties"));

    /** Icon bundle for <code>actions/document-revert</code>. */
    //public static final IconBundle DOCUMENT_REVERT = new CachingIconBundle(new TangoProjectIconBundle("actions", "document-revert"));

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

    /** Icon bundle for <code>actions/edit-delete</code>. */
    public static final IconBundle EDIT_DELETE = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-delete"));

    /** Icon bundle for <code>actions/edit-find</code>. */
    public static final IconBundle EDIT_FIND = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-find"));

    /** Icon bundle for <code>actions/edit-find-replace</code>. */
    public static final IconBundle EDIT_FIND_REPLACE = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-find-replace"));

    /** Icon bundle for <code>actions/edit-paste</code>. */
    public static final IconBundle EDIT_PASTE = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-paste"));

    /** Icon bundle for <code>actions/edit-redo</code>. */
    public static final IconBundle EDIT_REDO = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-redo"));

    /** Icon bundle for <code>actions/edit-select-all</code>. */
    public static final IconBundle EDIT_SELECT_ALL = new CachingIconBundle(new TangoProjectIconBundle("actions", "edit-select-all"));

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

    /** Icon bundle for <code>actions/format-text-direction-ltr</code>. */
    //public static final IconBundle FORMAT_TEXT_DIRECTION_LTR = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-text-direction-ltr"));

    /** Icon bundle for <code>actions/format-text-direction-rtl</code>. */
    //public static final IconBundle FORMAT_TEXT_DIRECTION_RTL = new CachingIconBundle(new TangoProjectIconBundle("actions", "format-text-direction-rtl"));

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

    /** Icon bundle for <code>actions/help-about</code>. */
    //public static final IconBundle HELP_ABOUT = new CachingIconBundle(new TangoProjectIconBundle("actions", "help-about"));

    /** Icon bundle for <code>actions/help-contents</code>. */
    //public static final IconBundle HELP_CONTENTS = new CachingIconBundle(new TangoProjectIconBundle("actions", "help-contents"));

    /** Icon bundle for <code>actions/help-faq</code>. */
    //public static final IconBundle HELP_FAQ = new CachingIconBundle(new TangoProjectIconBundle("actions", "help-faq"));

    /** Icon bundle for <code>actions/insert-image</code>. */
    //public static final IconBundle INSERT_IMAGE = new CachingIconBundle(new TangoProjectIconBundle("actions", "insert-image"));

    /** Icon bundle for <code>actions/insert-link</code>. */
    //public static final IconBundle INSERT_LINK = new CachingIconBundle(new TangoProjectIconBundle("actions", "insert-link"));

    /** Icon bundle for <code>actions/insert-object</code>. */
    //public static final IconBundle INSERT_OBJECT = new CachingIconBundle(new TangoProjectIconBundle("actions", "insert-object"));

    /** Icon bundle for <code>actions/insert-text</code>. */
    //public static final IconBundle INSERT_TEXT = new CachingIconBundle(new TangoProjectIconBundle("actions", "insert-text"));

    /** Icon bundle for <code>actions/list-add</code>. */
    public static final IconBundle LIST_ADD = new CachingIconBundle(new TangoProjectIconBundle("actions", "list-add"));

    /** Icon bundle for <code>actions/list-remove</code>. */
    public static final IconBundle LIST_REMOVE = new CachingIconBundle(new TangoProjectIconBundle("actions", "list-remove"));

    /** Icon bundle for <code>actions/mail-forward</code>. */
    public static final IconBundle MAIL_FORWARD = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-forward"));

    /** Icon bundle for <code>actions/mail-mark-important</code>. */
    //public static final IconBundle MAIL_MARK_IMPORTANT = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-mark-important"));

    /** Icon bundle for <code>actions/mail-mark-junk</code>. */
    public static final IconBundle MAIL_MARK_JUNK = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-mark-junk"));

    /** Icon bundle for <code>actions/mail-mark-notjunk</code>. */
    //public static final IconBundle MAIL_MARK_NOTJUNK = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-mark-notjunk"));

    /** Icon bundle for <code>actions/mail-mark-read</code>. */
    //public static final IconBundle MAIL_MARK_READ = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-mark-read"));

    /** Icon bundle for <code>actions/mail-mark-unread</code>. */
    //public static final IconBundle MAIL_MARK_UNREAD = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-mark-unread"));

    /** Icon bundle for <code>actions/mail-message-new</code>. */
    public static final IconBundle MAIL_MESSAGE_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-message-new"));

    /** Icon bundle for <code>actions/mail-reply-all</code>. */
    public static final IconBundle MAIL_REPLY_ALL = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-reply-all"));

    /** Icon bundle for <code>actions/mail-reply-sender</code>. */
    public static final IconBundle MAIL_REPLY_SENDER = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-reply-sender"));

    /** Icon bundle for <code>actions/mail-send</code>. */
    //public static final IconBundle MAIL_SEND = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-send"));

    /** Icon bundle for <code>actions/mail-send-receive</code>. */
    public static final IconBundle MAIL_SEND_RECEIVE = new CachingIconBundle(new TangoProjectIconBundle("actions", "mail-send-receive"));

    /** Icon bundle for <code>actions/media-eject</code>. */
    public static final IconBundle MEDIA_EJECT = new CachingIconBundle(new TangoProjectIconBundle("actions", "media-eject"));

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

    /** Icon bundle for <code>actions/object-flip-horizontal</code>. */
    //public static final IconBundle OBJECT_FLIP_HORIZONTAL = new CachingIconBundle(new TangoProjectIconBundle("actions", "object-flip-horizontal"));

    /** Icon bundle for <code>actions/object-flip-vertical</code>. */
    //public static final IconBundle OBJECT_FLIP_VERTICAL = new CachingIconBundle(new TangoProjectIconBundle("actions", "object-flip-vertical"));

    /** Icon bundle for <code>actions/object-rotate-left</code>. */
    //public static final IconBundle OBJECT_ROTATE_LEFT = new CachingIconBundle(new TangoProjectIconBundle("actions", "object-rotate-left"));

    /** Icon bundle for <code>actions/object-rotate-right</code>. */
    //public static final IconBundle OBJECT_ROTATE_RIGHT = new CachingIconBundle(new TangoProjectIconBundle("actions", "object-rotate-right"));

    /** Icon bundle for <code>actions/process-stop</code>. */
    public static final IconBundle PROCESS_STOP = new CachingIconBundle(new TangoProjectIconBundle("actions", "process-stop"));

    /** Icon bundle for <code>actions/system-lock-screen</code>. */
    public static final IconBundle SYSTEM_LOCK_SCREEN = new CachingIconBundle(new TangoProjectIconBundle("actions", "system-lock-screen"));

    /** Icon bundle for <code>actions/system-log-out</code>. */
    public static final IconBundle SYSTEM_LOG_OUT = new CachingIconBundle(new TangoProjectIconBundle("actions", "system-log-out"));

    /** Icon bundle for <code>actions/system-run</code>. */
    //public static final IconBundle SYSTEM_RUN = new CachingIconBundle(new TangoProjectIconBundle("actions", "system-run"));

    /** Icon bundle for <code>actions/system-search</code>. */
    public static final IconBundle SYSTEM_SEARCH = new CachingIconBundle(new TangoProjectIconBundle("actions", "system-search"));

    /** Icon bundle for <code>actions/system-shutdown</code>. */
    public static final IconBundle SYSTEM_SHUTDOWN = new CachingIconBundle(new TangoProjectIconBundle("actions", "system-shutdown"));

    /** Icon bundle for <code>actions/tab-new</code>. */
    public static final IconBundle TAB_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "tab-new"));

    /** Icon bundle for <code>actions/tools-check-spelling</code>. */
    //public static final IconBundle TOOLS_CHECK_SPELLING = new CachingIconBundle(new TangoProjectIconBundle("actions", "tools-check-spelling"));

    /** Icon bundle for <code>actions/view-fullscreen</code>. */
    public static final IconBundle VIEW_FULLSCREEN = new CachingIconBundle(new TangoProjectIconBundle("actions", "view-fullscreen"));

    /** Icon bundle for <code>actions/view-refresh</code>. */
    public static final IconBundle VIEW_REFRESH = new CachingIconBundle(new TangoProjectIconBundle("actions", "view-refresh"));

    /** Icon bundle for <code>actions/view-restore</code>. */
    //public static final IconBundle VIEW_RESTORE = new CachingIconBundle(new TangoProjectIconBundle("actions", "view-restore"));

    /** Icon bundle for <code>actions/view-sort-ascending</code>. */
    //public static final IconBundle VIEW_SORT_ASCENDING = new CachingIconBundle(new TangoProjectIconBundle("actions", "view-sort-ascending"));

    /** Icon bundle for <code>actions/view-sort-descending</code>. */
    //public static final IconBundle VIEW_SORT_DESCENDING = new CachingIconBundle(new TangoProjectIconBundle("actions", "view-sort-descending"));

    /** Icon bundle for <code>actions/window-close</code>. */
    //public static final IconBundle WINDOW_CLOSE = new CachingIconBundle(new TangoProjectIconBundle("actions", "window-close"));

    /** Icon bundle for <code>actions/window-new</code>. */
    public static final IconBundle WINDOW_NEW = new CachingIconBundle(new TangoProjectIconBundle("actions", "window-new"));

    /** Icon bundle for <code>actions/zoom-best-fit</code>. */
    //public static final IconBundle ZOOM_BEST_FIT = new CachingIconBundle(new TangoProjectIconBundle("actions", "zoom-best-fit"));

    /** Icon bundle for <code>actions/zoom-in</code>. */
    //public static final IconBundle ZOOM_IN = new CachingIconBundle(new TangoProjectIconBundle("actions", "zoom-in"));

    /** Icon bundle for <code>actions/zoom-original</code>. */
    //public static final IconBundle ZOOM_ORIGINAL = new CachingIconBundle(new TangoProjectIconBundle("actions", "zoom-original"));

    /** Icon bundle for <code>actions/zoom-out</code>. */
    //public static final IconBundle ZOOM_OUT = new CachingIconBundle(new TangoProjectIconBundle("actions", "zoom-out"));

    /** Icon bundle for <code>animations/process-working</code>. */
    //public static final IconBundle PROCESS_WORKING = new CachingIconBundle(new TangoProjectIconBundle("animations", "process-working"));

    /** Icon bundle for <code>apps/accessories-calculator</code>. */
    public static final IconBundle ACCESSORIES_CALCULATOR = new CachingIconBundle(new TangoProjectIconBundle("apps", "accessories-calculator"));

    /** Icon bundle for <code>apps/accessories-character-map</code>. */
    public static final IconBundle ACCESSORIES_CHARACTER_MAP = new CachingIconBundle(new TangoProjectIconBundle("apps", "accessories-character-map"));

    /** Icon bundle for <code>apps/accessories-dictionary</code>. */
    //public static final IconBundle ACCESSORIES_DICTIONARY = new CachingIconBundle(new TangoProjectIconBundle("apps", "accessories-dictionary"));

    /** Icon bundle for <code>apps/accessories-text-editor</code>. */
    public static final IconBundle ACCESSORIES_TEXT_EDITOR = new CachingIconBundle(new TangoProjectIconBundle("apps", "accessories-text-editor"));

    /** Icon bundle for <code>apps/help-browser</code>. */
    public static final IconBundle HELP_BROWSER = new CachingIconBundle(new TangoProjectIconBundle("apps", "help-browser"));

    /** Icon bundle for <code>apps/internet-group-chat</code>. */
    public static final IconBundle INTERNET_GROUP_CHAT = new CachingIconBundle(new TangoProjectIconBundle("apps", "internet-group-chat"));

    /** Icon bundle for <code>apps/internet-mail</code>. */
    public static final IconBundle INTERNET_MAIL = new CachingIconBundle(new TangoProjectIconBundle("apps", "internet-mail"));

    /** Icon bundle for <code>apps/internet-web-browser</code>. */
    public static final IconBundle INTERNET_WEB_BROWSER = new CachingIconBundle(new TangoProjectIconBundle("apps", "internet-web-browser"));

    /** Icon bundle for <code>apps/office-calendar</code>. */
    public static final IconBundle OFFICE_CALENDAR = new CachingIconBundle(new TangoProjectIconBundle("apps", "office-calendar"));

    /** Icon bundle for <code>apps/multimedia-volume-control</code>. */
    public static final IconBundle MULTIMEDIA_VOLUME_CONTROL = new CachingIconBundle(new TangoProjectIconBundle("apps", "multimedia-volume-control"));

    /** Icon bundle for <code>apps/preferences-desktop-accessibility</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_ACCESSIBILITY = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-accessibility"));

    /** Icon bundle for <code>apps/preferences-desktop-assistive-technology</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_ASSISTIVE_TECHNOLOGY = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-assistive-technology"));

    /** Icon bundle for <code>apps/preferences-desktop-font</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_FONT = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-font"));

    /** Icon bundle for <code>apps/preferences-desktop-keyboard</code>. */
    //public static final IconBundle PREFERENCES_DESKTOP_KEYBOARD = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-keyboard"));

    /** Icon bundle for <code>apps/preferences-desktop-keyboard-shortcuts</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_KEYBOARD_SHORTCUTS = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-keyboard-shortcuts"));

    /** Icon bundle for <code>apps/preferences-desktop-locale</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_LOCALE = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-locale"));

    /** Icon bundle for <code>apps/preferences-desktop-multimedia</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_MULTIMEDIA = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-multimedia"));

    /** Icon bundle for <code>apps/preferences-desktop-remote-desktop</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_REMOTE_DESKTOP = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-remote-desktop"));

    /** Icon bundle for <code>apps/preferences-desktop-screensaver</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_SCREENSAVER = new CachingIconBundle(new TangoProjectIconBundle("apps", "preferences-desktop-screensaver"));

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

    /** Icon bundle for <code>apps/system-software-update</code>. */
    public static final IconBundle SYSTEM_SOFTWARE_UPDATE = new CachingIconBundle(new TangoProjectIconBundle("apps", "system-software-update"));

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

    /** Icon bundle for <code>categories/applications-engineering</code>. */
    //public static final IconBundle APPLICATIONS_ENGINEERING = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-engineering"));

    /** Icon bundle for <code>categories/applications-games</code>. */
    public static final IconBundle APPLICATIONS_GAMES = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-games"));

    /** Icon bundle for <code>categories/applications-graphics</code>. */
    public static final IconBundle APPLICATIONS_GRAPHICS = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-graphics"));

    /** Icon bundle for <code>categories/applications-internet</code>. */
    public static final IconBundle APPLICATIONS_INTERNET = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-internet"));

    /** Icon bundle for <code>categories/applications-multimedia</code>. */
    public static final IconBundle APPLICATIONS_MULTIMEDIA = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-multimedia"));

    /** Icon bundle for <code>categories/applications-office</code>. */
    public static final IconBundle APPLICATIONS_OFFICE = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-office"));

    /** Icon bundle for <code>categories/applications-other</code>. */
    public static final IconBundle APPLICATIONS_OTHER = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-other"));

    /** Icon bundle for <code>categories/applications-science</code>. */
    //public static final IconBundle APPLICATIONS_SCIENCE = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-science"));

    /** Icon bundle for <code>categories/applications-system</code>. */
    public static final IconBundle APPLICATIONS_SYSTEM = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-system"));

    /** Icon bundle for <code>categories/applications-utilities</code>. */
    //public static final IconBundle APPLICATIONS_UTILITIES = new CachingIconBundle(new TangoProjectIconBundle("categories", "applications-utilities"));

    /** Icon bundle for <code>categories/preferences-desktop</code>. */
    public static final IconBundle PREFERENCES_DESKTOP = new CachingIconBundle(new TangoProjectIconBundle("categories", "preferences-desktop"));

    /** Icon bundle for <code>categories/preferences-desktop-peripherals</code>. */
    public static final IconBundle PREFERENCES_DESKTOP_PERIPHERALS = new CachingIconBundle(new TangoProjectIconBundle("categories", "preferences-desktop-peripherals"));

    /** Icon bundle for <code>categories/preferences-desktop-personal</code>. */
    //public static final IconBundle PREFERENCES_DESKTOP_PERSONAL = new CachingIconBundle(new TangoProjectIconBundle("categories", "preferences-desktop-personal"));

    /** Icon bundle for <code>categories/preferences-other</code>. */
    //public static final IconBundle PREFERENCES_OTHER = new CachingIconBundle(new TangoProjectIconBundle("categories", "preferences-other"));

    /** Icon bundle for <code>categories/preferences-system</code>. */
    public static final IconBundle PREFERENCES_SYSTEM = new CachingIconBundle(new TangoProjectIconBundle("categories", "preferences-system"));

    /** Icon bundle for <code>categories/preferences-system-network</code>. */
    //public static final IconBundle PREFERENCES_SYSTEM_NETWORK = new CachingIconBundle(new TangoProjectIconBundle("categories", "preferences-system-network"));

    /** Icon bundle for <code>categories/system-help</code>. */
    //public static final IconBundle SYSTEM_HELP = new CachingIconBundle(new TangoProjectIconBundle("categories", "system-help"));

    /** Icon bundle for <code>devices/audio-card</code>. */
    public static final IconBundle AUDIO_CARD = new CachingIconBundle(new TangoProjectIconBundle("devices", "audio-card"));

    /** Icon bundle for <code>devices/audio-input-microphone</code>. */
    public static final IconBundle AUDIO_INPUT_MICROPHONE = new CachingIconBundle(new TangoProjectIconBundle("devices", "audio-input-microphone"));

    /** Icon bundle for <code>devices/battery</code>. */
    public static final IconBundle BATTERY = new CachingIconBundle(new TangoProjectIconBundle("devices", "battery"));

    /** Icon bundle for <code>devices/camera-photo</code>. */
    public static final IconBundle CAMERA_PHOTO = new CachingIconBundle(new TangoProjectIconBundle("devices", "camera-photo"));

    /** Icon bundle for <code>devices/camera-video</code>. */
    public static final IconBundle CAMERA_VIDEO = new CachingIconBundle(new TangoProjectIconBundle("devices", "camera-video"));

    /** Icon bundle for <code>devices/computer</code>. */
    public static final IconBundle COMPUTER = new CachingIconBundle(new TangoProjectIconBundle("devices", "computer"));

    /** Icon bundle for <code>devices/drive-harddisk</code>. */
    public static final IconBundle DRIVE_HARDDISK = new CachingIconBundle(new TangoProjectIconBundle("devices", "drive-harddisk"));

    /** Icon bundle for <code>devices/drive-optical</code>. */
    public static final IconBundle DRIVE_OPTICAL = new CachingIconBundle(new TangoProjectIconBundle("devices", "drive-optical"));

    /** Icon bundle for <code>devices/drive-removable-media</code>. */
    public static final IconBundle DRIVE_REMOVABLE_MEDIA = new CachingIconBundle(new TangoProjectIconBundle("devices", "drive-removable-media"));

    /** Icon bundle for <code>devices/input-gaming</code>. */
    public static final IconBundle INPUT_GAMING = new CachingIconBundle(new TangoProjectIconBundle("devices", "input-gaming"));

    /** Icon bundle for <code>devices/input-keyboard</code>. */
    public static final IconBundle INPUT_KEYBOARD = new CachingIconBundle(new TangoProjectIconBundle("devices", "input-keyboard"));

    /** Icon bundle for <code>devices/input-mouse</code>. */
    public static final IconBundle INPUT_MOUSE = new CachingIconBundle(new TangoProjectIconBundle("devices", "input-mouse"));

    /** Icon bundle for <code>devices/media-flash</code>. */
    public static final IconBundle MEDIA_FLASH = new CachingIconBundle(new TangoProjectIconBundle("devices", "media-flash"));

    /** Icon bundle for <code>devices/media-floppy</code>. */
    public static final IconBundle MEDIA_FLOPPY = new CachingIconBundle(new TangoProjectIconBundle("devices", "media-floppy"));

    /** Icon bundle for <code>devices/media-optical</code>. */
    public static final IconBundle MEDIA_OPTICAL = new CachingIconBundle(new TangoProjectIconBundle("devices", "media-optical"));

    /** Icon bundle for <code>devices/media-tape</code>. */
    //public static final IconBundle MEDIA_TAPE = new CachingIconBundle(new TangoProjectIconBundle("devices", "media-tape"));

    /** Icon bundle for <code>devices/modem</code>. */
    //public static final IconBundle MODEM = new CachingIconBundle(new TangoProjectIconBundle("devices", "modem"));

    /** Icon bundle for <code>devices/multimedia-player</code>. */
    public static final IconBundle MULTIMEDIA_PLAYER = new CachingIconBundle(new TangoProjectIconBundle("devices", "multimedia-player"));

    /** Icon bundle for <code>devices/network-wired</code>. */
    public static final IconBundle NETWORK_WIRED = new CachingIconBundle(new TangoProjectIconBundle("devices", "network-wired"));

    /** Icon bundle for <code>devices/network-wireless</code>. */
    public static final IconBundle NETWORK_WIRELESS = new CachingIconBundle(new TangoProjectIconBundle("devices", "network-wireless"));

    /** Icon bundle for <code>devices/printer</code>. */
    public static final IconBundle PRINTER = new CachingIconBundle(new TangoProjectIconBundle("devices", "printer"));

    /** Icon bundle for <code>devices/video-display</code>. */
    public static final IconBundle VIDEO_DISPLAY = new CachingIconBundle(new TangoProjectIconBundle("devices", "video-display"));

    /** Icon bundle for <code>emblems/emblem-default</code>. */
    //public static final IconBundle EMBLEM_DEFAULT = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-default"));

    /** Icon bundle for <code>emblems/emblem-documents</code>. */
    //public static final IconBundle EMBLEM_DOCUMENTS = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-documents"));

    /** Icon bundle for <code>emblems/emblem-downloads</code>. */
    //public static final IconBundle EMBLEM_DOWNLOADS = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-downloads"));

    /** Icon bundle for <code>emblems/emblem-favorite</code>. */
    public static final IconBundle EMBLEM_FAVORITE = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-favorite"));

    /** Icon bundle for <code>emblems/emblem-important</code>. */
    public static final IconBundle EMBLEM_IMPORTANT = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-important"));

    /** Icon bundle for <code>emblems/emblem-mail</code>. */
    //public static final IconBundle EMBLEM_MAIL = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-mail"));

    /** Icon bundle for <code>emblems/emblem-photos</code>. */
    public static final IconBundle EMBLEM_PHOTOS = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-photos"));

    /** Icon bundle for <code>emblems/emblem-readonly</code>. */
    public static final IconBundle EMBLEM_READONLY = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-readonly"));

    /** Icon bundle for <code>emblems/emblem-shared</code>. */
    //public static final IconBundle EMBLEM_SHARED = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-shared"));

    /** Icon bundle for <code>emblems/emblem-symbolic-link</code>. */
    public static final IconBundle EMBLEM_SYMBOLIC_LINK = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-symbolic-link"));

    /** Icon bundle for <code>emblems/emblem-synchronized</code>. */
    //public static final IconBundle EMBLEM_SYNCHRONIZED = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-synchronized"));

    /** Icon bundle for <code>emblems/emblem-system</code>. */
    public static final IconBundle EMBLEM_SYSTEM = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-system"));

    /** Icon bundle for <code>emblems/emblem-unreadable</code>. */
    public static final IconBundle EMBLEM_UNREADABLE = new CachingIconBundle(new TangoProjectIconBundle("emblems", "emblem-unreadable"));

    /** Icon bundle for <code>emotes/face-angel</code>. */
    public static final IconBundle FACE_ANGEL = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-angel"));

    /** Icon bundle for <code>emotes/face-crying</code>. */
    public static final IconBundle FACE_CRYING = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-crying"));

    /** Icon bundle for <code>emotes/face-devilish</code>. */
    public static final IconBundle FACE_DEVILISH = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-devilish"));

    /** Icon bundle for <code>emotes/face-glasses</code>. */
    public static final IconBundle FACE_GLASSES = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-glasses"));

    /** Icon bundle for <code>emotes/face-grin</code>. */
    public static final IconBundle FACE_GRIN = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-grin"));

    /** Icon bundle for <code>emotes/face-angel</code>. */
    public static final IconBundle FACE_KISS = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-kiss"));

    /** Icon bundle for <code>emotes/face-monkey</code>. */
    public static final IconBundle FACE_MONKEY = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-monkey"));

    /** Icon bundle for <code>emotes/face-plain</code>. */
    public static final IconBundle FACE_PLAIN = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-plain"));

    /** Icon bundle for <code>emotes/face-sad</code>. */
    public static final IconBundle FACE_SAD = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-sad"));

    /** Icon bundle for <code>emotes/face-smile</code>. */
    public static final IconBundle FACE_SMILE = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-smile"));

    /** Icon bundle for <code>emotes/face-smile-big</code>. */
    public static final IconBundle FACE_SMILE_BIG = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-smile-big"));

    /** Icon bundle for <code>emotes/face-smirk</code>. */
    //public static final IconBundle FACE_SMIRK = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-smirk"));

    /** Icon bundle for <code>emotes/face-surprise</code>. */
    public static final IconBundle FACE_SURPRISE = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-surprise"));

    /** Icon bundle for <code>emotes/face-wink</code>. */
    public static final IconBundle FACE_WINK = new CachingIconBundle(new TangoProjectIconBundle("emotes", "face-wink"));

    /** Icon bundle for <code>intl/flag-..</code>. */
    //public static final IconBundle FLAG_.. = new CachingIconBundle(new TangoProjectIconBundle("intl", "flag-.."));

    /** Icon bundle for <code>mimetypes/application-certificate</code>. */
    public static final IconBundle APPLICATION_CERTIFICATE = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "application-certificate"));

    /** Icon bundle for <code>mimetypes/application-x-executable</code>. */
    public static final IconBundle APPLICATION_X_EXECUTABLE = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "application-x-executable"));

    /** Icon bundle for <code>mimetypes/audio-x-generic</code>. */
    public static final IconBundle AUDIO_X_GENERIC = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "audio-x-generic"));

    /** Icon bundle for <code>mimetypes/font-x-generic</code>. */
    public static final IconBundle FONT_X_GENERIC = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "font-x-generic"));

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

    /** Icon bundle for <code>mimetypes/x-office-calendar</code>. */
    public static final IconBundle X_OFFICE_CALENDAR = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-calendar"));

    /** Icon bundle for <code>mimetypes/x-office-document</code>. */
    public static final IconBundle X_OFFICE_DOCUMENT = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-document"));

    /** Icon bundle for <code>mimetypes/x-office-document-template</code>. */
    public static final IconBundle X_OFFICE_DOCUMENT_TEMPLATE = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-document-template"));

    /** Icon bundle for <code>mimetypes/x-office-drawing</code>. */
    public static final IconBundle X_OFFICE_DRAWING = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-drawing"));

    /** Icon bundle for <code>mimetypes/x-office-drawing-template</code>. */
    public static final IconBundle X_OFFICE_DRAWING_TEMPLATE = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-drawing-template"));

    /** Icon bundle for <code>mimetypes/x-office-presentation</code>. */
    public static final IconBundle X_OFFICE_PRESENTATION = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-presentation"));

    /** Icon bundle for <code>mimetypes/x-office-presentation-template</code>. */
    public static final IconBundle X_OFFICE_PRESENTATION_TEMPLATE = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-presentation-template"));

    /** Icon bundle for <code>mimetypes/x-office-spreadsheet</code>. */
    public static final IconBundle X_OFFICE_SPREADSHEET = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-spreadsheet"));

    /** Icon bundle for <code>mimetypes/x-office-spreadsheet-template</code>. */
    public static final IconBundle X_OFFICE_SPREADSHEET_TEMPLATE = new CachingIconBundle(new TangoProjectIconBundle("mimetypes", "x-office-spreadsheet-template"));

    /** Icon bundle for <code>places/folder</code>. */
    public static final IconBundle FOLDER = new CachingIconBundle(new TangoProjectIconBundle("places", "folder"));

    /** Icon bundle for <code>places/folder-remote</code>. */
    public static final IconBundle FOLDER_REMOTE = new CachingIconBundle(new TangoProjectIconBundle("places", "folder-remote"));

    /** Icon bundle for <code>places/folder-saved-search</code>. */
    public static final IconBundle FOLDER_SAVED_SEARCH = new CachingIconBundle(new TangoProjectIconBundle("places", "folder-saved-search"));

    /** Icon bundle for <code>places/network-server</code>. */
    public static final IconBundle NETWORK_SERVER = new CachingIconBundle(new TangoProjectIconBundle("places", "network-server"));

    /** Icon bundle for <code>places/network-workgroup</code>. */
    public static final IconBundle NETWORK_WORKGROUP = new CachingIconBundle(new TangoProjectIconBundle("places", "network-workgroup"));

    /** Icon bundle for <code>places/start-here</code>. */
    public static final IconBundle START_HERE = new CachingIconBundle(new TangoProjectIconBundle("places", "start-here"));

    /** Icon bundle for <code>places/user-desktop</code>. */
    public static final IconBundle USER_DESKTOP = new CachingIconBundle(new TangoProjectIconBundle("places", "user-desktop"));

    /** Icon bundle for <code>places/user-home</code>. */
    public static final IconBundle USER_HOME = new CachingIconBundle(new TangoProjectIconBundle("places", "user-home"));

    /** Icon bundle for <code>places/user-trash</code>. */
    public static final IconBundle USER_TRASH = new CachingIconBundle(new TangoProjectIconBundle("places", "user-trash"));

    /** Icon bundle for <code>status/appointment-missed</code>. */
    //public static final IconBundle APPOINTMENT_MISSED = new CachingIconBundle(new TangoProjectIconBundle("status", "appointment-missed"));

    /** Icon bundle for <code>status/appointment-soon</code>. */
    //public static final IconBundle APPOINTMENT_SOON = new CachingIconBundle(new TangoProjectIconBundle("status", "appointment-soon"));

    /** Icon bundle for <code>status/audio-volume-high</code>. */
    public static final IconBundle AUDIO_VOLUME_HIGH = new CachingIconBundle(new TangoProjectIconBundle("status", "audio-volume-high"));

    /** Icon bundle for <code>status/audio-volume-low</code>. */
    public static final IconBundle AUDIO_VOLUME_LOW = new CachingIconBundle(new TangoProjectIconBundle("status", "audio-volume-low"));

    /** Icon bundle for <code>status/audio-volume-medium</code>. */
    public static final IconBundle AUDIO_VOLUME_MEDIUM = new CachingIconBundle(new TangoProjectIconBundle("status", "audio-volume-medium"));

    /** Icon bundle for <code>status/audio-volume-muted</code>. */
    public static final IconBundle AUDIO_VOLUME_MUTED = new CachingIconBundle(new TangoProjectIconBundle("status", "audio-volume-muted"));

    /** Icon bundle for <code>status/battery-caution</code>. */
    public static final IconBundle BATTERY_CAUTION = new CachingIconBundle(new TangoProjectIconBundle("status", "battery-caution"));

    /** Icon bundle for <code>status/battery-low</code>. */
    //public static final IconBundle BATTERY_LOW = new CachingIconBundle(new TangoProjectIconBundle("status", "battery-low"));

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
    public static final IconBundle FOLDER_DRAG_ACCEPT = new CachingIconBundle(new TangoProjectIconBundle("status", "folder-drag-accept"));

    /** Icon bundle for <code>status/folder-open</code>. */
    public static final IconBundle FOLDER_OPEN = new CachingIconBundle(new TangoProjectIconBundle("status", "folder-open"));

    /** Icon bundle for <code>status/folder-visiting</code>. */
    public static final IconBundle FOLDER_VISITING = new CachingIconBundle(new TangoProjectIconBundle("status", "folder-visiting"));

    /** Icon bundle for <code>status/image-loading</code>. */
    public static final IconBundle IMAGE_LOADING = new CachingIconBundle(new TangoProjectIconBundle("status", "image-loading"));

    /** Icon bundle for <code>status/image-missing</code>. */
    public static final IconBundle IMAGE_MISSING = new CachingIconBundle(new TangoProjectIconBundle("status", "image-missing"));

    /** Icon bundle for <code>status/mail-attachment</code>. */
    public static final IconBundle MAIL_ATTACHMENT = new CachingIconBundle(new TangoProjectIconBundle("status", "mail-attachment"));

    /** Icon bundle for <code>status/mail-unread</code>. */
    //public static final IconBundle MAIL_UNREAD = new CachingIconBundle(new TangoProjectIconBundle("status", "mail-unread"));

    /** Icon bundle for <code>status/mail-read</code>. */
    //public static final IconBundle MAIL_READ = new CachingIconBundle(new TangoProjectIconBundle("status", "mail-read"));

    /** Icon bundle for <code>status/mail-replied</code>. */
    //public static final IconBundle MAIL_REPLIED = new CachingIconBundle(new TangoProjectIconBundle("status", "mail-replied"));

    /** Icon bundle for <code>status/mail-signed</code>. */
    //public static final IconBundle MAIL_SIGNED = new CachingIconBundle(new TangoProjectIconBundle("status", "mail-signed"));

    /** Icon bundle for <code>status/mail-signed-verified</code>. */
    //public static final IconBundle MAIL_SIGNED_VERIFIED = new CachingIconBundle(new TangoProjectIconBundle("status", "mail-signed-verified"));

    /** Icon bundle for <code>status/media-playlist-repeat</code>. */
    //public static final IconBundle MEDIA_PLAYLIST_REPEAT = new CachingIconBundle(new TangoProjectIconBundle("status", "media-playlist-repeat"));

    /** Icon bundle for <code>status/media-playlist-shuffle</code>. */
    //public static final IconBundle MEDIA_PLAYLIST_SHUFFLE = new CachingIconBundle(new TangoProjectIconBundle("status", "media-playlist-shuffle"));

    /** Icon bundle for <code>status/network-error</code>. */
    public static final IconBundle NETWORK_ERROR = new CachingIconBundle(new TangoProjectIconBundle("status", "network-error"));

    /** Icon bundle for <code>status/network-idle</code>. */
    public static final IconBundle NETWORK_IDLE = new CachingIconBundle(new TangoProjectIconBundle("status", "network-idle"));

    /** Icon bundle for <code>status/network-offline</code>. */
    public static final IconBundle NETWORK_OFFLINE = new CachingIconBundle(new TangoProjectIconBundle("status", "network-offline"));

    /** Icon bundle for <code>status/network-receive</code>. */
    public static final IconBundle NETWORK_RECEIVE = new CachingIconBundle(new TangoProjectIconBundle("status", "network-receive"));

    /** Icon bundle for <code>status/network-transmit</code>. */
    public static final IconBundle NETWORK_TRANSMIT = new CachingIconBundle(new TangoProjectIconBundle("status", "network-transmit"));

    /** Icon bundle for <code>status/network-transmit-receive</code>. */
    public static final IconBundle NETWORK_TRANSMIT_RECEIVE = new CachingIconBundle(new TangoProjectIconBundle("status", "network-transmit-receive"));

    /** Icon bundle for <code>status/network-wireless-encrypted</code>. */
    public static final IconBundle NETWORK_WIRELESS_ENCRYPTED = new CachingIconBundle(new TangoProjectIconBundle("status", "network-wireless-encrypted"));

    /** Icon bundle for <code>status/printer-error</code>. */
    public static final IconBundle PRINTER_ERROR = new CachingIconBundle(new TangoProjectIconBundle("status", "printer-error"));

    /** Icon bundle for <code>status/printer-printing</code>. */
    //public static final IconBundle PRINTER_PRINTING = new CachingIconBundle(new TangoProjectIconBundle("status", "printer-printing"));

    /** Icon bundle for <code>status/security-high</code>. */
    //public static final IconBundle SECURITY_HIGH = new CachingIconBundle(new TangoProjectIconBundle("status", "security-high"));

    /** Icon bundle for <code>status/security-medium</code>. */
    //public static final IconBundle SECURITY_MEDIUM = new CachingIconBundle(new TangoProjectIconBundle("status", "security-medium"));

    /** Icon bundle for <code>status/security-low</code>. */
    //public static final IconBundle SECURITY_LOW = new CachingIconBundle(new TangoProjectIconBundle("status", "security-low"));

    /** Icon bundle for <code>status/software-update-available</code>. */
    public static final IconBundle SOFTWARE_UPDATE_AVAILABLE = new CachingIconBundle(new TangoProjectIconBundle("status", "software-update-available"));

    /** Icon bundle for <code>status/software-update-urgent</code>. */
   public static final IconBundle SOFTWARE_UPDATE_URGENT = new CachingIconBundle(new TangoProjectIconBundle("status", "software-update-urgent"));

    /** Icon bundle for <code>status/sync-error</code>. */
    //public static final IconBundle SYNC_ERROR = new CachingIconBundle(new TangoProjectIconBundle("status", "sync-error"));

    /** Icon bundle for <code>status/sync-synchronizing</code>. */
    //public static final IconBundle SYNC_SYNCHRONIZING = new CachingIconBundle(new TangoProjectIconBundle("status", "sync-synchronizing"));

    /** Icon bundle for <code>status/task-due</code>. */
    //public static final IconBundle TASK_DUE = new CachingIconBundle(new TangoProjectIconBundle("status", "task-due"));

    /** Icon bundle for <code>status/task-passed-due</code>. */
    //public static final IconBundle TASK_PASSED_DUE = new CachingIconBundle(new TangoProjectIconBundle("status", "task-passed-due"));

    /** Icon bundle for <code>status/user-away</code>. */
    //public static final IconBundle USER_AWAY = new CachingIconBundle(new TangoProjectIconBundle("status", "user-away"));

    /** Icon bundle for <code>status/user-idle</code>. */
    //public static final IconBundle USER_IDLE = new CachingIconBundle(new TangoProjectIconBundle("status", "user-idle"));

    /** Icon bundle for <code>status/user-offline</code>. */
    //public static final IconBundle USER_OFFLINE = new CachingIconBundle(new TangoProjectIconBundle("status", "user-offline"));

    /** Icon bundle for <code>status/user-online</code>. */
    //public static final IconBundle USER_ONLINE = new CachingIconBundle(new TangoProjectIconBundle("status", "user-online"));

    /** Icon bundle for <code>status/user-trash-full</code>. */
    public static final IconBundle USER_TRASH_FULL = new CachingIconBundle(new TangoProjectIconBundle("status", "user-trash-full"));

    /** Icon bundle for <code>status/weather-clear</code>. */
    public static final IconBundle WEATHER_CLEAR = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-clear"));

    /** Icon bundle for <code>status/weather-clear-night</code>. */
    public static final IconBundle WEATHER_CLEAR_NIGHT = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-clear-night"));

    /** Icon bundle for <code>status/weather-few-clouds</code>. */
    public static final IconBundle WEATHER_FEW_CLOUDS = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-few-clouds"));

    /** Icon bundle for <code>status/weather-few-clouds-night</code>. */
    public static final IconBundle WEATHER_FEW_CLOUDS_NIGHT = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-few-clouds-night"));

    /** Icon bundle for <code>status/weather-fog</code>. */
    //public static final IconBundle WEATHER_FOG = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-fog"));

    /** Icon bundle for <code>status/weather-overcast</code>. */
    public static final IconBundle WEATHER_OVERCAST = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-overcast"));

    /** Icon bundle for <code>status/weather-severe-alert</code>. */
    public static final IconBundle WEATHER_SEVERE_ALERT = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-severe-alert"));

    /** Icon bundle for <code>status/weather-showers</code>. */
    public static final IconBundle WEATHER_SHOWERS = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-showers"));

    /** Icon bundle for <code>status/weather-showers-scattered</code>. */
    public static final IconBundle WEATHER_SHOWERS_SCATTERED = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-showers-scattered"));

    /** Icon bundle for <code>status/weather-snow</code>. */
    public static final IconBundle WEATHER_SNOW = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-snow"));

    /** Icon bundle for <code>status/weather-storm</code>. */
    public static final IconBundle WEATHER_STORM = new CachingIconBundle(new TangoProjectIconBundle("status", "weather-storm"));

    /** Map of Tango Project icon bundles keyed by name. */
    //private static final Map<String, IconBundle> iconBundles = new HashMap<String, IconBundle>(275);
    private static final Map iconBundles = new HashMap(275);

    static
    {
        iconBundles.put("address-book-new", ADDRESS_BOOK_NEW);
        //iconBundles.put("application-exit", APPLICATION_EXIT);
        iconBundles.put("appointment-new", APPOINTMENT_NEW);
        iconBundles.put("bookmark-new", BOOKMARK_NEW);
        iconBundles.put("contact-new", CONTACT_NEW);
        //iconBundles.put("dialog-cancel", DIALOG_CANCEL);
        //iconBundles.put("dialog-close", DIALOG_CLOSE);
        //iconBundles.put("dialog-ok", DIALOG_OK);
        iconBundles.put("document-new", DOCUMENT_NEW);
        iconBundles.put("document-open", DOCUMENT_OPEN);
        //iconBundles.put("document-open-recent", DOCUMENT_OPEN_RECENT);
        //iconBundles.put("document-page-setup", DOCUMENT_PAGE_SETUP);
        iconBundles.put("document-print", DOCUMENT_PRINT);
        iconBundles.put("document-print-preview", DOCUMENT_PRINT_PREVIEW);
        iconBundles.put("document-properties", DOCUMENT_PROPERTIES);
        //iconBundles.put("document-revert", DOCUMENT_REVERT);
        iconBundles.put("document-save", DOCUMENT_SAVE);
        iconBundles.put("document-save-as", DOCUMENT_SAVE_AS);
        iconBundles.put("edit-clear", EDIT_CLEAR);
        iconBundles.put("edit-copy", EDIT_COPY);
        iconBundles.put("edit-cut", EDIT_CUT);
        iconBundles.put("edit-delete", EDIT_DELETE);
        iconBundles.put("edit-find", EDIT_FIND);
        iconBundles.put("edit-find-replace", EDIT_FIND_REPLACE);
        iconBundles.put("edit-paste", EDIT_PASTE);
        iconBundles.put("edit-redo", EDIT_REDO);
        iconBundles.put("edit-select-all", EDIT_SELECT_ALL);
        iconBundles.put("edit-undo", EDIT_UNDO);
        iconBundles.put("folder-new", FOLDER_NEW);
        iconBundles.put("format-indent-less", FORMAT_INDENT_LESS);
        iconBundles.put("format-indent-more", FORMAT_INDENT_MORE);
        iconBundles.put("format-justify-center", FORMAT_JUSTIFY_CENTER);
        iconBundles.put("format-justify-fill", FORMAT_JUSTIFY_FILL);
        iconBundles.put("format-justify-left", FORMAT_JUSTIFY_LEFT);
        iconBundles.put("format-justify-right", FORMAT_JUSTIFY_RIGHT);
        iconBundles.put("format-text-bold", FORMAT_TEXT_BOLD);
        //iconBundles.put("format-text-direction-ltr", FORMAT_TEXT_DIRECTION_LTR);
        //iconBundles.put("format-text-direction-rtl", FORMAT_TEXT_DIRECTION_RTL);
        iconBundles.put("format-text-italic", FORMAT_TEXT_ITALIC);
        iconBundles.put("format-text-strikethrough", FORMAT_TEXT_STRIKETHROUGH);
        iconBundles.put("format-text-underline", FORMAT_TEXT_UNDERLINE);
        iconBundles.put("go-bottom", GO_BOTTOM);
        iconBundles.put("go-down", GO_DOWN);
        iconBundles.put("go-first", GO_FIRST);
        //iconBundles.put("go-home", GO_HOME);
        iconBundles.put("go-jump", GO_JUMP);
        iconBundles.put("go-last", GO_LAST);
        iconBundles.put("go-next", GO_NEXT);
        iconBundles.put("go-previous", GO_PREVIOUS);
        iconBundles.put("go-top", GO_TOP);
        iconBundles.put("go-up", GO_UP);
        //iconBundles.put("help-about", HELP_ABOUT);
        //iconBundles.put("help-contents", HELP_CONTENTS);
        //iconBundles.put("help-faq", HELP_FAQ);
        //iconBundles.put("insert-image", INSERT_IMAGE);
        //iconBundles.put("insert-link", INSERT_LINK);
        //iconBundles.put("insert-object", INSERT_OBJECT);
        //iconBundles.put("insert-text", INSERT_TEXT);
        iconBundles.put("list-add", LIST_ADD);
        iconBundles.put("list-remove", LIST_REMOVE);
        iconBundles.put("mail-forward", MAIL_FORWARD);
        //iconBundles.put("mail-mark-important", MAIL_MARK_IMPORTANT);
        iconBundles.put("mail-mark-junk", MAIL_MARK_JUNK);
        //iconBundles.put("mail-mark-notjunk", MAIL_MARK_NOTJUNK);
        //iconBundles.put("mail-mark-read", MAIL_MARK_READ);
        //iconBundles.put("mail-mark-unread", MAIL_MARK_UNREAD);
        iconBundles.put("mail-message-new", MAIL_MESSAGE_NEW);
        iconBundles.put("mail-reply-all", MAIL_REPLY_ALL);
        iconBundles.put("mail-reply-sender", MAIL_REPLY_SENDER);
        //iconBundles.put("mail-send", MAIL_SEND);
        iconBundles.put("mail-send-receive", MAIL_SEND_RECEIVE);
        iconBundles.put("media-eject", MEDIA_EJECT);
        iconBundles.put("media-playback-pause", MEDIA_PLAYBACK_PAUSE);
        iconBundles.put("media-playback-start", MEDIA_PLAYBACK_START);
        iconBundles.put("media-playback-stop", MEDIA_PLAYBACK_STOP);
        iconBundles.put("media-record", MEDIA_RECORD);
        iconBundles.put("media-seek-backward", MEDIA_SEEK_BACKWARD);
        iconBundles.put("media-seek-forward", MEDIA_SEEK_FORWARD);
        iconBundles.put("media-skip-backward", MEDIA_SKIP_BACKWARD);
        iconBundles.put("media-skip-forward", MEDIA_SKIP_FORWARD);
        //iconBundles.put("object-flip-horizontal", OBJECT_FLIP_HORIZONTAL);
        //iconBundles.put("object-flip-vertical", OBJECT_FLIP_VERTICAL);
        //iconBundles.put("object-rotate-left", OBJECT_ROTATE_LEFT);
        //iconBundles.put("object-rotate-right", OBJECT_ROTATE_RIGHT);
        iconBundles.put("process-stop", PROCESS_STOP);
        iconBundles.put("system-lock-screen", SYSTEM_LOCK_SCREEN);
        iconBundles.put("system-log-out", SYSTEM_LOG_OUT);
        //iconBundles.put("system-run", SYSTEM_RUN);
        iconBundles.put("system-search", SYSTEM_SEARCH);
        iconBundles.put("system-shutdown", SYSTEM_SHUTDOWN);
        iconBundles.put("tab-new", TAB_NEW);
        //iconBundles.put("tools-check-spelling", TOOLS_CHECK_SPELLING);
        iconBundles.put("view-fullscreen", VIEW_FULLSCREEN);
        iconBundles.put("view-refresh", VIEW_REFRESH);
        //iconBundles.put("view-sort-ascending", VIEW_SORT_ASCENDING);
        //iconBundles.put("view-sort-descending", VIEW_SORT_DESCENDING);
        //iconBundles.put("window-close", WINDOW_CLOSE);
        iconBundles.put("window-new", WINDOW_NEW);
        //iconBundles.put("zoom-best-fit", ZOOM_BEST_FIT);
        //iconBundles.put("zoom-in", ZOOM_IN);
        //iconBundles.put("zoom-original", ZOOM_ORIGINAL);
        //iconBundles.put("zoom-out", ZOOM_OUT);

        //iconBundles.put("process-working", PROCESS_WORKING);

        iconBundles.put("accessories-calculator", ACCESSORIES_CALCULATOR);
        iconBundles.put("accessories-character-map", ACCESSORIES_CHARACTER_MAP);
        //iconBundles.put("accessories-dictionary", ACCESSORIES_DICTIONARY);
        iconBundles.put("accessories-text-editor", ACCESSORIES_TEXT_EDITOR);
        iconBundles.put("help-browser", HELP_BROWSER);
        iconBundles.put("internet-group-chat", INTERNET_GROUP_CHAT);
        iconBundles.put("internet-mail", INTERNET_MAIL);
        iconBundles.put("internet-web-browser", INTERNET_WEB_BROWSER);
        iconBundles.put("office-calendar", OFFICE_CALENDAR);
        //iconBundles.put("multimedia-volume-control", MULTIMEDIA_VOLUME_CONTROL);
        iconBundles.put("preferences-desktop-accessibility", PREFERENCES_DESKTOP_ACCESSIBILITY);
        iconBundles.put("preferences-desktop-assistive-technology", PREFERENCES_DESKTOP_ASSISTIVE_TECHNOLOGY);
        iconBundles.put("preferences-desktop-font", PREFERENCES_DESKTOP_FONT);
        //iconBundles.put("preferences-desktop-keyboard", PREFERENCES_DESKTOP_KEYBOARD);
        iconBundles.put("preferences-desktop-keyboard-shortcuts", PREFERENCES_DESKTOP_KEYBOARD_SHORTCUTS);
        iconBundles.put("preferences-desktop-locale", PREFERENCES_DESKTOP_LOCALE);
        iconBundles.put("preferences-desktop-multimedia", PREFERENCES_DESKTOP_MULTIMEDIA);
        iconBundles.put("preferences-desktop-screensaver", PREFERENCES_DESKTOP_SCREENSAVER);
        iconBundles.put("preferences-desktop-remote-desktop", PREFERENCES_DESKTOP_REMOTE_DESKTOP);
        iconBundles.put("preferences-desktop-theme", PREFERENCES_DESKTOP_THEME);
        iconBundles.put("preferences-desktop-wallpaper", PREFERENCES_DESKTOP_WALLPAPER);
        iconBundles.put("preferences-system-network-proxy", PREFERENCES_SYSTEM_NETWORK_PROXY);
        iconBundles.put("preferences-system-session", PREFERENCES_SYSTEM_SESSION);
        iconBundles.put("preferences-system-windows", PREFERENCES_SYSTEM_WINDOWS);
        iconBundles.put("system-file-manager", SYSTEM_FILE_MANAGER);
        //iconBundles.put("system-software-update", SYSTEM_SOFTWARE_UPDATE);
        iconBundles.put("system-users", SYSTEM_USERS);
        iconBundles.put("utilities-system-monitor", UTILITIES_SYSTEM_MONITOR);
        iconBundles.put("utilities-terminal", UTILITIES_TERMINAL);
        iconBundles.put("applications-accessories", APPLICATIONS_ACCESSORIES);
        //iconBundles.put("applications-development", APPLICATIONS_DEVELOPMENT);
        //iconBundles.put("applications-engineering", APPLICATIONS_ENGINEERING);
        iconBundles.put("applications-games", APPLICATIONS_GAMES);
        iconBundles.put("applications-graphics", APPLICATIONS_GRAPHICS);
        iconBundles.put("applications-internet", APPLICATIONS_INTERNET);
        iconBundles.put("applications-multimedia", APPLICATIONS_MULTIMEDIA);
        iconBundles.put("applications-office", APPLICATIONS_OFFICE);
        iconBundles.put("applications-other", APPLICATIONS_OTHER);
        //iconBundles.put("applications-science", APPLICATIONS_SCIENCE);
        iconBundles.put("applications-system", APPLICATIONS_SYSTEM);
        //iconBundles.put("applications-utilities", APPLICATIONS_UTILITIES);
        iconBundles.put("preferences-desktop", PREFERENCES_DESKTOP);
        iconBundles.put("preferences-desktop-peripherals", PREFERENCES_DESKTOP_PERIPHERALS);
        //iconBundles.put("preferences-desktop-personal", PREFERENCES_DESKTOP_PERSONAL);
        //iconBundles.put("preferences-other", PREFERENCES_OTHER);
        iconBundles.put("preferences-system", PREFERENCES_SYSTEM);
        //iconBundles.put("preferences-system-network", PREFERENCES_SYSTEM_NETWORK);
        //iconBundles.put("system-help", SYSTEM_HELP);
        iconBundles.put("audio-card", AUDIO_CARD);
        iconBundles.put("audio-input-microphone", AUDIO_INPUT_MICROPHONE);
        iconBundles.put("battery", BATTERY);
        iconBundles.put("camera-photo", CAMERA_PHOTO);
        iconBundles.put("camera-video", CAMERA_VIDEO);
        iconBundles.put("computer", COMPUTER);
        iconBundles.put("drive-harddisk", DRIVE_HARDDISK);
        iconBundles.put("drive-optical", DRIVE_OPTICAL);
        iconBundles.put("drive-removable-media", DRIVE_REMOVABLE_MEDIA);
        iconBundles.put("input-gaming", INPUT_GAMING);
        iconBundles.put("input-keyboard", INPUT_KEYBOARD);
        iconBundles.put("input-mouse", INPUT_MOUSE);
        iconBundles.put("media-flash", MEDIA_FLASH);
        iconBundles.put("media-floppy", MEDIA_FLOPPY);
        iconBundles.put("media-optical", MEDIA_OPTICAL);
        //iconBundles.put("media-tape", MEDIA_TAPE);
        //iconBundles.put("modem", MODEM);
        iconBundles.put("multimedia-player", MULTIMEDIA_PLAYER);
        iconBundles.put("network-wired", NETWORK_WIRED);
        iconBundles.put("network-wireless", NETWORK_WIRELESS);
        iconBundles.put("printer", PRINTER);
        iconBundles.put("video-display", VIDEO_DISPLAY);

        //iconBundles.put("emblem-default", EMBLEM_DEFAULT);
        //iconBundles.put("emblem-documents", EMBLEM_DOCUMENTS);
        //iconBundles.put("emblem-downloads", EMBLEM_DOWNLOADS);
        iconBundles.put("emblem-favorite", EMBLEM_FAVORITE);
        iconBundles.put("emblem-important", EMBLEM_IMPORTANT);
        //iconBundles.put("emblem-mail", EMBLEM_MAIL);
        iconBundles.put("emblem-photos", EMBLEM_PHOTOS);
        iconBundles.put("emblem-readonly", EMBLEM_READONLY);
        //iconBundles.put("emblem-shared", EMBLEM_SHARED);
        iconBundles.put("emblem-symbolic-link", EMBLEM_SYMBOLIC_LINK);
        //iconBundles.put("emblem-synchronized", EMBLEM_SYNCHRONIZED);
        iconBundles.put("emblem-system", EMBLEM_SYSTEM);
        iconBundles.put("emblem-unreadable", EMBLEM_UNREADABLE);

        iconBundles.put("face-angel", FACE_ANGEL);
        iconBundles.put("face-crying", FACE_CRYING);
        iconBundles.put("face-devilish", FACE_DEVILISH);
        iconBundles.put("face-glasses", FACE_GLASSES);
        iconBundles.put("face-grin", FACE_GRIN);
        iconBundles.put("face-kiss", FACE_KISS);
        iconBundles.put("face-monkey", FACE_MONKEY);
        iconBundles.put("face-plain", FACE_PLAIN);
        iconBundles.put("face-sad", FACE_SAD);
        iconBundles.put("face-smile", FACE_SMILE);
        iconBundles.put("face-smile-big", FACE_SMILE_BIG);
        //iconBundles.put("face-smirk", FACE_SMIRK);
        iconBundles.put("face-surprise", FACE_SURPRISE);
        iconBundles.put("face-wink", FACE_WINK);

        //iconBundles.put("flag-..", FLAG_..);

        iconBundles.put("application-certificate", APPLICATION_CERTIFICATE);
        iconBundles.put("application-x-executable", APPLICATION_X_EXECUTABLE);
        iconBundles.put("audio-x-generic", AUDIO_X_GENERIC);
        iconBundles.put("font-x-generic", FONT_X_GENERIC);
        iconBundles.put("image-x-generic", IMAGE_X_GENERIC);
        iconBundles.put("package-x-generic", PACKAGE_X_GENERIC);
        iconBundles.put("text-html", TEXT_HTML);
        iconBundles.put("text-x-generic", TEXT_X_GENERIC);
        iconBundles.put("text-x-generic-template", TEXT_X_GENERIC_TEMPLATE);
        iconBundles.put("text-x-script", TEXT_X_SCRIPT);
        iconBundles.put("video-x-generic", VIDEO_X_GENERIC);
        iconBundles.put("x-office-address-book", X_OFFICE_ADDRESS_BOOK);
        iconBundles.put("x-office-calendar", X_OFFICE_CALENDAR);
        iconBundles.put("x-office-document", X_OFFICE_DOCUMENT);
        iconBundles.put("x-office-document-template", X_OFFICE_DOCUMENT_TEMPLATE);
        iconBundles.put("x-office-drawing", X_OFFICE_DRAWING);
        iconBundles.put("x-office-drawing-template", X_OFFICE_DRAWING_TEMPLATE);
        iconBundles.put("x-office-presentation", X_OFFICE_PRESENTATION);
        iconBundles.put("x-office-presentation-template", X_OFFICE_PRESENTATION_TEMPLATE);
        iconBundles.put("x-office-spreadsheet", X_OFFICE_SPREADSHEET);
        iconBundles.put("x-office-spreadsheet-template", X_OFFICE_SPREADSHEET_TEMPLATE);

        iconBundles.put("folder", FOLDER);
        iconBundles.put("folder-remote", FOLDER_REMOTE);
        iconBundles.put("folder-saved-search", FOLDER_SAVED_SEARCH);
        iconBundles.put("network-server", NETWORK_SERVER);
        iconBundles.put("network-workgroup", NETWORK_WORKGROUP);
        iconBundles.put("start-here", START_HERE);
        iconBundles.put("user-desktop", USER_DESKTOP);
        iconBundles.put("user-home", USER_HOME);
        iconBundles.put("user-trash", USER_TRASH);

        //iconBundles.put("appointment-missed", APPOINTMENT_MISSED);
        //iconBundles.put("appointment-soon", APPOINTMENT_SOON);
        iconBundles.put("audio-volume-high", AUDIO_VOLUME_HIGH);
        iconBundles.put("audio-volume-low", AUDIO_VOLUME_LOW);
        iconBundles.put("audio-volume-medium", AUDIO_VOLUME_MEDIUM);
        iconBundles.put("audio-volume-muted", AUDIO_VOLUME_MUTED);
        iconBundles.put("battery-caution", BATTERY_CAUTION);
        //iconBundles.put("battery-low", BATTERY_LOW);
        iconBundles.put("dialog-error", DIALOG_ERROR);
        iconBundles.put("dialog-information", DIALOG_INFORMATION);
        //iconBundles.put("dialog-password", DIALOG_PASSWORD);
        //iconBundles.put("dialog-question", DIALOG_QUESTION);
        iconBundles.put("dialog-warning", DIALOG_WARNING);
        iconBundles.put("folder-drag-accept", FOLDER_DRAG_ACCEPT);
        iconBundles.put("folder-open", FOLDER_OPEN);
        iconBundles.put("folder-visiting", FOLDER_VISITING);
        iconBundles.put("image-loading", IMAGE_LOADING);
        iconBundles.put("image-missing", IMAGE_MISSING);
        iconBundles.put("mail-attachment", MAIL_ATTACHMENT);
        //iconBundles.put("mail-unread", MAIL_UNREAD);
        //iconBundles.put("mail-read", MAIL_READ);
        //iconBundles.put("mail-replied", MAIL_REPLIED);
        //iconBundles.put("mail-signed", MAIL_SIGNED);
        //iconBundles.put("mail-signed-verified", MAIL_SIGNED_VERIFIED);
        //iconBundles.put("media-playlist-repeat", MEDIA_PLAYLIST_REPEAT);
        //iconBundles.put("media-playlist-shuffle", MEDIA_PLAYLIST_SHUFFLE);
        iconBundles.put("network-error", NETWORK_ERROR);
        iconBundles.put("network-idle", NETWORK_IDLE);
        iconBundles.put("network-offline", NETWORK_OFFLINE);
        iconBundles.put("network-receive", NETWORK_RECEIVE);
        iconBundles.put("network-transmit", NETWORK_TRANSMIT);
        iconBundles.put("network-transmit-receive", NETWORK_TRANSMIT_RECEIVE);
        iconBundles.put("network-wireless-encrypted", NETWORK_WIRELESS_ENCRYPTED);
        iconBundles.put("printer-error", PRINTER_ERROR);
        //iconBundles.put("printer-printing", PRINTER_PRINTING);
        //iconBundles.put("security-high", SECURITY_HIGH);
        //iconBundles.put("security-medium", SECURITY_MEDIUM);
        //iconBundles.put("security-low", SECURITY_LOW);
        //iconBundles.put("software-update-available", SOFTWARE_UPDATE_AVAILABLE);
        //iconBundles.put("software-update-urgent", SOFTWARE_UPDATE_URGENT);
        //iconBundles.put("sync-error", SYNC_ERROR);
        //iconBundles.put("sync-synchronizing", SYNC_SYNCHRONIZING);
        //iconBundles.put("task-due", TASK_DUE);
        //iconBundles.put("task-passed-due", TASK_PASSED_DUE);
        //iconBundles.put("user-away", USER_AWAY);
        //iconBundles.put("user-idle", USER_IDLE);
        //iconBundles.put("user-offline", USER_OFFLINE);
        //iconBundles.put("user-online", USER_ONLINE);
        iconBundles.put("user-trash-full", USER_TRASH_FULL);
        iconBundles.put("weather-clear", WEATHER_CLEAR);
        iconBundles.put("weather-clear-night", WEATHER_CLEAR_NIGHT);
        iconBundles.put("weather-few-clouds", WEATHER_FEW_CLOUDS);
        iconBundles.put("weather-few-clouds-night", WEATHER_FEW_CLOUDS_NIGHT);
        //iconBundles.put("weather-fog", WEATHER_FOG);
        iconBundles.put("weather-overcast", WEATHER_OVERCAST);
        iconBundles.put("weather-severe-alert", WEATHER_SEVERE_ALERT);
        iconBundles.put("weather-showers", WEATHER_SHOWERS);
        iconBundles.put("weather-showers-scattered", WEATHER_SHOWERS_SCATTERED);
        iconBundles.put("weather-snow", WEATHER_SNOW);
        iconBundles.put("weather-storm", WEATHER_STORM);
    }

    /**
     * Return the Tango Project icon bundle with the specified name, if any.
     *
     * @param name Tango Project icon bundle name
     * @return the Tango Project icon bundle with the specified name, or
     *    <code>null</code> if no such icon bundle exists
     */
    public static IconBundle valueOf(final String name)
    {
        //return iconBundles.get(name);
        return (IconBundle) iconBundles.get(name);
    }
}
