package org.rudty.reservation.reservation.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 없는유저() {
        Assert.assertEquals(userRepository.getUserSN("테스트123"), Optional.empty());
    }

    @Test
    public void 있는유저() {
        Assert.assertEquals(userRepository.getUserSN("테스트 유저"),Optional.of(1));
    }

}
