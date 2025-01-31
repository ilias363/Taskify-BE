package ma.ilias.taskifybe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomPageable<T> {
    private int currentPage;
    private int size;
    private long totalElements;
    private long totalPages;
    private List<T> content;
}
