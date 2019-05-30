package hello.repository;
import hello.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PlayerRepository extends JpaRepository<Player,Integer> {

}