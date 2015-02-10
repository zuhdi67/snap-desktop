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
package org.esa.snap.gui.actions.file;

import org.esa.beam.framework.datamodel.Product;
import org.esa.snap.gui.SnapApp;
import org.openide.util.HelpCtx;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * Action for exporting a product.
 *
 * @author Marco Peters
 */
public class ExportProductAction extends AbstractAction implements HelpCtx.Provider{
    // todo (mp) - should only be enabled if a product is selected
    /**
     * Action factory method used in NetBeans {@code layer.xml} file, e.g.
     * <p>
     * <pre>
     * &lt;file name="org-esa-beam-csv-dataio-ExportCSVProduct.instance"&gt;
     *     &lt;attr name="instanceCreate"
     *         methodvalue="org.openide.awt.Actions.alwaysEnabled"/&gt;
     *     &lt;attr name="delegate"
     *         methodvalue="org.esa.snap.gui.actions.file.ExportProductAction.create"/&gt;
     *     &lt;attr name="displayName"
     *         stringvalue="CSV Product"/&gt;
     *     &lt;attr name="formatName"
     *         stringvalue="CSV"/&gt;
     *     &lt;attr name="useAllFileFilter"
     *         boolvalue="true"/&gt;
     *     &lt;attr name="helpId"
     *         stringvalue="exportCsvProduct"/&gt;
     *     &lt;attr name="ShortDescription"
     *         stringvalue=">Writes a product in CSV format."/&gt;
     * &lt;/file&gt;
     * </pre>
     *
     * @param configuration Configuration attributes from layer.xml.
     * @return The action.
     *
     * @since SNAP 2
     */
    public static ExportProductAction create(Map<String, Object> configuration) {
        ExportProductAction exportProductAction = new ExportProductAction();
        exportProductAction.setFormatName((String) configuration.get("formatName"));
        exportProductAction.setHelpCtx((String) configuration.get("helpId"));
        exportProductAction.setUseAllFileFilter((Boolean) configuration.get("useAllFileFilter"));
        return exportProductAction;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return (HelpCtx) getValue("helpCtx");
    }

    public void setHelpCtx(String helpId) {
        putValue("helpCtx", helpId != null ? new HelpCtx(helpId) : null);
    }

    public void setFormatName(String formatName) {
        putValue("formatName", formatName);
    }

    public void setUseAllFileFilter(Boolean useAllFileFilter) {
        putValue("useAllFileFilter", useAllFileFilter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Product selectedProduct = SnapApp.getDefault().getSelectedProduct();
        if (selectedProduct != null) {
            new SaveProductAsAction(selectedProduct).execute();
        }
    }

}
