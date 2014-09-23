package com.daren.example.webapp.wicket.page;


import com.daren.core.web.component.lightbox2.LightboxLink;
import com.daren.core.web.component.lightbox2.LightboxPanel;
import com.daren.core.web.wicket.BasePanel;
import org.apache.wicket.extensions.markup.html.image.resource.ThumbnailImageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * 项目名称:  urgent-project
 * 类描述:    测试lightbox2
 * 创建人:   dlw
 * 创建时间:  2014/9/15 17:21
 *在需要引用此框架的地方的pox中添加:
     <dependency>
     <groupId>com.daren.core</groupId>
     <artifactId>com.daren.core.web.component.lightbox2</artifactId>
     </dependency>
     在版本控制当中添加:
     <dependency>
     <groupId>com.daren.core</groupId>
     <artifactId>com.daren.core.web.component.lightbox2</artifactId>
     <version>${project.version}</version>
     <scope>provided</scope>
     </dependency>
 */
public class Lightbox2PageTest extends BasePanel {


    public Lightbox2PageTest(String id, WebMarkupContainer wmc) {
        super(id, wmc);
//        add(new Label("tabs",""));
        /**
         * 访问本地资源的例子
         * 例子中的图片在
         * modules.example
         *      example-webapp-wicket
         *          main
         *              resources
         *                  page
         *                      resources下
         */
        // Using Resoures
        ResourceReference image = new PackageResourceReference(Lightbox2PageTest.class,"resources/image-1.jpg");
        //ResourceReference thumbnail = new PackageResourceReference(Lightbox2PageTest.class,"resources/thumb-1.jpg");
        ThumbnailImageResource  thumbnail=new ThumbnailImageResource(image.getResource(),40);//根据原图进行压缩
        add(new LightboxLink("link", image).add(new Image("image", thumbnail)));

        /**
         *访问公共资源的例子
         * 例子中的六个图片在
         * core-web-resources
         *      resources
         *          custome
         *              lightbox2下
         *说明:原图片地址   和  缩略图片地址  需上传文件时 生成 且为可访问的资源,例如放在iis下的或孙哥开放的resource路径下面的这种
         *参数:add(new LightboxPanel(页面wicket:id 对应的名称, 原图片地址, 缩略图片地址, "plant"));
         *
         * 页面动态生成<wicket:container wicket:id="lightbox1"></wicket:container>这种标签本人暂且不会
         */
        // Image Set
        add(new LightboxPanel("lightbox1", "../../cus/lightbox2/image-1.jpg", "../../cus/lightbox2/thumb-1.jpg", "plant"));
        add(new LightboxPanel("lightbox2", "../../cus/lightbox2/image-2.jpg", "../../cus/lightbox2/thumb-2.jpg", "plant"));
        add(new LightboxPanel("lightbox3", "http://www.baidu.com/img/bd_logo1.png", "../../cus/lightbox2/thumb-3.jpg", "plant"));
    }
}
