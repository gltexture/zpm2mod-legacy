package ru.BouH_.notifications;

import net.minecraft.item.Item;

public interface INotification {
    Item getLogo();

    void drawWindow(int scaledWidth, int scaledHeight);
}
