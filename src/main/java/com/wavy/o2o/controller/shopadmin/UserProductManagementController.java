package com.wavy.o2o.controller.shopadmin;

import com.wavy.o2o.dto.EchartSeries;
import com.wavy.o2o.dto.EchartXAxis;
import com.wavy.o2o.dto.UserProductMapDto;
import com.wavy.o2o.entity.Product;
import com.wavy.o2o.entity.ProductSellDaily;
import com.wavy.o2o.entity.Shop;
import com.wavy.o2o.entity.UserProductMap;
import com.wavy.o2o.service.IProductSellDailyService;
import com.wavy.o2o.service.impl.IUserProductMapService;
import com.wavy.o2o.util.TypeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by WavyPeng on 2018/7/23.
 */
@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {
    @Autowired
    private IUserProductMapService userProductMapService;
    @Autowired
    private IProductSellDailyService productSellDailyService;

    @RequestMapping(value = "/listuserproductmapsbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listUserProductMapsByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取分页信息
        int pageIndex = TypeUtil.getInt(request,"pageIndex");
        int pageSize = TypeUtil.getInt(request,"pageSize");
        // 获取当前的店铺信息
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        // 空值校验，确保shopId不为空
        if(pageIndex>=0 && pageSize>=0 && currentShop!=null && currentShop.getShopId()!=null){
            // 添加查询条件
            UserProductMap userProductMapCondition = new UserProductMap();
            userProductMapCondition.setShop(currentShop);
            String productName = TypeUtil.getString(request,"productName");
            if(productName!=null){
                // 若前端想按照商品名模糊查询，则传入productName
                Product product = new Product();
                product.setProductName(productName);
                userProductMapCondition.setProduct(product);
            }

            // 根据传入的查询条件获取该店铺的商品销售情况
            UserProductMapDto ud = userProductMapService.listUserProductMap(userProductMapCondition,pageIndex,pageSize);
            modelMap.put("userProductMapList",ud.getUserProductMapList());
            modelMap.put("count",ud.getCount());
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty info");
        }
        return modelMap;
    }

    @RequestMapping(value = "/listproductselldailyinfobyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listProductSellDailyInfoByShop(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取当前店铺信息
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        // 空值校验，主要确保shopId不为空
        if((currentShop!=null)&&(currentShop.getShopId()!=null)){
            // 添加查询条件
            ProductSellDaily productSellDailyCondition = new ProductSellDaily();
            productSellDailyCondition.setShop(currentShop);
            Calendar calendar = Calendar.getInstance();
            // 获取昨天的日期
            calendar.add(Calendar.DATE,-1);
            Date endTime = calendar.getTime();
            // 获取七天前的日期
            calendar.add(Calendar.DATE,-6);
            Date beginTime = calendar.getTime();
            // 根据传入的查询条件获取该店铺的商品销售情况
            List<ProductSellDaily> productSellDailyList = productSellDailyService.listProductSellDaily(productSellDailyCondition,beginTime,endTime);
            // 指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 商品名列表，保证唯一性
            HashSet<String> legendData = new HashSet<>();
            // x轴数据
            HashSet<String> xData = new HashSet<>();
            // 定义series
            List<EchartSeries> series = new ArrayList<>();
            // 日销量列表
            List<Integer> totalList = new ArrayList<>();
            // 当前商品名，默认为空
            String currentProductName = "";
            for(int i=0;i<productSellDailyList.size();i++){
                ProductSellDaily productSellDaily = productSellDailyList.get(i);
                // 去重
                legendData.add(productSellDaily.getProduct().getProductName());
                xData.add(sdf.format(productSellDaily.getCreateTime()));
                if(!currentProductName.equals(productSellDaily.getProduct().getProductName())
                        && !currentProductName.isEmpty()){
                    EchartSeries es = new EchartSeries();
                    es.setName(currentProductName);
                    es.setData(totalList.subList(0,totalList.size()));
                    series.add(es);
                    // 重置totalList
                    totalList = new ArrayList<>();
                    // 变换下currentProductId为当前的productId
                    currentProductName = productSellDaily.getProduct().getProductName();
                    // 继续添加新的值
                    totalList.add(productSellDaily.getTotal());
                }else {
                    // 如果还是当前的productId则继续添加新值
                    totalList.add(productSellDaily.getTotal());
                    currentProductName = productSellDaily.getProduct().getProductName();
                }

                // 队列之末，需要将最后一个商品销量信息也添加上
                if(i==productSellDailyList.size()-1){
                    EchartSeries es = new EchartSeries();
                    es.setName(currentProductName);
                    es.setData(totalList.subList(0,totalList.size()));
                    series.add(es);
                }
            }
            modelMap.put("series",series);
            modelMap.put("legendData",legendData);
            // 拼接出xAxis
            List<EchartXAxis> xAxis = new ArrayList<>();
            EchartXAxis exa = new EchartXAxis();
            exa.setData(xData);
            xAxis.add(exa);
            modelMap.put("xAxis",xAxis);
            modelMap.put("success",true);
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","empty shopId");
        }
        return modelMap;
    }
}
