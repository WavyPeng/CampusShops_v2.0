package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.ProductDao;
import com.wavy.o2o.dao.ProductImgDao;
import com.wavy.o2o.dto.ImageDto;
import com.wavy.o2o.dto.ProductDto;
import com.wavy.o2o.entity.Product;
import com.wavy.o2o.entity.ProductImg;
import com.wavy.o2o.enums.ProductStateEnum;
import com.wavy.o2o.exception.ProductOperationException;
import com.wavy.o2o.service.IProductService;
import com.wavy.o2o.util.ImageUtil;
import com.wavy.o2o.util.PageUtil;
import com.wavy.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    /**
     * 添加商品信息以及图片处理
     * 1.处理缩略图，获取缩略图相对路径并赋值给product
     * 2.往tb_product写入商品信息，获取productId
     * 3.结合productId批量处理商品详情图
     * 4.将商品详情图列表批量插入tb_product_img中
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     */
    @Override
    @Transactional
    public ProductDto addProduct(Product product, ImageDto thumbnail, List<ImageDto> productImgList)
            throws ProductOperationException{
        // 空值判断
        if(product!=null && product.getShop()!=null && product.getShop().getShopId()!=null){
            // 给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // 默认为上架的状态
            product.setEnableStatus(1);
            // 若商品缩略图不为空则添加
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                // 创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败:" + e.toString());
            }
            // 若商品详情图不为空则添加
            if (productImgList != null && productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            return new ProductDto(ProductStateEnum.SUCCESS, product);
        } else {
            // 传参为空则返回空值错误信息
            return new ProductDto(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 查询商品列表并分页，可输入的条件有： 商品名（模糊），商品状态，店铺Id,商品类别
     * @param productCondition
     * @param pageIndex 页码
     * @param pageSize 每页显示记录数
     * @return
     */
    @Override
    public ProductDto getProductList(Product productCondition, int pageIndex, int pageSize) {
        // 将页码转换成数据库记录的行号
        int rowIndex = PageUtil.pageIndexToRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,pageIndex,pageSize);
        // 查询商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductDto pd = new ProductDto();
        pd.setProductList(productList);
        pd.setCount(count);
        return pd;
    }

    /**
     * 通过商品Id查询唯一的商品信息
     * @param productId
     * @return
     */
    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    /**
     * 修改商品信息以及图片处理
     * 1.若缩略图参数有值，则处理缩略图，若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
     * 2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
     * 3.将tb_product_img下面的该商品原先的商品详情图记录全部清除
     * 4.更新tb_product_img以及tb_product的信息
     *
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductDto modifyProduct(Product product, ImageDto thumbnail, List<ImageDto> productImgList)
            throws ProductOperationException {
        if(product!=null && product.getShop()!=null && product.getShop().getShopId()!=null){
            // 设置最新更新时间
            product.setLastEditTime(new Date());
            if(thumbnail!=null){ // 如果要更新缩略图
                Product tmpProduct = productDao.queryProductById(product.getProductId());
                if(tmpProduct.getImgAddr()!=null){ // 如果原缩略图不为空
                    // 删除原来的缩略图
                    ImageUtil.deleteFileOrPath(tmpProduct.getImgAddr());
                }
                // 添加新缩略图
                addThumbnail(product, thumbnail);
            }
            // 如果更新商品详情图，则将原先的删除并存入新的图片
            if(productImgList!=null && productImgList.size()>0){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgList);
            }
            try{
                // 更新商品
                int effectedNum = productDao.updateProduct(product);
                if(effectedNum<=0){
                    throw new ProductOperationException("更新商品信息失败");
                }
                return new ProductDto(ProductStateEnum.SUCCESS,product);
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败"+e.toString());
            }
        }else{
            return new ProductDto(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageDto thumbnail){
        // 获取图片存储路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 批量添加图片
     * @param product
     * @param productImgDtoList
     */
    private void addProductImgList(Product product, List<ImageDto> productImgDtoList){
        // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        // 遍历图片一次去处理，并添加进productImg实体类里
        for(ImageDto imageDto:productImgDtoList){
            String imgAddr = ImageUtil.generateNormalImg(imageDto, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        // 如果确实是有图片需要添加的，就执行批量添加操作
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
            }
        }
    }

    /**
     * 删除某商品下的所有详情图
     * @param productId
     */
    private void deleteProductImgList(long productId){
        // 根据productId获取原来的图片信息
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        // 删除原来的图片
        for(ProductImg productImg:productImgList){
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        // 删除数据库中的记录
        productImgDao.deleteProductImgByProductId(productId);
    }
}