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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JPanel;

import org.dishevelled.layout.LabelFieldPanel;

/**
 * Config view.
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


    ConfigView(final VariationModel model)
    {
        super();
        setOpaque(false);

        this.model = model;

        // todo:  bind these
        //    and text field width is not working
        species = new JTextField(20);
        species.setText(model.getSpecies());
        reference = new JTextField(20);
        reference.setText(model.getReference());
        ensemblGeneIdColumn = new JTextField(32);
        ensemblGeneIdColumn.setText(model.getEnsemblGeneIdColumn());
        canonical = new JCheckBox("Use canonical transcripts only", model.isCanonical());
        somatic = new JCheckBox("Include somatic variations", model.isSomatic());

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
        addField("Feature service:", model.getFeatureService().toString());
        addField("Variation service:", model.getVariationService().toString());
        addField("Consequence service:", model.getVariationConsequenceService().toString());
        addField("Consequence prediction service:", model.getVariationConsequencePredictionService().toString());
        addSpacing(12);
        addField(" ", canonical);
        addField(" ", somatic);
        addFinalSpacing();
    }

    private JPanel wrap(final JTextField textField)
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(textField);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(Box.createGlue());
        panel.add(Box.createGlue());
        return panel;
    }
}