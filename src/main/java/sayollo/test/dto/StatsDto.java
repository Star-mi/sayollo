package sayollo.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {
    String filterType;
    int impressions;
    int adRequests;
    double fillRate;
}
