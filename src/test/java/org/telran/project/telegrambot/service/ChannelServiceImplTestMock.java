package org.telran.project.telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telran.project.telegrambot.model.Channel;
import org.telran.project.telegrambot.repository.ChannelRepository;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChannelServiceImplTestMock {


    private ChannelService channelService;

    @Mock
    private ChannelRepository channelRepository;

    @BeforeEach
    void setUp() {
        channelService = new ChannelServiceImpl(channelRepository);
    }

    @Test
    void canGetChannel() {
        when(channelRepository.existsByChannelId(100)).thenReturn(true);
        channelService.getChannel(100);
        verify(channelRepository).findByChannelId(100);
    }

    @Test
    void canCreateChannelWithChannel() {
        Channel channel = new Channel("channel", 100);

        channelService.createChannel(channel);

        ArgumentCaptor<Channel> channelArgumentCaptor = ArgumentCaptor.forClass(Channel.class);

        verify(channelRepository).save(channelArgumentCaptor.capture());

        Channel capturedChannel = channelArgumentCaptor.getValue();

        assertEquals(channel, capturedChannel);
    }

    @Test
    void canDeleteChannel() {
        Channel channel = new Channel("channel", 100);

        when(channelRepository.existsByChannelId(100)).thenReturn(true);
        when(channelService.getChannel(100)).thenReturn(channel);

        channelService.deleteChannel(100);

        ArgumentCaptor<Channel> channelArgumentCaptor = ArgumentCaptor.forClass(Channel.class);

        verify(channelRepository).delete(channelArgumentCaptor.capture());

        Channel capturedChannel = channelArgumentCaptor.getValue();

        assertEquals(channel, capturedChannel);
    }

    @Test
    void updateChannel() {
    }

    @Test
    void canListChannels() {
        channelService.listChannels();
        verify(channelRepository).findAll();
    }

    @Test
    void canCreateChannelWithConstructor() {
        channelService.createChannel(new Channel("channel", 100));

        ArgumentCaptor<Channel> channelArgumentCaptor = ArgumentCaptor.forClass(Channel.class);

        verify(channelRepository).save(channelArgumentCaptor.capture());

        Channel capturedChannel = channelArgumentCaptor.getValue();

        assertEquals("channel", capturedChannel.getName());
    }

    @Test
    void canChangeBotEnabledToTrue() {
        channelService.on(100);
        verify(channelRepository).changeIsBotEnabledToTrue(100);
    }

    @Test
    void canChangeBotEnabledToFalse() {
        channelService.off(100);
        verify(channelRepository).changeIsBotEnabledToFalse(100);
    }
}