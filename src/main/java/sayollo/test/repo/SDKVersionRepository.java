package sayollo.test.repo;

import org.springframework.data.repository.CrudRepository;
import sayollo.test.model.SDKVersion;

public interface SDKVersionRepository extends CrudRepository<SDKVersion, String> {
}
