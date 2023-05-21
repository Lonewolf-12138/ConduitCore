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
import java.util.Map;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/18 23:20
 * @description TODO
 */

public class BuffConfig {

    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDirectory(), "conduitcore/buff.json");

    private static final Map<String ,Object> DEFAULT_CONFIG = new HashMap<>();

    private static boolean enable;
    private static float offX;
    private static float offY;
    private static String format;
    /**
     * 是否右对齐
     */
    private static boolean isRightAligned;
    /**
     * 是否向下延展
     */
    private static boolean isDescending;

    static {
        DEFAULT_CONFIG.put("enable", true);
        DEFAULT_CONFIG.put("offX", 0.95F);
        DEFAULT_CONFIG.put("offY", 0.05F);
        DEFAULT_CONFIG.put("format", "§f<value> §f<attr> §f(<duration>)");
        DEFAULT_CONFIG.put("isRightAligned", true);
        DEFAULT_CONFIG.put("isDescending", true);
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
        offX = (Float) getValueFromJson(configJson, "offX", Float.class, needModify);
        offY = (Float) getValueFromJson(configJson, "offY", Float.class, needModify);
        format = ((String) getValueFromJson(configJson, "format", String.class, needModify)).replace("&", "§");
        isRightAligned = (Boolean) getValueFromJson(configJson, "isRightAligned", Boolean.class, needModify);
        isDescending  = (Boolean) getValueFromJson(configJson, "isDescending", Boolean.class, needModify);
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
        configMap.put("offX", offX);
        configMap.put("offY", offY);
        configMap.put("format", format);
        configMap.put("isRightAligned ", isRightAligned);
        configMap.put("isDescending", isDescending);
        ConfigUtil.modifyJsonWithMap(CONFIG_FILE, configMap);
    }

    public static void setEnable(boolean enable) {
        BuffConfig.enable = enable;
    }

    public static void setOffX(float offX) {
        BuffConfig.offX = offX;
    }

    public static void setOffY(float offY) {
        BuffConfig.offY = offY;
    }

    public static void setFormat(String format) {
        BuffConfig.format = format.replace("&", "§");
    }

    public static void setIsRightAligned(boolean isRightAligned) {
        BuffConfig.isRightAligned = isRightAligned;
    }

    public static void setIsDescending(boolean isDescending) {
        BuffConfig.isDescending = isDescending;
    }

    public static boolean isEnable(){
        return enable;
    }

    public static String replaceRenderText(String attr, String value, String dur) {
        return format.replace("<attr>", attr)
                .replace("<duration>", dur)
                .replace("<value>", value);
    }

    public static float getOffX(Window window){
        return window.getScaledWidth() * offX;
    }

    public static float getOffY(Window window){
        return window.getScaledHeight() * offY;
    }

    public static float getRealOffX(){
        return offX;
    }

    public static float getRealOffY(){
        return offY;
    }

    public static String getFormat() {
        return format.replace("§", "&");
    }

    public static boolean isRightAligned() {
        return isRightAligned;
    }

    public static boolean isDescending() {
        return isDescending;
    }

}
