package com.hcg.search.Repository;

import com.hcg.search.bean.ItemEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemEsRepository extends ElasticsearchRepository<ItemEs,Long> {
    public List<ItemEs> findByPriceBetween(Integer price1,Integer price2);
    public Page<ItemEs> findByPriceBetween(Integer price1, Integer price2, Pageable pageable);
}
