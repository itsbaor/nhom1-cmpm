//package com.nhom1.socialmedia.exception;
//
//import com.nhom1.socialmedia.model.dto.ApiResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Xử lý mọi lỗi RuntimeException
//    @ExceptionHandler(value = Exception.class)
//    ResponseEntity<ApiResponse<Object>> handlingRuntimeException(RuntimeException exception) {
//        ApiResponse<Object> apiResponse = ApiResponse.builder()
//                .code(ErrorCode.UNCATEGORYZED_EXCEPTION.getCode())
//                .message(ErrorCode.UNCATEGORYZED_EXCEPTION.getMessage())
//                .build();
//
//        return ResponseEntity.badRequest().body(apiResponse);
//    }
//
//    // Xử lý AppException (bao gồm lỗi từ ErrorCode)
//    @ExceptionHandler(value = AppException.class)
//    public ResponseEntity<ApiResponse<Object>> handlingAppException(AppException exception) {
//        ErrorCode errorCode = exception.getErrorCode();
//
//        ApiResponse<Object> apiResponse = ApiResponse.builder()
//                .code(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//
//        return ResponseEntity.badRequest().body(apiResponse);
//    }
//
//    // Xử lý MethodArgumentNotValidException khi có lỗi validation
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    ResponseEntity<ApiResponse<Object>> handlingValidation(MethodArgumentNotValidException exception) {
//        // Lấy thông báo lỗi từ fieldError (sử dụng DefaultMessage nếu có)
//        String errorMessage = exception.getFieldError().getDefaultMessage();
//
//        // Tìm ErrorCode phù hợp
//        ErrorCode errorCode = ErrorCode.UNCATEGORYZED_EXCEPTION;
//        for (ErrorCode code : ErrorCode.values()) {
//            if (code.getMessage().equals(errorMessage)) {
//                errorCode = code;
//                break;
//            }
//        }
//
//        ApiResponse<Object> apiResponse = ApiResponse.builder()
//                .code(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//
//        return ResponseEntity.badRequest().body(apiResponse);
//
//    }
//    @ControllerAdvice
//    public class GlobalExceptionHandler1 {
//        @ExceptionHandler(AppException.class)
//        public ResponseEntity<?> handleAppException(AppException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//
//}
package com.nhom1.socialmedia.exception;

import com.nhom1.socialmedia.model.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý mọi Exception (bao gồm RuntimeException)
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Object>> handlingRuntimeException(Exception exception) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .code(ErrorCode.UNCATEGORYZED_EXCEPTION.getCode())
                .message(ErrorCode.UNCATEGORYZED_EXCEPTION.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Xử lý AppException (bao gồm lỗi từ ErrorCode)
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Object>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Xử lý MethodArgumentNotValidException khi có lỗi validation
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handlingValidation(MethodArgumentNotValidException exception) {
        // Lấy thông báo lỗi từ fieldError (sử dụng DefaultMessage nếu có)
        String errorMessage = exception.getFieldError().getDefaultMessage();

        // Tìm ErrorCode phù hợp
        ErrorCode errorCode = ErrorCode.UNCATEGORYZED_EXCEPTION;
        for (ErrorCode code : ErrorCode.values()) {
            if (code.getMessage().equals(errorMessage)) {
                errorCode = code;
                break;
            }
        }

        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
