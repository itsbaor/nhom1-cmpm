package Demo_security.demo.exception;

import Demo_security.demo.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý mọi lỗi RuntimeException
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORYZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORYZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Xử lý AppException (bao gồm lỗi từ ErrorCode)
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Xử lý MethodArgumentNotValidException khi có lỗi validation (Lỗi từ các annotation như @Size, @NotNull,...)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        // Lấy thông báo lỗi từ fieldError (sử dụng DefaultMessage nếu có)
        String errorMessage = exception.getFieldError().getDefaultMessage();

        // Lấy ErrorCode từ thông báo lỗi (Username_INVALID, PASSWORD_INVALID...)
        ErrorCode errorCode = ErrorCode.valueOf(errorMessage);

        // Tạo ApiResponse và trả về lỗi
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
