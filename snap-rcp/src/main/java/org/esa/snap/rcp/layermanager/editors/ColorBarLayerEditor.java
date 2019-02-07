/*
 * Copyright (C) 2010 Brockmann Consult GmbH (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */
package org.esa.snap.rcp.layermanager.editors;

import com.bc.ceres.binding.PropertyDescriptor;
import com.bc.ceres.binding.ValueRange;
import com.bc.ceres.binding.ValueSet;
import com.bc.ceres.swing.binding.BindingContext;
import org.esa.snap.core.layer.ColorBarLayerType;
import org.esa.snap.ui.layer.AbstractLayerConfigurationEditor;

import java.awt.*;

/**
 * Editor for colorbar layer.
 *
 * @author Marco Zuehlke
 * @author Daniel Knowles
 * @version $Revision$ $Date$
 * @since BEAM 4.6
 */
//SEP2018 - Daniel Knowles - adds numerous new properties and related binding contexts.
//JAN2018 - Daniel Knowles - updated with SeaDAS gridline revisions

public class ColorBarLayerEditor extends AbstractLayerConfigurationEditor {


    @Override
    protected void addEditablePropertyDescriptors() {



        // Color Bar Location Section

        addSectionBreak(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_SECTION_NAME,
                ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_SECTION_LABEL,
                ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_SECTION_TOOLTIP);

        PropertyDescriptor locationInsidePD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_INSIDE_NAME, Boolean.class);
        locationInsidePD.setDefaultValue(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_INSIDE_DEFAULT);
        locationInsidePD.setDisplayName(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_INSIDE_LABEL);
        locationInsidePD.setDescription(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_INSIDE_TOOLTIP);
        locationInsidePD.setDefaultConverter();
        addPropertyDescriptor(locationInsidePD);

