package com.wavy.o2o.service.impl;

import com.wavy.o2o.dao.ProductSellDailyDao;
import com.wavy.o2o.entity.ProductSellDaily;
import com.wavy.o2o.service.IProductSellDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by WavyPeng on 2018/7/22.
 */
@Service
public class ProductSellDailyServiceImpl implements IProductSellDailyService {

    private static final Logger log = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);

    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Override
    public void dailyCalculate() {
//        System.out.println("Quartz跑起来啦");
        log.info("Quartz running");
        // 统计在tb_user_product_map里产生销量的每个店铺的各件商品的日销量
        productSellDailyDao.insertProductSellDaily();
        // 统计余下商品的日销量，设置为0（当日未销售）
        productSellDailyDao.insertDefaultProductSellDaily();
    }

    @Override
    public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDailyCondition,
                                                       Date beginTime, Date endTime) {
        return productSellDailyDao.queryProductSellDailyList(productSellDailyCondition,beginTime,endTime);
    }
}
