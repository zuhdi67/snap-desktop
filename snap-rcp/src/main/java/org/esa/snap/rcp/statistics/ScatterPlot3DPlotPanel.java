/*
 * Copyright (C) 2012 Brockmann Consult GmbH (info@brockmann-consult.de)
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

package org.esa.snap.rcp.statistics;

import com.bc.ceres.binding.Property;
import com.bc.ceres.binding.PropertyContainer;
import com.bc.ceres.binding.ValidationException;
import com.bc.ceres.binding.ValueSet;
import com.bc.ceres.core.ProgressMonitor;
import com.bc.ceres.swing.TableLayout;
import com.bc.ceres.swing.binding.BindingContext;
import com.jidesoft.swing.SimpleScrollPane;
import org.esa.snap.core.datamodel.Band;
import org.esa.snap.core.datamodel.Mask;
import org.esa.snap.core.datamodel.Product;
import org.esa.snap.core.datamodel.RasterDataNode;
import org.esa.snap.core.datamodel.Stx;
import org.esa.snap.core.datamodel.StxFactory;
import org.esa.snap.core.util.Debug;
import org.esa.snap.rcp.SnapApp;
import org.esa.snap.ui.GridBagUtils;
import org.esa.snap.ui.UIUtils;
import org.esa.snap.ui.tool.ToolButtonFactory;
import org.openide.windows.TopComponent;

import javax.swing.AbstractButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The scatter plot pane within the statistics window.
 *
 * @author Olaf Danne
 * @author Sabine Embacher
 */
class ScatterPlot3DPlotPanel extends PagePanel {

    private AbstractButton hideAndShowButton;
    private JPanel backgroundPanel;
    private RoiMaskSelector roiMaskSelector;
    protected AbstractButton refreshButton;
    private final boolean refreshButtonEnabled;

    private AxisRangeControl xAxisRangeControl;
    private AxisRangeControl yAxisRangeControl;
    private AxisRangeControl zAxisRangeControl;

    private final static String PROPERTY_NAME_AUTO_MIN_MAX = "autoMinMax";
    private final static String PROPERTY_NAME_MIN = "min";
    private final static String PROPERTY_NAME_MAX = "max";
    private final static String PROPERTY_NAME_USE_ROI_MASK = "useRoiMask";
    private final static String PROPERTY_NAME_ROI_MASK = "roiMask";
    private final static String PROPERTY_NAME_X_PRODUCT = "xProduct";
    private final static String PROPERTY_NAME_Y_PRODUCT = "yProduct";
    private final static String PROPERTY_NAME_Z_PRODUCT = "zProduct";
    private final static String PROPERTY_NAME_X_BAND = "xBand";
    private final static String PROPERTY_NAME_Y_BAND = "yBand";
    private final static String PROPERTY_NAME_Z_BAND = "zBand";

    private BindingContext bindingContext;
    private DataSourceConfig dataSourceConfig;
    private Property xProductProperty;
    private Property yProductProperty;
    private Property zProductProperty;
    private Property xBandProperty;
    private Property yBandProperty;
    private Property zBandProperty;
    private JComboBox<ListCellRenderer> xProductList;
    private JComboBox<ListCellRenderer> yProductList;
    private JComboBox<ListCellRenderer> zProductList;
    private JComboBox<ListCellRenderer> xBandList;
    private JComboBox<ListCellRenderer> yBandList;
    private JComboBox<ListCellRenderer> zBandList;
    //todo instead using referenceSize, use referenceSceneRasterTransform
    private Dimension referenceSize;

    public static final String CHART_TITLE = "3D Scatter Plot";
    private static final String NO_DATA_MESSAGE = "No 3D scatter plot computed yet.";
    private static final int NUM_DECIMALS = 2;
    private ScatterPlot3dJzyPanel scatterPlot3dJzyPanel;

