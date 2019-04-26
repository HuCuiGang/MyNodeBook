package org.bear.front.service.impl;

import org.bear.bean.Item;
import org.bear.bean.ItemDesc;
import org.bear.front.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ItemServiceImpl implements ItemService {

     private Logger LOGGER=LoggerFactory.getLogger(ItemServiceImpl.class);

    public static final String ITEM_URL="http://manage.xxkj.cn/rest/rpc/item/";

    public static final String ITEM_DESC_URL="http://manage.xxkj.cn/rest/rpc/item/desc/";

   // @Autowired
   // private ItemRpcService itemRpcService;

    /*
    @Override
    public Item queryItemById(Long itemId) {

        if(StringUtils.isEmpty(itemId)){
            LOGGER.info("传入的商品id不能为空!");
            return null;
        }
        LOGGER.debug("查询的商品id为{}",itemId);
        String json = HttpClientUtil.doGet(ITEM_URL + itemId);

        Result result = JsonUtils.jsonToPojo(json, Result.class);
        if(result==null){
            LOGGER.error("后台服务器返回为null!");
            return null;
        }
        if(result.getStatus().equals("success")){
            LOGGER.debug("请求成功!");
            Item item= MyBeanUtils.copyBean(Item.class,result.getData());
            return item;
        }
        LOGGER.error("后台服务器发生错误，错误原因为{}",result.getMessage());
        return null;
    }
    */



    @Override
    public Item queryItemById(Long itemId) {

        if(StringUtils.isEmpty(itemId)){
            LOGGER.info("传入的商品id不能为空!");
            return null;
        }
        LOGGER.debug("查询的商品id为{}",itemId);
       // Item item = itemRpcService.queryItemById(itemId);
        return null;
    }

    @Override
    public ItemDesc queryItemDescById(Long itemId) {
        return null;
    }


}
