/*

    dsh-identify  Lightweight components for identifiable beans.
    Copyright (c) 2003-2011 held jointly by the individual authors.

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
package org.dishevelled.identify;

import java.beans.BeanInfo;
import java.beans.BeanDescriptor;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.IntrospectionException;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.dishevelled.iconbundle.IconBundle;

import org.dishevelled.iconbundle.impl.PNGIconBundle;

/**
 * Provides static utility methods for determining
 * the name property value and icon bundle for a bean.
 *
 * @author  Michael Heuer
 * @version $Revision$ $Date$
 */
public final class IdentifyUtils
{
    /** Name strategy. */
    private NameStrategy nameStrategy;

    /** Icon bundle strategy. */
    private IconBundleStrategy iconBundleStrategy;

    /** Default icon bundle. */
    private IconBundle defaultIconBundle;

    /** Private static instance of IdentifyUtils. */
    private static IdentifyUtils instance;

    /** GTK look and feel class name. */
    private static final String GTK_LOOK_AND_FEEL_CLASS_NAME = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";

    /** JDK &lt;= 1.5 MacOSX look and feel class name. */
    private static final String JDK15_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME = "apple.laf.AquaLookAndFeel";

    /** JDK &gt;= 1.6 MacOSX look and feel class name. */
    private static final String JDK16_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME = "com.apple.laf.AquaLookAndFeel";

    /** Windows look and feel class name. */
    private static final String WINDOWS_LOOK_AND_FEEL_CLASS_NAME = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";


    /**
     * Private no-arg constructor.
     */
    private IdentifyUtils()
    {
        nameStrategy = new DefaultNameStrategy();
        iconBundleStrategy = new DefaultIconBundleStrategy();

        URL url = getClass().getResource("default.png");
        defaultIconBundle = new PNGIconBundle(url);
    }


    /**
     * Return the static instance of IdentifyUtils.
     *
     * @return the static instance of IdentifyUtils
     */
    public static IdentifyUtils getInstance()
    {
        if (instance == null)
        {
            instance = new IdentifyUtils();
        }
        return instance;
    }

    /**
     * Return a name for the specified bean using the set name
     * strategy.  Return the String <code>"null"</code> if
     * <code>bean</code> is null.
     *
     * @see #getNameStrategy
     *
     * @param bean bean
     * @return a name for the specified bean
     */
    public static String getNameFor(final Object bean)
    {
        return getInstance().getNameStrategy().getNameFor(bean);
    }

    /**
     * Return an icon bundle for the specified bean using the
     * set icon bundle strategy.  Return <code>null</code> if
     * <code>bean</code> is null, and the default icon bundle
     * if an icon bundle cannot otherwise be found.
     *
     * @see #getIconBundleStrategy
     * @see #getDefaultIconBundle
     *
     * @param bean bean
     * @return an icon bundle for the specified bean
     */
    public static IconBundle getIconBundleFor(final Object bean)
    {
        return getInstance().getIconBundleStrategy().getIconBundleFor(bean);
    }

    /**
     * Return true if the current look and feel is the GTK look and feel.
     *
     * @see #GTK_LOOK_AND_FEEL_CLASS_NAME
     * @return true if the current look and feel is the GTK look and feel
     */
    public static boolean isGTKLookAndFeel()
    {
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        if (lookAndFeel == null)
        {
            return false;
        }
        return GTK_LOOK_AND_FEEL_CLASS_NAME.equals(lookAndFeel.getClass().getName());
    }

    /**
     * Return true if the current look and feel is the MacOSX look and feel.
     *
     * @see #JDK15_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME
     * @see #JDK16_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME
     * @return true if the current look and feel is the MacOSX look and feel
     */
    public static boolean isMacOSXLookAndFeel()
    {
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        if (lookAndFeel == null)
        {
            return false;
        }
        return JDK15_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME.equals(lookAndFeel.getClass().getName())
            || JDK16_MAC_OSX_LOOK_AND_FEEL_CLASS_NAME.equals(lookAndFeel.getClass().getName());
    }

    /**
     * Return true if the current look and feel is the Windows look and feel.
     *
     * @see #WINDOWS_LOOK_AND_FEEL_CLASS_NAME
     * @return true if the current look and feel is the Windows look and feel
     */
    public static boolean isWindowsLookAndFeel()
    {
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        if (lookAndFeel == null)
        {
            return false;
        }
        return WINDOWS_LOOK_AND_FEEL_CLASS_NAME.equals(lookAndFeel.getClass().getName());
    }

    /**
     * Return the strategy used to determine the value for
     * a name property on a bean.
     *
     * @return the name strategy
     */
    public NameStrategy getNameStrategy()
    {
        return nameStrategy;
    }

    /**
     * Set the strategy used to determine the value for
     * a name property on a bean to <code>nameStrategy</code>.
     *
     * @param nameStrategy name strategy, must not be null
     */
    public void setNameStrategy(final NameStrategy nameStrategy)
    {
        if (nameStrategy == null)
        {
            throw new IllegalArgumentException("nameStrategy must not be null");
        }

        this.nameStrategy = nameStrategy;
    }

