package com.offnine.carten.modal;

import com.offnine.carten.domain.PaymentStatus;

import lombok.Data;


@Data
public class PaymentDetails {

    private String paymentId;

    private String esewaLinkedReferenceId;

    private String esewaLinkStatus;

    private PaymentStatus status;

}
