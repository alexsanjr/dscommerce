package com.alexsanjr.dscommerce.dto;

import java.time.Instant;

public class PaymentDTO {
    private Long id;
    private Instant moment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }
}