    ScatterPlot3DPlotPanel(TopComponent parentDialog, String helpId) {
        super(parentDialog, helpId, CHART_TITLE);
        refreshButtonEnabled = false;
//        scatterPlotModel = new ScatterPlotModel();
//        bindingContext = new BindingContext(PropertyContainer.createObjectBacked(scatterPlotModel));
//        final PropertyChangeListener userSettingsUpdateListener = evt -> {
//            if (getRaster() != null) {
//                final VectorDataNode pointDataSourceValue = scatterPlotModel.pointDataSource;
//                final AttributeDescriptor dataFieldValue = scatterPlotModel.dataField;
//                final UserSettings userSettings = getUserSettings(getRaster().getProduct());
//                userSettings.set(getRaster().getName(), pointDataSourceValue, dataFieldValue);
//            }
//        };

//        bindingContext.addPropertyChangeListener(PROPERTY_NAME_DATA_FIELD, userSettingsUpdateListener);
//        bindingContext.addPropertyChangeListener(PROPERTY_NAME_POINT_DATA_SOURCE, userSettingsUpdateListener);
    }

    @Override
    protected void initComponents() {
        dataSourceConfig = new DataSourceConfig();
        bindingContext = new BindingContext(PropertyContainer.createObjectBacked(dataSourceConfig));

        xBandList = new JComboBox<>();
        xBandList.setRenderer(new BandListCellRenderer());
        bindingContext.bind(PROPERTY_NAME_X_BAND, xBandList);
        xBandList.addActionListener(e -> {
            final Object value = xBandList.getSelectedItem();
            if (value != null) {
                final Dimension rasterSize = ((RasterDataNode) value).getRasterSize();
                if (!rasterSize.equals(referenceSize)) {
                    referenceSize = rasterSize;
                    updateBandList(getProduct(), yBandProperty, true);
                    updateBandList(getProduct(), zBandProperty, true);
                }
            }
        });
        xBandProperty = bindingContext.getPropertySet().getProperty(PROPERTY_NAME_X_BAND);

        yBandList = new JComboBox<>();
        yBandList.setRenderer(new BandListCellRenderer());
        bindingContext.bind(PROPERTY_NAME_Y_BAND, yBandList);
        yBandProperty = bindingContext.getPropertySet().getProperty(PROPERTY_NAME_Y_BAND);

        zBandList = new JComboBox<>();
        zBandList.setRenderer(new BandListCellRenderer());
        bindingContext.bind(PROPERTY_NAME_Z_BAND, zBandList);
        zBandProperty = bindingContext.getPropertySet().getProperty(PROPERTY_NAME_Z_BAND);

        xProductList = new JComboBox<>();
        xProductList.addItemListener(new ProductListListener(xBandProperty, true));
        xProductList.setRenderer(new ProductListCellRenderer());
        bindingContext.bind(PROPERTY_NAME_X_PRODUCT, xProductList);
        xProductProperty = bindingContext.getPropertySet().getProperty(PROPERTY_NAME_X_PRODUCT);

        yProductList = new JComboBox<>();
        yProductList.addItemListener(new ProductListListener(yBandProperty, false));
        yProductList.setRenderer(new ProductListCellRenderer());
        bindingContext.bind(PROPERTY_NAME_Y_PRODUCT, yProductList);
        yProductProperty = bindingContext.getPropertySet().getProperty(PROPERTY_NAME_Y_PRODUCT);

        zProductList = new JComboBox<>();
        zProductList.addItemListener(new ProductListListener(zBandProperty, false));
        zProductList.setRenderer(new ProductListCellRenderer());
        bindingContext.bind(PROPERTY_NAME_Z_PRODUCT, zProductList);
        zProductProperty = bindingContext.getPropertySet().getProperty(PROPERTY_NAME_Z_PRODUCT);

        xAxisRangeControl = new AxisRangeControl("X-Axis");
        yAxisRangeControl = new AxisRangeControl("Y-Axis");
        zAxisRangeControl = new AxisRangeControl("Z-Axis");



//        final ScatterPlot3DFXPanel scatterPlot3DFXPanel = new ScatterPlot3DFXPanel();
//        scatterPlot3DFXPanel.init();
        scatterPlot3dJzyPanel = new ScatterPlot3dJzyPanel();
//        Runnable worker = new Runnable() {
//
//            @Override
//            public void run() {
                scatterPlot3dJzyPanel.init();
//            }
//        };
//        RequestProcessor.getDefault().post(worker);
//        add(scatterPlot3DFXPanel);
//        add(scatterPlot3dJzyPanel);
//        createUI(scatterPlot3DFXPanel, createOptionsPanel(), new RoiMaskSelector(bindingContext));
        createUI(scatterPlot3dJzyPanel, createOptionsPanel(), new RoiMaskSelector(bindingContext));
        initActionEnablers();
        updateUIState();
    }

