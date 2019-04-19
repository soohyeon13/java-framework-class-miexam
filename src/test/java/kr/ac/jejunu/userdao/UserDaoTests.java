package kr.ac.jejunu.userdao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class UserDaoTests {
    private UserDao userDao;

    @Before
    public void setup() {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao =applicationContext.getBean("userDao",UserDao.class);
    }

    @Test
    public void testGet() throws SQLException, ClassNotFoundException {
        Long id = 1l;
        String name = "허윤호";
        String password = "1234";
        User user = userDao.get(id);
        assertThat(user.getId(), is(id));
        assertThat(user.getName(), is(name));
        assertThat(user.getPassword(), is(password));
    }
    @Test
    public void testAdd() throws SQLException, ClassNotFoundException {
        String name = "헐크";
        String password = "1111";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Long id = userDao.add(user);
        User resultSet = userDao.get(id);
        assertThat(resultSet.getId(),is(id));
        assertThat(resultSet.getName(),is(name));
        assertThat(resultSet.getPassword(),is(password));
    }
    @Test
    public void testUpdata() throws SQLException, ClassNotFoundException {
        String name = "헐크";
        String password = "1111";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Long id = userDao.add(user);
        user.setId(id);

        String changedName = "허윤호";
        user.setName(changedName);
        String changedPassword="1111";
        user.setPassword(changedPassword);
        userDao.update(user);

        User resultset = userDao.get(id);
        assertThat(resultset.getId(),is(id));
        assertThat(resultset.getName(),is(changedName));
        assertThat(resultset.getPassword(),is(changedPassword));
    }
    @Test
    public void testDelect() throws SQLException, ClassNotFoundException {
        String name = "헐크";
        String password = "1111";
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Long id = userDao.add(user);

        userDao.delect(id);

        user = userDao.get(id);
        assertThat(user, nullValue());
    }
}
