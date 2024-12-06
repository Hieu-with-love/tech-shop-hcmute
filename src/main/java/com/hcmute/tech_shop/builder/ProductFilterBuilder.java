package com.hcmute.tech_shop.builder;

import java.util.List;

public class ProductFilterBuilder {
    private String name;
    private String categoryName;
    private List<String> brandNames;
    private List<String> rams;
    private Long minPrice;
    private Long maxPrice;

    // Constructor private để đảm bảo chỉ sử dụng Builder
    private ProductFilterBuilder(Builder builder) {
        this.name = builder.name;
        this.categoryName = builder.categoryName;
        this.brandNames = builder.brandNames;
        this.rams = builder.rams;
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;
    }

    // Builder class
    public static class Builder {
        private String name;
        private String categoryName;
        private List<String> brandNames;
        private List<String> rams;
        private Long minPrice;
        private Long maxPrice;

        // Setter cho name
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

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

    // Getter methods
    public String getName() {
        return name;
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



