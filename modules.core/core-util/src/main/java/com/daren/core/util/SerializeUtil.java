package com.daren.core.util;

import org.apache.wicket.application.IClassResolver;
import org.ops4j.pax.wicket.util.serialization.PaxWicketObjectInputStream;
import org.ops4j.pax.wicket.util.serialization.PaxWicketObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Iterator;

/**
 * @类描述：redis 序列化应用类
 * @创建人：sunlf
 * @创建时间：2014-07-01 下午3:47
 * @修改人：
 * @修改时间：
 * @修改备注：
 */
public class SerializeUtil {
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            /*baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);*/
//			byte[] bytes = baos.toByteArray();
            baos = new ByteArrayOutputStream();
            PaxWicketObjectOutputStream devModeOS = new PaxWicketObjectOutputStream(baos);

            devModeOS.writeObject(object);
            devModeOS.flush();
            devModeOS.close();
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Object unserialize(byte[] bytes) {
        /*ClassLoader curThreadCls=Thread.currentThread().getContextClassLoader();
        ClassLoader cls=SerializeUtil.class.getClassLoader();
        if(curThreadCls!=cls)
            Thread.currentThread().setContextClassLoader(cls);
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			
			return  ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;*/
        IClassResolver resolver = new IClassResolver() {
            @Override
            public Class<?> resolveClass(String classname)
                    throws ClassNotFoundException {
                ClassLoader classLoader = getClass().getClassLoader();
                return classLoader.loadClass(classname);
            }

            @Override
            public Iterator<URL> getResources(String name) {
                throw new UnsupportedOperationException("This method should NOT BE CALLED!");
            }


            @Override
            public ClassLoader getClassLoader() {
                throw new UnsupportedOperationException("This method should NOT BE CALLED!");
            }
        };

        ByteArrayInputStream roBAIS = new ByteArrayInputStream(bytes);
        PaxWicketObjectInputStream roOIS = null;
        try {
            roOIS = new PaxWicketObjectInputStream(roBAIS, resolver);
            return roOIS.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
