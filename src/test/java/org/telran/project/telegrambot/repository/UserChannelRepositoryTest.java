package org.telran.project.telegrambot.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.telran.project.telegrambot.model.UserChannel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserChannelRepositoryTest {

    @Autowired
    private UserChannelRepository userChannelRepository;


    @BeforeEach
    void setUp() {
        userChannelRepository.save(new UserChannel(1, 200));
        userChannelRepository.save(new UserChannel(1,300));
        userChannelRepository.save(new UserChannel(2, 100));
        userChannelRepository.save(new UserChannel(3,200));

    }

    @AfterEach
    void tearDown() {
        userChannelRepository.deleteAll();
    }

    @Test
    void canFindByUserId() {
        List<UserChannel> channelListByUserId = userChannelRepository.findByUserId(1);
        assertEquals(2, channelListByUserId.size());
    }

    @Test
    void canFindByUserIdAndChannelId() {
        UserChannel byUserIdAndChannelId = userChannelRepository.findByUserIdAndChannelId(2, 100);
        assertNotNull(byUserIdAndChannelId);
    }

    @Test
    void WhenExistsByUserIdThenTrue() {
        boolean existsByUserId = userChannelRepository.existsByUserId(2);
        assertEquals(true, existsByUserId);

    }
}