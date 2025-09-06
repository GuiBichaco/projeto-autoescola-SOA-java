package com.example.autoescola.repository;

import com.example.autoescola.entity.Instrutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    Page<Instrutor> findAllByAtivoTrue(Pageable pageable);

    @Query("SELECT i FROM Instrutor i WHERE i.ativo = true AND i.id NOT IN " +
            "(SELECT a.instrutor.id FROM Agendamento a WHERE a.dataHora = :dataHora AND a.cancelado = false)")
    List<Instrutor> findAvailableInstructors(@Param("dataHora") LocalDateTime dataHora);
}