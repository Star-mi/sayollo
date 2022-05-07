package sayollo.test.service;

import org.springframework.http.ResponseEntity;
import sayollo.test.dto.RequestDto;
import sayollo.test.dto.StatsDto;

public interface IAppService {
    String getAd(RequestDto requestDto);

    void impression(RequestDto requestDto);

    StatsDto getStats(String filterType, String filter);
}
