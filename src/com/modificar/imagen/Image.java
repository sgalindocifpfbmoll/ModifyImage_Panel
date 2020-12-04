package com.modificar.imagen;

import Helper.Matrix2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class Image extends JComponent {
    // Attributes
    private BufferedImage bfImage;
    private int widthImage, heightImage, depth;
    private String path;
    private byte[] vector;
    public boolean selected = false;

    //<editor-fold desc="CONSTRUCTORS">
    public Image(String path, int depth) {
        try {
            this.setPath(path);
            this.setBfImage(ImageIO.read(new File(path)));
            this.setWidthImage(this.getBfImage().getWidth());
            this.setHeightImage(this.getBfImage().getHeight());
            this.setDepth(depth);
            this.setVector(((DataBufferByte) this.getBfImage().getRaster().getDataBuffer()).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image(String path, int depth, int width, int height) {
        try {
            this.setPath(path);
            this.setBfImage(ImageIO.read(new File(path)));
            this.setWidthImage(this.getBfImage().getWidth());
            this.setHeightImage(this.getBfImage().getHeight());
            this.setDepth(depth);
            this.setVector(((DataBufferByte) this.getBfImage().getRaster().getDataBuffer()).getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image(BufferedImage bfImage, int depth){
        this.setPath("subaru.jpg");
        this.setBfImage(bfImage);
        this.setWidthImage(this.getBfImage().getWidth());
        this.setHeightImage(this.getBfImage().getHeight());
        this.setDepth(depth);
        this.setVector(((DataBufferByte) this.getBfImage().getRaster().getDataBuffer()).getData());
    }

    public Image(Image image){
        this.setBfImage(image.getBfImage());
        this.setWidthImage(image.getBfImage().getWidth());
        this.setHeightImage(image.getBfImage().getHeight());
        this.setDepth(3);
        this.setVector(((DataBufferByte) image.getBfImage().getRaster().getDataBuffer()).getData());
    }
    //</editor-fold>

    //<editor-fold desc="GETTERS">
    public BufferedImage getBfImage() {
        return bfImage;
    }

    public int getWidthImage() {
        return widthImage;
    }

    public int getHeightImage() {
        return heightImage;
    }

    public int getDepth() {
        return depth;
    }

    public byte[] getVector() {
        return vector;
    }

    public int getPosition(int row, int column, int depth) {
        return ((row) * (this.getWidthImage()) * this.getDepth()) + ((column) * this.getDepth()) + (depth);
    }

    public String getPath(){
        return this.path;
    }

    public boolean isSelected() {
        return selected;
    }

//</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setBfImage(BufferedImage bfImage) {
        this.bfImage = bfImage;
    }

    public void setWidthImage(int widthImage) {
        this.widthImage = widthImage;
    }

    public void setHeightImage(int heightImage) {
        this.heightImage = heightImage;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setVector(byte[] vector) {
        this.vector = vector;
    }

    public void setValue(int row, int column, int depth, int value){
        int position=this.getPosition(row, column, depth);
        this.vector[position]=(byte)value;
    }

    public void setValue(int row, int column, int depth, double value){
        int position=this.getPosition(row, column, depth);
        this.vector[position]=(byte)value;
    }

    public void setPath(String path){
        this.path=path;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setImage(Image image){
        this.setBfImage(image.getBfImage());
        this.setVector(image.getVector());
    }
//</editor-fold>

    public Image getModified(Matrix2D modifier){
        // Create a copy of the image
        Image imageOut=new Image(this.getPath(), this.getDepth());
        // Modify it
        for (int i = 0; i < imageOut.getHeightImage(); i++) {
            for (int j = 0; j < imageOut.getWidthImage(); j++) {
                for (int k = 0; k < imageOut.getDepth(); k++) {
                    if (i != 0 && j != 0 && i != imageOut.getHeightImage() - 1 && j != imageOut.getWidthImage() - 1) {
                        // Get the value
                        int value=this.getCalculatedValue(i, j, k, modifier);
                        // Set the value in the image
                        imageOut.setValue(i, j, k, value);
                    }
                }
            }
        }
        // Return the modified copy
        return imageOut;
    }

    public Image getModified(int r, int g, int b, int size){
        int sizeX = (int) (this.getWidthImage() * 0.01 * size);
        int sizeY = (int) (this.getHeightImage()* 0.01 * size);
        int differenceX= this.getWidthImage() - sizeX;
        int differenceY= this.getHeightImage() - sizeY;
        // Create a copy of the image
        Image imageOut=new Image(this.getPath(), this.getDepth());
        // Modify it
        for (int row = sizeY; row < differenceY; row++) {
            for (int column = sizeX; column < differenceX; column++) {
                for (int depth = 0; depth < imageOut.getDepth(); depth++) {
                    // Get the position of the pixel
                    int position= this.getPosition(row, column, depth);
                    int channel;
                    double value;
                    // Change RGB
                    if(depth == 0){
                        channel = b;
                    }else if (depth == 1){
                        channel = g;
                    }else{
                        channel = r;
                    }
                    // If it is -1 there is no change at color
                    if (channel == -1){
                        value = Byte.toUnsignedInt(this.getVector()[position]);
                    }else{
                        value = Byte.toUnsignedInt(this.getVector()[position]) * (channel * 0.01);
                    }
                    // If it overpasses or underpasses set default value
                    if(value > 255){
                        value= 255;
                    } else if(value < 0){
                        value = 0;
                    }
                    // Set the value
                    imageOut.setValue(row, column, depth, value);
                }
            }
        }
        // Return the modified copy
        return imageOut;
    }

    private int getCalculatedValue(int row, int column, int depth, Matrix2D modifier){
        int value=0;
        //
        for (int i = -1; i < modifier.getRows() - 1; i++) {
            for (int j = -1; j < modifier.getColumns() - 1; j++) {
                int position= this.getPosition(row + i, column + j, depth);
                value+=Byte.toUnsignedInt(this.getVector()[position]) * modifier.getValue(i + 1, j + 1);
            }
        }
        //Then divide the value by the modifier matrix divider
        value=value/modifier.getDivisor();
        if(value < 0){
            value=0;
        }else if(value > 255){
            value=255;
        }
        return value;
    }

    // --------------------- PAINT --------------------------
    public void paint(Graphics2D g, int posX, int posY, int scale){
        g.drawImage(this.getBfImage(), posX, posY, this.getWidthImage() * scale, this.getHeightImage() * scale,
                null);
    }

    public void paint(Graphics2D g, int posX, int posY){
        g.drawImage(this.getBfImage(), posX, posY, null);
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(this.getBfImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }

}
