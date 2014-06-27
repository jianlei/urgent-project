package com.daren.core.web.wicket.security;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.daren.core.web.wicket.PermissionConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.request.resource.DynamicImageResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * @类描述：验证码实现类
 * @创建人：sunlf
 * @创建时间：2014-05-04 下午4:44
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

public class CaptchaImage extends NonCachingImage {

    private DefaultKaptcha captchaProducer;

    // private DefaultKaptcha captchaProducer;
    public CaptchaImage(String id) {
        super(id);
        captchaProducer = new DefaultKaptcha();
        Config config = new Config(new Properties());
        captchaProducer.setConfig(config);

        setImageResource(new DynamicImageResource() {

            private BufferedImage getImageCaptchaService() {
                String capText = captchaProducer.createText();

                // store the text in the session
                Session session = SecurityUtils.getSubject().getSession();
                session.setAttribute(PermissionConstant.VALIDATE_CODE, capText);

                // create the image with the text
                BufferedImage bi = captchaProducer.createImage(capText);

                return bi;

            }

            @Override
            protected byte[] getImageData(Attributes attributes) {
                try {
                    final java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
                    BufferedImage bi = getImageCaptchaService();
                    // Write image using any matching ImageWriter
                    ImageIO.write(bi, "png", out);
                    // Return the image data
                    return out.toByteArray();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}