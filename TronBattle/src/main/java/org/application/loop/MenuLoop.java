package org.application.loop;

import org.application.utility.Settings;
import org.application.view.MenuView;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MenuLoop {
    // Attributi
    private ScheduledExecutorService executorService;
    private MenuView menuView;
    private int currentFrame = 0;
    private List<BufferedImage> frames;

    // Costruttore
    public MenuLoop(MenuView menuView) {
        this.menuView = menuView;
        this.frames = new ArrayList<>();
    }

    // Metodi
    public void startAnimation() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        Runnable animationTask = () -> {
            try {
                // Carica le immagini se non sono ancora caricate
                if (frames.isEmpty()) {
                    loadImages();
                }

                // Aggiorna l'immagine di sfondo
                currentFrame = (currentFrame + 1) % frames.size();
                menuView.setCurrentFrame(currentFrame);
                menuView.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executorService.scheduleAtFixedRate(animationTask, 0, 70, TimeUnit.MILLISECONDS);
    }   // metodo per far partire l'animazione
    public void stopAnimation() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }   // metodo per fermare l'animazione
    private void loadImages() throws IOException {
        for (int i = 0; i < 50; i++) {
            String imagePath = "/background/bgFrame/frame" + i + ".jpg";
            BufferedImage originalImage = ImageIO.read(getClass().getResource(imagePath));

            // Crea un AffineTransform per ridimensionare l'immagine
            AffineTransform at = new AffineTransform();
            at.scale(Settings.WINDOW_SIZEX / (double) originalImage.getWidth(), Settings.WINDOW_SIZEY / (double) originalImage.getHeight());

            // Crea un AffineTransformOp con la trasformazione
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

            // Ridimensiona l'immagine
            BufferedImage scaledImage = scaleOp.filter(originalImage, null);

            frames.add(scaledImage);
        }
    }

    // Getters
    public List<BufferedImage> getFrames() {
        return frames;
    }
}
