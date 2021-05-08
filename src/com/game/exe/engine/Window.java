package com.game.exe.engine;

import com.game.exe.engine.GameContainer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

public class Window {
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;

    private GameContainer gc;

    public Color backgroundColour = new Color(146,244,255);
    private boolean loader = true;


    public Window(GameContainer gc) {
        this.gc = gc;
        image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        frame = new JFrame(gc.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(backgroundColour);

        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/misc/icon.png")));

        frame.setLayout(new BorderLayout());

        BufferedImage cursorImage = new BufferedImage(7,10,BufferedImage.TYPE_INT_ARGB);
        try {
            cursorImage = ImageIO.read(getClass().getResource("/assets/misc/cursor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cursorImage.createGraphics();
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0), "game.exe Default Cursor");
        frame.setCursor(cursor);

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setState(Frame.NORMAL);
        frame.requestFocus();

        canvas.createBufferStrategy(2);
        canvas.setBackground(backgroundColour);
        canvas.setEnabled(true);
        bs = canvas.getBufferStrategy();
        g =  bs.getDrawGraphics();
    }

    public void update() {
        g.drawImage(image,0,0,canvas.getWidth(),canvas.getHeight(),null);
        bs.show();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setScale(int scale) {
        Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.requestFocus();
    }

}
