package me.lonewolf.conduitcore.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/18 23:09
 * @description TODO
 */

public class ConfigUtil {

    public static void init() {
        try {
            BuffConfig.init();
            SkillCoolDownConfig.init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void modifyJsonWithMap(File file, Map<String, Object> map) throws IOException {
        // 读取现有的 JSON 文件并将其解析为一个 JsonObject 对象
        FileReader reader = new FileReader(file);
        JsonObject configJson = new Gson().fromJson(reader, JsonObject.class);
        reader.close();

        // 遍历传入的 Map，将其键值对更新到 JsonObject 中
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            configJson.addProperty(key, value.toString());
        }

        // 保存修改后的 JSON 数据到文件
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(configJson);
        json = json.replace("\n", System.lineSeparator()); // 替换默认的换行符
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

}
