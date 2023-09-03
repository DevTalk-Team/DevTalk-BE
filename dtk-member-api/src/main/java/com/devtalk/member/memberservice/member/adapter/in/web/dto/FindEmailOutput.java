package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindEmailOutput {
    String email;

    public FindEmailOutput(String email) {
        this.email = email;
    }
}
