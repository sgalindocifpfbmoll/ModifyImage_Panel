package Canvas;

import Helper.Matrix2D;
import Modificator.Modifications;
import com.modificar.imagen.Image;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;

public class Viewer extends JFrame {
    private final MyPanel panelImages, panelControl;
    private GridBagConstraints gbConsImages = new GridBagConstraints();
    private GridBagConstraints gbConsControl = new GridBagConstraints();
    private String imagePath;
    private int rows = 3, columns = 3;
    private HashMap<Image, Dimension> images = new HashMap<>();

    //<editor-fold desc="CONSTRUCTORS">
    public Viewer(String title) {
        super(title);
        // Set BorderLayout to the layout frame
        this.getContentPane().setLayout(new BorderLayout());
        // Create the 2 panels
        panelImages = new MyPanel(new GridBagLayout(), Color.GRAY);
        panelControl = new MyPanel(new GridBagLayout(), Color.LIGHT_GRAY);
        // Set the preferences for both panels
        initPanelImages("subaru.jpg");
        initPanelControl();
        // Add the panels to the current frame
        addPanel(panelImages, BorderLayout.CENTER);
        addPanel(panelControl, BorderLayout.WEST);
        // Initialize frame components
        initComponents();
    }
    //</editor-fold>

    //<editor-fold desc="PANELS PREFERENCES">
    private void addPanelImagesPreferences(String filePath) {
        // Set the imagePath
        imagePath = filePath;
        // Initialize the constraint for setting the config of the panel
        gbConsImages.fill = GridBagConstraints.BOTH;
        gbConsImages.weightx = 1;
        gbConsImages.weighty = 1;
    }

    private void addPanelControlPreferences() {
        // Size of leftPanel
        //panelControl.setBorder(0, 300, 0, 0);
        // Initialize the constraint for setting the config of the panel
        gbConsControl.fill = GridBagConstraints.BOTH;
        gbConsControl.weightx = 1;
        gbConsControl.weighty = 1;
    }
    //</editor-fold>

    //<editor-fold desc="INITIALIZERS">
    private void initPanelControl() {
        // Set the Control panel preferences first of all
        addPanelControlPreferences();
        //FileChooser
        JFileChooser fileChooser = new JFileChooser();
        // Boxes
        MyPanel boxButtons = new MyPanel(new GridLayout(2,2));
        MyPanel boxInfo = new MyPanel(new GridLayout(5,2));
        boxButtons.setBorder(BorderFactory.createRaisedBevelBorder());
        // Text Labels
        JLabel fileLabel = new JLabel("FILE");
        JLabel effectsLabel = new JLabel("Effects");
        JLabel areaLabel = new JLabel("Area");
        JLabel brightLabel = new JLabel("Bright Channel");
        JLabel rLabel = new JLabel("Red Channel");
        JLabel gLabel = new JLabel("Green Channel");
        JLabel bLabel = new JLabel("Blue Channel");
        JLabel focusLabel = new JLabel("Focus");
        // Add different buttons
        Button buttonSelect = new Button("Load image");
        Button buttonOriginal = new Button("Original image");
        Button buttonNegative = new Button("Negative image");
        Button buttonRemove = new Button("Remove image");
        // Set the buttons actions
        buttonRemove.addActionListener(e -> {
            // Modify the images
            modifyImages(Modifications.DELETEIMAGE);
        });
        buttonNegative.addActionListener(e -> {
            // Modify the images
            modifyImages(Modifications.NEGATIVEIMAGE);
        });
        buttonSelect.addActionListener(e -> {
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                changeImage(fileChooser.getSelectedFile().getPath());
            }
        });
        buttonOriginal.addActionListener(e -> {
            // Modify the images
            modifyImages(Modifications.ORIGINALIMAGE);
        });
        // Sliders
        JSlider areaSlider = new JSlider(0, 50, 0);
        JSlider brightSlider = new JSlider(0, 255, 100);
        JSlider rSlider = new JSlider(0, 255, 100);
        JSlider gSlider = new JSlider(0, 255, 100);
        JSlider bSlider = new JSlider(0, 255, 100);
        JSlider focusSlider = new JSlider(-5, 5, 0);
        // Set the sliders actions
        areaSlider.addChangeListener(e ->{
            changeRGB(rSlider.getValue(), gSlider.getValue(), bSlider.getValue(), areaSlider.getValue());
        });
        brightSlider.addChangeListener(e ->{
            changeRGB(brightSlider.getValue(), brightSlider.getValue(), brightSlider.getValue(), areaSlider.getValue());
        });
        rSlider.addChangeListener(e ->{
            changeRGB(rSlider.getValue(), gSlider.getValue(), bSlider.getValue(), areaSlider.getValue());
        });
        gSlider.addChangeListener(e ->{
            changeRGB(rSlider.getValue(), gSlider.getValue(), bSlider.getValue(), areaSlider.getValue());
        });
        bSlider.addChangeListener(e ->{
            changeRGB(rSlider.getValue(), gSlider.getValue(), bSlider.getValue(), areaSlider.getValue());
        });
        focusSlider.addChangeListener(e ->{
            focusImage(focusSlider.getValue());
        });
        // Add the buttons box to the Control panel
        boxButtons.add(buttonSelect);
        boxButtons.add(buttonOriginal);
        boxButtons.add(buttonRemove);
        boxButtons.add(buttonNegative);
        // Add info to the FILE box
        JTextArea boxInfo_Description = new JTextArea("Description");
        JTextArea boxInfo_Value = new JTextArea("Value");
        JTextArea boxInfo_KP = new JTextArea("Total KPixels");
        JTextArea boxInfo_KB = new JTextArea("Total KBytes");
        JTextArea boxInfo_pixelHeight = new JTextArea("Pixels Height");
        JTextArea boxInfo_pixelWidth = new JTextArea("Pixels Width");

