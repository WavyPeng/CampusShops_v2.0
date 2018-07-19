package com.wavy.o2o.dao;

import com.wavy.o2o.entity.ProductSellDaily;
import com.wavy.o2o.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by WavyPeng on 2018/7/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductSellDailyDaoTest {
    @Autowired
    private ProductSellDailyDao productSellDailyDao;

//    @Test
//    public void testAInsertProductSellDaily()throws Exception{
//        // 创建商品日销量统计
//        int num = productSellDailyDao.insertProductSellDaily();
//        assertEquals(0,num);
//    }

    @Test
    public void testQueryProductSellDaily()throws Exception{
        ProductSellDaily productSellDaily = new ProductSellDaily();
        Shop shop = new Shop();
        shop.setShopId(29L);
        productSellDaily.setShop(shop);
        List<ProductSellDaily> productSellDailyList = productSellDailyDao.queryProductSellDailyList(productSellDaily,null,null);
        assertEquals(2,productSellDailyList.size());
    }
}
