package by.grsu.schedule.service.gateway.grsu.dto;

import lombok.Data;

@Data
public class GrsuResponseWrapper<T> {
    T items;
}
