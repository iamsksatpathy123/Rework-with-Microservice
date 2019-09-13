package com.userent.demo.repository;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.userent.demo.entity.Article;

@Transactional
@Repository
public class ArticleDAO implements IArticleDAO {
	@PersistenceContext	
	private EntityManager entityManager;	
	@Override
	public Article getArticleById(int articleId) {
		return entityManager.find(Article.class, articleId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> getAllArticles() {
		String hql = "FROM Article as atcl ORDER BY atcl.articleId DESC";
		return (List<Article>) entityManager.createQuery(hql).getResultList();
	}	
	@Override
	public void createArticle(Article article) {
		entityManager.persist(article);
	}
	@Override
	public void updateArticle(Article article) {
		Article artcl = getArticleById(article.getArticleId());
		artcl.setTitle(article.getTitle());
		artcl.setCategory(article.getCategory());
	    artcl.setDate(article.getDate());
		artcl.setMail(article.getMail());
		entityManager.flush();
	}
	@Override
	public void deleteArticle(int articleId) {
		entityManager.remove(getArticleById(articleId));
	}
	@Override
	public boolean articleExists(String title, String category, String mail, String date) {
		String hql = "FROM Article as atcl WHERE atcl.title =: title and atcl.category =: category and atcl.mail =: mail and atcl.date =: date";
		int count = entityManager.createQuery(hql).setParameter("title", title)
		              .setParameter("category", category).setParameter("mail", mail)
		              .setParameter("date", date).getResultList().size();
		return count > 0 ? true : false;
	}
}
