package com.example.timelist.beans;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Group {
    private UUID groupId;

    @Pattern(regexp = "[A-Z]{2}-\\d{2}")
    private String name;
    private List<String> studentIds;
}
