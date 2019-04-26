package org.bear.admin.service.impl;

import org.bear.admin.service.ItemDescService;
import org.bear.admin.service.ItemService;
import org.bear.bean.Item;
import org.bear.bean.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    @Autowired
    private ItemDescService itemDescService;

    public void addItem(Item item, String desc) {

        if(item==null||StringUtils.isEmpty(desc)){
            return;
        }

        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        this.save(item);

        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setCreated(item.getCreated());
        itemDesc.setUpdated(item.getCreated());
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);
    }



    @Override
    public void updateItem(Item item, String desc) {

        if(item==null||StringUtils.isEmpty(desc)){
            return;
        }
        //设置修改时间
        item.setUpdated(new Date());
        this.update(item);

        //修改商品描述
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(item.getUpdated());
        itemDescService.update(itemDesc);


    }



}
