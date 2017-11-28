package rizki.practicum.learning.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import rizki.practicum.learning.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User,String>{

}
