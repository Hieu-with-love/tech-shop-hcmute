package com.hcmute.tech_shop.builder;

import java.util.List;

public class ProductFilterBuilder {
    private String categoryName;
    private List<String> brandNames;
    private List<String> rams;
    private Long minPrice;
    private Long maxPrice;

    private ProductFilterBuilder(Builder builder) {
        this.categoryName = builder.categoryName;
        this.brandNames = builder.brandNames;
        this.rams = builder.rams;
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;
    }

    public static class Builder {
        private String categoryName;
        private List<String> brandNames;
        private List<String> rams;
        private Long minPrice;
        private Long maxPrice;

        public Builder setCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder setBrandNames(List<String> brandNames) {
            this.brandNames = brandNames;
            return this;
        }

        public Builder setRams(List<String> rams) {
            this.rams = rams;
            return this;
        }

        public Builder setMinPrice(Long minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public Builder setMaxPrice(Long maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public ProductFilterBuilder build() {
            return new ProductFilterBuilder(this);
        }
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<String> getBrandNames() {
        return brandNames;
    }

    public List<String> getRams() {
        return rams;
    }

    public Long getMinPrice() {
        return minPrice;
    }

    public Long getMaxPrice() {
        return maxPrice;
    }

}


