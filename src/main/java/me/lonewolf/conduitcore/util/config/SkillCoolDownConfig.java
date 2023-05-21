package me.lonewolf.conduitcore.util.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.Window;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/20 17:29
 * @description TODO
 */

public class SkillCoolDownConfig {

    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDirectory(), "conduitcore/skillcooldown.json");

    private static final Map<String ,Object> DEFAULT_CONFIG = new HashMap<>();

    private static boolean enable;
    private static float offY;
    private static String format;
    private static String separator;
    private static boolean isCoordinateFromBottom;

    static {
        DEFAULT_CONFIG.put("enable", true);
        DEFAULT_CONFIG.put("offY", 88F);
        DEFAULT_CONFIG.put("format", "§7<skill> (§f<cooldown>s§7)");
        DEFAULT_CONFIG.put("separator", "§7 - ");
        DEFAULT_CONFIG.put("isCoordinateFromBottom", true);
    }

    public static void init() throws IOException {
        if (!CONFIG_FILE.exists()) {
            // 配置文件不存在，创建默认配置
            CONFIG_FILE.getParentFile().mkdirs();
            CONFIG_FILE.createNewFile();
            JsonObject defaultConfigJson = new JsonObject();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(defaultConfigJson);
            json = json.replace("\n", System.lineSeparator()); // 替换默认的换行符
            FileWriter writer = new FileWriter(CONFIG_FILE);
            writer.write(json);
            writer.close();
        }
        Map<String, Object> needModify = new HashMap<>();
        FileReader reader = new FileReader(CONFIG_FILE);
        JsonObject configJson = new Gson().fromJson(reader, JsonObject.class);
        reader.close();
        enable = (Boolean) getValueFromJson(configJson, "enable", Boolean.class, needModify);
        offY = (Float) getValueFromJson(configJson, "offY", Float.class, needModify);
        format = ((String) getValueFromJson(configJson, "format", String.class, needModify)).replace("&", "§");
        separator = ((String) getValueFromJson(configJson, "separator", String.class, needModify)).replace("&", "§");
        isCoordinateFromBottom = (Boolean) getValueFromJson(configJson, "isCoordinateFromBottom", Boolean.class, needModify);
        ConfigUtil.modifyJsonWithMap(CONFIG_FILE, needModify);
    }

    private static Object getValueFromJson(JsonObject jsonObject, String property, Class<?> targetType, Map<String, Object> needModify) {
        JsonElement jsonElement = jsonObject.get(property);
        if(jsonElement == null || jsonElement.isJsonNull()){
            Object defaultValue = DEFAULT_CONFIG.get(property);
            needModify.put(property, defaultValue);
            return defaultValue;
        }else {
            return new Gson().fromJson(jsonElement, targetType);
        }
    }

    public static void saveConfig() throws IOException {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("enable", enable);
        configMap.put("offY", offY);
        configMap.put("format", format);
        configMap.put("separator ", separator);
        configMap.put("isCoordinateFromBottom ", isCoordinateFromBottom);
        ConfigUtil.modifyJsonWithMap(CONFIG_FILE, configMap);
    }

    public static void setEnable(boolean enable) {
        SkillCoolDownConfig.enable = enable;
    }


    public static void setOffY(float offY) {
        SkillCoolDownConfig.offY = offY;
    }

    public static void setFormat(String format) {
        SkillCoolDownConfig.format = format.replace("&", "§");
    }

    public static void setSeparator(String separator) {
        SkillCoolDownConfig.separator = separator.replace("&", "§");
    }

    public static void setIsCoordinateFromBottom(boolean isCoordinateFromBottom) {
        SkillCoolDownConfig.isCoordinateFromBottom = isCoordinateFromBottom;
    }

    public static boolean isEnable(){
        return enable;
    }

    public static String replaceRenderText(Map<String, String> skillCoolDownMap) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = skillCoolDownMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            sb.append(format.replace("<skill>", next.getKey()).replace("<cooldown>", next.getValue()));
            if(iterator.hasNext()){
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static float getOffY(Window window){
        if(isCoordinateFromBottom){
            return window.getScaledHeight() - offY;
        }else {
            return offY;
        }
    }

    public static float getRealOffY(){
        return offY;
    }

    public static String getFormat() {
        return format.replace("§", "&");
    }

    public static String getSeparator() {
        return separator.replace("§", "&");
    }

    public static boolean isCoordinateFromBottom() {
        return isCoordinateFromBottom;
    }
}
