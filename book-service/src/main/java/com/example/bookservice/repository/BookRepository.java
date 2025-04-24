package com.example.bookservice.repository;

import com.example.bookservice.entity.Book;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("""
    select book
    from Book book
    left join fetch book.user user
    where book.id = :id
    """)
    Optional<Book> findByIdJoinFetchUser(Long id);

    @Query("""
        select book
        from Book book
        join fetch book.author author
    """)
    List<Book> findAllJoinFetchAuthor(Pageable pageable);

    @Query("""
        select book
        from Book book
        join fetch book.author author
        where lower(book.title) like lower(concat('%', :title,'%'))
    """)
    List<Book> findAllByTitleJoinFetchAuthor(String title, Pageable pageable);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("""
        select book
        from Book book
        where book.user is null
        and book.id = :id
    """)
    Optional<Book> findByIdAndUserIsNullWithOptimisticLock(Long id);
}