        JTextArea boxInfo_KP_value = new JTextArea();
        JTextArea boxInfo_KB_value = new JTextArea();
        JTextArea boxInfo_pixelHeight_value = new JTextArea();
        JTextArea boxInfo_pixelWidth_value = new JTextArea();

        boxInfo.add(boxInfo_Description);
        boxInfo.add(boxInfo_Value);
        boxInfo.add(boxInfo_KP);
        boxInfo.add(boxInfo_KP_value);
        boxInfo.add(boxInfo_KB);
        boxInfo.add(boxInfo_KB_value);
        boxInfo.add(boxInfo_pixelHeight);
        boxInfo.add(boxInfo_pixelHeight_value);
        boxInfo.add(boxInfo_pixelWidth);
        boxInfo.add(boxInfo_pixelWidth_value);

        // GB elements
        gbConsControl.fill = GridBagConstraints.HORIZONTAL;
        gbConsControl.gridx = 0;
        gbConsControl.gridy = 0;
        panelControl.add(effectsLabel, gbConsControl);
        gbConsControl.gridx++;
        panelControl.add(boxButtons, gbConsControl);
        gbConsControl.gridx = 0;
        gbConsControl.gridy++;
        panelControl.add(fileLabel, gbConsControl);
        gbConsControl.gridx++;
        panelControl.add(boxInfo, gbConsControl);
        gbConsControl.gridx = 0;
        gbConsControl.gridy++;
        panelControl.add(areaLabel, gbConsControl);
        gbConsControl.gridx++;
        panelControl.add(areaSlider, gbConsControl);
        gbConsControl.gridx = 0;

