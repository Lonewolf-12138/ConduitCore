package me.lonewolf.conduitcore.render.gui.hud.buff;

/**
 * @author Lonewolf_12138(QQ1090001011)
 * @date 2023/5/18 23:57
 * @description TODO
 */

public class Buff {

    /**
     * 数值
     */
    public int value;
    /**
     * 持续时间
     */
    public long duration;

    public Buff(int value, long duration) {
        this.value = value;
        this.duration = duration;
    }

}
