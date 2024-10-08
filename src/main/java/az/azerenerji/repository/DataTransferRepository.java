package az.azerenerji.repository;

import az.azerenerji.model.DataTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataTransferRepository extends JpaRepository<DataTransfer,Long> {

}
