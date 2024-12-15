package com.example.payment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ProcessedOrderResponse {
    private Long orderId;
    private boolean success;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date paymentDate;

    public ProcessedOrderResponse(Long orderId, boolean success, Date paymentDate) {
        this.orderId = orderId;
        this.success = success;
        this.paymentDate = paymentDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "ProcessedOrderResponse{" +
                "orderId=" + orderId +
                ", success=" + success +
                ", paymentDate=" + paymentDate +
                '}';
    }
}
