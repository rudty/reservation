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
public class RoomRepositoryTest {

    @Autowired
    RoomRepository roomRepository;

    @Test
    public void 없는방() {
        Assert.assertEquals(roomRepository.getRoomSN("테스트123"), Optional.empty());
    }

    @Test
    public void 있는방() {
        Assert.assertEquals(roomRepository.getRoomSN("테스트"), Optional.of(1L));
    }
}