    private void initActionEnablers() {
        RefreshActionEnabler roiMaskActionEnabler =
                new RefreshActionEnabler(refreshButton, PROPERTY_NAME_USE_ROI_MASK, PROPERTY_NAME_ROI_MASK,
                                         PROPERTY_NAME_X_PRODUCT, PROPERTY_NAME_Y_PRODUCT, PROPERTY_NAME_Z_PRODUCT,
                                         PROPERTY_NAME_X_BAND, PROPERTY_NAME_Y_BAND, PROPERTY_NAME_Z_BAND);
        bindingContext.addPropertyChangeListener(roiMaskActionEnabler);
        RefreshActionEnabler rangeControlActionEnabler = new RefreshActionEnabler(refreshButton, PROPERTY_NAME_MIN,
                                                                                  PROPERTY_NAME_AUTO_MIN_MAX,
                                                                                  PROPERTY_NAME_MAX);
        xAxisRangeControl.getBindingContext().addPropertyChangeListener(rangeControlActionEnabler);
        yAxisRangeControl.getBindingContext().addPropertyChangeListener(rangeControlActionEnabler);
        zAxisRangeControl.getBindingContext().addPropertyChangeListener(rangeControlActionEnabler);
    }

    private void updateChartData() {
        final RasterDataNode xNode = dataSourceConfig.xBand;
        final RasterDataNode yNode = dataSourceConfig.yBand;
        final RasterDataNode zNode = dataSourceConfig.zBand;
        final RenderedImage xImage = xNode.getSourceImage().getImage(0);
//        final RenderedImage xImage = xNode.getSourceImage().getImage(xNode.getSourceImage().getModel().getLevelCount() - 1);

        final RenderedImage yImage = yNode.getSourceImage().getImage(0);
//        final RenderedImage yImage = yNode.getSourceImage().getImage(yNode.getSourceImage().getModel().getLevelCount() - 1);
        final RenderedImage zImage = zNode.getSourceImage().getImage(0);
//        final RenderedImage zImage = zNode.getSourceImage().getImage(zNode.getSourceImage().getModel().getLevelCount() - 1);
        final int xSize = xImage.getWidth() * xImage.getHeight();
        final float[] xData = new float[xSize];
        xImage.getData().getPixels(0, 0, xImage.getWidth(), xImage.getHeight(), xData);
        System.out.println("Read out x pixels");
        final double xScale = xNode.getScalingFactor();
        final int ySize = yImage.getWidth() * yImage.getHeight();
        final float[] yData = new float[ySize];
        yImage.getData().getPixels(0, 0, yImage.getWidth(), yImage.getHeight(), yData);
        System.out.println("Read out y pixels");
        final double yScale = yNode.getScalingFactor();
        final int zSize = zImage.getWidth() * zImage.getHeight();
        final float[] zData = new float[zSize];
        zImage.getData().getPixels(0, 0, zImage.getWidth(), zImage.getHeight(), zData);
        System.out.println("Read out z pixels");
        final double zScale = zNode.getScalingFactor();
        try {
            setRange(xAxisRangeControl, xNode, null, ProgressMonitor.NULL);
            setRange(yAxisRangeControl, yNode, null, ProgressMonitor.NULL);
            setRange(zAxisRangeControl, zNode, null, ProgressMonitor.NULL);
        } catch (IOException e) {
            //todo retrieve min max from arrays
        }
        scatterPlot3dJzyPanel.setChartTitle("3D Scatter Plot");
        scatterPlot3dJzyPanel.setLabelNames(xNode.getName(), yNode.getName(), zNode.getName());
        scatterPlot3dJzyPanel.updateChart(xData, yData, zData, xScale, yScale, zScale,
                                          xAxisRangeControl.getMin().floatValue(), xAxisRangeControl.getMax().floatValue(),
                                          yAxisRangeControl.getMin().floatValue(), yAxisRangeControl.getMax().floatValue(),
                                          zAxisRangeControl.getMin().floatValue(), zAxisRangeControl.getMax().floatValue());
//        xImage.getData().getPixels()
    }

