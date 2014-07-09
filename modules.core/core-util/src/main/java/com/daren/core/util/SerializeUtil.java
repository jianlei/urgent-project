package com.daren.core.util;

import com.google.gson.Gson;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.Set;

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
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public static String serializeJson(Object object) {

        Gson mapper = new Gson();
        try {
            return mapper.toJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static <T> T unserializeJson(String json, Class cls) {

        Gson mapper = new Gson();
        try {
            return (T) mapper.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @SuppressWarnings("unchecked")
    public static <T> T unserialize(byte[] bytes) {

        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais) {
                Set<ClassLoader> lhs = new LinkedHashSet<ClassLoader>();

                {
                    // Keep a set if discovered class loaders
                    lhs.add(getClass().getClassLoader());
                }

                @Override
                protected Class<?> resolveClass(ObjectStreamClass desc)
                        throws ClassNotFoundException, IOException {

                    for (ClassLoader cl : lhs)
                        try {
                            Class<?> c = cl.loadClass(desc.getName());

                            // we found the class, so we can use its class loader,
                            // it is in the proper class space  if the uses constraints
                            // are set properly (and you're using bnd so you should be ok)

                            lhs.add(c.getClassLoader());

                            // The paranoid among us would check
                            // the serial uuid here ...
                            // long uuid = desc.getSerialVersionUID();
                            // Field field = c.getField("serialVersionUID");
                            // assert uuid == field.get(null)

                            return c;
                        } catch (Exception e) {
                            // Ignore
                        }

                    // Fallback (for void and primitives)
                    return super.resolveClass(desc);
                }
            };

            Object o = ois.readObject();
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
