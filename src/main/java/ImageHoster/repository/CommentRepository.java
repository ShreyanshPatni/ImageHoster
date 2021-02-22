package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CommentRepository {

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Comment createComment(Comment newComment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newComment);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
        }

        return  newComment;
    }

    public List<Comment> getAllCommentsOfImage(Image image) {
        EntityManager em = emf.createEntityManager();
        int imageId = image.getId();
        try {
            TypedQuery<Comment> query = em.createQuery("SELECT c FROM Comment c WHERE c.image =:image",
                                                        Comment.class)
                                                        .setParameter("image",image);
            return query.getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
    }
}