    private JPanel createOptionsPanel() {
        final JPanel optionsPanel = GridBagUtils.createPanel();
        final GridBagConstraints gbc = GridBagUtils.createConstraints("anchor=NORTHWEST,fill=HORIZONTAL,insets.top=0,weightx=1,gridx=0");
        GridBagUtils.addToPanel(optionsPanel, xAxisRangeControl.getPanel(), gbc, "gridy=0, insets.top=2");
        GridBagUtils.addToPanel(optionsPanel, xProductList, gbc, "gridy=1,insets.left=4,insets.right=2");
        GridBagUtils.addToPanel(optionsPanel, xBandList, gbc, "gridy=2,insets.left=4,insets.right=2");
        GridBagUtils.addToPanel(optionsPanel, yAxisRangeControl.getPanel(), gbc, "gridy=3,insets.left=0,insets.right=0");
        GridBagUtils.addToPanel(optionsPanel, yProductList, gbc, "gridy=4,insets.left=4,insets.right=2");
        GridBagUtils.addToPanel(optionsPanel, yBandList, gbc, "gridy=5,insets.left=4,insets.right=2");
        GridBagUtils.addToPanel(optionsPanel, zAxisRangeControl.getPanel(), gbc, "gridy=6,insets.left=0,insets.right=0");
        GridBagUtils.addToPanel(optionsPanel, zProductList, gbc, "gridy=7,insets.left=4,insets.right=2");
        GridBagUtils.addToPanel(optionsPanel, zBandList, gbc, "gridy=8,insets.left=4,insets.right=2");
        return optionsPanel;
    }

    private static void setRange(AxisRangeControl axisRangeControl, RasterDataNode raster, Mask mask, ProgressMonitor pm) throws IOException {
        if (axisRangeControl.isAutoMinMax()) {
            Stx stx;
            if (mask == null) {
                stx = raster.getStx(false, pm);
            } else {
                stx = new StxFactory().withRoiMask(mask).create(raster, pm);
            }
            axisRangeControl.adjustComponents(stx.getMinimum(), stx.getMaximum(), NUM_DECIMALS);
        }
    }

    @Override
    protected String getDataAsText() {
        return null;
    }

    @Override
    protected void updateComponents() {
//        plot.setImage(null);
//        plot.setDataset(null);
//        if (isProductChanged()) {
//            plot.getDomainAxis().setLabel("X");
//            plot.getRangeAxis().setLabel("Y");
//        }
        final ValueSet productValueSet = new ValueSet(createAvailableProductList());
        xProductProperty.getDescriptor().setValueSet(productValueSet);
        yProductProperty.getDescriptor().setValueSet(productValueSet);
        zProductProperty.getDescriptor().setValueSet(productValueSet);

        if (productValueSet.getItems().length > 0) {
            Product currentProduct = getProduct();
            try {
                xProductProperty.setValue(currentProduct);
                yProductProperty.setValue(currentProduct);
                zProductProperty.setValue(currentProduct);
            } catch (ValidationException ignored) {
                Debug.trace(ignored);
            }
        }
        updateBandList(getProduct(), xBandProperty, false);
        updateBandList(getProduct(), yBandProperty, true);
        updateBandList(getProduct(), zBandProperty, true);
        if (roiMaskSelector != null) {
            roiMaskSelector.updateMaskSource(getProduct(), getRaster());
        }
        refreshButton.setEnabled(xBandProperty.getValue() != null && yBandProperty.getValue() != null &&
                                         zBandProperty.getValue() != null);
        updateUIState();
    }

