package az.azerenerji.repository;

import az.azerenerji.model.DataTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DataTransferRepository extends JpaRepository<DataTransfer,Long> {
    @Query("SELECT d FROM DataTransfer d WHERE d.createdTime >= :startDate AND d.createdTime <= :endDate")
    List<DataTransfer> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
