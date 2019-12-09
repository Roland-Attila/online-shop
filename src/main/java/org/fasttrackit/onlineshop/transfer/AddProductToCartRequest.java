package org.fasttrackit.onlineshop.transfer;

import javax.validation.constraints.NotNull;

public class AddProductToCartRequest {

    @NotNull
    private long productId;
    @NotNull
    private long customerId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "AddProductToCartRequest{" +
                "productId=" + productId +
                ", customerId=" + customerId +
                '}';
    }
}
