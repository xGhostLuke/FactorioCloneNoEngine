package main;

import map.Direction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * class to load all the images
 */
public class ImageLoader {

    private static final Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();


    /**
     * Method to load all Images
     */
    public static void loadAll(){
        try{
            images.put("dirt", load("/res/GrasTile.png"));
            images.put("copperOre", load("/res/Coppertile.png"));
            images.put("stoneOre", load("/res/StoneTile.png"));
            images.put("coalOre", load("/res/ColeTile.png")); //tf is this spelling

            images.put("miner", load("/res/Drill.png"));
            loadRotations("miner");

            images.put("belt", load("/res/belt_top.png"));
            loadRotations("belt");

        }catch (IOException e){
            System.out.println("EEEE");
            System.out.println("Error loading images");
        }
    }

    /**
     * Method to load an image with the path
     *
     * @param path path to the image
     * @return BufferedImage at path
     * @throws IOException if image isnt found
     */
    public static BufferedImage load(String path) throws IOException {
        return ImageIO.read(ImageLoader.class.getResourceAsStream(path));
    }

    /**
     * Method to get an laoded Image
     *
     * @param name name of the image (key value in die image map)
     * @return BufferedImage belonging to the name
     */
    public static BufferedImage getImage(String name){
        if(!images.containsKey(name)){
            System.out.println("Image not found");
        }
        return images.get(name);
    }

    /**
     * Method to rotate images so I dont have to draw certain building
     *
     * @param img the image to rotate
     * @param angleDegrees degrees to rotate the image
     * @return BufferedImage rotated Image
     */
    private static BufferedImage rotateSimple(BufferedImage img, double angleDegrees) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage rotated = new BufferedImage(w, h, img.getType());
        var g2d = rotated.createGraphics();
        g2d.rotate(Math.toRadians(angleDegrees), w / 2.0, h / 2.0);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return rotated;
    }

    private static void loadRotations(String name){
        for (int i = 0; i < 360; i = i + 90){
            switch (i){
                case 0:
                    images.put(name + "_top",rotateSimple(getImage(name), i));
                case 180:
                    images.put(name + "_down",rotateSimple(getImage(name), i));
                break;
                case 90:
                        images.put(name + "_right",rotateSimple(getImage(name), i));
                break;
                case 270:
                    images.put(name + "_left",rotateSimple(getImage(name), i));
                break;
            }
        }
    }

    public static BufferedImage getImageWithRotation(String name, Direction rotation){
        //System.out.println(name + "_" + rotation.name().toLowerCase());
        return getImage(name.toLowerCase() + "_" + rotation.name().toLowerCase());
    }
}
