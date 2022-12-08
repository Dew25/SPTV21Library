

package manager;

import entity.Author;
import entity.Book;
import entity.History;
import entity.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class BaseDataManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("SPTV21LibraryPU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();
    
    public void saveBooks(List<Book> books){
        tx.begin();
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                if(book.getId() == null){
                    for (int j = 0; j < book.getAuthors().size(); j++) {
                        Author author = book.getAuthors().get(j);
                        if(author.getId() == null){
                            em.persist(author);
                        }else{
                            em.merge(author);
                        }
                    }
                    em.persist(book);
                }else{
                    em.merge(book);
                }
            }
        tx.commit();
    }
    public List<Book> loadBooks() {
        try {
            return em.createQuery("SELECT b FROM Book b")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public void saveReaders(List<Reader> readers){
        tx.begin();
            for (int i = 0; i < readers.size(); i++) {
                Reader reader = readers.get(i);
                if(reader.getId() == null){
                    em.persist(reader);
                }else{
                    em.merge(reader);
                }
            }
        tx.commit();
    }
    public List<Reader> loadReaders() {
        try {
            return em.createQuery("SELECT r FROM Reader r")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    public void saveHistories(List<History> histories){
        tx.begin();
            for (int i = 0; i < histories.size(); i++) {
                History history = histories.get(i);
                if(history.getId() == null){
                    em.persist(history);
                }else{
                    em.merge(history);
                }
            }
        tx.commit();
    }
    public List<History> loadHistories() {
        try {
            return em.createQuery("SELECT h FROM History h")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
