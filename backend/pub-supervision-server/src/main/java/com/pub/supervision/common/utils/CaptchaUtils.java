package com.pub.supervision.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class CaptchaUtils {

    private static final String[] FONT_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789".split("");
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final int CODE_LENGTH = 4;
    private static final int LINE_COUNT = 4;
    private static final int DOT_COUNT = 50;

    public static CaptchaResult generate() {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        Random random = new Random();

        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            String ch = FONT_CHARS[random.nextInt(FONT_CHARS.length)];
            code.append(ch);

            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24 + random.nextInt(8)));

            double theta = Math.toRadians(random.nextInt(30) - 15);
            int x = 10 + i * 25;
            int y = 25 + random.nextInt(10);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.rotate(theta, x, y);
            g2.drawString(ch, x, y);
            g2.dispose();
        }

        g.setColor(new Color(100 + random.nextInt(100), 100 + random.nextInt(100), 100 + random.nextInt(100)));
        for (int i = 0; i < LINE_COUNT; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        g.setColor(new Color(150, 150, 150));
        for (int i = 0; i < DOT_COUNT; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            g.fillOval(x, y, 2, 2);
        }

        g.dispose();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
            String uuid = UUID.randomUUID().toString();

            return new CaptchaResult(uuid, "data:image/png;base64," + base64, code.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate captcha", e);
        }
    }

    public static class CaptchaResult {
        private final String uuid;
        private final String image;
        private final String code;

        public CaptchaResult(String uuid, String image, String code) {
            this.uuid = uuid;
            this.image = image;
            this.code = code;
        }

        public String getUuid() { return uuid; }
        public String getImage() { return image; }
        public String getCode() { return code; }
    }
}
