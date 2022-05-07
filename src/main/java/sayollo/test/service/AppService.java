package sayollo.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sayollo.test.dto.RequestDto;
import sayollo.test.dto.StatsDto;
import sayollo.test.exception.models.FilterTypeException;
import sayollo.test.exception.models.SDKVersionNotFoundException;
import sayollo.test.exception.models.UserNotFoundException;
import sayollo.test.model.SDKVersion;
import sayollo.test.model.User;
import sayollo.test.repo.SDKVersionRepository;
import sayollo.test.repo.UserRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


@Service
public class AppService implements IAppService {
    private static final String URL = "https://6u3td6zfza.execute-api.us-east-2.amazonaws.com/prod/ad/vast";
    UserRepository userRepository;
    SDKVersionRepository sdkVersionRepository;


    @Autowired
    public AppService(UserRepository userRepository, SDKVersionRepository sdkVersionRepository) {
        this.userRepository = userRepository;
        this.sdkVersionRepository = sdkVersionRepository;
    }

    @Override
    public String getAd(RequestDto requestDto) {
        RestTemplate restTemplate = new RestTemplate();
        String response = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
            RequestEntity<String> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI(URL));
            ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
            response = responseEntity.getBody();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        saveUserAdInformation(requestDto.getUserName());
        saveSDKVersionAdInformation(requestDto.getSdkVersion());
        return response;
    }

    private void saveSDKVersionAdInformation(String title) {
        SDKVersion sdkVersion = sdkVersionRepository.findById(title).orElse(null);
        if (sdkVersion == null) {
            sdkVersionRepository.save(new SDKVersion(title, 1, 0));
        } else {
            int numberOfRequests = sdkVersion.getAdVolume() + 1;
            sdkVersion.setAdVolume(numberOfRequests);
            sdkVersionRepository.save(sdkVersion);
        }
    }

    private void saveUserAdInformation(String userName) {
        User user = userRepository.findById(userName).orElse(null);
        if (user == null) {
            userRepository.save(new User(userName, 1, 0));
        } else {
            int numberOfRequests = user.getAdVolume() + 1;
            user.setAdVolume(numberOfRequests);
            userRepository.save(user);
        }
    }

    @Override
    public void impression(RequestDto requestDto) {
        saveUserImpressionInformation(requestDto.getUserName());
        saveSDKVersionImpressionInformation(requestDto.getSdkVersion());
    }

    private void saveSDKVersionImpressionInformation(String title) {
        SDKVersion sdkVersion = sdkVersionRepository.findById(title).orElse(null);
        if (sdkVersion == null) {
            sdkVersionRepository.save(new SDKVersion(title, 0, 1));
        } else {
            int numberOfRequests = sdkVersion.getImpressionsVolume() + 1;
            sdkVersion.setImpressionsVolume(numberOfRequests);
            sdkVersionRepository.save(sdkVersion);
        }
    }

    private void saveUserImpressionInformation(String userName) {
        User user = userRepository.findById(userName).orElse(null);
        if (user == null) {
            userRepository.save(new User(userName, 0, 1));
        } else {
            int numberOfRequests = user.getImpressionsVolume() + 1;
            user.setImpressionsVolume(numberOfRequests);
            userRepository.save(user);
        }
    }

    @Override
    public StatsDto getStats(String filterType, String filter) {
        if (filterType.equalsIgnoreCase("user")) {
            User user = userRepository.findById(filter).orElseThrow(() -> new UserNotFoundException(filter));
            double fillRate = 0;
            if (user.getAdVolume() != 0) {
                fillRate = 1.0 * user.getImpressionsVolume() / user.getAdVolume();
            }
            return new StatsDto(
                    filterType+" - "+filter,
                    user.getImpressionsVolume(),
                    user.getAdVolume(),
                    fillRate);
        }

        if (filterType.equalsIgnoreCase("sdkVersion")) {
            SDKVersion sdkVersion = sdkVersionRepository.findById(filter).orElseThrow(() -> new SDKVersionNotFoundException(filter));
            double fillRate = 0;
            if (sdkVersion.getAdVolume() != 0) {
                fillRate = 1.0 * sdkVersion.getImpressionsVolume() / sdkVersion.getAdVolume();
            }
            return new StatsDto(
                    filterType+" - "+filter,
                    sdkVersion.getImpressionsVolume(),
                    sdkVersion.getAdVolume(),
                    fillRate);
        }
        throw new FilterTypeException(filterType);
    }
}
