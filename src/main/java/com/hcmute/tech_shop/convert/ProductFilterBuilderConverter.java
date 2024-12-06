package com.hcmute.tech_shop.convert;

import com.hcmute.tech_shop.builder.ProductFilterBuilder;
import com.hcmute.tech_shop.entities.Product;
import com.hcmute.tech_shop.utils.MapUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProductFilterBuilderConverter {

    public ProductFilterBuilder toProductFilterBuilder(Map<String, Object> params) {
        ProductFilterBuilder productFilterBuilder = new ProductFilterBuilder.Builder()
                .setName(MapUtil.getObject(params,"name",String.class))
                .setCategoryName(MapUtil.getObject(params, "categoryName", String.class))
                .setBrandNames(MapUtil.getObject(params, "brandNames", List.class))
                .setRams(MapUtil.getObject(params, "rams", List.class))
                .setMinPrice(MapUtil.getObject(params, "minPrice", Long.class))
                .setMaxPrice(MapUtil.getObject(params, "maxPrice", Long.class))
                .build();
        return productFilterBuilder;
    }
}