        PropertyDescriptor locationPlacementPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_PLACEMENT_NAME, String.class);
        locationPlacementPD.setDefaultValue(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_PLACEMENT_DEFAULT);
        locationPlacementPD.setValueSet(new ValueSet(ColorBarLayerType.getColorBarLocationArray()));
        locationPlacementPD.setDisplayName(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_PLACEMENT_LABEL);
        locationPlacementPD.setDescription(ColorBarLayerType.PROPERTY_COLORBAR_LOCATION_PLACEMENT_TOOLTIP);
        locationPlacementPD.setDefaultConverter();
        addPropertyDescriptor(locationPlacementPD);





        // Grid Spacing Section

        addSectionBreak(ColorBarLayerType.PROPERTY_GRID_SPACING_SECTION_NAME,
                ColorBarLayerType.PROPERTY_GRID_SPACING_SECTION_LABEL,
                ColorBarLayerType.PROPERTY_GRID_SPACING_SECTION_TOOLTIP);

        PropertyDescriptor gridSpacingLatPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_GRID_SPACING_LAT_NAME, Double.class);
        gridSpacingLatPD.setDefaultValue(ColorBarLayerType.PROPERTY_GRID_SPACING_LAT_DEFAULT);
        gridSpacingLatPD.setValueRange(new ValueRange(0.0, 90.00));
        gridSpacingLatPD.setDisplayName(ColorBarLayerType.PROPERTY_GRID_SPACING_LAT_LABEL);
        gridSpacingLatPD.setDescription(ColorBarLayerType.PROPERTY_GRID_SPACING_LAT_TOOLTIP);
        gridSpacingLatPD.setDefaultConverter();
        addPropertyDescriptor(gridSpacingLatPD);

        PropertyDescriptor gridSpacingLonPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_GRID_SPACING_LON_NAME, Double.class);
        gridSpacingLonPD.setDefaultValue(ColorBarLayerType.PROPERTY_GRID_SPACING_LON_DEFAULT);
        gridSpacingLonPD.setValueRange(new ValueRange(0.0, 180.00));
        gridSpacingLonPD.setDisplayName(ColorBarLayerType.PROPERTY_GRID_SPACING_LON_LABEL);
        gridSpacingLonPD.setDescription(ColorBarLayerType.PROPERTY_GRID_SPACING_LON_TOOLTIP);
        gridSpacingLonPD.setDefaultConverter();
        addPropertyDescriptor(gridSpacingLonPD);


        // Labels Section

        addSectionBreak(ColorBarLayerType.PROPERTY_LABELS_SECTION_NAME,
                ColorBarLayerType.PROPERTY_LABELS_SECTION_LABEL,
                ColorBarLayerType.PROPERTY_LABELS_SECTION_TOOLTIP);

        PropertyDescriptor labelsNorthPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_NORTH_NAME, Boolean.class);
        labelsNorthPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_NORTH_DEFAULT);
        labelsNorthPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_NORTH_LABEL);
        labelsNorthPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_NORTH_TOOLTIP);
        labelsNorthPD.setDefaultConverter();
        addPropertyDescriptor(labelsNorthPD);

        PropertyDescriptor labelsSouthPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_SOUTH_NAME, Boolean.class);
        labelsSouthPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_SOUTH_DEFAULT);
        labelsSouthPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_SOUTH_LABEL);
        labelsSouthPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_SOUTH_TOOLTIP);
        labelsSouthPD.setDefaultConverter();
        addPropertyDescriptor(labelsSouthPD);

        PropertyDescriptor labelsWestPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_WEST_NAME, Boolean.class);
        labelsWestPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_WEST_DEFAULT);
        labelsWestPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_WEST_LABEL);
        labelsWestPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_WEST_TOOLTIP);
        labelsWestPD.setDefaultConverter();
        addPropertyDescriptor(labelsWestPD);

        PropertyDescriptor labelsEastPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_EAST_NAME, Boolean.class);
        labelsEastPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_EAST_DEFAULT);
        labelsEastPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_EAST_LABEL);
        labelsEastPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_EAST_TOOLTIP);
        labelsEastPD.setDefaultConverter();
        addPropertyDescriptor(labelsEastPD);

        PropertyDescriptor labelsSuffixPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_SUFFIX_NSWE_NAME, Boolean.class);
        labelsSuffixPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_SUFFIX_NSWE_DEFAULT);
        labelsSuffixPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_SUFFIX_NSWE_LABEL);
        labelsSuffixPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_SUFFIX_NSWE_TOOLTIP);
        labelsSuffixPD.setDefaultConverter();
        addPropertyDescriptor(labelsSuffixPD);

        PropertyDescriptor labelsDecimalPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_DECIMAL_VALUE_NAME, Boolean.class);
        labelsDecimalPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_DECIMAL_VALUE_DEFAULT);
        labelsDecimalPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_DECIMAL_VALUE_LABEL);
        labelsDecimalPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_DECIMAL_VALUE_TOOLTIP);
        labelsDecimalPD.setDefaultConverter();
        addPropertyDescriptor(labelsDecimalPD);

        PropertyDescriptor labelsInsidePD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, Boolean.class);
        labelsInsidePD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_INSIDE_DEFAULT);
        labelsInsidePD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_INSIDE_LABEL);
        labelsInsidePD.setDescription(ColorBarLayerType.PROPERTY_LABELS_INSIDE_TOOLTIP);
        labelsInsidePD.setDefaultConverter();
        addPropertyDescriptor(labelsInsidePD);

        PropertyDescriptor labelsItalicsPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_ITALIC_NAME, Boolean.class);
        labelsItalicsPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_ITALIC_DEFAULT);
        labelsItalicsPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_ITALIC_LABEL);
        labelsItalicsPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_ITALIC_TOOLTIP);
        labelsItalicsPD.setDefaultConverter();
        addPropertyDescriptor(labelsItalicsPD);

        PropertyDescriptor labelsBoldPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_BOLD_NAME, Boolean.class);
        labelsBoldPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_BOLD_DEFAULT);
        labelsBoldPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_BOLD_LABEL);
        labelsBoldPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_BOLD_TOOLTIP);
        labelsBoldPD.setDefaultConverter();
        addPropertyDescriptor(labelsBoldPD);

        PropertyDescriptor labelsRotationLatPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LAT_NAME, Double.class);
        labelsRotationLatPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LAT_DEFAULT);
        labelsRotationLatPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LAT_LABEL);
        labelsRotationLatPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LAT_TOOLTIP);
        labelsRotationLatPD.setDefaultConverter();
        labelsRotationLatPD.setValueRange(new ValueRange(0, 900));
        addPropertyDescriptor(labelsRotationLatPD);

        PropertyDescriptor labelsRotationLonPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LON_NAME, Double.class);
        labelsRotationLonPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LON_DEFAULT);
        labelsRotationLonPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LON_LABEL);
        labelsRotationLonPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LON_TOOLTIP);
        labelsRotationLonPD.setDefaultConverter();
        labelsRotationLonPD.setValueRange(new ValueRange(0, 900));
        addPropertyDescriptor(labelsRotationLonPD);

        PropertyDescriptor labelsFontPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_FONT_NAME, String.class);
        labelsFontPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_FONT_DEFAULT);
        labelsFontPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_FONT_LABEL);
        labelsFontPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_FONT_TOOLTIP);
        labelsFontPD.setValueSet(new ValueSet(ColorBarLayerType.PROPERTY_LABELS_FONT_VALUE_SET));
        labelsFontPD.setDefaultConverter();
        addPropertyDescriptor(labelsFontPD);

        PropertyDescriptor labelSizePD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_SIZE_NAME, Integer.class);
        labelSizePD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_SIZE_DEFAULT);
        labelSizePD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_SIZE_LABEL);
        labelSizePD.setDescription(ColorBarLayerType.PROPERTY_LABELS_SIZE_TOOLTIP);
        labelSizePD.setValueRange(new ValueRange(ColorBarLayerType.PROPERTY_LABELS_SIZE_VALUE_MIN, ColorBarLayerType.PROPERTY_LABELS_SIZE_VALUE_MAX));
        labelSizePD.setDefaultConverter();
        addPropertyDescriptor(labelSizePD);

        PropertyDescriptor labelColorPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_LABELS_COLOR_NAME, Color.class);
        labelColorPD.setDefaultValue(ColorBarLayerType.PROPERTY_LABELS_COLOR_DEFAULT);
        labelColorPD.setDisplayName(ColorBarLayerType.PROPERTY_LABELS_COLOR_LABEL);
        labelColorPD.setDescription(ColorBarLayerType.PROPERTY_LABELS_COLOR_TOOLTIP);
        labelColorPD.setDefaultConverter();
        addPropertyDescriptor(labelColorPD);


        // Gridlines Section

        addSectionBreak(ColorBarLayerType.PROPERTY_GRIDLINES_SECTION_NAME,
                ColorBarLayerType.PROPERTY_GRIDLINES_SECTION_LABEL,
                ColorBarLayerType.PROPERTY_GRIDLINES_SECTION_TOOLTIP);


        PropertyDescriptor gridlinesShowPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_NAME, Boolean.class);
        gridlinesShowPD.setDefaultValue(ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_DEFAULT);
        gridlinesShowPD.setDisplayName(ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_LABEL);
        gridlinesShowPD.setDescription(ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_TOOLTIP);
        gridlinesShowPD.setDefaultConverter();
        addPropertyDescriptor(gridlinesShowPD);

        PropertyDescriptor girdlinesWidthPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_GRIDLINES_WIDTH_NAME, Double.class);
        girdlinesWidthPD.setDefaultValue(ColorBarLayerType.PROPERTY_GRIDLINES_WIDTH_DEFAULT);
        girdlinesWidthPD.setDisplayName(ColorBarLayerType.PROPERTY_GRIDLINES_WIDTH_LABEL);
        girdlinesWidthPD.setDescription(ColorBarLayerType.PROPERTY_GRIDLINES_WIDTH_TOOLTIP);
        girdlinesWidthPD.setDefaultConverter();
        addPropertyDescriptor(girdlinesWidthPD);

        PropertyDescriptor gridlinesDashedPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_GRIDLINES_DASHED_PHASE_NAME, Double.class);
        gridlinesDashedPD.setDefaultValue(ColorBarLayerType.PROPERTY_GRIDLINES_DASHED_PHASE_DEFAULT);
        gridlinesDashedPD.setDisplayName(ColorBarLayerType.PROPERTY_GRIDLINES_DASHED_PHASE_LABEL);
        gridlinesDashedPD.setDescription(ColorBarLayerType.PROPERTY_GRIDLINES_DASHED_PHASE_TOOLTIP);
        gridlinesDashedPD.setDefaultConverter();
        addPropertyDescriptor(gridlinesDashedPD);

        PropertyDescriptor gridlinesTransparencyPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_GRIDLINES_TRANSPARENCY_NAME, Double.class);
        gridlinesTransparencyPD.setDefaultValue(ColorBarLayerType.PROPERTY_GRIDLINES_TRANSPARENCY_DEFAULT);
        gridlinesTransparencyPD.setValueRange(new ValueRange(0, 1));
        gridlinesTransparencyPD.setDisplayName(ColorBarLayerType.PROPERTY_GRIDLINES_TRANSPARENCY_LABEL);
        gridlinesTransparencyPD.setDescription(ColorBarLayerType.PROPERTY_GRIDLINES_TRANSPARENCY_TOOLTIP);
        gridlinesTransparencyPD.setDefaultConverter();
        addPropertyDescriptor(gridlinesTransparencyPD);

        PropertyDescriptor gridlinesColorPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_GRIDLINES_COLOR_NAME, Color.class);
        gridlinesColorPD.setDefaultValue(ColorBarLayerType.PROPERTY_GRIDLINES_COLOR_DEFAULT);
        gridlinesColorPD.setDisplayName(ColorBarLayerType.PROPERTY_GRIDLINES_COLOR_LABEL);
        gridlinesColorPD.setDescription(ColorBarLayerType.PROPERTY_GRIDLINES_COLOR_TOOLTIP);
        gridlinesColorPD.setDefaultConverter();
        addPropertyDescriptor(gridlinesColorPD);


        // Border Section

        addSectionBreak(ColorBarLayerType.PROPERTY_BORDER_SECTION_NAME,
                ColorBarLayerType.PROPERTY_BORDER_SECTION_LABEL,
                ColorBarLayerType.PROPERTY_BORDER_SECTION_TOOLTIP);

        PropertyDescriptor borderShowPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_BORDER_SHOW_NAME, Boolean.class);
        borderShowPD.setDefaultValue(ColorBarLayerType.PROPERTY_BORDER_SHOW_DEFAULT);
        borderShowPD.setDisplayName(ColorBarLayerType.PROPERTY_BORDER_SHOW_LABEL);
        borderShowPD.setDescription(ColorBarLayerType.PROPERTY_BORDER_SHOW_TOOLTIP);
        borderShowPD.setDefaultConverter();
        addPropertyDescriptor(borderShowPD);

        PropertyDescriptor borderWidthPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_BORDER_WIDTH_NAME, Double.class);
        borderWidthPD.setDefaultValue(ColorBarLayerType.PROPERTY_BORDER_WIDTH_DEFAULT);
        borderWidthPD.setDisplayName(ColorBarLayerType.PROPERTY_BORDER_WIDTH_LABEL);
        borderWidthPD.setDescription(ColorBarLayerType.PROPERTY_BORDER_WIDTH_TOOLTIP);
        borderWidthPD.setDefaultConverter();
        addPropertyDescriptor(borderWidthPD);

        PropertyDescriptor borderColorPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_BORDER_COLOR_NAME, Color.class);
        borderColorPD.setDefaultValue(ColorBarLayerType.PROPERTY_BORDER_COLOR_DEFAULT);
        borderColorPD.setDisplayName(ColorBarLayerType.PROPERTY_BORDER_COLOR_LABEL);
        borderColorPD.setDescription(ColorBarLayerType.PROPERTY_BORDER_COLOR_TOOLTIP);
        borderColorPD.setDefaultConverter();
        addPropertyDescriptor(borderColorPD);


        // Tickmark Section

        addSectionBreak(ColorBarLayerType.PROPERTY_TICKMARKS_SECTION_NAME,
                ColorBarLayerType.PROPERTY_TICKMARKS_SECTION_LABEL,
                ColorBarLayerType.PROPERTY_TICKMARKS_SECTION_TOOLTIP);

        PropertyDescriptor tickmarksShowPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_TICKMARKS_SHOW_NAME, Boolean.class);
        tickmarksShowPD.setDefaultValue(ColorBarLayerType.PROPERTY_TICKMARKS_SHOW_DEFAULT);
        tickmarksShowPD.setDisplayName(ColorBarLayerType.PROPERTY_TICKMARKS_SHOW_LABEL);
        tickmarksShowPD.setDescription(ColorBarLayerType.PROPERTY_TICKMARKS_SHOW_TOOLTIP);
        tickmarksShowPD.setDefaultConverter();
        addPropertyDescriptor(tickmarksShowPD);

        PropertyDescriptor tickmarksInsidePD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_TICKMARKS_INSIDE_NAME, Boolean.class);
        tickmarksInsidePD.setDefaultValue(ColorBarLayerType.PROPERTY_TICKMARKS_INSIDE_DEFAULT);
        tickmarksInsidePD.setDisplayName(ColorBarLayerType.PROPERTY_TICKMARKS_INSIDE_LABEL);
        tickmarksInsidePD.setDescription(ColorBarLayerType.PROPERTY_TICKMARKS_INSIDE_TOOLTIP);
        tickmarksInsidePD.setDefaultConverter();
        addPropertyDescriptor(tickmarksInsidePD);

        PropertyDescriptor tickmarksLengthPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_TICKMARKS_LENGTH_NAME, Double.class);
        tickmarksLengthPD.setDefaultValue(ColorBarLayerType.PROPERTY_TICKMARKS_LENGTH_DEFAULT);
        tickmarksLengthPD.setDisplayName(ColorBarLayerType.PROPERTY_TICKMARKS_LENGTH_LABEL);
        tickmarksLengthPD.setDescription(ColorBarLayerType.PROPERTY_TICKMARKS_LENGTH_TOOLTIP);
        tickmarksLengthPD.setDefaultConverter();
        addPropertyDescriptor(tickmarksLengthPD);

        PropertyDescriptor tickmarksColorPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_TICKMARKS_COLOR_NAME, Color.class);
        tickmarksColorPD.setDefaultValue(ColorBarLayerType.PROPERTY_TICKMARKS_COLOR_DEFAULT);
        tickmarksColorPD.setDisplayName(ColorBarLayerType.PROPERTY_TICKMARKS_COLOR_LABEL);
        tickmarksColorPD.setDescription(ColorBarLayerType.PROPERTY_TICKMARKS_COLOR_TOOLTIP);
        tickmarksColorPD.setDefaultConverter();
        addPropertyDescriptor(tickmarksColorPD);


        // Corner Label Section

        addSectionBreak(ColorBarLayerType.PROPERTY_CORNER_LABELS_SECTION_NAME,
                ColorBarLayerType.PROPERTY_CORNER_LABELS_SECTION_LABEL,
                ColorBarLayerType.PROPERTY_CORNER_LABELS_SECTION_TOOLTIP);

        PropertyDescriptor cornerLabelsNorthPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_CORNER_LABELS_NORTH_NAME, Boolean.class);
        cornerLabelsNorthPD.setDefaultValue(ColorBarLayerType.PROPERTY_CORNER_LABELS_NORTH_DEFAULT);
        cornerLabelsNorthPD.setDisplayName(ColorBarLayerType.PROPERTY_CORNER_LABELS_NORTH_LABEL);
        cornerLabelsNorthPD.setDescription(ColorBarLayerType.PROPERTY_CORNER_LABELS_NORTH_TOOLTIP);
        cornerLabelsNorthPD.setDefaultConverter();
        addPropertyDescriptor(cornerLabelsNorthPD);

        PropertyDescriptor cornerLabelsSouthPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_CORNER_LABELS_SOUTH_NAME, Boolean.class);
        cornerLabelsSouthPD.setDefaultValue(ColorBarLayerType.PROPERTY_CORNER_LABELS_SOUTH_DEFAULT);
        cornerLabelsSouthPD.setDisplayName(ColorBarLayerType.PROPERTY_CORNER_LABELS_SOUTH_LABEL);
        cornerLabelsSouthPD.setDescription(ColorBarLayerType.PROPERTY_CORNER_LABELS_SOUTH_TOOLTIP);
        cornerLabelsSouthPD.setDefaultConverter();
        addPropertyDescriptor(cornerLabelsSouthPD);

        PropertyDescriptor cornerLabelsWestPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_CORNER_LABELS_WEST_NAME, Boolean.class);
        cornerLabelsWestPD.setDefaultValue(ColorBarLayerType.PROPERTY_CORNER_LABELS_WEST_DEFAULT);
        cornerLabelsWestPD.setDisplayName(ColorBarLayerType.PROPERTY_CORNER_LABELS_WEST_LABEL);
        cornerLabelsWestPD.setDescription(ColorBarLayerType.PROPERTY_CORNER_LABELS_WEST_TOOLTIP);
        cornerLabelsWestPD.setDefaultConverter();
        addPropertyDescriptor(cornerLabelsWestPD);

        PropertyDescriptor cornerLabelsEastPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_CORNER_LABELS_EAST_NAME, Boolean.class);
        cornerLabelsEastPD.setDefaultValue(ColorBarLayerType.PROPERTY_CORNER_LABELS_EAST_DEFAULT);
        cornerLabelsEastPD.setDisplayName(ColorBarLayerType.PROPERTY_CORNER_LABELS_EAST_LABEL);
        cornerLabelsEastPD.setDescription(ColorBarLayerType.PROPERTY_CORNER_LABELS_EAST_TOOLTIP);
        cornerLabelsEastPD.setDefaultConverter();
        addPropertyDescriptor(cornerLabelsEastPD);


        // Inner Labels Section

        addSectionBreak(ColorBarLayerType.PROPERTY_INSIDE_LABELS_SECTION_NAME,
                ColorBarLayerType.PROPERTY_INSIDE_LABELS_SECTION_LABEL,
                ColorBarLayerType.PROPERTY_INSIDE_LABELS_SECTION_TOOLTIP);

        PropertyDescriptor innerLabelsBgTransparencyPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_TRANSPARENCY_NAME, Double.class);
        innerLabelsBgTransparencyPD.setDefaultValue(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_TRANSPARENCY_DEFAULT);
        innerLabelsBgTransparencyPD.setValueRange(new ValueRange(0, 1));
        innerLabelsBgTransparencyPD.setDisplayName(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_TRANSPARENCY_LABEL);
        innerLabelsBgTransparencyPD.setDescription(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_TRANSPARENCY_TOOLTIP);
        innerLabelsBgTransparencyPD.setDefaultConverter();
        addPropertyDescriptor(innerLabelsBgTransparencyPD);


        PropertyDescriptor innerLabelsBgColorPD = new PropertyDescriptor(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_COLOR_NAME, Color.class);
        innerLabelsBgColorPD.setDefaultValue(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_COLOR_DEFAULT);
        innerLabelsBgColorPD.setDisplayName(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_COLOR_LABEL);
        innerLabelsBgColorPD.setDescription(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_COLOR_TOOLTIP);
        innerLabelsBgColorPD.setDefaultConverter();
        addPropertyDescriptor(innerLabelsBgColorPD);


        BindingContext bindingContext = getBindingContext();


        boolean lineEnabled = (Boolean) bindingContext.getPropertySet().getValue(
                ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_NAME);


        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_GRIDLINES_WIDTH_NAME, lineEnabled,
                ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_NAME, lineEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_GRIDLINES_COLOR_NAME, lineEnabled,
                ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_NAME, lineEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_GRIDLINES_TRANSPARENCY_NAME, lineEnabled,
                ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_NAME, lineEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_GRIDLINES_DASHED_PHASE_NAME, lineEnabled,
                ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_NAME, lineEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_GRIDLINES_WIDTH_NAME, lineEnabled,
                ColorBarLayerType.PROPERTY_GRIDLINES_SHOW_NAME, lineEnabled);


        boolean borderEnabled = (Boolean) bindingContext.getPropertySet().getValue(
                ColorBarLayerType.PROPERTY_BORDER_SHOW_NAME);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_BORDER_COLOR_NAME, borderEnabled,
                ColorBarLayerType.PROPERTY_BORDER_SHOW_NAME, borderEnabled);


        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_BORDER_WIDTH_NAME, borderEnabled,
                ColorBarLayerType.PROPERTY_BORDER_SHOW_NAME, borderEnabled);


        // Set enablement associated with "Labels Inside" checkbox

        boolean textInsideEnabled = (Boolean) bindingContext.getPropertySet().getValue(
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_TRANSPARENCY_NAME, textInsideEnabled,
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, textInsideEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_INSIDE_LABELS_BG_COLOR_NAME, textInsideEnabled,
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, textInsideEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LAT_NAME, !textInsideEnabled,
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, textInsideEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_LABELS_ROTATION_LON_NAME, !textInsideEnabled,
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, textInsideEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_CORNER_LABELS_NORTH_NAME, !textInsideEnabled,
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, textInsideEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_CORNER_LABELS_SOUTH_NAME, !textInsideEnabled,
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, textInsideEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_CORNER_LABELS_WEST_NAME, !textInsideEnabled,
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, textInsideEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_CORNER_LABELS_EAST_NAME, !textInsideEnabled,
                ColorBarLayerType.PROPERTY_LABELS_INSIDE_NAME, textInsideEnabled);


        boolean tickMarkEnabled = (Boolean) bindingContext.getPropertySet().getValue(
                ColorBarLayerType.PROPERTY_TICKMARKS_SHOW_NAME);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_TICKMARKS_INSIDE_NAME, tickMarkEnabled,
                ColorBarLayerType.PROPERTY_TICKMARKS_SHOW_NAME, tickMarkEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_TICKMARKS_LENGTH_NAME, tickMarkEnabled,
                ColorBarLayerType.PROPERTY_TICKMARKS_SHOW_NAME, tickMarkEnabled);

        bindingContext.bindEnabledState(ColorBarLayerType.PROPERTY_TICKMARKS_COLOR_NAME, tickMarkEnabled,
                ColorBarLayerType.PROPERTY_TICKMARKS_SHOW_NAME, tickMarkEnabled);


    }


    private void addSectionBreak(String name, String label, String toolTip) {
        PropertyDescriptor descriptor = new PropertyDescriptor(name, Boolean.class);
        descriptor.setDisplayName(label);
        descriptor.setDescription(toolTip);
        addPropertyDescriptor(descriptor);
    }


}
