package sayollo.test.repo;

import org.springframework.data.repository.CrudRepository;
import sayollo.test.model.User;

public interface UserRepository extends CrudRepository<User, String> {
}