        gbConsControl.gridy++;
        panelControl.add(brightLabel, gbConsControl);
        gbConsControl.gridx++;
        panelControl.add(brightSlider, gbConsControl);
        gbConsControl.gridx = 0;
        gbConsControl.gridy++;
        panelControl.add(rLabel, gbConsControl);
        gbConsControl.gridx++;
        panelControl.add(rSlider, gbConsControl);
        gbConsControl.gridx = 0;
        gbConsControl.gridy++;
        panelControl.add(gLabel, gbConsControl);
        gbConsControl.gridx++;
        panelControl.add(gSlider, gbConsControl);
        gbConsControl.gridx = 0;
        gbConsControl.gridy++;
        panelControl.add(bLabel, gbConsControl);
        gbConsControl.gridx++;
        panelControl.add(bSlider, gbConsControl);
        gbConsControl.gridx = 0;
        gbConsControl.gridy++;
        panelControl.add(focusLabel, gbConsControl);
        gbConsControl.gridx++;
        panelControl.add(focusSlider, gbConsControl);

    }

    private void initPanelImages(String filePath) {
        // Set the Images panel preferences first of all
        addPanelImagesPreferences(filePath);
        // Add the images to the panel
        showImages();
    }

    private void initComponents() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Open window on maximized
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }
    //</editor-fold>

    private void focusImage(int value){
        for (Image image: images.keySet()){
            if(image.isSelected()){
                Image modified = new Image(imagePath, image.getDepth());
                if (value > 0){
                    for (int i = 0; i < value; i++) {
                        modified = modified.getModified(new Matrix2D(Modifications.BLUR));
                    }
                }else if (value < 0){
                    for (int i = 0; i > value; i--) {
                        modified = modified.getModified(new Matrix2D(Modifications.SHARP));
                    }
                }
                image.setImage(modified);
            }
        }
        update();
    }

    private void changeImage(String filePath){
        this.imagePath = filePath;
        resetPanel(filePath);
    }

    private void changeRGB (int r, int g, int b, int percentage){
        for (Image image: images.keySet()){
            if(image.isSelected()){
                Image modified = new Image(imagePath, 3).getModified(r, g, b, percentage);
                image.setImage(modified);
            }
        }
        update();
    }

    private void showImages() {
        //

        // Insert the images as components
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Image image = new Image(imagePath, 3);
                insertImage(image, row, column);
                images.put(image, new Dimension(column, row));
            }
        }
        update();
    }

    private void modifyImages(int option) {
        // Get the images k,v in a iterator
        Iterator<Image> iterator = images.keySet().iterator();
        // Temporary Hashmap to add later modified keys and values to the Hashmap Images
        HashMap<Image, Dimension> toBeAdded = new HashMap<>();
        // While iterator has a k,v
        while (iterator.hasNext()) {
            Image image = iterator.next();
            if (image.isSelected()) {
                Image modified;
                Matrix2D modifier = null;
                switch (option) {
                    case Modifications.DELETEIMAGE:
                        panelImages.remove(image);
                        iterator.remove();
                        break;
                    case Modifications.NEGATIVEIMAGE:
                        modifier = new Matrix2D(Modifications.NEGATIVE);
                        // Get the modified image
                        modified = image.getModified(modifier);
                        // Remove the original image from the Images panel and insert the new one
                        panelImages.remove(image);
                        insertImage(modified, images.get(image).height, images.get(image).width);
                        // Remove the original image from the hashmap and insert the new one
                        toBeAdded.put(modified, new Dimension(images.get(image).width, images.get(image).height));
                        iterator.remove();
                        break;
                    case Modifications.ORIGINALIMAGE:
                        // Get the modified image
                        modified = new Image(image.getPath(), image.getDepth());
                        // Remove the original image from the Images panel and insert the new one
                        panelImages.remove(image);
                        insertImage(modified, images.get(image).height, images.get(image).width);
                        // Remove the original image from the hashmap and insert the new one
                        toBeAdded.put(modified, new Dimension(images.get(image).width, images.get(image).height));
                        iterator.remove();
                        break;
                }
            }
        }
        // Add the modified images to the hashmap images
        images.putAll(toBeAdded);
        // Update the panel
        update();
    }

    private void insertImage(Image image, int row, int column) {
        // Add a mouse listener
        image.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (image.isSelected()) {
                    image.setSelected(false);
                    image.setBorder(null);
                } else {
                    image.setSelected(true);
                    image.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
                            BorderFactory.createLoweredBevelBorder()));
                }
            }

            //<editor-fold desc="EVENTS NOT USED">
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
            //</editor-fold>
        });
        gbConsImages.gridx = column;
        gbConsImages.gridy = row;
        panelImages.add(image, gbConsImages);
    }

    private void addPanel(MyPanel panel, String place) {
        this.getContentPane().add(panel, place);
    }

    private void resetPanel(String filePath){
        // Clear all images
        images.clear();
        panelImages.removeAll();
        // Init again the preferences
        initPanelImages(filePath);
        // Show the images
        showImages();
    }

    private void update() {
        revalidate();
        repaint();
    }

    // -------------- TEST --------------

    private void deselectImages() {
        Component[] components = panelImages.getComponents();
        for (Component component : components) {
            ((Image) component).setSelected(false);
            ((Image) component).setBorder(null);
        }
    }

    public void run() {
        this.setVisible(true);
    }   // Not in use

    private void relocateImages(Dimension position){
        // All images with (superior row + superior column) or superior column
        // Get the images k,v in a iterator
        Iterator<Image> iterator = images.keySet().iterator();
        // While iterator has a k,v
        while (iterator.hasNext()) {
            Image image = iterator.next();
            if(images.get(image).height > position.height){
                if(images.get(image).height == 0){
                    panelImages.remove(image);
                    gbConsImages.gridx = position.width;
                    gbConsImages.gridy = position.height -1;
                    panelImages.add(image, gbConsImages);
                }else{
                    panelImages.remove(image);
                    gbConsImages.gridx = position.width - 1;
                    gbConsImages.gridy = position.height;
                    panelImages.add(image, gbConsImages);
                }
            }else if (images.get(image).width > position.width){
                panelImages.remove(image);
                gbConsImages.gridx = position.width - 1;
                gbConsImages.gridy = position.height;
                panelImages.add(image, gbConsImages);
            }
        }
    }
}


