package com.example.timelist.controller;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CreatedResponseStudent {
    UUID studentId;
}
