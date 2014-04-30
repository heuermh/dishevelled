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

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import com.github.heuermh.ensemblrestclient.EnsemblRestClientFactory;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.variation.ensembl.EnsemblRestClientFeatureService;
import org.dishevelled.variation.ensembl.EnsemblRestClientVariationService;
import org.dishevelled.variation.ensembl.EnsemblRestClientVariationConsequenceService;
import org.dishevelled.variation.ensembl.EnsemblRestClientVariationConsequencePredictionService;

import org.dishevelled.variation.gemini.GeminiVariationService;
import org.dishevelled.variation.gemini.GeminiVariationConsequenceService;

import org.dishevelled.variation.synthetic.SyntheticFeatureService;
import org.dishevelled.variation.synthetic.SyntheticGenome;
import org.dishevelled.variation.synthetic.SyntheticVariationService;
import org.dishevelled.variation.synthetic.SyntheticVariationConsequenceService;
import org.dishevelled.variation.synthetic.SyntheticVariationConsequencePredictionService;

import org.dishevelled.variation.vcf.SnpEffVcfVariationConsequenceService;
import org.dishevelled.variation.vcf.VcfVariationService;
import org.dishevelled.variation.vcf.VepVcfVariationConsequenceService;

/**
 * Config view.
 *
 * @author  Michael Heuer
 */
