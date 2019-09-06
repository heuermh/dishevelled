/*
 * VennEuler -- A Venn and Euler Diagram program.
 *
 * Copyright 2009 by Leland Wilkinson.
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1 (the "License")
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the License.
 */

package edu.uic.ncdm.venn;

import java.util.List;
import java.util.Set;
import java.util.Map;

public class VennDiagram {
    public double[][] centers;
    public double[] diameters;
    public double[] areas;
    public double[] residuals;
    public double[] colors;
    public double[] counts;
    public String[] circleLabels;
    public String[] residualLabels;
    public boolean[] warnings;
    public double[][] luneCenters;
    public List<Set<String>> luneIds;
    public double stress;
    public double stress01;
    public double stress05;

    public VennDiagram(double[][] centers, double[] diameters, double[] areas, double[] residuals,
                       String[] circleLabels, String[] residualLabels, double[] colors, 
					   double[] counts, boolean[] warnings, double[][] luneCenters, 
					   List<Set<String>> luneIds,
					   double stress, double stress01, double stress05) {
        this.centers = centers;
        this.diameters = diameters;
        this.areas = areas;
        this.residuals = residuals;
        this.circleLabels = circleLabels;
        this.residualLabels = residualLabels;
        this.colors = colors;
        this.counts = counts;
        this.warnings = warnings;
        this.luneCenters = luneCenters;
        this.luneIds = luneIds;
        this.stress = stress;
        this.stress01 = stress01;
        this.stress05 = stress05;
    }
}
