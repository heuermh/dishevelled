/*

    dsh-variation-cytoscape3-app  Variation Cytoscape3 app.
    Copyright (c) 2013 held jointly by the individual authors.

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

import static org.dishevelled.variation.so.SequenceOntology.countAssignments;
import static org.dishevelled.variation.so.SequenceOntology.indexByName;
import static org.dishevelled.variation.so.SequenceOntology.sequenceVariants;

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

import com.google.common.collect.ImmutableSet;

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
 */
final class VariationUtils
{

    static String ensemblGeneId(final CyNode node, final CyNetwork network, final String ensemblGeneIdColumn)
    {
        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        return row.get(ensemblGeneIdColumn, String.class);
    }

    static void addCount(final CyNode node, final CyNetwork network, final String columnName, final int count)
    {
        CyTable table = network.getDefaultNodeTable();
        CyRow row = table.getRow(node.getSUID());
        if (table.getColumn(columnName) == null)
        {
            table.createColumn(columnName, Integer.class, false);
        }
        row.set(columnName, count);
    }

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

    private static final class AssignableNode extends AbstractAssignable
    {
        private final CyNode node;

        AssignableNode(final CyNode node)
        {
            super();
            this.node = node;
        }
    }
}