final class ConfigView
    extends JPanel
{
    /** Variation model. */
    private final VariationModel model;

    /** Species. */
    private final JTextField species;

    /** Reference. */
    private final JTextField reference;

    /** Ensembl gene id column. */
    // todo: should be a combo box containing all the column names from the current node table
    private final JTextField ensemblGeneIdColumn;

    /** Ensembl REST server URL. */
    private final JTextField ensemblRestServerUrl;

    /** GEMINI database name. */
    private final JTextField geminiDatabaseName;

    /** VCF file name. */
    private final JTextField vcfFileName;

    /** Feature service name. */
    private final JComboBox featureServiceName;

    /** Variation service name. */
    private final JComboBox variationServiceName;

    /** Variation consequence service name. */
    private final JComboBox variationConsequenceServiceName;

    /** Variation consequence prediction service name. */
    private final JComboBox variationConsequencePredictionServiceName;

    /** Use canonical transcripts only. */
    private final JCheckBox canonical;

    /** Include somatic variations. */
    private final JCheckBox somatic;

    /** Apply action. */
    private final AbstractAction apply = new AbstractAction("Apply")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                apply();
            }
        };

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

    /** Feature service name changed. */
    private final ActionListener featureServiceNameChanged = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                apply.setEnabled(true);
            }
        };

    /** Variation service name changed. */
    private final ActionListener variationServiceNameChanged = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                apply.setEnabled(true);
            }
        };

    /** Variation consequence service name changed. */
    private final ActionListener variationConsequenceServiceNameChanged = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                apply.setEnabled(true);
            }
        };

    /** Variation consequence prediction service name changed. */
    private final ActionListener variationConsequencePredictionServiceNameChanged = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                apply.setEnabled(true);
            }
        };

    /** Feature service names. */
    private static final String[] FEATURE_SERVICE_NAMES = new String[] { "Ensembl REST client", "Synthetic genome" };

    /** Variation service names. */
    private static final String[] VARIATION_SERVICE_NAMES = new String[] { "Ensembl REST client", "GEMINI command line", "VCF file", "Synthetic genome" };

    /** Variation consequence service names. */
    private static final String[] VARIATION_CONSEQUENCE_SERVICE_NAMES = new String[] { "Ensembl REST client", "GEMINI command line", "SnpEff-annotated VCF file", "VEP-annotated VCF file", "Synthetic genome" };

    /** Variation consequence prediction service names. */
    private static final String[] VARIATION_CONSEQUENCE_PREDICTION_SERVICE_NAMES = new String[] { "Ensembl REST client", "Synthetic genome" };


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

        ensemblRestServerUrl = new JTextField(32);
        ensemblRestServerUrl.setText("http://beta.rest.ensembl.org/");

        geminiDatabaseName = new JTextField(48);
        geminiDatabaseName.setText("example.db");

        vcfFileName = new JTextField(48);
        vcfFileName.setText("example.vcf");

        featureServiceName = new JComboBox(FEATURE_SERVICE_NAMES);
        featureServiceName.addActionListener(featureServiceNameChanged);

        variationServiceName = new JComboBox(VARIATION_SERVICE_NAMES);
        variationServiceName.addActionListener(variationServiceNameChanged);

        variationConsequenceServiceName = new JComboBox(VARIATION_CONSEQUENCE_SERVICE_NAMES);
        variationConsequenceServiceName.addActionListener(variationConsequenceServiceNameChanged);

        variationConsequencePredictionServiceName = new JComboBox(VARIATION_CONSEQUENCE_PREDICTION_SERVICE_NAMES);
        variationConsequencePredictionServiceName.addActionListener(variationConsequencePredictionServiceNameChanged);

        canonical = new JCheckBox("Use canonical transcripts only", model.isCanonical());
        canonical.addActionListener(canonicalActionListener);

        somatic = new JCheckBox("Include somatic variations", model.isSomatic());
        somatic.addActionListener(somaticActionListener);

        canonical.setEnabled(false);
        somatic.setEnabled(false);

        apply();
        layoutComponents();
    }


    /**
     * Layout components.
     */
    private void layoutComponents()
    {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 12, 12, 48));
        add("Center", createMainPanel());
        add("South", createButtonPanel());
    }

    private JPanel createMainPanel()
    {
        LabelFieldPanel panel = new LabelFieldPanel();
        panel.setOpaque(false);
        panel.addField("Species:", wrap(species));
        panel.addField("Reference:", wrap(reference));
        panel.addField("Ensembl gene id column:", wrap(ensemblGeneIdColumn));
        panel.addSpacing(12);
        panel.addField("Ensembl REST service URL:", wrap(ensemblRestServerUrl));
        panel.addField("GEMINI database name:", createGeminiDatabaseNamePanel());
        panel.addField("VCF file name:", createVcfFileNamePanel());
        panel.addSpacing(12);

        /*

          ideas . . .

          default to no service ?
          map of service implementation name to factory
          factory provides a labelfieldpanel with configuration fields
          when service combo box changes swap out the configuration panel
          configuration panel should reuse configuration for other services if possible
          checkbox to share configuration?
          any changes enable Apply button which instantiates the services and sets them in the model
          the Apply button is then disabled

          or include all the configuration parameters and enable/disable as required
            Ensembl REST server URL, string, default to http://beta.rest.ensembl.org/
            GEMINI database name, file, default to example.db, include [...] button to open file browser
            VCF file, file, default to example.vcf, include [...] button to open file browser
            Synthetic genome chromosomes, integer, default to 22
            Synthetic genome bp, long, default to 3000000000L

          or remove these from the config view and try to do this configuration when the task starts?
             might be necessary to have this for say the add button in variation and consequence views

         */

        panel.addField("Feature service:", wrap(featureServiceName));
        panel.addField("Variation service:", wrap(variationServiceName));
        panel.addField("Consequence service:", wrap(variationConsequenceServiceName));
        panel.addField("Consequence prediction service:", wrap(variationConsequencePredictionServiceName));
        panel.addSpacing(12);
        panel.addField(" ", canonical);
        panel.addField(" ", somatic);
        panel.addFinalSpacing();
        return panel;
    }

    /**
     * Create and return a new GEMINI database name panel.
     *
     * @return a new GEMINI database name panel
     */
    private JPanel createGeminiDatabaseNamePanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(geminiDatabaseName);
        panel.add(Box.createHorizontalStrut(4));
        panel.add(new JButton(new AbstractAction("...")
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    JFileChooser fileChooser = new JFileChooser();
                    if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(ConfigView.this))
                    {
                        geminiDatabaseName.setText(fileChooser.getSelectedFile().getName());
                    }
                }
            }));
        return panel;
    }

    /**
     * Create and return a new VCF file name panel.
     *
     * @return a new VCF file name panel
     */
    private JPanel createVcfFileNamePanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(vcfFileName);
        panel.add(Box.createHorizontalStrut(4));
        panel.add(new JButton(new AbstractAction("...")
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    JFileChooser fileChooser = new JFileChooser();
                    if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(ConfigView.this))
                    {
                        vcfFileName.setText(fileChooser.getSelectedFile().getName());
                    }
                }
            }));
        return panel;
    }

    /**
     * Create and return a new button panel.
     *
     * @return a new button panel
     */
    private JPanel createButtonPanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(Box.createHorizontalGlue());
        panel.add(Box.createHorizontalGlue());
        panel.add(new JButton(apply));
        return panel;
    }

    /**
     * Wrap the specified component.
     *
     * @param component component to wrap
     * @return the specified component wrapped in a panel with additional spacing right
     */
    private static JPanel wrap(final JComponent component)
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

    /**
     * Apply.
     */
    private void apply()
    {
        // oh for some injection
        String serverUrl = ensemblRestServerUrl.getText();
        String databaseName = geminiDatabaseName.getText();
        File vcfFile = new File(vcfFileName.getText());
        EnsemblRestClientFactory ensemblRestClientFactory = new EnsemblRestClientFactory();
        SyntheticGenome syntheticGenome = new SyntheticGenome(model.getSpecies(), model.getReference(), 22, 3000000000L);

        if ("Ensembl REST client".equals(featureServiceName.getSelectedItem()))
        {
            model.setFeatureService(new EnsemblRestClientFeatureService(model.getSpecies(), model.getReference(), ensemblRestClientFactory.createLookupService(serverUrl)));
        }
        else if ("Synthetic genome".equals(featureServiceName.getSelectedItem()))
        {
            model.setFeatureService(new SyntheticFeatureService(syntheticGenome));
        }

        if ("Ensembl REST client".equals(variationServiceName.getSelectedItem()))
        {
            model.setVariationService(new EnsemblRestClientVariationService(model.getSpecies(), model.getReference(), ensemblRestClientFactory.createFeatureService(serverUrl)));
        }
        else if ("GEMINI command line".equals(variationServiceName.getSelectedItem()))
        {
            model.setVariationService(new GeminiVariationService(model.getSpecies(), model.getReference(), databaseName));
        }
        else if ("VCF file".equals(variationServiceName.getSelectedItem()))
        {
            model.setVariationService(new VcfVariationService(model.getSpecies(), model.getReference(), vcfFile));
        }
        else if ("Synthetic genome".equals(variationServiceName.getSelectedItem()))
        {
            model.setVariationService(new SyntheticVariationService(syntheticGenome));
        }

        if ("Ensembl REST client".equals(variationConsequenceServiceName.getSelectedItem()))
        {
            model.setVariationConsequenceService(new EnsemblRestClientVariationConsequenceService(model.getSpecies(), model.getReference(), ensemblRestClientFactory.createVariationService(serverUrl)));
        }
        else if ("GEMINI command line".equals(variationConsequenceServiceName.getSelectedItem()))
        {
            model.setVariationConsequenceService(new GeminiVariationConsequenceService(model.getSpecies(), model.getReference(), databaseName));
        }
        else if ("SnpEff-annotated VCF file".equals(variationConsequenceServiceName.getSelectedItem()))
        {
            model.setVariationConsequenceService(new SnpEffVcfVariationConsequenceService(model.getSpecies(), model.getReference(), vcfFile));
        }
        else if ("VEP-annotated VCF file".equals(variationConsequenceServiceName.getSelectedItem()))
        {
            model.setVariationConsequenceService(new VepVcfVariationConsequenceService(model.getSpecies(), model.getReference(), vcfFile));
        }
        else if ("Synthetic genome".equals(variationConsequenceServiceName.getSelectedItem()))
        {
            model.setVariationConsequenceService(new SyntheticVariationConsequenceService(syntheticGenome));
        }

        if ("Ensembl REST client".equals(variationConsequencePredictionServiceName.getSelectedItem()))
        {
            model.setVariationConsequencePredictionService(new EnsemblRestClientVariationConsequencePredictionService(model.getSpecies(), model.getReference(), ensemblRestClientFactory.createVariationService(serverUrl)));
        }
        else if ("Synthetic genome".equals(variationConsequencePredictionServiceName.getSelectedItem()))
        {
            model.setVariationConsequencePredictionService(new SyntheticVariationConsequencePredictionService(syntheticGenome));
        }

        apply.setEnabled(false);
    }
}