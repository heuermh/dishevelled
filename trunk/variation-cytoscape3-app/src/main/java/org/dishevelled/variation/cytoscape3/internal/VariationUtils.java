/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013-2014 held jointly by the individual authors.

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
package org.dishevelled.variation.cytoscape3.internal;

import static com.google.common.base.Preconditions.checkNotNull;

import static org.dishevelled.variation.so.SequenceOntology.countAssignments;
import static org.dishevelled.variation.so.SequenceOntology.indexByName;
import static org.dishevelled.variation.so.SequenceOntology.sequenceVariants;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;

import org.dishevelled.variation.VariationConsequence;

import org.dishevelled.vocabulary.AbstractAssignable;
import org.dishevelled.vocabulary.Assignable;
import org.dishevelled.vocabulary.Authority;
import org.dishevelled.vocabulary.Concept;
import org.dishevelled.vocabulary.Domain;
import org.dishevelled.vocabulary.Evidence;

/**
 * Variation utils.
 *
 * @author  Michael Heuer
 */
final class VariationUtils
{

    /**
     * Private no-arg constructor.
     */
    private VariationUtils()
    {
        // empty
    }


    /**
     * Return a result status message.
     *
     * @param count count
     * @param childType child type
     * @param parentType parent type
     * @param parent parent
     * @return a result status message
     */
    static String resultStatusMessage(final int count, final String childType, final String parentType, final Object parent)
    {
        return resultStatusMessage("Found", count, childType, parentType, parent);
    }

    /**
     * Return a result status message.
     *
     * @param verb verb
     * @param count count
     * @param childType child type
     * @param parentType parent type
     * @param parent parent
     * @return a result status message
     */
    static String resultStatusMessage(final String verb, final int count, final String childType, final String parentType, final Object parent)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(verb);
        sb.append(" ");
        if (count == 0)
        {
            sb.append("no ");
            sb.append(childType);
            sb.append("s");
        }
        else if (count == 1)
        {
            sb.append(count);
            sb.append(" ");
            sb.append(childType);
        }
        else
        {
            sb.append(count);
            sb.append(" ");
            sb.append(childType);
            sb.append("s");
        }
        sb.append(" for ");
        sb.append(parentType);
        sb.append(" ");
        sb.append(parent.toString());
        return sb.toString();
    }

    /**
     * Return zero or more Ensembl gene ids from the specified Ensembl gene id column for the specified node.
     *
     * @param node node
     * @param network network
     * @param ensemblGeneIdColumn Ensembl gene id column
     * @return zero or more Ensembl gene ids from the specified Ensembl gene id column for the specified node
     */
    static Iterable<String> ensemblGeneIds(final CyNode node, final CyNetwork network, final String ensemblGeneIdColumn)
    {
        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        CyColumn column = table.getColumn(ensemblGeneIdColumn);

        if (column != null)
        {
            Class<?> columnClass = column.getType();
            if (String.class.equals(columnClass))
            {
                String ensemblGeneId = row.get(ensemblGeneIdColumn, String.class);
                return ImmutableList.of(ensemblGeneId);
            }
            else if (columnClass.equals(List.class))
            {
                Class<?> listClass = column.getListElementType();
                if (String.class.equals(listClass))
                {
                    return row.getList(ensemblGeneIdColumn, String.class);
                }
            }
        }
        return Collections.<String>emptyList();
    }

    /**
     * Add the specified count in the specified column name to the specified node.
     *
     * @param node node
     * @param network network
     * @param columnName column name
     * @param count count
     */
    static void addCount(final CyNode node, final CyNetwork network, final String columnName, final int count)
    {
        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        if (table.getColumn(columnName) == null)
        {
            table.createColumn(columnName, Integer.class, false);
        }
        // todo:  or add to current value
        row.set(columnName, count);
    }

    /**
     * Add counts for all consequence terms for the specified list of variation consequences for the specified node.
     *
     * @param node node
     * @param network network
     * @param variationConsequences list of variation consequences
     */
    static void addConsequenceCounts(final CyNode node, final CyNetwork network, final List<VariationConsequence> variationConsequences)
    {
        Domain sv = sequenceVariants();
        Map<String, Concept> indexByName = indexByName(sv);

        Authority so = sv.getAuthority();
        Assignable assignableNode = new AssignableNode(node);

        // todo: evidence code for variation predictions?
        Set<Evidence> evidence = ImmutableSet.of(new Evidence("IEA", 1.0d, 1.0d));

        for (VariationConsequence variationConsequence : variationConsequences)
        {
            so.createAssignment(indexByName.get(variationConsequence.getSequenceOntologyTerm()), assignableNode, evidence);
        }
        for (Map.Entry<Concept, Integer> entry : countAssignments(sv).entrySet())
        {
            addCount(node, network, entry.getKey().getName(), entry.getValue());
        }
    }

    /**
     * Return the maximum count value in the specified column in the specified network.
     *
     * @param network network
     * @param columnName column name
     * @return the maximum count value in the specified column in the specified network
     */
    static int maxCount(final CyNetwork network, final String columnName)
    {
        CyTable table = network.getDefaultNodeTable();
        CyColumn column = table.getColumn(columnName);
        int max = 0;
        for (Integer value : column.getValues(Integer.class))
        {
            if (value != null && value > max)
            {
                max = value;
            }
        }
        return max;
    }

    /**
     * Install a close action binding to <code>Ctrl-C</code>/<code>Command-C</code> for the specified dialog.
     *
     * @param dialog dialog, must not be null
     */
    static void installCloseKeyBinding(final JDialog dialog)
    {
        Action close = new AbstractAction()
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    dialog.dispatchEvent(new WindowEvent(dialog, WindowEvent.WINDOW_CLOSING));
                }
            };
        int menuKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        KeyStroke closeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, menuKeyMask);
        JRootPane rootPane = dialog.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(closeStroke, "close");
        rootPane.getActionMap().put("close", close);
    }

    /**
     * Assignable node.
     */
    private static final class AssignableNode extends AbstractAssignable
    {
        /** Node. */
        private final CyNode node;

        /**
         * Create a new assignable node for the specified node.
         *
         * @param node node, must not be null
         */
        AssignableNode(final CyNode node)
        {
            super();
            checkNotNull(node);
            this.node = node;
        }


        @Override
        public boolean equals(final Object o)
        {
            if (o == this)
            {
                return true;
            }
            if (!(o instanceof AssignableNode))
            {
                return false;
            }
            AssignableNode assignableNode = (AssignableNode) o;
            return node.equals(assignableNode.node);
        }

        @Override
        public int hashCode()
        {
            return node.hashCode();
        }
    }
}