package ma.ilias.taskifybe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDto<T> {
    public ResponseDto(String message, Boolean status) {
        this.message = message;
        this.status = status;
        this.data = null;
    }

    private String message;
    private Boolean status;
    private T data ;
}
