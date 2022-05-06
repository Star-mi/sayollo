package sayollo.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    String sdkVersion;
    String sessionId;
    String platform;
    String userName;
    String countryCode;
}
