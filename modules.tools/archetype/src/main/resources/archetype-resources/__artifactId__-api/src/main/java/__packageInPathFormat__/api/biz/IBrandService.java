package ${package}.api.biz;

import ${package}.api.persistence.BrandBeanImpl;
import ${package}.api.persistence.IBrandBean;

import java.util.List;

/**
 * Created by Administrator on 14-4-4.
 */
public interface IBrandService {

    public BrandBeanImpl updateBrand(BrandBeanImpl message);

    public BrandBeanImpl putBrand(BrandBeanImpl message);

    BrandBeanImpl addBrand(BrandBeanImpl message);

    public BrandBeanImpl findBrand(String id);

    public void deleteBrand(String id);

    public List<BrandBeanImpl> getAllBrand();

    public String getAttachmentUrl(BrandBeanImpl message, String fileName);

    public void destroy();
}
