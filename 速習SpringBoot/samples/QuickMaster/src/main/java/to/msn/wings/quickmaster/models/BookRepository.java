package to.msn.wings.quickmaster.models;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Collection<Book> findByPublisher(String publisher);
    Collection<Book> findByPriceGreaterThan(int price);
    Collection<Book> findByTitleLike(String title);
    Collection<Book> findByTitleContaining(String title);
    Collection<Book> findByPublisherIn(Collection<String> publisher);
    Collection<Book> findByPriceBetween(int low, int high);
    Collection<Book> findByTitleIsNull();
    Collection<Book> findByPublishedBefore(LocalDate published);
    Collection<Book> findByPublisherNot(String publisher);
    Collection<Book> findByPublisherAndPriceGreaterThanEqual(String publisher, int price);
    Collection<Book> findByPublisherOrderByPublishedDesc(String publisher);
    Optional<Book> findTopByPublisherOrderByPublishedDesc(String publisher);
    Collection<Book> findTop3ByPublisherOrderByPublishedDesc(String publisher);
    Collection<BookTitleOnly> findByPublisherOrderByPrice(String publisher);
    Collection<BookTitleOnly> findDistinctByPublisher(String publisher);
    Page<Book> findByPublisher(String publisher, Pageable pageable);
    @Query("SELECT b FROM Book b WHERE b.price < :price")
    //@Query(value="SELECT * FROM book WHERE price < :price", nativeQuery=true)
    Collection<Book> findByPrice(@Param("price") int price);
    @Query("SELECT b.publisher AS publisher, COUNT(b.isbn) AS number FROM Book b GROUP BY b.publisher")
    Collection<BookCount> groupByPublisher();
}