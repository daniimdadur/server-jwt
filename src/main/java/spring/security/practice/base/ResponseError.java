package spring.security.practice.base;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError<T> {
    private int statusCode;
    private String message;
    private Object errors;
}
