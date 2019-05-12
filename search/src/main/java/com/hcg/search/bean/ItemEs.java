package com.hcg.search.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "product",type = "docs",shards = 3,replicas = 2)
public class ItemEs {

    public ItemEs() {
    }

    public ItemEs(Long id, String name, String title, Long cid, Long price, String image) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.cid = cid;
        this.price = price;
        this.image = image;
    }

    @Id
    private Long id;

    @Field(type = FieldType.text,analyzer = "ik_max_word")
    private String name;

    @Field(type=FieldType.text,analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Long)
    private Long cid;

    @Field(type = FieldType.Long)
    private Long price;

    @Field(index = false,type = FieldType.keyword)
    private String image;

}
