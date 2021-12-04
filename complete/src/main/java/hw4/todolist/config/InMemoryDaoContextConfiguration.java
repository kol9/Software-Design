package hw4.todolist.config;

import hw4.todolist.dao.TodoListDao;
import hw4.todolist.dao.TodoListInMemoryDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nikolay Yarlychenko
 */
@Configuration
public class InMemoryDaoContextConfiguration {
    @Bean
    public TodoListDao todoListDao() {
        return new TodoListInMemoryDao();
    }
}