    private static Product[] createAvailableProductList() {
        return SnapApp.getDefault().getProductManager().getProducts();
    }

    private void updateBandList(final Product product, final Property bandProperty, boolean considerReferenceSize) {
        if (product == null) {
            return;
        }

        final ValueSet bandValueSet = new ValueSet(createAvailableBandList(product, considerReferenceSize));
        bandProperty.getDescriptor().setValueSet(bandValueSet);
        if (bandValueSet.getItems().length > 0) {
            RasterDataNode currentRaster = getRaster();
            if (bandValueSet.contains(getRaster())) {
                currentRaster = getRaster();
            }
            try {
                bandProperty.setValue(currentRaster);
            } catch (ValidationException ignored) {
                Debug.trace(ignored);
            }
        }
    }

    private RasterDataNode[] createAvailableBandList(final Product product, boolean considerReferenceSize) {
        final List<RasterDataNode> availableBandList = new ArrayList<>(17);
        if (product != null) {
            for (int i = 0; i < product.getNumBands(); i++) {
                final Band band = product.getBandAt(i);
                if (!considerReferenceSize || band.getRasterSize().equals(referenceSize)) {
                    availableBandList.add(band);
                }
            }
            if (!considerReferenceSize || product.getSceneRasterSize().equals(referenceSize)) {
                for (int i = 0; i < product.getNumTiePointGrids(); i++) {
                    availableBandList.add(product.getTiePointGridAt(i));
                }
            }
        }
        // if raster is only bound to the product and does not belong to it
        final RasterDataNode raster = getRaster();
        if (raster != null && raster.getProduct() == product &&
                (!considerReferenceSize || raster.getRasterSize().equals(raster.getProduct().getSceneRasterSize()))) {
            if (!availableBandList.contains(raster)) {
                availableBandList.add(raster);
            }
        }
        return availableBandList.toArray(new RasterDataNode[availableBandList.size()]);
    }

    private void updateUIState() {
        xAxisRangeControl.setComponentsEnabled(getRaster() != null);
        yAxisRangeControl.setComponentsEnabled(getRaster() != null);
        zAxisRangeControl.setComponentsEnabled(getRaster() != null);
    }

    private JPanel createTopPanel() {
        refreshButton = ToolButtonFactory.createButton(
                UIUtils.loadImageIcon("icons/ViewRefresh22.png"),
                false);
        refreshButton.setToolTipText("Refresh View");
        refreshButton.setName("refreshButton");
        refreshButton.addActionListener(e -> {
            updateChartData();
            refreshButton.setEnabled(false);
        });

        AbstractButton switchToTableButton = ToolButtonFactory.createButton(
                UIUtils.loadImageIcon("icons/Table24.png"),
                false);
        switchToTableButton.setToolTipText("Switch to Table View");
        switchToTableButton.setName("switchToTableButton");
        switchToTableButton.setEnabled(hasAlternativeView());
        switchToTableButton.addActionListener(e -> showAlternativeView());

        final TableLayout tableLayout = new TableLayout(6);
        tableLayout.setColumnFill(2, TableLayout.Fill.HORIZONTAL);
        tableLayout.setColumnWeightX(2, 1.0);
        tableLayout.setRowPadding(0, new Insets(0, 4, 0, 0));
        JPanel buttonPanel = new JPanel(tableLayout);
        buttonPanel.add(refreshButton);
        tableLayout.setRowPadding(0, new Insets(0, 0, 0, 0));
        buttonPanel.add(switchToTableButton);
        buttonPanel.add(new JPanel());

        return buttonPanel;
    }

