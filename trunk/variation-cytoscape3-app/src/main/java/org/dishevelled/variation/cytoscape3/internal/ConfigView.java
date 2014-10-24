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

import static org.dishevelled.variation.cytoscape3.internal.VariationUtils.chooseFile;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Desktop.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import ca.odell.glazedlists.swing.DefaultEventComboBoxModel;

import com.github.heuermh.ensemblrestclient.EnsemblRestClientFactory;

import org.dishevelled.layout.LabelFieldPanel;

import org.dishevelled.variation.adam.AdamVariationService;

import org.dishevelled.variation.ensembl.EnsemblRestClientFeatureService;
import org.dishevelled.variation.ensembl.EnsemblRestClientVariationService;
import org.dishevelled.variation.ensembl.EnsemblRestClientVariationConsequenceService;
import org.dishevelled.variation.ensembl.EnsemblRestClientVariationConsequencePredictionService;

import org.dishevelled.variation.gemini.GeminiVariationService;
import org.dishevelled.variation.gemini.GeminiVariationConsequenceService;

// todo:  try to remove these dependencies if possible
import com.google.api.services.genomics.Genomics;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import org.dishevelled.variation.googlegenomics.GoogleGenomicsFactory;
import org.dishevelled.variation.googlegenomics.GoogleGenomicsVariationService;

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

    /** Column names. */
    private final JComboBox columnNames;

    /** Ensembl REST server URL. */
    private final JTextField ensemblRestServerUrl;

    /** Google Genomics API server URL. */
    private final JTextField googleGenomicsServerUrl;

    /** Google Genomics API datasetId. */
    private final JTextField googleGenomicsDatasetId;

    /** Google Genomics API authorization code. */
    private final JTextField googleGenomicsAuthorizationCode;

    /** Google Genomics API factory. */
    private final GoogleGenomicsFactory googleGenomicsFactory;

    /** Google Genomics API flow. */
    private GoogleAuthorizationCodeFlow googleGenomicsFlow;

    /** ADAM file name. */
    private final JTextField adamFileName;

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

    /** Refresh columns action. */
    private final AbstractAction refreshColumns = new AbstractAction("Refresh columns")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                refreshColumns();
            }
        };

    /** Google Genomics authorize action. */
    private final AbstractAction authorize = new AbstractAction("Authorize...")
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                authorize();
            }
        };

    /** Property change listener. */
    private final PropertyChangeListener propertyChangeListener = new PropertyChangeListener()
        {
            @Override
            public void propertyChange(final PropertyChangeEvent event)
            {
                if ("network".equals(event.getPropertyName()))
                {
                    refreshColumns();
                }
                else if ("species".equals(event.getPropertyName()))
                {
                    species.setText((String) event.getNewValue());
                }
                else if ("reference".equals(event.getPropertyName()))
                {
                    reference.setText((String) event.getNewValue());
                }
                else if ("ensemblGeneIdColumn".equals(event.getPropertyName()))
                {
                    columnNames.setSelectedItem(event.getNewValue());
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

    /** Column names action listener. */
    private final ActionListener columnNamesActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent event)
            {
                if (columnNames.getSelectedItem() != null)
                {
                    model.setEnsemblGeneIdColumn((String) columnNames.getSelectedItem());
                }
                apply.setEnabled(true);
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
                if ("Google Genomics API".equals(variationServiceName.getSelectedItem()))
                {
                    googleGenomicsServerUrl.setEnabled(true);
                    googleGenomicsDatasetId.setEnabled(true);
                    googleGenomicsAuthorizationCode.setEnabled(true);
                    authorize.setEnabled(true);
                }
                else
                {
                    googleGenomicsServerUrl.setEnabled(false);
                    googleGenomicsDatasetId.setEnabled(false);
                    googleGenomicsAuthorizationCode.setEnabled(false);
                    authorize.setEnabled(false);
                }
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
    private static final String[] VARIATION_SERVICE_NAMES = new String[] { "Ensembl REST client", "ADAM file", "GEMINI command line", "Google Genomics API", "VCF file", "Synthetic genome" };

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

        columnNames = new JComboBox(new DefaultEventComboBoxModel<String>(model.columnNames()));
        columnNames.addActionListener(columnNamesActionListener);

        ensemblRestServerUrl = new JTextField(32);
        ensemblRestServerUrl.setText("http://rest.ensembl.org");

        googleGenomicsServerUrl = new JTextField(32);
        googleGenomicsServerUrl.setText("https://www.googleapis.com/genomics/v1beta");
        googleGenomicsServerUrl.setEnabled(false);

        googleGenomicsDatasetId = new JTextField(20);
        googleGenomicsDatasetId.setText("example");
        googleGenomicsDatasetId.setEnabled(false);

        googleGenomicsAuthorizationCode = new JTextField(32);
        googleGenomicsAuthorizationCode.setText("");
        googleGenomicsAuthorizationCode.setEnabled(false);

        authorize.setEnabled(false);
        googleGenomicsFactory = new GoogleGenomicsFactory();

        adamFileName = new JTextField(48);
        adamFileName.setText("example.adam");

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
        panel.addField("Ensembl gene id column:", createEnsemblGeneIdColumnPanel());
        panel.addSpacing(12);
        panel.addField("Feature service:", wrap(featureServiceName));
        panel.addField("Variation service:", wrap(variationServiceName));
        panel.addField("Consequence service:", wrap(variationConsequenceServiceName));
        panel.addField("Consequence prediction service:", wrap(variationConsequencePredictionServiceName));
        panel.addSpacing(12);
        panel.addField("Ensembl REST service URL:", wrap(ensemblRestServerUrl));
        panel.addField("VCF file name:", createVcfFileNamePanel());
        panel.addField("ADAM file name:", createAdamFileNamePanel());
        panel.addField("GEMINI database name:", createGeminiDatabaseNamePanel());
        panel.addField("Google Genomics API service URL:", wrap(googleGenomicsServerUrl));
        panel.addField("Google Genomics API datasetId:", wrap(googleGenomicsDatasetId));
        panel.addField("Google Genomics API authorization code:", createGoogleGenomicsAuthorizationCodePanel());
        panel.addSpacing(12);
        panel.addField(" ", canonical);
        panel.addField(" ", somatic);
        panel.addFinalSpacing();
        return panel;
    }

    /**
     * Create and return a new Ensembl gene id panel.
     *
     * @return a new Ensembl gene id panel
     */
    private JPanel createEnsemblGeneIdColumnPanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(columnNames);
        panel.add(Box.createHorizontalStrut(12));
        panel.add(new JButton(refreshColumns));
        panel.add(Box.createGlue());
        panel.add(Box.createGlue());
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
                    File geminiDatabaseFile = chooseFile((JDialog) getTopLevelAncestor(), "Select a GEMINI database file");
                    if (geminiDatabaseFile != null)
                    {
                        geminiDatabaseName.setText(geminiDatabaseFile.getAbsolutePath());
                    }
                }
            }));
        return panel;
    }

    /**
     * Create and return a new ADAM file name panel.
     *
     * @return a new ADAM file name panel
     */
    private JPanel createAdamFileNamePanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(adamFileName);
        panel.add(Box.createHorizontalStrut(4));
        panel.add(new JButton(new AbstractAction("...")
            {
                @Override
                public void actionPerformed(final ActionEvent event)
                {
                    File adamFile = chooseFile((JDialog) getTopLevelAncestor(), "Select a ADAM file", "^.*[adam,parquet]+$");
                    if (adamFile != null)
                    {
                        adamFileName.setText(adamFile.getAbsolutePath());
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
                    File vcfFile = chooseFile((JDialog) getTopLevelAncestor(), "Select a VCF file", "^.*[vcf,gz,gzip,bz,bzip2]+$");
                    if (vcfFile != null)
                    {
                        vcfFileName.setText(vcfFile.getAbsolutePath());
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
     * Create and return a new Google Genomics API authorization code panel.
     *
     * @return a new Google Genomics API authorization code panel
     */
    private JPanel createGoogleGenomicsAuthorizationCodePanel()
    {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(googleGenomicsAuthorizationCode);
        panel.add(Box.createHorizontalStrut(4));
        panel.add(new JButton(authorize));
        panel.add(Box.createGlue());
        panel.add(Box.createGlue());
        return panel;
    }

    /**
     * Authorize using the Google Genomics API.
     */
    private void authorize()
    {
        try
        {
            googleGenomicsFlow = googleGenomicsFactory.startFlow();
            String authorizationUrl = googleGenomicsFactory.authorizationUrl(googleGenomicsFlow);

            if (Desktop.isDesktopSupported())
            {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Action.BROWSE))
                {
                    desktop.browse(URI.create(authorizationUrl));
                }
            }
            // Finally just ask user to open in their browser using copy-paste
            //System.out.println("Please open the following URL in your browser:");
            //System.out.println("  " + url);
        }
        catch (IOException e)
        {
            throw new RuntimeException("could not authorize Google Genomics API", e);
        }
    }

    /**
     * Create and return the Google Genomics API.
     *
     * @return the Google Genomics API
     */
    private Genomics createGoogleGenomics()
    {
        try
        {
            return googleGenomicsFactory.genomics(googleGenomicsServerUrl.getText(), googleGenomicsAuthorizationCode.getText(), googleGenomicsFlow);
        }
        catch (IOException e)
        {
            throw new RuntimeException("could not create Google Genomics API", e);
        }
    }

    /**
     * Refresh columns.
     */
    private void refreshColumns()
    {
        model.refreshColumns();

        if (model.columnNames().contains(model.getEnsemblGeneIdColumn()))
        {
            columnNames.setSelectedItem(model.getEnsemblGeneIdColumn());
        }
        else
        {
            columnNames.setSelectedIndex(0);
        }
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
            model.setVariationService(new EnsemblRestClientVariationService(model.getSpecies(), model.getReference(), ensemblRestClientFactory.createOverlapService(serverUrl)));
        }
        else if ("ADAM file".equals(variationServiceName.getSelectedItem()))
        {
            model.setVariationService(new AdamVariationService(model.getSpecies(), model.getReference(), adamFileName.getText()));
        }
        else if ("GEMINI command line".equals(variationServiceName.getSelectedItem()))
        {
            model.setVariationService(new GeminiVariationService(model.getSpecies(), model.getReference(), databaseName));
        }
        else if ("Google Genomics API".equals(variationServiceName.getSelectedItem()))
        {
            /*

              when creating an instance of the service a second time with the same authorization token:

              Caused by: com.google.api.client.auth.oauth2.TokenResponseException: 400 Bad Request
              {
                "error" : "invalid_grant",
                "error_description" : "Invalid code."
              }
              at com.google.api.client.auth.oauth2.TokenResponseException.from(TokenResponseException.java:105)
              at com.google.api.client.auth.oauth2.TokenRequest.executeUnparsed(TokenRequest.java:287)
              at com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest.execute(GoogleAuthorizationCodeTokenRequest.java:158)
              at org.dishevelled.variation.googlegenomics.GoogleGenomicsFactory.genomics(GoogleGenomicsFactory.java:143)
              at org.dishevelled.variation.cytoscape3.internal.ConfigView.createGoogleGenomics(ConfigView.java:624)

              as a temporary workaround, use a cache with expiry same as auth token

             */
            model.setVariationService(new GoogleGenomicsVariationService(model.getSpecies(), model.getReference(), googleGenomicsDatasetId.getText(), createGoogleGenomics()));
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
