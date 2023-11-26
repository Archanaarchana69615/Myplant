package com.example.library.repository;

import com.example.library.model.WalletHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WalletHistoryRepository extends JpaRepository<WalletHistory,Long> {

    WalletHistory findById(long id);

    WalletHistory save(WalletHistory walletHistory);

    @Query(value = "select * from wallet_history where wallet_id=:id",nativeQuery = true)
    List<WalletHistory> findAllById(@Param("id") long id);

    List<WalletHistory> findAllByWalletId(long walletId);

}