    private JPanel createChartBottomPanel(final ScatterPlot3dJzyPanel chartPanel) {

        final AbstractButton zoomAllButton = ToolButtonFactory.createButton(
                UIUtils.loadImageIcon("icons/view-fullscreen.png"),
                false);
        zoomAllButton.setToolTipText("Zoom all.");
        zoomAllButton.setName("zoomAllButton.");
        zoomAllButton.addActionListener(e -> {
//            chartPanel.restoreAutoBounds();
            chartPanel.repaint();
        });

        final AbstractButton propertiesButton = ToolButtonFactory.createButton(
                UIUtils.loadImageIcon("icons/Edit24.gif"),
                false);
        propertiesButton.setToolTipText("Edit properties.");
        propertiesButton.setName("propertiesButton.");
//        propertiesButton.addActionListener(e -> chartPanel.doEditChartProperties());

        final AbstractButton saveButton = ToolButtonFactory.createButton(
                UIUtils.loadImageIcon("icons/Export24.gif"),
                false);
        saveButton.setToolTipText("Save chart as image.");
        saveButton.setName("saveButton.");
//        saveButton.addActionListener(e -> {
//            try {
//                chartPanel.doSaveAs();
//            } catch (IOException e1) {
//                AbstractDialog.showErrorDialog(chartPanel, "Could not save chart:\n" + e1.getMessage(), "Error");
//            }
//        });

        final AbstractButton printButton = ToolButtonFactory.createButton(
                UIUtils.loadImageIcon("icons/Print24.gif"),
                false);
        printButton.setToolTipText("Print chart.");
        printButton.setName("printButton.");
//        printButton.addActionListener(e -> chartPanel.createChartPrintJob());

        final TableLayout tableLayout = new TableLayout(6);
        tableLayout.setColumnFill(4, TableLayout.Fill.HORIZONTAL);
        tableLayout.setColumnWeightX(4, 1.0);
        JPanel buttonPanel = new JPanel(tableLayout);
        tableLayout.setRowPadding(0, new Insets(0, 4, 0, 0));
        buttonPanel.add(zoomAllButton);
        tableLayout.setRowPadding(0, new Insets(0, 0, 0, 0));
        buttonPanel.add(propertiesButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(printButton);
        buttonPanel.add(new JPanel());
        buttonPanel.add(getHelpButton());

        return buttonPanel;
    }

    /**
     * Responsible for creating the UI layout.
     *
     * @param chartPanel the panel of the chart
     * @param optionsPanel the options panel for changing settings
     * @param roiMaskSelector optional ROI mask selector, can be {@code null} if not wanted.
     */
    protected void createUI(ScatterPlot3dJzyPanel chartPanel, JPanel optionsPanel, RoiMaskSelector roiMaskSelector) {
        this.roiMaskSelector = roiMaskSelector;
        final JPanel extendedOptionsPanel = GridBagUtils.createPanel();
        GridBagConstraints extendedOptionsPanelConstraints = GridBagUtils.createConstraints("insets.left=4,insets.right=2,anchor=NORTHWEST,fill=HORIZONTAL,insets.top=2,weightx=1");
        GridBagUtils.addToPanel(extendedOptionsPanel, new JSeparator(), extendedOptionsPanelConstraints, "gridy=0");
        if (this.roiMaskSelector != null) {
            GridBagUtils.addToPanel(extendedOptionsPanel, this.roiMaskSelector.createPanel(), extendedOptionsPanelConstraints, "gridy=1,insets.left=-4");
            GridBagUtils.addToPanel(extendedOptionsPanel, new JPanel(), extendedOptionsPanelConstraints, "gridy=1,insets.left=-4");
        }
        GridBagUtils.addToPanel(extendedOptionsPanel, optionsPanel, extendedOptionsPanelConstraints, "insets.left=0,insets.right=0,gridy=2,fill=VERTICAL,fill=HORIZONTAL,weighty=1");
        GridBagUtils.addToPanel(extendedOptionsPanel, new JSeparator(), extendedOptionsPanelConstraints, "insets.left=4,insets.right=2,gridy=5,anchor=SOUTHWEST");

        final SimpleScrollPane optionsScrollPane = new SimpleScrollPane(extendedOptionsPanel,
                                                                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        optionsScrollPane.setBorder(null);
        optionsScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        final JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(createTopPanel(), BorderLayout.NORTH);
        rightPanel.add(optionsScrollPane, BorderLayout.CENTER);
        rightPanel.add(createChartBottomPanel(chartPanel), BorderLayout.SOUTH);

        final ImageIcon collapseIcon = UIUtils.loadImageIcon("icons/PanelRight12.png");
        final ImageIcon collapseRolloverIcon = ToolButtonFactory.createRolloverIcon(collapseIcon);
        final ImageIcon expandIcon = UIUtils.loadImageIcon("icons/PanelLeft12.png");
        final ImageIcon expandRolloverIcon = ToolButtonFactory.createRolloverIcon(expandIcon);

        hideAndShowButton = ToolButtonFactory.createButton(collapseIcon, false);
        hideAndShowButton.setToolTipText("Collapse Options Panel");
        hideAndShowButton.setName("switchToChartButton");
        hideAndShowButton.addActionListener(new ActionListener() {

            private boolean rightPanelShown;

            @Override
            public void actionPerformed(ActionEvent e) {
                rightPanel.setVisible(rightPanelShown);
                if (rightPanelShown) {
                    hideAndShowButton.setIcon(collapseIcon);
                    hideAndShowButton.setRolloverIcon(collapseRolloverIcon);
                    hideAndShowButton.setToolTipText("Collapse Options Panel");
                } else {
                    hideAndShowButton.setIcon(expandIcon);
                    hideAndShowButton.setRolloverIcon(expandRolloverIcon);
                    hideAndShowButton.setToolTipText("Expand Options Panel");
                }
                rightPanelShown = !rightPanelShown;
            }
        });

        backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.add(chartPanel, BorderLayout.CENTER);
        backgroundPanel.add(rightPanel, BorderLayout.EAST);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(backgroundPanel, new Integer(0));
        layeredPane.add(hideAndShowButton, new Integer(1));
        add(layeredPane);
    }

    @Override
    public void doLayout() {
        super.doLayout();
        backgroundPanel.setBounds(0, 0, getWidth() - 8, getHeight() - 8);
        hideAndShowButton.setBounds(getWidth() - hideAndShowButton.getWidth() - 12, 2, 24, 24);
    }

    private static String formatProductName(final Product product) {
        String name = product.getName().substring(0, Math.min(10, product.getName().length()));
        if (product.getName().length() > 10) {
            name += "...";
        }
        return product.getProductRefString() + name;
    }

    void renderChart() {
        if (scatterPlot3dJzyPanel != null) {
            scatterPlot3dJzyPanel.renderChart();
        }
    }

    private static class DataSourceConfig {

        public boolean useRoiMask;
        public Mask roiMask;
        private Product xProduct;
        private Product yProduct;
        private Product zProduct;
        private RasterDataNode xBand;
        private RasterDataNode yBand;
        private RasterDataNode zBand;
        private Product xProductProperty;
        private Product yProductProperty;
        private Product zProductProperty;
        private Property xBandProperty;
        private Property yBandProperty;
        private Property zBandProperty;
    }

    private static class BandListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                this.setText(((RasterDataNode) value).getName());
            }
            return this;
        }
    }

    private static class ProductListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                this.setText(formatProductName((Product) value));
            }
            return this;
        }
    }

    private class ProductListListener implements ItemListener {

        private final Property bandProperty;
        private final boolean considerReferenceSize;

        ProductListListener(Property bandProperty, boolean considerReferenceSize) {
            this.bandProperty = bandProperty;
            this.considerReferenceSize = considerReferenceSize;
        }

        @Override
        public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                final Product selectedProduct = (Product) event.getItem();
                updateBandList(selectedProduct, bandProperty, considerReferenceSize);
            }
        }

    }

}
