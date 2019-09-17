package null.product.service.impl;

import null.product.entity.ProductsEntity;
import null.product.mapper.ProductsMapper;
import null.product.service.IProductsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author zhuyunlong2018
 * @since 2019-09-16
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, ProductsEntity> implements IProductsService {

}
