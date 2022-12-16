package br.com.giulianabezerra.tutorialspringdatapagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TutorialSpringDataPaginationApplication {

  public static void main(String[] args) {
    SpringApplication.run(TutorialSpringDataPaginationApplication.class, args);
  }

}

record Post(Long id, String content, String author) {

}

interface PostRepository extends PagingAndSortingRepository<Post, Long> {

}

@Service
class PostService {
  PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Page<Post> listPosts(Pageable pageable) {
    return postRepository.findAll(pageable);
  }
}

@RestController
@RequestMapping("/posts")
class PostController {
  @Autowired
  PostService postService;

  @GetMapping
  public List<Post> listPosts(Pageable pageable) {
    return postService.listPosts(pageable).getContent();
  }
}