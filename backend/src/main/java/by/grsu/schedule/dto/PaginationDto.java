package by.grsu.schedule.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

@Data
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaginationDto {
    int page;
    int rowsPerPage;
    int totalPages;
    long totalElements;

    public static PaginationDto of(Page<?> page) {
        return PaginationDto.builder()
                .page(page.getNumber())
                .rowsPerPage(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
