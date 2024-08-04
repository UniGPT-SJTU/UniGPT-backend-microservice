package com.unigpt.plugin.serviceImpl;

import com.unigpt.plugin.Repository.PluginRepository;
import com.unigpt.plugin.Repository.UserRepository;
import com.unigpt.plugin.client.BotServiceClient;
import com.unigpt.plugin.dto.*;
import com.unigpt.plugin.model.Plugin;
import com.unigpt.plugin.model.User;
import com.unigpt.plugin.service.PluginService;
import com.unigpt.plugin.utils.PaginationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PluginServiceImpl implements PluginService {
    private final PluginRepository pluginRepository;
    private final UserRepository userRepository;
    private final BotServiceClient botServiceClient;

    public PluginServiceImpl(
            PluginRepository pluginRepository,
            UserRepository userRepository,
            BotServiceClient botServiceClient) {
        this.pluginRepository = pluginRepository;
        this.userRepository = userRepository;
        this.botServiceClient = botServiceClient;
    }


    public ResponseDTO createPlugin(PluginInfoDTO dto, Integer userid) throws Exception {
        User user = userRepository.findByTrueId(userid)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userid));

//        TODO: Connect to Plugin serverless
//        // 构建目标文件路径
//        String directoryPath = "src/main/resources/" + user.getAccount();
//        String filePath = directoryPath + "/" + dto.getName() + ".py";
//
//        // 判断文件是否存在，如果存在则抛出异常
//        if (Files.exists(Paths.get(filePath))) {
//            return new ResponseDTO(false, "Plugin already exists");
//        }
//
//        // 创建目录
//        Path path = Paths.get(directoryPath);
//        Files.createDirectories(path);
//
//        // 将code字段的内容写入到文件中
//        Path file = Paths.get(filePath);
//        Files.writeString(file, dto.getCode(), StandardOpenOption.CREATE);

        String filePath = "";
        Plugin plugin = new Plugin(dto, user, filePath);
        pluginRepository.save(plugin);

        // Call botServiceClient to create a plugin
        ResponseEntity<ResponseDTO> response =  botServiceClient.createPlugin(plugin.getId(), new PluginEditInfoDTO(plugin));
        // No need for undo operation since plugin is not created in pluginRepository yet
//        if(response.getStatusCode().isError()){
//            throw new Exception("Failed to create plugin in Bot Microservice");
//        }
        if(!Objects.requireNonNull(response.getBody()).getOk()){
            throw new Exception("Failed to create plugin in Bot Microservice");
        }

        return new ResponseDTO(true, "Create plugin successfully");
    }

    public GetPluginsOkResponseDTO getPlugins(String q, String order, Integer page, Integer pageSize) {
        List<PluginBriefInfoDTO> plugins;
        if (order.equals("latest")) {
            plugins = pluginRepository.findAllByOrderByIdDesc()
                    .stream()
                    .filter(plugin -> q.isEmpty() || plugin.getName().contains(q))
                    .filter(plugin -> plugin.getIsPublished())
                    .map(plugin -> new PluginBriefInfoDTO(plugin.getId(), plugin.getName(), plugin.getDescription(), plugin.getAvatar()))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Invalid order parameter");
        }

        return new GetPluginsOkResponseDTO(plugins.size(), PaginationUtils.paginate(plugins, page, pageSize));
    }

    public PluginDetailInfoDTO getPluginInfo(Integer pluginid, Integer userid){

        Plugin plugin = pluginRepository.findById(pluginid)
                .orElseThrow(() -> new NoSuchElementException("Plugin not found for ID: " + pluginid));

        User user = userRepository.findByTrueId(userid)
                .orElseThrow(() -> new NoSuchElementException("User not found for ID: " + userid));

        if (!plugin.getIsPublished() && plugin.getCreator() != user) {
            // 如果plugin未发布且请求用户不是plugin的创建者，则抛出异常
            throw new NoSuchElementException("Plugin not published for ID: " + pluginid);
        }
        return new PluginDetailInfoDTO(plugin, user);
    }
//
//    @Override
//    public PluginEditInfoDTO getPluginEditInfo(Integer id, String token) {
//        Plugin plugin = pluginRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("Plugin not found for ID: " + id));
//
//        User user = authService.getUserByToken(token);
//
//        if (plugin.getCreator() != user) {
//            // 如果请求用户不是plugin的创建者，则抛出异常
//            throw new NoSuchElementException("Permission denied for ID: " + id);
//        }
//        return new PluginEditInfoDTO(plugin, user);
//    }
//
//
//    @Override
//    public ResponseDTO testCreatePlugin(PluginCreateTestDTO dto, String token) throws Exception {
//        User user = authService.getUserByToken(token);
//
//        // 构建目标文件路径
//        String directoryPath = "src/main/resources/test/" + user.getAccount();
//        String filePath = directoryPath + "/" + dto.getName() + ".py";
//
//        // 判断文件是否存在，如果存在则清除
//        if (Files.exists(Paths.get(filePath))) {
//            Files.delete(Paths.get(filePath));
//        }
//
//        // 创建目录
//        Path path = Paths.get(directoryPath);
//        Files.createDirectories(path);
//
//        // 将code字段的内容写入到文件中
//        Path file = Paths.get(filePath);
//        Files.writeString(file, dto.getCode(), StandardOpenOption.CREATE);
//
//        // 调用dockerService执行测试
//        String output = dockerService.invokeFunction("test/" + user.getAccount(), dto.getName(), "handler", dto.getParamsValue());
//
//        // 解析output为JSONObject来检查是否有error字段
//        JSONObject jsonResponse = new JSONObject(output);
//        boolean isSuccess = !jsonResponse.has("error");
//
//        // 删除测试文件
//        Files.delete(Paths.get(filePath));
//
//        return new ResponseDTO(isSuccess, output);
//    }
}