    /**
     * Return the strategy used to find an icon bundle for
     * a bean.
     *
     * @return the icon bundle strategy
     */
    public IconBundleStrategy getIconBundleStrategy()
    {
        return iconBundleStrategy;
    }

    /**
     * Set the strategy used to find an icon bundle for a bean
     * to <code>iconBundleStrategy</code>.
     *
     * @param iconBundleStrategy icon bundle strategy, must not
     *    be null
     */
    public void setIconBundleStrategy(final IconBundleStrategy iconBundleStrategy)
    {
        if (iconBundleStrategy == null)
        {
            throw new IllegalArgumentException("iconBundleStrategy must not be null");
        }

        this.iconBundleStrategy = iconBundleStrategy;
    }

    /**
     * Return the icon bundle to use as a default icon bundle,
     * in the case the icon bundle strategy is unable to find an
     * icon bundle for a bean.
     *
     * @return the icon bundle to use as a default icon bundle
     */
    public IconBundle getDefaultIconBundle()
    {
        return defaultIconBundle;
    }

    /**
     * Set the default icon bundle to <code>defaultIconBundle</code>.
     *
     * @param defaultIconBundle the default icon bundle, must
     *    not be null
     */
    public void setDefaultIconBundle(final IconBundle defaultIconBundle)
    {
        if (defaultIconBundle == null)
        {
            throw new IllegalArgumentException("defaultIconBundle must not be null");
        }
        this.defaultIconBundle = defaultIconBundle;
    }


    //
    //  strategies

    /**
     * Strategy used to determine the value for a name
     * property on a bean.
     */
    interface NameStrategy
    {

        /**
         * Return a name for the specified bean.  Return
         * the String <code>"null"</code> if
         * <code>bean</code> is null.
         *
         * @param bean bean
         * @return a name for the specified bean
         */
        String getNameFor(Object bean);
    }

    /**
     * Strategy used to find an icon bundle for a bean.
     */
    interface IconBundleStrategy
    {

        /**
         * Return an icon bundle for the specified bean.
         * Return <code>null</code> if <code>bean</code>
         * is null, and the default icon bundle if an icon bundle
         * cannot otherwise be found.
         *
         * @see #getDefaultIconBundle
         *
         * @param bean bean
         * @return an icon bundle for the specified bean
         */
        IconBundle getIconBundleFor(Object bean);
    }

    /**
     * Default name strategy.  Returns the string <code>"null"</code>
     * if the specified bean is null.  Otherwise
     * <ul>
     * <li>checks if the bean is instanceof Identifiable</li>
     * <li>checks if the bean's BeanInfo's BeanDescriptor is instanceof IdentifiableBeanDescriptor</li>
     * <li>finally, returns <code>bean.toString()</code></li>
     *</ul>
     */
    private class DefaultNameStrategy
        implements NameStrategy
    {

        /** {@inheritDoc} */
        public String getNameFor(final Object bean)
        {
            if (bean == null)
            {
                return "null";
            }

            if (bean instanceof Identifiable)
            {
                return ((Identifiable) bean).getName();
            }

            try
            {
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();

                if (beanDescriptor instanceof IdentifiableBeanDescriptor)
                {
                    int namePropertyIndex = ((IdentifiableBeanDescriptor) beanDescriptor).getNamePropertyIndex();
                    if (namePropertyIndex != -1)
                    {
                        PropertyDescriptor namePropertyDescriptor = beanInfo.getPropertyDescriptors()[namePropertyIndex];
                        Method readMethod = namePropertyDescriptor.getReadMethod();
                        String name = (String) readMethod.invoke(bean, new Object[] {});
                        return name;
                    }
                }
            }
            catch (IntrospectionException ie)
            {
                // empty
            }
            catch (IllegalAccessException iae)
            {
                // empty
            }
            catch (InvocationTargetException ite)
            {
                // empty
            }

            return bean.toString();
        }
    }

    /**
     * Default icon bundle strategy.  Returns <code>null</code> if
     * the specified bean is null.  Otherwise
     * <ul>
     * <li>checks if the bean is instanceof Identifiable</li>
     * <li>checks if the bean's BeanInfo's BeanDescriptor is instanceof IdentifiableBeanDescriptor</li>
     * <li>finally, returns the default icon bundle</li>
     * </ul>
     *
     * @see #getDefaultBeanInfo
     */
    private class DefaultIconBundleStrategy
        implements IconBundleStrategy
    {

        /** {@inheritDoc} */
        public IconBundle getIconBundleFor(final Object bean)
        {
            if (bean == null)
            {
                return null;
            }

            if (bean instanceof Identifiable)
            {
                return ((Identifiable) bean).getIconBundle();
            }

            try
            {
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();

                if (beanDescriptor instanceof IdentifiableBeanDescriptor)
                {
                    return ((IdentifiableBeanDescriptor) beanDescriptor).getIconBundle();
                }
            }
            catch (IntrospectionException ie)
            {
                // empty
            }

            return defaultIconBundle;
        }
    }
}
