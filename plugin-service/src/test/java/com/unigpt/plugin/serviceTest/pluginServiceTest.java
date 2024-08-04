package com.unigpt.plugin.serviceTest;



import com.unigpt.plugin.Repository.BotRepository;
import com.unigpt.plugin.Repository.PluginRepository;
import com.unigpt.plugin.Repository.UserRepository;
import com.unigpt.plugin.client.BotServiceClient;
import com.unigpt.plugin.dto.BotInfoDTO;
import com.unigpt.plugin.dto.PluginEditInfoDTO;
import com.unigpt.plugin.dto.PluginInfoDTO;
import com.unigpt.plugin.dto.ResponseDTO;
import com.unigpt.plugin.factory.dtoFactory;
import com.unigpt.plugin.model.Plugin;
import com.unigpt.plugin.model.User;
import com.unigpt.plugin.serviceImpl.PluginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class pluginServiceTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePlugin_Success() throws Exception {
        PluginRepository pluginRepository = Mockito.mock(PluginRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        BotServiceClient botServiceClient = Mockito.mock(BotServiceClient.class);
        PluginServiceImpl pluginServiceImpl = new PluginServiceImpl(pluginRepository, userRepository, botServiceClient);

        Integer userid = 1;
        User user = new User();
        PluginInfoDTO dto = dtoFactory.createPluginInfoDTO(1);
        Plugin plugin = new Plugin(dto, user, "");

        when(userRepository.findByTrueId(userid)).thenReturn(Optional.of(user));
        when(pluginRepository.save(plugin)).thenReturn(plugin);
        when(botServiceClient.createPlugin(plugin.getId(), new PluginEditInfoDTO(plugin)))
                .thenReturn(ResponseEntity.ok(new ResponseDTO(true, "Plugin created successfully")));

        ResponseDTO responseDTO = pluginServiceImpl.createPlugin(dto, userid);
        assertTrue(responseDTO.getOk());
    }
}
