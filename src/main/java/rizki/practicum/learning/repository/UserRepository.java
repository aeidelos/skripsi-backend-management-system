package rizki.practicum.learning.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import rizki.practicum.learning.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,String>{
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
    @Query("SELECT DISTINCT user FROM User user, Practicum practicum " +
            "WHERE user <> practicum.coordinatorAssistance AND (user.name LIKE %?1%)")
    List<User> findCandidateCoordinatorAssistance(String query);

    @Query("SELECT DISTINCT user FROM User user, Practicum practicum " +
            "WHERE user.name LIKE %?1%")
    List<User> findByName(String query);
}
