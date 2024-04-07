package by.grsu.schedule.gateway.grsu.dto;

import lombok.Data;

@Data
public class GrsuResponseWrapper<T> {
    T items;
}
