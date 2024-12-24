package ru.BouH_.options;

import net.minecraft.util.MathHelper;
import ru.BouH_.Main;

public class SettingFloatBar extends SettingObject {
    private final double min;
    private final double max;
    private final double step;
    private double value;

    public SettingFloatBar(String name, Object... value) {
        super(name, value);
        this.value = (double) value[0];
        this.min = (double) value[1];
        this.max = (double) value[2];
        this.step = (double) value[3];
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getStep() {
        return this.step;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double normalizeValue(double p_148266_1_) {
        return MathHelper.clamp_double((this.snapToStepClamp(p_148266_1_) - this.getMin()) / (this.getMax() - this.getMin()), 0.0F, 1.0F);
    }

    public double denormalizeValue(double p_148262_1_) {
        return this.snapToStepClamp(this.getMin() + (this.getMax() - this.getMin()) * MathHelper.clamp_double(p_148262_1_, 0.0F, 1.0F));
    }

    public double snapToStepClamp(double p_148268_1_) {
        p_148268_1_ = this.snapToStep(p_148268_1_);
        return MathHelper.clamp_double(p_148268_1_, this.getMin(), this.getMax());
    }

    public double snapToStep(double p_148264_1_) {
        if (this.step > 0.0F) {
            if (this.step == 0.01f) {
                p_148264_1_ = this.step * p_148264_1_ / this.step;
            } else {
                p_148264_1_ = this.step * (double) Math.round(p_148264_1_ / this.step);
            }
        }
        return p_148264_1_;
    }

    @Override
    public void performSetting(Object... objects) {
        this.setValue((double) objects[0]);
        Main.settingsZp.saveOptions();
    }
}
