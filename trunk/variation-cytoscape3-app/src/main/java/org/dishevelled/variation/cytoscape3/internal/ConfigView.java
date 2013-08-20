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

import static com.google.common.base.Preconditions.checkNotNull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JPanel;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Config view.
 *
 * @author  Michael Heuer
 */
final class ConfigView
    extends LabelFieldPanel
{
    /** Variation model. */
    private final VariationModel model;

    /** Species. */
    private final JTextField species;

    /** Reference. */
    private final JTextField reference;

    /** Ensembl gene id column. */
    private final JTextField ensemblGeneIdColumn;

    /** Use canonical transcripts only. */
    private final JCheckBox canonical;

    /** Include somatic variations. */
    private final JCheckBox somatic;

    /** Property change listener. */
    private final PropertyChangeListener propertyChangeListener = new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent event)
            {
                if ("species".equals(event.getPropertyName()))
                {
                    species.setText((String) event.getNewValue());
                }
                else if ("reference".equals(event.getPropertyName()))
                {
                    reference.setText((String) event.getNewValue());
                }
                else if ("ensemblGeneIdColumn".equals(event.getPropertyName()))
                {
                    ensemblGeneIdColumn.setText((String) event.getNewValue());
                }
                else if ("canonical".equals(event.getPropertyName()))
                {
                    canonical.setSelected((Boolean) event.getNewValue());
                }
                else if ("somatic".equals(event.getPropertyName()))
                {
                    somatic.setSelected((Boolean) event.getNewValue());
                }
            }
        };

    /** Species action listener. */
    private final ActionListener speciesActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                model.setSpecies(species.getText());
            }
        };

    /** Species focus listener. */
    private final FocusListener speciesFocusListener = new FocusAdapter()
        {
            @Override
            public void focusLost(final FocusEvent event)
            {
                model.setSpecies(species.getText());
            }
        };

    /** Reference action listener */
    private final ActionListener referenceActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                model.setReference(reference.getText());
            }
        };

    /** Reference focus listener. */
    private final FocusListener referenceFocusListener = new FocusAdapter()
        {
            @Override
            public void focusLost(final FocusEvent event)
            {
                model.setReference(reference.getText());
            }
        };

    /** Ensembl gene id column action listener. */
    private final ActionListener ensemblGeneIdColumnActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                model.setEnsemblGeneIdColumn(ensemblGeneIdColumn.getText());
            }
        };

    /** Ensembl gene id column focus listener. */
    private final FocusListener ensemblGeneIdColumnFocusListener = new FocusAdapter()
        {
            @Override
            public void focusLost(final FocusEvent event)
            {
                model.setEnsemblGeneIdColumn(ensemblGeneIdColumn.getText());
            }
        };

    /** Canonical action listener. */
    private final ActionListener canonicalActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                model.setCanonical(canonical.isSelected());
            }
        };

    /** Somatic action listener. */
    private final ActionListener somaticActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                model.setSomatic(somatic.isSelected());
            }
        };


    /**
     * Create a new config view with the specified variation model.
     *
     * @param model variation model, must not be null
     */
    ConfigView(final VariationModel model)
    {
        super();
        setOpaque(false);

        checkNotNull(model);
        this.model = model;
        model.addPropertyChangeListener(propertyChangeListener);

        // todo:  text field width is not working
        species = new JTextField(20);
        species.setText(model.getSpecies());
        species.addActionListener(speciesActionListener);
        species.addFocusListener(speciesFocusListener);

        reference = new JTextField(20);
        reference.setText(model.getReference());
        reference.addActionListener(referenceActionListener);
        reference.addFocusListener(referenceFocusListener);

        ensemblGeneIdColumn = new JTextField(32);
        ensemblGeneIdColumn.setText(model.getEnsemblGeneIdColumn());
        ensemblGeneIdColumn.addActionListener(ensemblGeneIdColumnActionListener);
        ensemblGeneIdColumn.addFocusListener(ensemblGeneIdColumnFocusListener);

        canonical = new JCheckBox("Use canonical transcripts only", model.isCanonical());
        canonical.addActionListener(canonicalActionListener);

        somatic = new JCheckBox("Include somatic variations", model.isSomatic());
        somatic.addActionListener(somaticActionListener);

        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        addSpacing(12);
        addField("Species:", wrap(species));
        addField("Reference:", wrap(reference));
        addField("Ensembl gene id column:", wrap(ensemblGeneIdColumn));
        addSpacing(12);
        addField("Feature service:", wrap(new JComboBox(new String[] { model.getFeatureService().toString() })));
        addField("Variation service:", wrap(new JComboBox(new String[] { model.getVariationService().toString() })));
        addField("Consequence service:", wrap(new JComboBox(new String[] { model.getVariationConsequenceService().toString() })));
        addField("Consequence prediction service:", wrap(new JComboBox(new String[] { model.getVariationConsequencePredictionService().toString() })));
        addSpacing(12);
        addField(" ", canonical);
        addField(" ", somatic);
        addFinalSpacing();
    }

    /**
     * Wrap the specified component.
     *
     * @param component component to wrap
     * @return the specified component wrapped in a panel with additional spacing right
     */
    private JPanel wrap(final JComponent component)
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(component);
        panel.add(Box.createHorizontalStrut(48));
        panel.add(Box.createGlue());
        panel.add(Box.createGlue());
        return panel;
    }
}