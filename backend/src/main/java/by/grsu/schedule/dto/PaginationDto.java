package by.grsu.schedule.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaginationDto {
    int page;
    int rowsPerPage;
    int totalPages;
    long totalElements;
}
