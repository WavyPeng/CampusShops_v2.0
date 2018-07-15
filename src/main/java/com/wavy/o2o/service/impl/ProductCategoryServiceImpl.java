package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.ProductCategoryDao;
import com.wavy.o2o.dao.ProductDao;
import com.wavy.o2o.dto.ProductCategoryDto;
import com.wavy.o2o.entity.ProductCategory;
import com.wavy.o2o.enums.ProductCategoryStateEnum;
import com.wavy.o2o.exception.ProductCategoryOperationException;
import com.wavy.o2o.service.IProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements IProductCategoryService{
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 查询指定某个店铺下的所有商品类别信息
     * @param shopId
     * @return
     */
    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    /**
     * 批量添加商品种类
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    @Override
    @Transactional
    public ProductCategoryDto addProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException {
        if(productCategoryList!=null && productCategoryList.size()>0){
            try{
                int effectNum = productCategoryDao.bacthInsertProductCategory(productCategoryList);
                if(effectNum<=0){
                    throw new ProductCategoryOperationException("商品类别创建失败");
                }else{
                    return new ProductCategoryDto(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchAddProductCategory error: " + e.getMessage());
            }
        }else{
            return new ProductCategoryDto(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    /**
     * 将此类别下的商品里的类别id置为空，再删除掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    @Override
    @Transactional
    public ProductCategoryDto deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException {
        // 解除tb_product里的商品与该producategoryId的关联
        try{
            int effectNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectNum < 0) {
                throw new ProductCategoryOperationException("商品类别更新失败");
            }
        }catch (Exception e){
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
        }
        // 删除该productCategory
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败");
            } else {
                return new ProductCategoryDto(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
        }
    }
}