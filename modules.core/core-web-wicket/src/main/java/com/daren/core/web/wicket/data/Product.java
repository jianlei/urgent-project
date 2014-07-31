package com.daren.core.web.wicket.data;

/**
 * @类描述：prodcut
 * @创建人：sunlf
 * @创建时间：2014-04-30 下午2:07
 * @修改人：
 * @修改时间：
 * @修改备注：
 */

import org.apache.wicket.util.io.IClusterable;

import java.util.Date;

public class Product implements IClusterable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String desc;
    private Date date;
    private double price;

    private Vendor vendor;

    public Product(final int id, final String name, final String description, final double price) {
        this(id, name, description, price, new Vendor());
    }

    public Product(final int id, final String name, final String description, final double price, Vendor vendor) {
        this.id = id;
        this.name = name;
        this.desc = description;
        this.date = new Date();
        this.price = price;
        this.vendor = vendor;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.desc;
    }

    public Date getDate() {
        return this.date;
    }

    public double getPrice() {
        return this.price;
    }

    public Vendor getVendor() {
        return this.vendor;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static class Vendor implements IClusterable {
        private static final long serialVersionUID = 1L;

        private String name;

        public Vendor() {
            this("noname");
        }

        public Vendor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}